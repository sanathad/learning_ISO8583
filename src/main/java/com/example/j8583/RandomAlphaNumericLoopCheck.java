package com.example.j8583;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class RandomAlphaNumericLoopCheck {

    static final String AB = "0123456789";
    static SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) {

        int randomOccurance = 10000;
        //String arr[] = new String[0];
        // Creating an empty Set
        Set <String>s = new HashSet();
        s.add("ABC");
        s.add("ABC");

        for (int i=0; i<randomOccurance ; i++) {
            String s1 = randomString(4);
            /*
            String s2 = randomString( 3).toString();
            String s3 = randomString( 3).toString();
            String s4 = randomString( 3).toString();

            System.out.println(s1+s2+s3+s4);
            */
            //System.out.println(s1);
            s.add(s1);

        }
        //System.out.println(s.size());
        System.out.println(s);
        System.out.println(s.size());

    }
    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
