package com.zsx.md.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class TreeNode {

    private Integer id;

    private Integer pid;

    private String title;

    private List<TreeNode> children;

}
