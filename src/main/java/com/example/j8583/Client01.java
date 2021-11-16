package com.example.j8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Client01 implements Runnable {

    private static final Log log = LogFactory.getLog(Client01.class);

    private static final String[] data = new String[] { "1234567890",
            "5432198765", "1020304050", "abcdefghij", "AbCdEfGhIj",
            "1a2b3c4d5e", "A1B2C3D4E5", "a0c0d0f0e0", "j5k4m3nh45" };
    private static final BigDecimal[] amounts = new BigDecimal[] {
            new BigDecimal("10"), new BigDecimal("20.50"),
            new BigDecimal("37.44") };
    private static MessageFactory mfact;
    private static ConcurrentHashMap<String, IsoMessage> pending = new ConcurrentHashMap<String, IsoMessage>();

    private Socket sock;
    private boolean done = false;

    public Client01(Socket socket) {
        sock = socket;
    }

    public void run() {
        byte[] lenbuf = new byte[2];
        try {
            // For high volume apps you will be better off only reading the
            // stream in one thread
            // and then using another thread to parse the buffers and process
            // the responses
            // Otherwise the network buffer might fill up and you can miss a
            // message.
            while (sock != null && sock.isConnected()) {
                sock.getInputStream().read(lenbuf);
                int size = ((lenbuf[0] & 0xff) << 8) | (lenbuf[1] & 0xff);
                byte[] buf = new byte[size];
                // We're not expecting ETX in this case
                if (sock.getInputStream().read(buf) == size) {
                    try {
                        // We'll use this header length as a reference.
                        // In practice, ISO headers for any message type are the
                        // same length.
                        String respHeader = mfact.getIsoHeader(0x200);
                        IsoMessage resp = mfact.parseMessage(buf,
                                respHeader == null ? 12 : respHeader.length());
                        log.debug(String.format("Read response %s conf %s: %s",
                                resp.getField(11), resp.getField(38),
                                new String(buf)));
                        pending.remove(resp.getField(11).toString());
                    } catch (ParseException ex) {
                        log.error("Parsing response", ex);
                    }
                } else {
                    pending.clear();
                    return;
                }
            }
        } catch (IOException ex) {
            if (done) {
                log.info(String.format(
                        "Socket closed because we're done (%d pending)",
                        pending.size()));
            } else {
                log.error(String.format("Reading responses, %d pending",
                        pending.size()), ex);
                try {
                    sock.close();
                } catch (IOException ex2) {
                }
                ;
            }
        } finally {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException ex) {
                }
                ;
            }
        }
    }

    protected void stop() {
        done = true;
        try {
            sock.close();
        } catch (IOException ex) {
            log.error("Couldn't close socket");
        }
        sock = null;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(System.currentTimeMillis());
        log.debug("Reading config");
        mfact = ConfigParser
                .createFromClasspathConfig("config/config.xml");
        mfact.setAssignDate(true);
        mfact.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System
                .currentTimeMillis() % 10000)));
        System.err.println("Connecting to server");
        Socket sock = new Socket("localhost", 9999);
        // Send 10 messages, then wait for the responses
        Client01 client = new Client01(sock);
        Thread reader = new Thread(client, "j8583-client");
        reader.start();
        for (int i = 0; i < 10; i++) {
            IsoMessage req = mfact.newMessage(0x200);
            req.setValue(4, amounts[rng.nextInt(amounts.length)],
                    IsoType.AMOUNT, 0);
            req.setValue(12, req.getObjectValue(7), IsoType.TIME, 0);
            req.setValue(13, req.getObjectValue(7), IsoType.DATE4, 0);
            req.setValue(15, req.getObjectValue(7), IsoType.DATE4, 0);
            req.setValue(17, req.getObjectValue(7), IsoType.DATE4, 0);
            req.setValue(37, System.currentTimeMillis() % 1000000,
                    IsoType.NUMERIC, 12);
            req.setValue(41, data[rng.nextInt(data.length)], IsoType.ALPHA, 16);
            req.setValue(48, data[rng.nextInt(data.length)], IsoType.LLLVAR, 0);
            pending.put(req.getField(11).toString(), req);
            System.err.println(String.format("Sending request %s", req.getField(11)));
            req.write(sock.getOutputStream(), 2);
        }
        log.debug("Waiting for responses");
        while (pending.size() > 0 && sock.isConnected()) {
            Thread.sleep(500);
        }
        client.stop();
        reader.interrupt();
        log.debug("DONE.");
    }

}
