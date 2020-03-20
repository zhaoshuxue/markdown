package com.zsx.md.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
import com.zsx.md.utils.TreeUtil;
import com.zsx.md.vo.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

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
}
