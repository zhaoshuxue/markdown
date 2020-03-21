package com.zsx.md.controller;

import com.alibaba.fastjson.JSON;
import com.zsx.md.service.NoteService;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private static Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;


    @GetMapping("tree")
    public ResultData asdf() {
        List<TreeNode> list = noteService.getNoteListByUserId(1);

        ResultData<List<TreeNode>> jsonData = new ResultData<>();
        jsonData.setSuccess(true);
        jsonData.setData(list);

        return jsonData;
    }


    @PostMapping("/save")
    public void save(@RequestBody NoteVO note) {
        logger.info(JSON.toJSONString(note));

    }

}
