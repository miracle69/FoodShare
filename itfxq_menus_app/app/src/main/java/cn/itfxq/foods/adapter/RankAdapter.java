package cn.itfxq.foods.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RankAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodEntity> foodEntityList;
    public RankAdapter(Context context) {
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
        //复用convertView
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ranklist_item,
                    null);
            vh.rank_id= (TextView) convertView.findViewById(R.id.rank_id);
            vh.rank_name = (TextView) convertView.findViewById(R.id.rank_name);
            vh.food_picIv = (ImageView) convertView.findViewById(R.id.rank_image);
            vh.rank_viewnum = (TextView) convertView.findViewById(R.id.rank_viewnum);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final FoodEntity bean = getItem(position);
        if (bean != null) {
            //设置前三个背景
            if(getItemId(position+1)==1L){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.rank1);
                vh.rank_id.setBackground(drawable);
            }else if(getItemId(position+1)==2L){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.rank2);
                vh.rank_id.setBackground(drawable);
            }else if(getItemId(position+1)==3L){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.rank3);
                vh.rank_id.setBackground(drawable);
            }else{
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.rankother);
                vh.rank_id.setText((position+1)+"");
                vh.rank_id.setTextSize(30);
                vh.rank_id.setGravity(Gravity.CENTER);
                vh.rank_id.setBackground(drawable);
            }
            vh.rank_name.setText(bean.getFoodName());
            vh.rank_viewnum.setText(bean.getViewnum()+"");
            Glide.with(mContext)
                    .load(bean.getFoodPic())
                    .error(R.mipmap.ic_launcher)
                    .into(vh.food_picIv);

            vh.rank_name.setOnClickListener(new View.OnClickListener(){

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


        }
        return convertView;
    }


    class ViewHolder {
        public TextView rank_name, rank_viewnum,rank_id;
        public ImageView food_picIv;
    }
}
