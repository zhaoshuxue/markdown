package com.zsx.md.controller;

import com.zsx.md.service.NoteService;
import com.zsx.md.vo.JsonData;
import com.zsx.md.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("tree")
    public JsonData asdf() {
        List<TreeNode> list = noteService.getNoteListByUserId(1);

        JsonData<List<TreeNode>> jsonData = new JsonData<>();
        jsonData.setSuccess(true);
        jsonData.setData(list);

        return jsonData;
    }

}
