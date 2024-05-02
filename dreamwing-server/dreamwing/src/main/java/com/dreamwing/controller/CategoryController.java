package com.dreamwing.controller;

import com.dreamwing.pojo.CategoryDTO;
import com.dreamwing.pojo.CategoryVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.CategoryService;
import com.dreamwing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated CategoryDTO categoryDTO){
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<CategoryVO> getById(@PathVariable Integer id){
        CategoryVO categoryVO=categoryService.getById(id);
        return Result.success(categoryVO);
    }

    @GetMapping("/list")
    public Result<List<CategoryVO>> list(){
        List<CategoryVO> categoryVOList=categoryService.list();
        return Result.success(categoryVOList);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
