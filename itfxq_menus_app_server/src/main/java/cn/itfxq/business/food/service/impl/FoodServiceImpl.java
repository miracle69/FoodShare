package cn.itfxq.business.food.service.impl;

import cn.itfxq.business.food.entity.CollectionEntity;
import cn.itfxq.business.food.entity.Food;
import cn.itfxq.business.food.mapper.FoodMapper;
import cn.itfxq.business.food.service.IFoodService;
import cn.itfxq.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Author:
 * @Decription: FoodServiceImpl
 * @Date: Created on 2021/12/19
 * @Email:
 */
@Service("foodService")
public class FoodServiceImpl extends BaseServiceImpl<Food> implements IFoodService {

    @Autowired
    private FoodMapper foodMapper;


    @Override
    public void addFood(Food food) {
        //设置创建时间
        food.setCreateTime(new Date());
        foodMapper.addFood(food);
    }

    /**
     * 更新菜品图片
     * @param food
     */
    @Override
    public void updateFoodPic(Food food) {
        foodMapper.updateFoodPic(food);
    }

    /**
     * 修改保存菜品
     * @param food
     */
    @Override
    public void editSaveFood(Food food) {
        foodMapper.editSaveFood(food);
    }

    /**
     * 删除菜品
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        foodMapper.deleteUser(id);
    }

    @Override
    public List<Food> tjCp() {
        return foodMapper.tjCp();
    }

    @Override
    public Food getFoodsDetailById(Long id) {
        //更新浏览量
        foodMapper.updateViewNum(id);
        Food food =  foodMapper.getFoodsDetailById(id);
        return food;
    }

    @Override
    public void scfood(CollectionEntity collectionEntity) {
        //如果已经收藏过 就不用再次插入
        CollectionEntity collectionFood = foodMapper.findCollectionFood(collectionEntity);
        if(collectionFood == null) {
            collectionEntity.setCreatetime(new Date());
            //收藏项目
            foodMapper.scfood(collectionEntity);
        }
    }

    /**
     * 取消收藏
     * @param collectionEntity
     */
    @Override
    public void cancelScFood(CollectionEntity collectionEntity) {
        foodMapper.cancelScFood(collectionEntity);
    }

    @Value("${itfxq.foodPic.path}")
    private String foodPicRequestPath;

    /**
     * 我的收藏
     * @param username
     * @return
     */
    @Override
    public List<CollectionEntity> queryMySc(String username) {
        List<CollectionEntity> collectionEntities = foodMapper.queryMySc(username);
        collectionEntities.forEach(collectionEntity -> {
            Food food = foodMapper.getFoodsDetailById(collectionEntity.getFoodid());
            food.setFoodPic(foodPicRequestPath+"/"+food.getFoodPic());
            collectionEntity.setFood(food);
        });
        return collectionEntities;
    }

    @Override
    public List<Food> queryRank() {
        return foodMapper.queryRank();
    }
}
