package cn.itfxq.foods.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.itfxq.foods.R;
import cn.itfxq.foods.activity.FoodDetailActivity;
import cn.itfxq.foods.entity.FoodEntity;
import cn.itfxq.foods.utils.CommonUtils;


public class TjCpAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodEntity> foodEntityList;
    public TjCpAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tjlist_item,
                    null);
            vh.tjcp_name = (TextView) convertView.findViewById(R.id.tjcp_name);
            vh.tjcp_type = (TextView) convertView.findViewById(R.id.tjcp_type);
            vh.tjcp_ycl = (TextView) convertView.findViewById(R.id.tjcp_ycl);
            vh.food_picIv = (ImageView) convertView.findViewById(R.id.tjcp_image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final FoodEntity bean = getItem(position);
        if (bean != null) {
            vh.tjcp_name.setText(bean.getFoodName());
            vh.tjcp_type.setText("类型:"+ CommonUtils.getFoodTypeStr(bean.getFoodType()));
            vh.tjcp_ycl.setText("材料:"+bean.getYcl());
            Glide.with(mContext)
                    .load(bean.getFoodPic())
                    .error(R.mipmap.ic_launcher)
                    .into(vh.food_picIv);

            vh.tjcp_name.setOnClickListener(new View.OnClickListener(){

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
        public TextView tjcp_name, tjcp_type, tjcp_ycl;
        public ImageView food_picIv;
    }
}
