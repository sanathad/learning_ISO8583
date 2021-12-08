package com.example.j8583;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    public static void main(String[] args) {
        //public String getUniqueStan() {
            int stanLength = 6;
            String sTime = ThreadLocalRandom.current().nextInt(999999) + "" + new Date().getTime();
            sTime = new StringBuilder(sTime).reverse().toString();
            StringBuilder stringBuilder = new StringBuilder(sTime.substring(0, stanLength));

            int randPosition = ThreadLocalRandom.current().nextInt(stanLength);
            char randNumber = (char)(ThreadLocalRandom.current().nextInt(10) + 48);
            stringBuilder.setCharAt(randPosition, randNumber);

            //return stringBuilder.toString();
        System.out.println(stringBuilder.toString());
        }
   // }

}
