package com.dreamwing.pojo;

import lombok.Data;

@Data
public class ArticleGetListDataDTO {
    Integer pageNum;
    Integer pageSize;
    Integer categoryId;
    Integer statusId;
    Integer typeId;
    String orderRule;
}
