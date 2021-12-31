package cn.itfxq.auth.query;

import cn.itfxq.common.query.BaseQuery;
import lombok.Data;

/**
 * @Author:
 * @Decription:
 * @Date: Created on 2021/12/12
 * @Email:
 */
@Data
public class UserQuery extends BaseQuery {


    private String username;

    private String email;

    private Long type;//1表示管理员 2表示老师用户

}
