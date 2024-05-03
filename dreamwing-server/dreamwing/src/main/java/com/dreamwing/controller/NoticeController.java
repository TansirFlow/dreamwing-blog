package com.dreamwing.controller;

import com.dreamwing.pojo.NoticeDTO;
import com.dreamwing.pojo.NoticeVO;
import com.dreamwing.pojo.Result;
import com.dreamwing.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Validated
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/add")
    public Result add(@RequestBody NoticeDTO noticeDTO) {
        noticeService.add(noticeDTO);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody NoticeDTO noticeDTO) {
        noticeService.update(noticeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<NoticeVO> getById(@PathVariable Integer id) {
        NoticeVO noticeVO = noticeService.getById(id);
        return Result.success(noticeVO);
    }

    @GetMapping("/list")
    public Result<List<NoticeVO>> getAll(){
        List<NoticeVO> noticeVOList=noticeService.getAll();
        return Result.success(noticeVOList);
    }
}
