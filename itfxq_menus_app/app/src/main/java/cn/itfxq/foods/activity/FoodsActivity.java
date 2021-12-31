package cn.itfxq.foods.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.itfxq.foods.R;
import cn.itfxq.foods.adapter.FoodAdapter;
import cn.itfxq.foods.entity.FoodEntity;
import cn.itfxq.foods.utils.ItFxqConstants;
import cn.itfxq.foods.utils.RemoteDatas;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoodsActivity extends AppCompatActivity implements View.
        OnClickListener {

    private FoodEntity mFoodEntity;
    private TextView foodNameTv;
    private ImageView foodPicIv;
    private FoodAdapter mFoodAdapter;
    public static final int OKSTATUS = 1;
    private FoodsHandler mFoodsHandler;

    private ListView foodsListView;
    //总的食物
    private List<FoodEntity> foodList = new ArrayList<>();

    private ListView listLv;
    SearchView menuSV;

    private String foodType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        mFoodsHandler = new FoodsHandler();
        mFoodAdapter=new FoodAdapter(this);
        foodsListView = findViewById(R.id.listLv);
        menuSV = findViewById(R.id.menuSV);
        menuSV.setSubmitButtonEnabled(true);
        foodType = getIntent().getStringExtra("foodType");
        init();

    }

    private void init(){
        //初始化adapter
        initAdapter();
        //初始化数据
        initData();
        //初始化视图
        initView();
        //设置数据
        setData();
        initEvent();
    }

    private void initEvent() {
        menuSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                List tempList = new ArrayList();
                //实际应用中应该在该方法内执行实际查询，此处仅使用Toast显示用户输入的查询内容
                tempList = foodList.stream().filter(foodEntity -> foodEntity.getFoodName()
                        .contains(keyword))
                        .collect(Collectors.toList());
                mFoodAdapter.setData(tempList);
                mFoodAdapter.notifyDataSetChanged();
                return false;
            }

            //用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }

    private void initAdapter(){


        mFoodAdapter = new FoodAdapter(this);
        foodsListView.setAdapter(mFoodAdapter);

    }



    /**
     * 初始化界面控件
     */
    private void initView() {

        foodNameTv = findViewById(R.id.foodNameTv);
        foodPicIv =  findViewById(R.id.foodPicIv);


        listLv = (ListView) findViewById(R.id.listLv);



    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(ItFxqConstants.FOOD_URL).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = OKSTATUS;
                msg.obj = res;
                mFoodsHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 设置界面数据
     */
    private void setData() {
        if (mFoodEntity == null) return;
        foodNameTv.setText(mFoodEntity.getFoodName());
        Glide.with(this)
                .load(mFoodEntity.getFoodPic())
                .error(R.mipmap.ic_launcher)
                .into(foodPicIv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }






    class FoodsHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case OKSTATUS:
                    if (msg.obj != null) {
                        String resultJson = (String) msg.obj;
                        //解析获取的JSON数据
                         foodList = RemoteDatas.INSTANCE.getFoodsList(resultJson);
                         if("1".equals(foodType)){
                             foodList = foodList.stream().filter(foodEntity ->
                                 foodEntity.getFoodType().equals("1")
                             ).collect(Collectors.toList());
                         }else if("2".equals(foodType)){
                             foodList = foodList.stream().filter(foodEntity ->
                                     foodEntity.getFoodType().equals("2")
                             ).collect(Collectors.toList());
                         }else if("3".equals(foodType)){
                             foodList = foodList.stream().filter(foodEntity ->
                                     foodEntity.getFoodType().equals("3")
                             ).collect(Collectors.toList());
                         }else if("4".equals(foodType)){
                             foodList = foodList.stream().filter(foodEntity ->
                                     foodEntity.getFoodType().equals("5") ||  foodEntity.getFoodType().equals("6")
                             ).collect(Collectors.toList());
                         }
                        mFoodAdapter.setData(foodList);
                        mFoodAdapter.notifyDataSetChanged();

                    }
                    break;

            }
        }
    }




}