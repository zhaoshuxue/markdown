package com.zsx.md.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.zsx.md.service.NoteService;
import com.zsx.md.utils.HttpUtil;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NoteService noteService;

    @GetMapping("tree")
    public ResultData tree() {
        ResultData<List<TreeNode>> jsonData = new ResultData<>();

        String userId = getSessionUser(request);
        if (userId == null) {
            jsonData.setSuccess(false);
            jsonData.setMessage("未登录");
            return jsonData;
        }

//        ["host","connection","pragma","cache-control","accept","sec-fetch-dest","user","x-requested-with","user-agent","content-type","sec-fetch-site","sec-fetch-mode","referer","accept-encoding","accept-language","cookie"]

        List<TreeNode> list = noteService.getNoteListByUserId(Integer.valueOf(userId));

        jsonData.setSuccess(true);
        jsonData.setData(list);
        return jsonData;
    }

    @PostMapping("/save")
    public ResultData save(@RequestBody NoteVO note) {
        ResultData jsonData = new ResultData();
        logger.info(JSON.toJSONString(note));
        if (note.getId() == null) {
            String userId = getSessionUser(request);
            if (userId == null) {
                jsonData.setSuccess(false);
                jsonData.setMessage("未登录，保存失败");
                return jsonData;
            }
            note.setUserId(Integer.valueOf(userId));
            return noteService.saveNote(note);
        } else {
            return noteService.updateNote(note);
        }
//        return noteService.saveNote(note);
    }

    @GetMapping("/get/{id}")
    public ResultData<NoteVO> getNote(@PathVariable(value = "id") Integer id) {
        return noteService.getNote(id, true);
    }

    /**
     * 拖拽移动节点
     *
     * @param id     被移动的节点id
     * @param target 目标节点id
     * @param type   移动类型
     * @return
     */
    @PostMapping("/move")
    public ResultData moveNode(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "target") Integer target,
            @RequestParam(value = "type") Integer type
    ) {
        return noteService.moveNode(id, target, type);
    }
}
