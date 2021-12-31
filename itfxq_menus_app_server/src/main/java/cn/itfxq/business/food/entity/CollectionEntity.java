package cn.itfxq.business.food.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:
 * @email:
 * @date: created by 2021/12/16
 * @copyright:
 */
@Data
public class CollectionEntity {

    private Long id;
    private Food food;
    private Long foodid;
    private String username;
    private Date createtime;
}
