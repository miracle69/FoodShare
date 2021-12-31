package cn.itfxq.foods.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import cn.itfxq.foods.R;
import cn.itfxq.foods.utils.CommonUtils;

/**
 * 我的基本信息
 */
public class MyBaseInfoActivity extends AppCompatActivity {

    TextView mybaseinfo_name,mybaseinfo_email,mybaseinfo_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base_info);
        initView();
        initData();
    }

    private void initView() {
        mybaseinfo_name = findViewById(R.id.mybaseinfo_name);
        mybaseinfo_email = findViewById(R.id.mybaseinfo_email);
        mybaseinfo_tel = findViewById(R.id.mybaseinfo_tel);
    }
    private void initData() {
        mybaseinfo_name.setText(CommonUtils.getLoginUser(this).getUsername());
        mybaseinfo_email.setText(CommonUtils.getLoginUser(this).getEmail());
        mybaseinfo_tel.setText(CommonUtils.getLoginUser(this).getTel());
    }
}