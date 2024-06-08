package com.dreamwing.controller;

import com.dreamwing.constants.GlobalConstants;
import com.dreamwing.pojo.*;
import com.dreamwing.service.ArticleService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {
    private Validator validator;
    @Autowired
    private ArticleService articleService;

    public ArticleController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @PostMapping("/add")
    public Result addArticle(@RequestBody ArticleDTO articleDTO) {
        System.out.println(articleDTO);
        Set<ConstraintViolation<ArticleDTO>> addViolations = validator.validate(articleDTO, ArticleDTO.AddArticleGroup.class);
        if (!addViolations.isEmpty()) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        articleService.add(articleDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ArticleVO> getById(@PathVariable Integer id) {
        ArticleVO articleVO = articleService.getById(id);
        return Result.success(articleVO);
    }


    @GetMapping
    public Result<PageBean<ArticleVO>> getList(
            Integer pageNum,
            Integer pageSize
    ) {
        PageBean<ArticleVO> list = articleService.list(pageNum, pageSize);
        return Result.success(list);
    }

    @PostMapping("/list")
    public Result<PageBean<ArticleVO>> getListByCondition(@RequestBody ArticleGetListDataDTO articleGetListDataDTO) {
        PageBean<ArticleVO> list = articleService.getListByCondition(articleGetListDataDTO);
        return Result.success(list);
    }

    @PostMapping("/setTagList/{articleId}")
    public Result setTagList(@PathVariable Integer articleId, @RequestBody String[] tagList) {
        articleService.setTagList(articleId,tagList);
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.updateArticle(articleDTO);
        return Result.success();
    }


    // 未启用分页查询的垃圾
    //    @GetMapping("/getList")
    //    public Result<List<ArticleVO>> getList(){
    //        List<ArticleVO> list=articleService.getList();
    //        return Result.success(list);
    //    }


    @PostMapping("/delete/{id}")
    public Result deleteArticleById(@PathVariable Integer id){
        articleService.deleteArticleById(id);
        return Result.success();
    }

    @PostMapping("/delete/list")
    public Result deleteArticleByIdList(@RequestBody List<Integer> idList) {
        articleService.deleteArticleByIdList(idList);
        return Result.success();
    }
}
