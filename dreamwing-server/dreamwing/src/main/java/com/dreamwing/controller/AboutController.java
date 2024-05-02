package com.dreamwing.controller;

import com.dreamwing.pojo.AboutVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
@Validated
public class AboutController {
    @Autowired
    private AboutService aboutService;
    @PostMapping("/add/{content}")
    public Result add(@PathVariable String content){
        aboutService.add(content);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AboutVO> getById(@PathVariable Integer id){
        AboutVO aboutVO=aboutService.getById(id);
        return Result.success(aboutVO);
    }

    @GetMapping("/list")
    public Result<List<AboutVO>> list(){
        List<AboutVO> aboutVOList=aboutService.list();
        return Result.success(aboutVOList);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        aboutService.delete(id);
        return Result.success();
    }
}
