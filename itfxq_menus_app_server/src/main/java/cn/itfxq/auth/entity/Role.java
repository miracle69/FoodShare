package cn.itfxq.auth.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:
 * @Decription:
 * @Date: Created on 2021/12/10
 * @Email:
 */
@Data
public class Role {
    private Long id;
    private String name;
    private String sn;
    private String desc;
    List<Permission> permissions = new ArrayList();
}
