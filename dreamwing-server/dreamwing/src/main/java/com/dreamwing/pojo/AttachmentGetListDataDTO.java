package com.dreamwing.pojo;

import lombok.Data;

@Data
public class AttachmentGetListDataDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String attachmentType;
    private String orderRule;
}
