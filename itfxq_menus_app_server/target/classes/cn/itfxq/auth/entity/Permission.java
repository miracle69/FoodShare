package cn.itfxq.auth.entity;

import lombok.Data;

/**
 * @Author:
 * @Decription:
 * @Date: Created on 2021/12/10
 * @Email:
 */
@Data
public class Permission {

    private Long id;
    private String name;
    private String title;
    private Long pid;
    private Long menuid;
}