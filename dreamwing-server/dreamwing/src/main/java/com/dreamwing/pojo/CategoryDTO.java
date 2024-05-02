package com.dreamwing.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private Integer id;
    @NotEmpty
    private String categoryName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer createUser;
}
