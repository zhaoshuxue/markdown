package com.zsx.md;

import com.alibaba.fastjson.JSON;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.TreeNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class UnitTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NoteService noteService;

    @Test
    void test3() {
        String sql = "select id, pid, types, title, summary, content, orders, create_person as createPerson, update_person as updatePerson from m_note where id = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, 1);

        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void test2() {
        NoteVO noteVO = new NoteVO();
        noteVO.setPid(1);
        noteVO.setUserId(1);

        noteVO.setTypes(1);
        noteVO.setTitle("标题22");
        noteVO.setSummary("标题22");
        noteVO.setContent("标题22");
        noteVO.setOrders(2);
        noteVO.setStatus(0);
        noteVO.setCreatePerson("赵");

        noteService.saveNote(noteVO);

    }

    @Test
    void test1() {
        List<TreeNode> mnotes = noteService.getNoteListByUserId(1);

        System.out.println(JSON.toJSONString(mnotes));
    }

    @Test
    void test() {
        System.out.println(jdbcTemplate);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_user");

        System.out.println(JSON.toJSONString(list));
    }

}
