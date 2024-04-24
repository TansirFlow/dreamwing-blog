package com.dreamwing.exception;

import com.dreamwing.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        // 处理唯一键重复的逻辑
        String errorMessage="";
        if(e.getMessage().contains("UserMapper")){
            errorMessage = "该用户名已存在";
        }
        return Result.error(StringUtils.hasLength(errorMessage)?errorMessage:"操作失败");
    }
}
