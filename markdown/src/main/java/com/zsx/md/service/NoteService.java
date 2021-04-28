package com.zsx.md.service;

import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.TreeNode;

import java.util.List;

public interface NoteService {

    List<TreeNode> getNoteListByUserId(Integer userId);

    /**
     * 获取文章
     *
     * @param id
     * @param getContent 是否获取内容
     * @return
     */
    ResultData<NoteVO> getNote(Integer id, boolean getContent);

    ResultData<String> saveNote(NoteVO noteVO);

    ResultData<String> updateNote(NoteVO noteVO);

    /**
     * 移动
     *
     * @param id     被移动的节点id
     * @param target 目标节点id
     * @param type   移动类型， 1：成为目标子节点，2：成为前节点，3：成为后节点
     * @return
     */
    ResultData<String> moveNode(Integer id, Integer target, Integer type);

}
