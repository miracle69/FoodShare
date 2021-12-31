package cn.itfxq.foods.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import cn.itfxq.foods.R;
import cn.itfxq.foods.adapter.ScCpAdapter;
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

public class MyScFoodActivity extends AppCompatActivity {

    ListView scLv;
    ScCpAdapter mScCpAdapter;
    ScFoodsHandler mScFoodsHandler;
    List<FoodEntity> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sc_food);
        initView();
        initData();
    }

    private void initView() {
        scLv = findViewById(R.id.sclistLv);
        mScCpAdapter = new ScCpAdapter(this);
        scLv.setAdapter(mScCpAdapter);
        mScFoodsHandler = new ScFoodsHandler();
    }


    private void initData() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("username", CommonUtils.getLoginUser(this).getUsername())
                .build();
        Request request = new Request.Builder()
                .url(ItFxqConstants.MYSCFOOD_URL)
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = ItFxqConstants.OK_STATUS;
                msg.obj = res;
                mScFoodsHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    class ScFoodsHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case ItFxqConstants.OK_STATUS:
                    if (msg.obj != null) {
                        String resultJson = (String) msg.obj;
                        //解析获取的JSON数据
                        foodList = RemoteDatas.INSTANCE.getFoodsList(resultJson);
                        mScCpAdapter.setData(foodList);
                        mScCpAdapter.notifyDataSetChanged();
                        CommonUtils.setListViewHeight(scLv);

                    }
                    break;

            }
        }
    }
}