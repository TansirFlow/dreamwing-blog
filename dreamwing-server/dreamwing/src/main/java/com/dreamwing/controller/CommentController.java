package com.dreamwing.controller;

import com.dreamwing.pojo.CommentDTO;
import com.dreamwing.pojo.CommentVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/add")
    public Result add(@RequestBody CommentDTO commentDTO){
        commentService.add(commentDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<CommentVO> getById(@PathVariable Integer id){
        CommentVO commentVO= commentService.getById(id);
        return Result.success(commentVO);
    }

    @GetMapping("/list")
    public Result<List<CommentVO>> list(){
        List<CommentVO> commentVOList=commentService.list();
        return Result.success(commentVOList);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        commentService.delete(id);
        return Result.success();
    }

    @GetMapping("/getByArticleId/{articleId}")
    public Result<List<CommentVO>>getByArticleId(@PathVariable Integer articleId){
        List<CommentVO> commentVOList=commentService.getCommentByArticleId(articleId);
        return Result.success(commentVOList);
    }

    @GetMapping("/getByArticleIdForAdmin/{articleId}")
    public Result<List<CommentVO>>getByArticleIdForAdmin(@PathVariable Integer articleId){
        List<CommentVO> commentVOList=commentService.getByArticleIdForAdmin(articleId);
        return Result.success(commentVOList);
    }
}
