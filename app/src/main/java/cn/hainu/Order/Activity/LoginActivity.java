package cn.hainu.Order.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import cn.hainu.Order.R;
import cn.hainu.Order.Util.HttpUtil;
import cn.hainu.Order.Util.ShareUtils;
import cn.hainu.Order.service.LoginThread;
import cn.hainu.Order.service.UserService;

import static android.media.AudioRecord.SUCCESS;

/**
 * 系统登录活动
 */
public class LoginActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;

    private Button btn_login;
    private Button btn_register;

    public static String et_name;
    public static String et_phone;
    public static String et_email;
    public static int et_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews(); //初始化页面控件
        initEvents(); //初始化控件事件
    }

    //初始化页面控件
    private void initViews() {
        accountEdit = findViewById(R.id.name);
        passwordEdit = findViewById(R.id.password);

        btn_login = findViewById(R.id.login);
        btn_register = findViewById(R.id.register);
    }

    //初始化控件事件
    private void initEvents() {
        //给登录按钮设置点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                /*//判断用户名和密码是否正确
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) { //用户名和密码都不为空
                    //尝试登陆
                    tryToLogin(name, password);
                }else{
                    showDialog("用户名或密码不能为空！");
                }*/

                if (UserService.login(name,password)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            ShareUtils.setUsername(name);
                            // 根据获取response的JSON格式的数据，设置个人信息和钱包信息
                            String json = UserService.getResp_json();
                            System.out.println("LoginActivity :   " + json);
                            JSONAnalysis(json);

                            //登录成功，跳转到菜单展示的主页面
                            Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("用户名称或者密码错误，请重新输入！");
                        }
                    });
                }

            }
        });

        //给注册按钮设置点击事件
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                //结束当前界面
                LoginActivity.this.finish();
            }
        });
    }


    /**
     * 为了方便，定义一个弹框控件的函数
     * @param msg 要显示的提示信息
     */
    private void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * JSON解析方法Test
     */
    protected void JSONAnalysis(String string) {
        JSONObject object = null;
        try {
            object = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * 在你获取的string这个JSON对象中，提取你所需要的信息。
         */
        String name = object.optString("name");
        String phone = object.optString("phone");
        String email = object.optString("email");
        int money = object.optInt("money");

        System.out.println("name: " + name);
        System.out.println("phone: " + phone);
        System.out.println("email: " + email);
        System.out.println("money: " + money);

        ShareUtils.setEt_money(money);
        ShareUtils.setEt_email(email);
        ShareUtils.setEt_name(name);
        ShareUtils.setEt_phone(phone);
    }


}
