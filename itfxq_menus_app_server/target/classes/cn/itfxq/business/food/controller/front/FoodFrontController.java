package cn.itfxq.business.food.controller.front;

import cn.itfxq.business.food.entity.CollectionEntity;
import cn.itfxq.business.food.entity.Food;
import cn.itfxq.business.food.service.IFoodService;
import cn.itfxq.common.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: FoodFrontController
 * @author:
 * @email:
 * @date: created by 2021/12/10
 * @copyright:
 */
@Controller
@RequestMapping("/frontfood")
public class FoodFrontController {

    @Autowired
    private IFoodService foodService;


    @Value("${itfxq.foodPic.path}")
    private String foodPicRequestPath;

    //推荐
    @RequestMapping("/tjcp")
    @ResponseBody
    public List<Food> tjCp(){
        List<Food> foods = foodService.tjCp();

        foods =  foods.stream().map(food -> {
            food.setFoodPic(foodPicRequestPath+"/"+food.getFoodPic());
            return food;
        }).collect(Collectors.toList());

        return foods;
    }
    //所有
    @RequestMapping("/all")
    @ResponseBody
    public List<Food> queryAll(){
        List<Food> foods = foodService.queryAll();

        foods =  foods.stream().map(food -> {
            food.setFoodPic(foodPicRequestPath+"/"+food.getFoodPic());
            return food;
        }).collect(Collectors.toList());

        return foods;
    }
    //更新浏览量
    @RequestMapping("/getFoodsDetailById")
    @ResponseBody
    public Food getFoodsDetailById(Long id){
        Food food = foodService.getFoodsDetailById(id);
        food.setFoodPic(foodPicRequestPath+"/"+food.getFoodPic());
        return food;
    }

    //收藏
    @RequestMapping("/scfood")
    @ResponseBody
    public ResultResponse scfood(CollectionEntity collectionEntity){
        try {
            foodService.scfood(collectionEntity);
            return ResultResponse.ok();
        }catch (Exception e){
            return ResultResponse.fail("收藏失败!");
        }
    }
    /**
     * 取消收藏
     * @param collectionEntity
     * @return
     */
    @RequestMapping("/cancelScfood")
    @ResponseBody
    public ResultResponse cancelScfood(CollectionEntity collectionEntity){
        try {
            foodService.cancelScFood(collectionEntity);
            return ResultResponse.ok();
        }catch (Exception e){
            return ResultResponse.fail("取消收藏失败!");
        }
    }

    /**
     * 查询我的收藏
     * @param username
     * @return
     */
    @RequestMapping("/queryMyScFood")
    @ResponseBody
    public List<Food> queryMyScFood(String username){

        List<CollectionEntity> collectionEntities = foodService.queryMySc(username);
        List<Food> foods = new ArrayList<>();
        collectionEntities.forEach(collectionEntity -> {
            foods.add(collectionEntity.getFood());
        });
        return foods;

    }

    /**
     * 查询排行榜
     * @return
     */
    @RequestMapping("/queryRank")
    @ResponseBody
    public List<Food> queryRank(){

        List<Food> foods = foodService.queryRank();
        foods =  foods.stream().map(food -> {
            food.setFoodPic(foodPicRequestPath+"/"+food.getFoodPic());
            return food;
        }).collect(Collectors.toList());

        return foods;

    }

}
