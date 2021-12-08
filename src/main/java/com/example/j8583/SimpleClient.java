package com.example.j8583;

import java.io.DataOutputStream;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        try{
            Socket s = new Socket("localhost",7777);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("############ Hello Socket Server ############");
            //dout.flush();
            //dout.close();
            //s.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
