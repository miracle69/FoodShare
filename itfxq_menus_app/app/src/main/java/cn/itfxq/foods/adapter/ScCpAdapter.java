package cn.itfxq.foods.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import cn.itfxq.foods.R;
import cn.itfxq.foods.activity.FoodDetailActivity;
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


public class ScCpAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodEntity> foodEntityList;
    private CancelScFoodsHandler mCancelScFoodsHandler;
    public ScCpAdapter(Context context) {
        this.mContext = context;
    }
    /**
     * 设置数据更新界面
     */
    public void setData(List<FoodEntity> foodEntityList) {
        this.foodEntityList = foodEntityList;
        notifyDataSetChanged();
    }
    /**
     * 获取Item的总数
     */
    @Override
    public int getCount() {
        return foodEntityList == null ? 0 : foodEntityList.size();
    }
    /**
     * 根据position得到对应Item的对象
     */
    @Override
    public FoodEntity getItem(int position) {
        return foodEntityList == null ? null : foodEntityList.get(position);
    }
    /**
     * 根据position得到对应Item的id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        mCancelScFoodsHandler = new CancelScFoodsHandler();
        //复用convertView
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sclist_item,
                    null);
            vh.sccp_name = (TextView) convertView.findViewById(R.id.sccp_name);
            vh.sccp_type = (TextView) convertView.findViewById(R.id.sccp_type);
            vh.sccp_ycl = (TextView) convertView.findViewById(R.id.sccp_ycl);
            vh.food_picIv = (ImageView) convertView.findViewById(R.id.sccp_image);
            vh.sccp_del = (ImageView) convertView.findViewById(R.id.sccp_del);
            vh.sccp_view = (ImageView) convertView.findViewById(R.id.sccp_view);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final FoodEntity bean = getItem(position);
        if (bean != null) {
            vh.sccp_name.setText(bean.getFoodName());
            vh.sccp_type.setText("类型:"+ CommonUtils.getFoodTypeStr(bean.getFoodType()));
            vh.sccp_ycl.setText("材料:"+bean.getYcl());
            Glide.with(mContext)
                    .load(bean.getFoodPic())
                    .error(R.mipmap.ic_launcher)
                    .into(vh.food_picIv);

            vh.sccp_name.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            FoodDetailActivity.class);
                    intent.putExtra("foodEntity", bean);
                    mContext.startActivity(intent);
                }
            });
            vh.sccp_view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            FoodDetailActivity.class);
                    intent.putExtra("foodEntity", bean);
                    mContext.startActivity(intent);
                }
            });

            vh.food_picIv.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            FoodDetailActivity.class);
                    intent.putExtra("foodEntity", bean);
                    mContext.startActivity(intent);
                }
            });

            vh.sccp_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodEntityList.remove(bean);

                    notifyDataSetChanged();
                    //删除收藏
                    deleteScFood(bean.getId());

                }
            });
        }
        return convertView;
    }

    private void deleteScFood(int foodid) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("foodid", foodid+"")
                .add("username", CommonUtils.getLoginUser(mContext).getUsername())
                .build();
        Request request = new Request.Builder()
                .url(ItFxqConstants.CANCELSCFOOD_URL)
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = ItFxqConstants.OK_STATUS;
                msg.obj = res;
                mCancelScFoodsHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

    class CancelScFoodsHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case ItFxqConstants.OK_STATUS:
                    //删除成功
                    Toast.makeText(mContext,"取消收藏成功",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }


    class ViewHolder {
        public TextView sccp_name, sccp_type, sccp_ycl;
        public ImageView food_picIv,sccp_del,sccp_view;
    }
}
