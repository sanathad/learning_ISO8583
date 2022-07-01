package com.example.j8583;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    public static void main(String[] args) {
        //public String getUniqueStan() {
            int stanLength = 6;
            String sTime = ThreadLocalRandom.current().nextInt(999999) + "" + new Date().getTime();
        System.out.println("sTime 01 "+sTime);
            sTime = new StringBuilder(sTime).reverse().toString();
        System.out.println("sTime 02 "+sTime);
            StringBuilder stringBuilder = new StringBuilder(sTime.substring(0, stanLength));
        System.out.println("stringBuilder "+stringBuilder);
            int randPosition = ThreadLocalRandom.current().nextInt(stanLength);
        System.out.println("randPosition "+randPosition);
            char randNumber = (char)(ThreadLocalRandom.current().nextInt(10) + 48);
        System.out.println("randNumber "+randNumber);
            stringBuilder.setCharAt(randPosition, randNumber);

            //return stringBuilder.toString();
        System.out.println(stringBuilder.toString());
        }
   // }

}
