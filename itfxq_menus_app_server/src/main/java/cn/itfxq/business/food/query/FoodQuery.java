package cn.itfxq.business.food.query;

import cn.itfxq.business.food.entity.Food;
import cn.itfxq.common.query.BaseQuery;
import lombok.Data;

/**
 * @description: FoodQuery
 * @author:
 * @email:
 * @date: created by 2021/12/17
 * @copyright:
 */
@Data
public class FoodQuery  extends BaseQuery {
    private String foodName;
}
