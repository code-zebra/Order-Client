package cn.hainu.Order.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.hainu.Order.Util.HttpUtil;

public class SettingThread extends Thread {
    private String path;
    private String username;
    private int money;
    private String phone;
    private String name;
    private String email;

    private String resp_json = "";

    private boolean result = false;

    /**
     * 登录线程构造函数
     * @param path
     * @param username
     * @param money
     * @param phone
     * @param name
     * @param email
     */
    public SettingThread(String path, String username, int money, String name, String phone, String email) {
        this.path = path;
        this.username = username;
        this.money = money;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置连接超时时间
            httpURLConnection.setReadTimeout(3000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post
            httpURLConnection.addRequestProperty("Connection", "Keep-Alive");

            //设置数据
            String data = "username=" + URLEncoder.encode(username, "utf-8")
                    + "&money=" + URLEncoder.encode(String.valueOf(money), "utf-8")
                    + "&name=" + URLEncoder.encode(name, "utf-8")
                    + "&phone=" + URLEncoder.encode(phone, "utf-8")
                    + "&email=" + URLEncoder.encode(email, "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //写入数据
            outputStream.write(data.getBytes("utf-8"));
            result = (httpURLConnection.getResponseCode() == 200);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult() {
        return result;
    }

    public String getResp_json() {
        return resp_json;
    }
}
