package com.zsx.md.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "m_note")
@DynamicInsert
@DynamicUpdate
public class Mnote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 设置主键为自增主键
    @Column(name = "id")
    private Integer id;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "types")
    private Integer types;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "orders")
    private Integer orders;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_person")
    private String createPerson;

    @Column(name = "update_person")
    private String updatePerson;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

}
