package com.zsx.md.service;

import com.zsx.md.entity.Mnote;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.TreeNode;

import java.util.List;

public interface NoteService {

    List<TreeNode> getNoteListByUserId(Integer userId);

    ResultData<String> saveNote(NoteVO noteVO);

    ResultData<String> updateNote(NoteVO noteVO);

}
