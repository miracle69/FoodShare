package cn.itfxq.foods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import cn.itfxq.foods.R;
import cn.itfxq.foods.entity.UserEntity;
import cn.itfxq.foods.utils.CommonUtils;


public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    //退出文本组件
    TextView logout_tv;
    TextView my_name_tv,my_scTv,my_rankTv,my_baseinfo,my_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);



        initView();
        initData();
        initEvent();
    }

    private void initView(){
        logout_tv = findViewById(R.id.logout);
        my_name_tv =  findViewById(R.id.my_name);
        my_scTv = findViewById(R.id.my_scTv);
        my_rankTv = findViewById(R.id.my_rankTv);
        my_baseinfo = findViewById(R.id.my_baseinfo);
        my_exit = findViewById(R.id.my_exit);

        my_scTv.setOnClickListener(this);
        my_rankTv.setOnClickListener(this);
        my_baseinfo.setOnClickListener(this);
        my_exit.setOnClickListener(this);
        logout_tv.setOnClickListener(this);
    }

    private void initData(){
        UserEntity loginUser = CommonUtils.getLoginUser(MyActivity.this);
        my_name_tv.setText(loginUser.getUsername());

    }

    private void initEvent(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_scTv:
                Intent intent=new Intent();
                intent.setClass(MyActivity.this,MyScFoodActivity.class);
                startActivity(intent);
                break;
            case R.id.my_rankTv:
                Intent intent1=new Intent();
                intent1.setClass(MyActivity.this,RankActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_baseinfo:
                //基本信息
                Intent intent2=new Intent();
                intent2.setClass(MyActivity.this,MyBaseInfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.my_exit:
                Intent intent3=new Intent();
                intent3.setClass(MyActivity.this,LoginActivity.class);
                startActivity(intent3);
                break;
            case R.id.logout:
                Intent intent4=new Intent();
                intent4.setClass(MyActivity.this,LoginActivity.class);
                startActivity(intent4);
                break;

        }
    }
}