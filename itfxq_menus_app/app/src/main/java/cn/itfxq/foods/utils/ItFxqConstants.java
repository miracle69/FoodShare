package cn.itfxq.foods.utils;

/**
 * 常量配置
 */
public class ItFxqConstants {

    public static String BASEURL = "http://10.254.81.127:80";

    //编码
    public static final String CHARSET = "utf-8";
    //推荐菜品
    public static final String TJCP_URL=BASEURL+"/frontfood/tjcp";
    //访问菜品地址
    public static final String FOOD_URL = BASEURL+"/frontfood/all";
    //通过id获取菜品
    public static final String GETFOODBYID_URL = BASEURL+"/frontfood/getFoodsDetailById";
    //收藏菜品
    public static final String SCFOOD_URL = BASEURL +"/frontfood/scfood";
    //取消收藏菜品
    public static final String CANCELSCFOOD_URL = BASEURL +"/frontfood/cancelScfood";


    //查询我的收藏
    public static final String MYSCFOOD_URL = BASEURL +"/frontfood/queryMyScFood";
    //查询排行榜
    public static final String RANK_URL = BASEURL + "/frontfood/queryRank";
    //登录地址
    public static final String LOGIN_URL = BASEURL+"/frontuser/login";
    //添加订单
    public static final String ORDER_URL = BASEURL+"/frontorder/add";

    //查询我的订单
    public static final String MYORDER_URL = BASEURL+"/frontorder/queryOrderByUsername";
    //注册用户
    public static final String REG_URL = BASEURL+"/frontuser/reg";

    //返回的状态
    public static final int OK_STATUS = 200;
    //错误的状态
    public static final int ERROR_STATUS = 500;
    //登录的Key
    public static final String LOGIN_USER_KEY = "loginUser";


}
