package com.example.web01;


import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Log4j2
@SpringBootTest
class Web01ApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
		System.out.println("---");
	}

	@Test
	public void testConnection() throws SQLException {
//		// 드라이브 설정되어 있어야함.

		@Cleanup
		Connection con = dataSource.getConnection();

		log.info(con);

		//Assertions.assertNotNull(con);
	}

}
