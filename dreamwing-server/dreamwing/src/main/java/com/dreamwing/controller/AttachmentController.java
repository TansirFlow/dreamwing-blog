package com.dreamwing.controller;

import com.dreamwing.pojo.*;
import com.dreamwing.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attachment")
@Validated
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private MinioPojo minioPojo;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        System.out.println(file);
        String url = attachmentService.upload(minioPojo.getBucketName(), file);
        return Result.success(url);
    }

    @GetMapping("/{id}")
    public Result<Attachment> getById(@PathVariable Integer id){
        Attachment attachment=attachmentService.getById(id);
        return Result.success(attachment);
    }

    @PostMapping("/list")
    public Result<PageBean<Attachment>> list(@RequestBody AttachmentGetListDataDTO attachmentGetListDataDTO){
        PageBean<Attachment> attachmentList = attachmentService.getListByCondition(attachmentGetListDataDTO);
        return Result.success(attachmentList);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        attachmentService.delete(id);
        return Result.success();
    }

    @GetMapping("/typeList")
    public Result<List<String>> getAttachmentTypeList(){
        List<String> typeList=attachmentService.getAttachmentTypeList();
        return Result.success(typeList);
    }
}
