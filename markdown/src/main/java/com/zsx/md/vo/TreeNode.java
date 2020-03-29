package com.zsx.md.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode {

    private Integer id;

    private Integer pid;

    private String title;

    private Integer orders;

    private List<TreeNode> children;

}
