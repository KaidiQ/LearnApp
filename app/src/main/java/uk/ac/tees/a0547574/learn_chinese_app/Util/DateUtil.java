package uk.ac.tees.a0547574.learn_chinese_app.Util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
        @SuppressLint("SimpleDateFormat")
        public static String getNowDateTime(String formatStr) {
            String format = formatStr;
            if (format==null || format.length()<=0) {
                format = "yyyyMMddHHmmss";
            }
            SimpleDateFormat s_format = new SimpleDateFormat(format);
            return s_format.format(new Date());
        }

        @SuppressLint("SimpleDateFormat")
        public static String getNowTime() {
            SimpleDateFormat s_format = new SimpleDateFormat("HH:mm:ss");
            return s_format.format(new Date());
        }

        @SuppressLint("SimpleDateFormat")
        public static String getNowTimeDetail() {
            SimpleDateFormat s_format = new SimpleDateFormat("HH:mm:ss.SSS");
            return s_format.format(new Date());
        }

    }
