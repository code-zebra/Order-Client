package cn.Order;

import org.junit.Test;

import cn.hainu.Order.Util.HttpCallbackListener;
import cn.hainu.Order.Util.HttpUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void TestHttpUtils() {
        String address = "http://10.0.2.2:8080/service/editInfo?username=123";
        HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                System.out.println("finish...");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("error...");
            }
        });

    }
}