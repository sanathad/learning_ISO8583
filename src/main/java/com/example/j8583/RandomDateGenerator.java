package com.example.j8583;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDateGenerator {

    public static void main(String[] args) {
        DateFormat date10Format = new SimpleDateFormat("MMddHHmmss");
        DateFormat date4Format = new SimpleDateFormat("MMdd");

        String s1 = date10Format.format(new Date());
        String s2 = date4Format.format(new Date());

        String normalString = "1209213716";



        System.out.println(s1);
        System.out.println(s2);

    }
}
