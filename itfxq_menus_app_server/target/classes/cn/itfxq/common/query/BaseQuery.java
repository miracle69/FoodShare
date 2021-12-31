package cn.itfxq.common.query;

import lombok.Data;

/**
 * @description: BaseQuery 公共的查询条件
 * @author:
 * @qq:
 * @官网:www.itfxq.cn
 * @datetime: 2021/12/18
 */
@Data
public class BaseQuery {
    //开始位置
    private Integer offset = 0;
    //每页显示条数
    private Integer pageSize = 10;
}
