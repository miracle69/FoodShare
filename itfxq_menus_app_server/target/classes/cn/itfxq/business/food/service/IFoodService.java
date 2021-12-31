package cn.itfxq.business.food.service;

import cn.itfxq.auth.entity.User;
import cn.itfxq.business.food.entity.CollectionEntity;
import cn.itfxq.business.food.entity.Food;
import cn.itfxq.common.service.IBaseService;

import java.util.List;

/**
 * 菜品service层接口
 */
public interface IFoodService extends IBaseService<Food> {
    /**
     * 添加菜品
     * @param food
     */
    void addFood(Food food);

    /**
     * 更新菜品图片
     * @param food
     */
    void updateFoodPic(Food food);

    /**
     * 修改菜品
     * @param food
     */
    void editSaveFood(Food food);

    /**
     * 删除菜品
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 推荐菜品
     * @return
     */
    List<Food> tjCp();

    /**
     * 获取菜品
     * @param id
     * @return
     */
    Food getFoodsDetailById(Long id);

    /**
     * 收藏项目
     * @param collectionEntity
     */
    void scfood(CollectionEntity collectionEntity);

    /**
     * 取消收藏
     * @param collectionEntity
     */
    void cancelScFood(CollectionEntity collectionEntity);
    /**
     * 查询我的收藏
     */
    List<CollectionEntity> queryMySc(String username);

    /**
     * 查询前10的排行榜
     * @return
     */
    List<Food> queryRank();
}
