package com.learning.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package_name:   com.learning.utils
 * user:           dongkui
 * date:           2020/5/27 0027
 * email:          ccie20079@126.com
 */
public class DateHelper {
    public static String getDateOfToday(){
        String result = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return  result;
    }
}
