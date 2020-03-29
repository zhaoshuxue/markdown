package com.zsx.md.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.TODO;
import com.zsx.md.config.PropertiesConfig;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
import com.zsx.md.utils.FileUtil;
import com.zsx.md.utils.TreeUtil;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.ResultData;
import com.zsx.md.vo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class NoteServiceImpl implements NoteService {

    private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public List<TreeNode> getNoteListByUserId(Integer userId) {

        String sql = "select id, pid, types, title, summary, content, orders, create_person as createPerson, update_person as updatePerson from m_note where status = 0 and user_id = " + userId;

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        logger.info(JSON.toJSONString(list));

        List<Mnote> mnotes = JSONArray.parseArray(JSON.toJSONString(list), Mnote.class);

        logger.info(JSON.toJSONString(mnotes));

        List<TreeNode> treeNodes = TreeUtil.convert(mnotes);

        List<TreeNode> tree = TreeUtil.buildTree(treeNodes);

        tree = TreeUtil.sortTree(tree);

        logger.info(JSON.toJSONString(tree));

        return tree;
    }

    @Override
    public ResultData<NoteVO> getNote(Integer id, boolean getContent) {
        ResultData<NoteVO> resultData = new ResultData<>();

        String sql = "select id, pid, types, title, summary, content, orders, create_person as createPerson, update_person as updatePerson from m_note where id = ?";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);

        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            NoteVO mnote = JSONObject.parseObject(JSON.toJSONString(map), NoteVO.class);

            if (getContent) {
                String content = mnote.getContent();

                String text = FileUtil.readFile(propertiesConfig.getMdFilePath() + content);

                mnote.setText(text);
            }

            resultData.setSuccess(true);
            resultData.setData(mnote);
        }

        return resultData;
    }

    @Override
    public ResultData<String> saveNote(NoteVO noteVO) {

        String content = String.valueOf(System.currentTimeMillis()) + ".md";

        FileUtil.writeFile(noteVO.getText(), propertiesConfig.getMdFilePath() + content);

//        todo
        noteVO.setUserId(1);
        noteVO.setTypes(1);
        noteVO.setSummary("test");
        noteVO.setContent(content);
        noteVO.setOrders(new Random().nextInt(100));
        noteVO.setStatus(0);
        noteVO.setCreatePerson("admin");

        String sql = "INSERT INTO m_note (pid, user_id, types, title, summary, content, orders, status, create_person) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int update = jdbcTemplate.update(sql, noteVO.getPid(), noteVO.getUserId(), noteVO.getTypes(), noteVO.getTitle(), noteVO.getSummary(), noteVO.getContent(), noteVO.getOrders(), noteVO.getStatus(), noteVO.getCreatePerson());
        logger.info("update = {}", update);

        return ResultData.build(true, "保存成功", "");
    }

    @Override
    public ResultData<String> updateNote(NoteVO noteVO) {
        ResultData<NoteVO> resultData = this.getNote(noteVO.getId(), false);
        if (resultData.isSuccess()) {
            NoteVO data = resultData.getData();

            String content = data.getContent();
            FileUtil.writeFile(noteVO.getText(), propertiesConfig.getMdFilePath() + content);

//            TODO
            String sql = "UPDATE m_note SET title=?, content=?, update_person=? WHERE (id= ?)";
            int update = jdbcTemplate.update(sql, noteVO.getTitle(), content, noteVO.getUpdatePerson(), data.getId());
        } else {
            this.saveNote(noteVO);
        }
//        logger.info("update = {}", update);
        return ResultData.build(true, "保存成功", "");
    }


    /**
     * 移动
     *
     * @param id     被移动的节点id
     * @param target 目标节点id
     * @param type   移动类型， 1：成为目标子节点，2：成为前节点，3：成为后节点
     * @return
     */
    @Override
    public ResultData<String> moveNode(Integer id, Integer target, Integer type) {
        ResultData<String> resultData = new ResultData<>();
        if (id == null || target == null || type == null) {
            resultData.setSuccess(false);
            resultData.setMessage("参数错误");
            return resultData;
        }

        ResultData<NoteVO> noteResultData = this.getNote(id, false);
        NoteVO note = noteResultData.getData();

        if (type == 1) {
            // 子节点，直接修改pid即可
            String sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
            int update = jdbcTemplate.update(sql, target, 1, note.getUpdatePerson(), note.getId());

        } else if (type == 2) {
            // 成为前节点
            ResultData<NoteVO> targetResultData = this.getNote(target, false);
            NoteVO targetNode = targetResultData.getData();
            // 让目标及下面的节点的排序orders全部+1
            String sql = "UPDATE m_note SET orders=orders+1, update_person=? WHERE (pid= ? and orders >= ?)";
            jdbcTemplate.update(sql, note.getUpdatePerson(), targetNode.getPid(), targetNode.getOrders());

            // 接着 把 节点 替换 目标节点 的位置
            sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
            jdbcTemplate.update(sql, targetNode.getPid(), targetNode.getOrders(), note.getUpdatePerson(), note.getId());

        } else if (type == 3) {
            // 成为后节点
            ResultData<NoteVO> targetResultData = this.getNote(target, false);
            NoteVO targetNode = targetResultData.getData();

            // 让目标下面的节点的排序orders全部+1
            String sql = "UPDATE m_note SET orders=orders+1, update_person=? WHERE (pid= ? and orders > ?)";
            jdbcTemplate.update(sql, note.getUpdatePerson(), targetNode.getPid(), targetNode.getOrders());

            // 接着 把 节点 放到 目标节点 的排序下面的位置
            sql = "UPDATE m_note SET pid=?, orders=?, update_person=? WHERE (id= ?)";
            jdbcTemplate.update(sql, targetNode.getPid(), targetNode.getOrders() + 1, note.getUpdatePerson(), note.getId());
        } else {
            resultData.setSuccess(false);
            resultData.setMessage("参数type错误");
        }

        return resultData;
    }
}
