package cn.itfxq.business.food.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @description: Food
 * @author:
 * @email:
 * @date: created by 2021/12/17
 * @copyright:
 */
@Data
public class Food {
    //排行id
    private Long rankid;

    //菜品id
    private Long id;

    //菜品名称
    private String foodName;

    //口味
    private String taste;

    //价格
    private Double price;

    //购物车数量
    private Long count;

    //菜品图片
    private String foodPic;

    //菜品类型 1-早餐 2 午餐 3晚餐 4 小吃 5水果 6甜点
    private Long foodType;

    private String ycl;//原材料

    private String zf;//做法

    private Long viewnum;//浏览次数

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
