package com.example.j8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;

public class Example01 {

    public static void print(IsoMessage m) {
        System.out.printf("TYPE: %04x\n", m.getType());
        for (int i = 2; i < 128; i++) {
            if (m.hasField(i)) {
                System.out.printf("F %3d(%s): %s -> '%s'\n", i, m.getField(i)
                        .getType(), m.getObjectValue(i), m.getField(i)
                        .toString());
            }
        }
    }



    public static void main(String[] args) throws Exception{
        System.out.println("01");
        MessageFactory mfact = ConfigParser.createFromClasspathConfig("config/config.xml");
        System.out.println("02");
        mfact.setAssignDate(true);
        System.out.println("03");
        mfact.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System
                .currentTimeMillis() % 100000)));
        System.out.println("04");
        InputStream is = Example01.class.getClassLoader().getResourceAsStream("config/parse.txt");
        InputStreamReader isr = new InputStreamReader(is);
        LineNumberReader reader = new LineNumberReader(isr);

       /* LineNumberReader reader = new LineNumberReader(new InputStreamReader(
                Example01.class.getClassLoader().getResourceAsStream(
                        "config/parse.txt")));*/

        System.out.println("05");
        String line = reader.readLine();
        System.out.println("06");
        while (line != null && line.length() > 0) {
            System.out.println("07");
            IsoMessage m = mfact.parseMessage(line.getBytes(), 12);
            System.out.println("08");
            print(m);
            System.out.println("09");
            line = reader.readLine();
        }
        System.out.println("10");
        reader.close();
        System.out.println("11");

        // Create a new message
        System.err.println("NEW MESSAGE");
        IsoMessage m = mfact.newMessage(0x200);
        System.out.println("12");
        m.setBinary(true);
        System.out.println("13");
        m.setValue(4, new BigDecimal("501.25"), IsoType.AMOUNT, 0);
        m.setValue(12, new Date(), IsoType.TIME, 0);
        m.setValue(15, new Date(), IsoType.DATE4, 0);
        m.setValue(17, new Date(), IsoType.DATE_EXP, 0);
        m.setValue(37, 12345678, IsoType.NUMERIC, 12);
        m.setValue(41, "TEST-TERMINAL", IsoType.ALPHA, 16);
        System.out.println("14");
        FileOutputStream fout = new FileOutputStream("tmp/iso.bin");
        System.out.println("15");
        m.write(fout, 2);
        System.out.println("16");
        fout.close();
        System.out.println("17");
        print(m);
        System.out.println("18");
        System.err.println("PARSE BINARY FROM FILE");
        byte[] buf = new byte[2];
        System.out.println("19");
        //FileInputStream fin = new FileInputStream("tmp/iso.bin");
        System.out.println("20");
        //fin.read(buf);
        System.out.println("21");
        //int len = ((buf[0] & 0xff) << 4) | (buf[1] & 0xff);
        System.out.println("22");
        //buf = new byte[len];
        System.out.println("23");
        //fin.read(buf);
        System.out.println("24");
        //fin.close();
        System.out.println("25");
        mfact.setUseBinaryMessages(true);
        System.out.println("26");
        //m = mfact.parseMessage(buf, mfact.getIsoHeader(0x200).length());
        System.out.println("27");
        print(m);
        System.out.println("28");
    }

}
