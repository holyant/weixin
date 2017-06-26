package dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import dao.HelloDao;

public class HelloDaoImpl implements HelloDao{
	private JdbcTemplate jdbcTemplate;
	private SqlMapClient sqlMapClient;

	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void deleteEmp(int empno) throws Exception{
//		String sql = "delete from emp where empno = ?";
//		jdbcTemplate.update(sql, new Object[]{empno});
		try {
			this.sqlMapClient.delete("ibatisTest.deleteTest");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
	
}
