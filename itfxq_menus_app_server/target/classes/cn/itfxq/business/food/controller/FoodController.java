package cn.itfxq.business.food.controller;

import cn.itfxq.auth.entity.Role;
import cn.itfxq.auth.entity.User;
import cn.itfxq.auth.query.UserQuery;
import cn.itfxq.auth.serivce.IRoleService;
import cn.itfxq.auth.serivce.IUserService;
import cn.itfxq.auth.util.CommonUtils;
import cn.itfxq.business.food.entity.Food;
import cn.itfxq.business.food.query.FoodQuery;
import cn.itfxq.business.food.service.IFoodService;
import cn.itfxq.common.page.PageList;
import cn.itfxq.common.result.ResultResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:
 * @Decription: FoodController
 * @Date: Created on 2021/12/16
 * @Email:
 */
@Controller
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private IFoodService foodService;

    @Value("${itfxq.upload.path}")
    private String uploadFoodPath;




    @GetMapping("/index")
    public String index(String menuid, Model model){
        model.addAttribute("menuid",menuid);

        //用户首页
        return "views/food/food_list";
    }



    @GetMapping("/listpage")
    @ResponseBody
    public PageList listpage(FoodQuery foodQuery){

        return  foodService.listpage(foodQuery);
    }

    //新增菜品
    @PostMapping("/addFood")
    @ResponseBody
    public int addFood(Food food){
        foodService.addFood(food);
        Integer foodId = Integer.parseInt(food.getId()+"");
        //添加成功返回菜品id
        return foodId;
    }

    @PostMapping("/editSaveFood")
    @ResponseBody
    public ResultResponse editSaveFood(Food food){
        try{
            foodService.editSaveFood(food);
            return ResultResponse.ok();
        }catch(Exception e) {
            e.printStackTrace();
            return ResultResponse.fail("修改失败");
        }

    }

    @RequestMapping(value="/uploadFoodPic", method= RequestMethod.POST)
    @ResponseBody
    public ResultResponse uploadFoodPic(HttpServletRequest req, Integer id, @RequestParam("file") MultipartFile file){
        try {
            if(file.isEmpty()){
                return new ResultResponse("文件为空");
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String uuidString = UUID.randomUUID().toString();
            String newFileName= uuidString + suffixName;

            String imgUploadPath = uploadFoodPath+"/food";
            File path = new File(imgUploadPath);
            if (!path.exists()) path.mkdirs();

            File savefile = new File(path,newFileName);
            if (!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();
            file.transferTo(savefile);

            //更新菜品表
            Food food = new Food();
            food.setId(Long.parseLong(id+""));
            food.setFoodPic(newFileName);
            foodService.updateFoodPic(food);

            return new ResultResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/showimg/{image_name}")
    public String showimg(@PathVariable("image_name") String image_name,HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 获得的系统的根目录
        File fileParent = new File(File.separator);
        // 获得/usr/CBeann目录
        System.out.println("读取头像:"+image_name);
        File file = null ;
        String os = System.getProperty("os.name");
        ServletOutputStream out = response.getOutputStream();
        try {
            if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
                String uploadFoodImgPath = uploadFoodPath+"/food";
                file = new File(uploadFoodImgPath +"\\"+ image_name);
            } else {  //linux 和mac
                file = new File(fileParent, uploadFoodPath.substring(1) +"/"+ image_name);
            }
            IOUtils.copy(new FileInputStream(file),out);
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    //删除菜品
    @GetMapping("/deleteFood")
    @ResponseBody
    public ResultResponse deleteFood(@RequestParam(required = true) Long id){
        ResultResponse ajaxResult = new ResultResponse();
        try {
            foodService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse("删除失败");
        }

        return ajaxResult;
    }






}
