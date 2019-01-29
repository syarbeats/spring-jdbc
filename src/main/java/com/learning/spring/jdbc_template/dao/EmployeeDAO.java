package com.learning.spring.jdbc_template.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.learning.spring.jdbc_template.EmployeeMapper;
import com.learning.spring.jdbc_template.EmployeeResultSetExtractor;
import com.learning.spring.jdbc_template.entity.Employee;

public class EmployeeDAO {

	private JdbcTemplate jdbcTemplate;
	private Logger logger = Logger.getLogger(EmployeeDAO.class);
	private List<Employee> listEmployee;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int saveEmployee(Employee emp) {
		String query = "insert into employee values("+emp.getId()+",'"+emp.getName()+"',"+emp.getSalary()+")";
		logger.log(Level.INFO, "Query= "+query);
		return jdbcTemplate.update(query);
	}
	
	public int updateEmployee(Employee emp) {
		String query = "update employee set name='"+emp.getName()+"',salary="+emp.getSalary()+" where id="+emp.getId();
		logger.log(Level.INFO, "Query= "+query);
		return jdbcTemplate.update(query);
	}
	
	public int deleteEmployee(Employee emp) {
		String query="delete from employee where id="+emp.getId();
		logger.log(Level.INFO, "Query= "+query);
		return jdbcTemplate.update(query);
	}

	/*
	 * JDBC TEMPLATE ROW MAPPER
	 * */
	public List<Employee> getListEmployee() {
		return jdbcTemplate.query("select * from employee", new EmployeeMapper());
	}

	/*
	 * JDBC TEMPLATE RESULT SET EXTRACTOR
	 * */
	public List<Employee> getListEmployees(){
		String query = "select * from employee";
		return jdbcTemplate.query(query, new EmployeeResultSetExtractor());
	}
	
	/*
	 * JDBC TEMPLATE PREPARED STATEMENT
	 * */
	public List<Employee> getEmployeeData(int id) {
		String query = "select * from employee where id = ? ";
		return jdbcTemplate.query(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				
				preparedStatement.setInt(1, id);
				
			}
			
		}, new EmployeeMapper());
	}
}
