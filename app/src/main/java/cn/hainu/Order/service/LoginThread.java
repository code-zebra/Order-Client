package cn.hainu.Order.service;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.hainu.Order.Util.HttpUtil;

public class LoginThread extends Thread {
    private String path;
    private String username;
    private String password;

    private String resp_json = "";

    private boolean result = false;

    /**
     * 登录线程构造函数
     * @param path
     * @param username
     * @param password
     */
    public LoginThread(String path, String username, String password)
    {
        this.path = path;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置连接超时时间
            httpURLConnection.setReadTimeout(3000);//设置读取超时时间
            httpURLConnection.setRequestMethod("POST");//设置请求方法,post
            httpURLConnection.addRequestProperty("Connection", "Keep-Alive");

            String data = "name=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");//设置数据
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
            httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
            httpURLConnection.setDoOutput(true);//允许输出
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));//写入数据
            result = (httpURLConnection.getResponseCode() == 200);

            // 测试获取json对象
            if (result) {
                /**
                 * 如果获取的code为200，则证明数据获取是正确的。
                 */
                InputStream is = httpURLConnection.getInputStream();
                resp_json = HttpUtil.readMyInputStream(is);
                System.out.println("LoginThread :  " + resp_json);

/*                *//**
                 * 子线程发送消息到主线程，并将获取的结果带到主线程，让主线程来更新UI。
                 *//*
                Message msg = new Message();
                msg.obj = result;
                msg.what = SUCCESS;*/
            }


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
