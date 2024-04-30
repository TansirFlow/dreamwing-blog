package com.dreamwing.controller;

import com.dreamwing.constants.GlobalConstants;
import com.dreamwing.pojo.Article;
import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.pojo.User;
import com.dreamwing.service.ArticleService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.websocket.server.PathParam;
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
    public Result addArticle(@RequestBody Article article){
        System.out.println(article);
        Set<ConstraintViolation<Article>> addViolations = validator.validate(article, Article.AddArticleGroup.class);
        if (!addViolations.isEmpty()) {
            return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
        }
        articleService.add(article);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Integer id){
        Article article=articleService.getById(id);
        return Result.success(article);
    }


    @GetMapping
    public Result<List<ArticleVO>> getList(){
        List<ArticleVO> list=articleService.getList();
        return Result.success(list);
    }
}
