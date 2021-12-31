package cn.itfxq.business.food.mapper;

import cn.itfxq.business.food.entity.CollectionEntity;
import cn.itfxq.business.food.entity.Food;
import cn.itfxq.common.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: FoodMapper
 * @author:
 * @email:
 * @date: created by 2021/12/19
 * @copyright:
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food> {

    /**
     * 新增菜品
     * @param food
     */
    @Insert("insert into t_foods(foodName,taste,price,foodType,createTime,ycl,zf,viewnum)" +
            " values(#{foodName},#{taste},#{price},#{foodType},#{createTime},#{ycl},#{zf},#{viewnum})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addFood(Food food);

    /**
     * 更新菜品图片
     * @param food
     */
    @Update("update t_foods set foodPic=#{foodPic} where id=#{id}")
    void updateFoodPic(Food food);

    /**
     * 修改菜品
     * @param food
     */
    @Update("update t_foods set foodName=#{foodName}," +
            "taste=#{taste}," +
            "price=#{price}," +
            "foodType=#{foodType},ycl=#{ycl},zf=#{zf},viewnum=#{viewnum}  " +
            "where id=#{id}")
    void editSaveFood(Food food);

    /**
     * 删除菜品
     * @param id
     */
    @Delete("delete from t_foods where id=#{id}")
    void deleteUser(Long id);

    @Select("select * from t_foods order by id desc limit 0,4")
    List<Food> tjCp();

    @Update("update t_foods set viewnum = viewnum+1 where id=#{id}")
    void updateViewNum(Long id);

    @Select("select * from t_foods where id=#{id}")
    Food getFoodsDetailById(Long id);

    @Insert("insert into t_collection(username,foodid,createtime) values(#{username},#{foodid},#{createtime})")
    void scfood(CollectionEntity collectionEntity);

    @Select("select * from t_collection where username=#{username} and foodid=#{foodid}")
    CollectionEntity findCollectionFood(CollectionEntity collectionEntity);

    @Delete("delete from t_collection where username=#{username} and foodid=#{foodid}")
    void cancelScFood(CollectionEntity collectionEntity);

    @Select("select * from t_collection where username=#{username}")
    List<CollectionEntity> queryMySc(String username);

    @Select("select * from t_foods order by viewnum desc limit 0,10")
    List<Food> queryRank();
}
