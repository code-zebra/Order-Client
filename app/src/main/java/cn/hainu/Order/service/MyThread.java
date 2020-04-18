package cn.hainu.Order.service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class MyThread extends Thread {
    private String path;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private int money;



    private boolean result = false;

    /**
     * 登录线程构造函数
     * @param path
     * @param username
     * @param password
     */
    public MyThread(String path, String username, String password)
    {
        this.path = path;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
//        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置连接超时时间
            httpURLConnection.setReadTimeout(3000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post

            String data = "name=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据
            result = (httpURLConnection.getResponseCode() == 200);

/*            // 测试获取json对象
            if (result) {
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
                // 读取响应
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                String line;
                String res = "";
                while ((line = reader.readLine()) != null) {
                    res += line;
                }
                reader.close();
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult() {
        return result;
    }
}