package com.zyh.ZyhG1.core;

import java.time.LocalDateTime;

public class NormalHelper {
    public static String GetCurrentTime() {
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }
}
