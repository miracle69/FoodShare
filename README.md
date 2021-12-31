# FoodShare

# itfxq_menus_app
编译器：android studio
运行的安卓模拟器：夜神模拟器

需要修改的文件：ItFxConstants.java
修改的地方：
public static String BASEURL = "http://10.254.81.127:80";

改成自己的公网IP

# itfxq_menus_app_server
运行编译器：intellij idea
需要修改的部分：applicantion.properties
修改的部分：
# 图片存储路径配置
itfxq.upload.path=D://itfxq_menus_app_server/src/main/resource/static/upload/
itfxq.foodPic.path=http://10.254.81.127:80/food/showimg

改成自己的本地位置
改成自己的公网IP
