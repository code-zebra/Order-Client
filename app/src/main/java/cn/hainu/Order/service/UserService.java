package cn.hainu.Order.service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class UserService {

//    static String localhost = "10.217.192.49";
    // 模拟器把自己当成了localhost,以及127.0.0.1了，因此如果基于本地的web项目测试的话，必须修改IP为：10.0.2.2
    static String localhost = "10.0.2.2";
    static String resp_json;
    // 是否成功
    boolean result;
    public static boolean login(String name, String password) {
        LoginThread loginThread = new LoginThread("http://" + localhost + ":8080/service/login",
                name, password);
        try {
            loginThread.start();
            loginThread.join();
            resp_json = loginThread.getResp_json();
            System.out.println("UserService : " + resp_json);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loginThread.getResult();
    }

    public static boolean register(String name, String password) {
        MyThread myThread = new MyThread("http://" + localhost + ":8080/service/register",
                name, password);
        try {
            myThread.start();
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myThread.getResult();
    }

    public static boolean setting(String username, int money, String name, String phone, String email) {
        SettingThread settingThread = new SettingThread("http://" + localhost + ":8080/service/editInfo",
                username, money, name, phone, email);
        try {
            settingThread.start();
            settingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return settingThread.getResult();
    }

    public static String getResp_json() {
        return resp_json;
    }
}

