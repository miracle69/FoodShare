package cn.itfxq.foods.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import cn.itfxq.foods.entity.UserEntity;

/**
 * @description: CommonUtils 工具类
 * @createTime 2020/12/13
 */
public class CommonUtils extends Activity {



    public static String formatTime(int length){
        Date date = new Date(length);
        //时间格式化工具
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String totalTime = sdf.format(date);
        return totalTime;
    }

    public static boolean isEmpty(String content){
        if(content == null || "".equals(content)){
            return true;
        }else{
            return false;
        }

    }

    //跳转方法--不传参数
    public static void navigateTo(Context from, Class<?> to){
        Intent intent=new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }
    //跳转方法--传递参数
    public static void navigateTo(Context from, Class<?> to, Bundle bundle){
        Intent intent=new Intent();
        intent.setClass(from, to);
        intent.putExtras(bundle);
        from.startActivity(intent);
    }

    //存储登录用户信息
    public static void storeLoginUser(Map userMap, Context context){
        SharedPreferences settings = context.getSharedPreferences(ItFxqConstants.LOGIN_USER_KEY, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username",(String)userMap.get("username"));
        editor.putString("password",(String)userMap.get("password"));
        editor.putString("tel",(String)userMap.get("tel"));
        editor.putString("email",(String)userMap.get("email"));
        editor.commit();
    }

    //读取用户的信息
    public static UserEntity getLoginUser(Context context){
        SharedPreferences settings = context.getSharedPreferences(ItFxqConstants.LOGIN_USER_KEY, 0);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(settings.getString("username",""));
        userEntity.setPassword(settings.getString("password",""));
        userEntity.setEmail(settings.getString("email",""));
        userEntity.setTel(settings.getString("tel",""));
        return userEntity;
    }

    //订单生成规则
    public static String getOrderNum(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        String randomNum = String.format("%04d", new Random().nextInt(9999));
        return dateStr+randomNum;
    }

    public static String getFoodTypeStr(String foodType){
        String result = "";
        switch (foodType){
            case "1" : result = "早餐";break;
            case "2" : result = "午餐";break;
            case "3" : result = "晚餐";break;
            case "4" : result = "小吃";break;
            case "5" : result = "水果";break;
            case "6" : result = "甜点";break;
            case "7" : result = "其他";break;
        }
        return result;
    }
    public static void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }






}
