package cn.hainu.Order.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.hainu.Order.R;
import cn.hainu.Order.Util.ShareUtils;
import cn.hainu.Order.service.UserService;

/**
 * 个人账户信息
 */
public class MyMoneyActivity extends Activity {
    //账户余额显示文字控件
    private TextView myMoney;
    private Button btnSure;
    private Button btnsaveMoney;
    private int nowMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_money);
        myMoney = findViewById(R.id.my_money);
        btnSure = findViewById(R.id.btn_Sure);
        btnsaveMoney = findViewById(R.id.btn_SaveMoney);
        //读取本地文件中的账户余额
        nowMoney = ShareUtils.getEt_money();
        myMoney.setText(String.valueOf(nowMoney));

        //给确定按钮设置点击事件
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前界面，将自动返回系统上一级未被销毁的活动！比如结账活动或者主页面活动
                MyMoneyActivity.this.finish();
            }
        });

        //给充值按钮设置点击事件
        btnsaveMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String username = ShareUtils.getUsername();
            String name = ShareUtils.getEt_name();
            String email = ShareUtils.getEt_email();
            String phone = ShareUtils.getEt_phone();

            //给账户余额加200元
            nowMoney = nowMoney + 200;
            if (setting(username, nowMoney,name,phone,email)) {
                ShareUtils.setEt_money(nowMoney);
                myMoney.setText(String.valueOf(nowMoney));
            }
            else {
                // 保存失败
            }
            }
        });


    }

    private boolean setting(String username, int money, String name, String phone, String email) {
        if (UserService.setting(username, money, name, phone, email)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyMoneyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyMoneyActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return false;
    }
}
