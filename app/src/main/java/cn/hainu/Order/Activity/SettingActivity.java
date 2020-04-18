package cn.hainu.Order.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.hainu.Order.R;
import cn.hainu.Order.Util.ShareUtils;
import cn.hainu.Order.service.SettingThread;
import cn.hainu.Order.service.UserService;

/**
 * 设置
 */
public class SettingActivity extends Activity {
    private EditText etname; //姓名输入框
    private EditText etPhone; //手机号输入框
    private EditText etMail; //邮箱输入框
    private Button btnInputCompleted; //输入完成按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);
        etname = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMail = (EditText) findViewById(R.id.et_mail);
        btnInputCompleted = (Button) findViewById(R.id.btn_input_completed);
        //登录时默认自动在本地保存了一份客户信息，将其读取出来并显示
        etname.setText(ShareUtils.getEt_name());
        etPhone.setText(ShareUtils.getEt_phone());
        etMail.setText(ShareUtils.getEt_email());
        btnInputCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ShareUtils.getUsername();
                int money = ShareUtils.getEt_money();
                String name = etname.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etMail.getText().toString();


                // 判断是否保存成功
                if (setting(username, money, name, phone, email)) {
                    //保存用户新设置的客户个人信息
                    ShareUtils.setEt_name(name);
                    ShareUtils.setEt_phone(phone);
                    ShareUtils.setEt_email(email);

                } else {
                    // 保存失败
                }

                finish();
            }
        });
    }

    private boolean setting(String username, int money, String name, String phone, String email) {
        if (UserService.setting(username, money, name, phone, email)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return false;
    }
}
