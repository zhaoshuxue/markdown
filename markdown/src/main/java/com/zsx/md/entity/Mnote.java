package com.zsx.md.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Mnote {

    private Integer id;

    private Integer pid;

    private Integer userId;

    private Integer types;

    private String title;

    private String summary;

    private String content;

    private Integer orders;

    private Integer status;

    private String createPerson;

    private String updatePerson;

    private Date createdTime;

    private Date updatedTime;

}
