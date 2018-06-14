package kamo.co.za.layoutsmovementdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {

    public static String returnDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        try {
            Date date = new Date();
            String dateTime = dateFormat.format(date);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;        }
}}
