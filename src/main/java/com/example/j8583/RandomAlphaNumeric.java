package com.example.j8583;

import java.security.SecureRandom;

public class RandomAlphaNumeric {

    static final String AB = "ABCDEFGHIJKLM01234NOPQRSTUVWXYZabcdefghijklm56789nopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) {

        String s1 = randomString( 3).toString();
        String s2 = randomString( 3).toString();
        String s3 = randomString( 3).toString();
        String s4 = randomString( 3).toString();

        System.out.println(s1+s2+s3+s4);

    }
    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}
