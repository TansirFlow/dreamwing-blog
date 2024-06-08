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

    @PostMapping("/list/admin")
    public Result<PageBean<ArticleVO>> getListByConditionForAdmin(@RequestBody ArticleGetListDataDTO articleGetListDataDTO) {
        PageBean<ArticleVO> list = articleService.getListByConditionForAdmin(articleGetListDataDTO);
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

    @PostMapping("/update/admin")
    public Result updateArticleForAdmin(@RequestBody ArticleDTO articleDTO) {
        articleService.updateArticleForAdmin(articleDTO);
        return Result.success();
    }


    @PostMapping("/delete/{id}")
    public Result deleteArticleById(@PathVariable Integer id){
        articleService.deleteArticleById(id);
        return Result.success();
    }

    @PostMapping("/delete/admin/{id}")
    public Result deleteArticleByIdForAdmin(@PathVariable Integer id){
        articleService.deleteArticleByIdForAdmin(id);
        return Result.success();
    }

    @PostMapping("/delete/list")
    public Result deleteArticleByIdList(@RequestBody List<Integer> idList) {
        articleService.deleteArticleByIdList(idList);
        return Result.success();
    }

    @PostMapping("/delete/list/admin")
    public Result deleteArticleByIdListForAdmin(@RequestBody List<Integer> idList) {
        articleService.deleteArticleByIdListForAdmin(idList);
        return Result.success();
    }
}
