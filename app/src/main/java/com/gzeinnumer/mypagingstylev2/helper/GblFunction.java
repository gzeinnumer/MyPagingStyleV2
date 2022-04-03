package com.gzeinnumer.mypagingstylev2.helper;

import android.annotation.SuppressLint;

import com.gzeinnumer.mypagingstylev2.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GblFunction {

    public static final String TAG = "GblFunction";

    public static boolean isDebugActive() {
        return BuildConfig.DEBUG;
    }

    public static String msgDebugOrRelease(String debug, String realese) {
        if (BuildConfig.DEBUG) {
            return debug;
        } else {
            return realese;
        }
    }

    public static String getDateIn(int count) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, count);
        Date date = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean checkBetween(String dateToCheck, String startDate, String endDate) {
        boolean res = false;
        SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd"); //22-05-2013
        SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd"); //22-05-2013
        try {
            Date requestDate = fmt2.parse(dateToCheck);
            Date fromDate = fmt1.parse(startDate);
            Date toDate = fmt1.parse(endDate);
            res = requestDate.compareTo(fromDate) >= 0 && requestDate.compareTo(toDate) <= 0;
        } catch (ParseException pex) {
            pex.printStackTrace();
        }
        return res;
    }
}
