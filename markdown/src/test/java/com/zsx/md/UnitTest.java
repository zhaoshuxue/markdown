package com.zsx.md;

import com.alibaba.fastjson.JSON;
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

	@Test
	void test() {
		System.out.println(jdbcTemplate);

		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_user");

		System.out.println(JSON.toJSONString(list));
	}

}
