package com.dreamwing.controller;

import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.pojo.PageBean;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.ArticleService;
import com.dreamwing.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Validated
public class PublicController {
    @Autowired
    private PublicService publicService;
    @GetMapping("/article")
    public Result<PageBean<ArticleVO>> getList(
            Integer pageNum,
            Integer pageSize
    ) {
        PageBean<ArticleVO> list = publicService.getArticleList(pageNum, pageSize);
        return Result.success(list);
    }

    @GetMapping("/category")
    public Result<List<CategoryVO>> getCategoryList(){
        List<CategoryVO> categoryVOList=publicService.getCategoryList();
        return Result.success(categoryVOList);
    }

    @GetMapping("/article/status")
    public Result<List<Map<String,String>>> getArticleStatusList(){
        List<Map<String,String>> mapList=new ArrayList<>();
        Map<String,String> map1=new HashMap<>();
        map1.put("id","1");
        map1.put("name","公开");
        Map<String,String> map2=new HashMap<>();
        map2.put("id","2");
        map2.put("name","私密");
        Map<String,String> map3=new HashMap<>();
        map3.put("id","3");
        map3.put("name","草稿");
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        return Result.success(mapList);
    }

    @GetMapping("/article/type")
    public Result<List<Map<String,String>>> getArticleTypeList(){
        List<Map<String,String>> mapList=new ArrayList<>();
        Map<String,String> map1=new HashMap<>();
        map1.put("id","1");
        map1.put("name","原创");
        Map<String,String> map2=new HashMap<>();
        map2.put("id","2");
        map2.put("name","转载");
        Map<String,String> map3=new HashMap<>();
        map3.put("id","3");
        map3.put("name","翻译");
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        return Result.success(mapList);
    }

    @GetMapping("/article/{id}")
    public Result<ArticleVO> getById(@PathVariable Integer id) {
        ArticleVO articleVO = publicService.getArticleById(id);
        return Result.success(articleVO);
    }
}
