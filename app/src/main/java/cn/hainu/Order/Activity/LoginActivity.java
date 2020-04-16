package cn.hainu.Order.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.hainu.Order.R;
import cn.hainu.Order.Util.ShareUtils;
import cn.hainu.Order.service.UserService;

/**
 * 系统登录活动
 */
public class LoginActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews(); //初始化页面控件
        initEvents(); //初始化控件事件
    }

    //初始化页面控件
    private void initViews() {
        accountEdit = (EditText) findViewById(R.id.name);
        passwordEdit = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);
        btn_register=(Button) findViewById(R.id.register);
    }

    //初始化控件事件
    private void initEvents() {
        //给登录按钮设置点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = accountEdit.getText().toString();
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
     * 进行登录请求的数据库查询
     * @param username 用户名
     * @param password 密码
     */
    private void tryToLogin(String username, String password) {
        //获取数据库中该用户名对应的密码
        String realPassword = ShareUtils.getString(this, username, "");
        //对数据库查询结果进行判断和处理
        if (password.equals(realPassword)) {
            //给用户的钱包余额设定金额
            ShareUtils.putInt(this, "money", 300);
            ShareUtils.putString(this, "user_true_name", "黄杰峰");
            ShareUtils.putString(this, "user_true_phone", "12345678910");
            ShareUtils.putString(this, "user_true_mail",  "123456789@qq.com");
            //登录成功，跳转到菜单展示的主页面
            Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
            startActivity(intent);
            finish();
        } else {
            showDialog("用户名称或者密码错误，请重新输入！");
        }
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
}
