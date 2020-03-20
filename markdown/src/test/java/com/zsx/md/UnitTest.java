package com.zsx.md;

import com.alibaba.fastjson.JSON;
import com.zsx.md.entity.Mnote;
import com.zsx.md.service.NoteService;
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
    void test1() {
		List<Mnote> mnotes = noteService.getNoteListByUserId(1);

		System.out.println(JSON.toJSONString(mnotes));
    }

    @Test
    void test() {
        System.out.println(jdbcTemplate);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_user");

        System.out.println(JSON.toJSONString(list));
    }

}
