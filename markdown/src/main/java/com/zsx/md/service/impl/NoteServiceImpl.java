package com.zsx.md.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

        logger.info(JSON.toJSONString(tree));


        return tree;
    }

    @Override
    public ResultData<NoteVO> getNote(Integer id) {
        ResultData<NoteVO> resultData = new ResultData<>();

        String sql = "select id, pid, types, title, summary, content, orders, create_person as createPerson, update_person as updatePerson from m_note where id = ?";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);

        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            NoteVO mnote = JSONObject.parseObject(JSON.toJSONString(map), NoteVO.class);

            String content = mnote.getContent();

            String text = FileUtil.readFile(propertiesConfig.getMdFilePath() + content);

            mnote.setText(text);

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
        return null;
    }

    @Override
    public ResultData<String> updateNote(NoteVO noteVO) {
        String sql = "UPDATE m_note SET pid=?, user_id=?, types=?, title=?, summary=?, content=?, orders=?, status=?, update_person=? WHERE (id= ?)";
        int update = jdbcTemplate.update(sql, noteVO.getPid(), noteVO.getUserId(), noteVO.getTypes(), noteVO.getTitle(), noteVO.getSummary(), noteVO.getContent(), noteVO.getOrders(), noteVO.getStatus(), noteVO.getUpdatePerson(), noteVO.getId());
        logger.info("update = {}", update);
        return null;

    }
}
