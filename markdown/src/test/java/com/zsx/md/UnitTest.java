package com.zsx.md;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.zsx.md.config.PropertiesConfig;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
import com.zsx.md.vo.NoteVO;
import com.zsx.md.vo.TreeNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UnitTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NoteService noteService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Test
    void test4() {

    }

    @Test
    void test3() {
        String sql = "select id, pid, types, title, summary, content, orders, create_person as createPerson, update_person as updatePerson from m_note where id = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, 1);

        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);

            NoteVO mnote = JSONObject.parseObject(JSON.toJSONString(map), NoteVO.class);

            mnote.setOrders(111);

            mnote.setUserId(100);

            mnote.setStatus(0);

            noteService.updateNote(mnote);

        }

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
