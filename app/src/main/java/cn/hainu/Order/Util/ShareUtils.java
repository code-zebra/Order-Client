package cn.hainu.Order.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * SharedPreferences工具类
 */
public class ShareUtils {

    private static String username;



    private static String et_name;
    private static String et_phone;
    private static String et_email;
    private static int et_money;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ShareUtils.username = username;
    }

    public static String getEt_name() {
        return et_name;
    }

    public static void setEt_name(String et_name) {
        ShareUtils.et_name = et_name;
    }

    public static String getEt_phone() {
        return et_phone;
    }

    public static void setEt_phone(String et_phone) {
        ShareUtils.et_phone = et_phone;
    }

    public static String getEt_email() {
        return et_email;
    }

    public static void setEt_email(String et_email) {
        ShareUtils.et_email = et_email;
    }

    public static int getEt_money() {
        return et_money;
    }

    public static void setEt_money(int et_money) {
        ShareUtils.et_money = et_money;
    }


}
