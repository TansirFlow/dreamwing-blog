package com.dreamwing.controller;

import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.TagVO;
import com.dreamwing.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/add/{name}")
    public Result add(@PathVariable String name){
        tagService.add(name);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<TagVO> getById(@PathVariable Integer id){
        TagVO tagVO=tagService.getById(id);
        return Result.success(tagVO);
    }

    @GetMapping("/list")
    public Result<List<TagVO>> list(){
        List<TagVO> tagVOList=tagService.list();
        return Result.success(tagVOList);
    }
}
