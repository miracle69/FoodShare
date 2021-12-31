package cn.itfxq.foods.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import cn.itfxq.foods.R;
import cn.itfxq.foods.adapter.TjCpAdapter;
import cn.itfxq.foods.entity.FoodEntity;
import cn.itfxq.foods.utils.CommonUtils;
import cn.itfxq.foods.utils.ItFxqConstants;
import cn.itfxq.foods.utils.RemoteDatas;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    PopupWindow popupWindow;//定义popupWindow
    Button myMenuBtn;//我的菜单
    ImageView leftMenuIv;
    TextView main_mycenter,main_rank,main_mysc,f1Tv,f2Tv,f3Tv,f4Tv,f5Tv;
    ListView tjListView;
    TjCpAdapter mTjCpAdapter;
    List<FoodEntity> mtjFoodEntityList;
    MainHandler mMainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initEvent();
    }

    private void initDatas() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(ItFxqConstants.TJCP_URL)
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        // 开启异步线程访问网络
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = ItFxqConstants.OK_STATUS;
                msg.obj = res;
                mMainHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public void initView() {

        leftMenuIv = findViewById(R.id.leftMenuIv);
        main_mycenter = findViewById(R.id.main_mycenter);
        main_rank = findViewById(R.id.main_rank);
        main_mysc = findViewById(R.id.main_mysc);
        f1Tv=findViewById(R.id.f1tv);
        f2Tv=findViewById(R.id.f2tv);
        f3Tv=findViewById(R.id.f3tv);
        f4Tv=findViewById(R.id.f4tv);
        f5Tv=findViewById(R.id.f5tv);
        tjListView = findViewById(R.id.tjListViewId);
        mTjCpAdapter = new TjCpAdapter(this);
        mMainHandler = new MainHandler();
    }

    public void initEvent() {

        leftMenuIv.setOnClickListener(this);
        main_mycenter.setOnClickListener(this);
        main_rank.setOnClickListener(this);
        main_mysc.setOnClickListener(this);
        f1Tv.setOnClickListener(this);
        f2Tv.setOnClickListener(this);
        f3Tv.setOnClickListener(this);
        f4Tv.setOnClickListener(this);
        f5Tv.setOnClickListener(this);


    }

    //菜单按钮的单机事件
    public void OnMenu(View v){
        //获取自定义菜单的布局文件
        View popupWindow_view=getLayoutInflater().inflate(R.layout.menu,null,false);
        //创建popupWindow实例，设置菜单的宽度和高度
        popupWindow=new PopupWindow(popupWindow_view, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        //设置菜单显示在按钮的下面
        popupWindow.showAsDropDown(findViewById(R.id.btn_menu),0,0);
        myMenuBtn= popupWindow_view.findViewById(R.id.myMenuBtn);

        //单机其它位置隐藏菜单
        popupWindow_view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                //如果菜单存在并且处于显示状态
                if (popupWindow!=null&&popupWindow.isShowing()){
                    popupWindow.dismiss();//关闭菜单
                    popupWindow=null;
                }
                return false;
            }
        });
        myMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtils.navigateTo(MainActivity.this,MyActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,FoodsActivity.class);
        DrawerLayout drawerLayout = findViewById(R.id.DrawerLayout);
        View leftlayout = findViewById(R.id.left_layout);
        drawerLayout.setScrimColor(0x00ffffff);
        Bundle bundle = new Bundle();


        switch (v.getId()) {

            case R.id.leftMenuIv:
                drawerLayout.openDrawer(leftlayout);
                break;
            case R.id.main_mycenter:
                //个人中心
                drawerLayout.closeDrawer(leftlayout);
                CommonUtils.navigateTo(MainActivity.this,MyActivity.class);
                break;
            case R.id.main_mysc:
                //我的收藏
                drawerLayout.closeDrawer(leftlayout);
                CommonUtils.navigateTo(MainActivity.this,MyScFoodActivity.class);
                break;
            case R.id.main_rank:
                //我的排行榜
                drawerLayout.closeDrawer(leftlayout);
                CommonUtils.navigateTo(MainActivity.this,RankActivity.class);
                break;
            case R.id.f1tv:

                bundle.putString("foodType", "1");
                //早餐
                CommonUtils.navigateTo(MainActivity.this,FoodsActivity.class,bundle);

                break;
            case R.id.f2tv:
                bundle.putString("foodType", "2");
                //午餐
                CommonUtils.navigateTo(MainActivity.this,FoodsActivity.class,bundle);
                break;
            case R.id.f3tv:
                bundle.putString("foodType", "3");
                //晚餐
                CommonUtils.navigateTo(MainActivity.this,FoodsActivity.class,bundle);
                break;
            case R.id.f4tv:
                bundle.putString("foodType", "4");
                //水果和甜点
                CommonUtils.navigateTo(MainActivity.this,FoodsActivity.class,bundle);
                break;
            case R.id.f5tv:
                bundle.putString("foodType", "5");
                //全部
                CommonUtils.navigateTo(MainActivity.this,FoodsActivity.class,bundle);
                break;

        }
    }

    class MainHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case ItFxqConstants.OK_STATUS:
                    //解析获取的JSON数据
                    Gson gson = new Gson();
                    //通过反射得到type对象
                    Type listType = new TypeToken<Map>() {
                    }.getType();

                      String resultJson = (String) msg.obj;
                       //解析获取的JSON数据
                       mtjFoodEntityList = RemoteDatas.INSTANCE.getFoodsList(resultJson);
                        //创建适配器对象
                        mTjCpAdapter = new TjCpAdapter(MainActivity.this);
                        mTjCpAdapter.setData(mtjFoodEntityList);
                        tjListView.setAdapter(mTjCpAdapter);
                        CommonUtils.setListViewHeight(tjListView);
                        //数据源变化，提示适配器更新
                        mTjCpAdapter.notifyDataSetChanged();


                    break;
                case ItFxqConstants.ERROR_STATUS:
                    Toast.makeText(MainActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}