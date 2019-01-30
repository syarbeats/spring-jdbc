package com.learning.spring.jdbc_template.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.learning.spring.jdbc_template.EmployeeMapper;
import com.learning.spring.jdbc_template.EmployeeResultSetExtractor;
import com.learning.spring.jdbc_template.EmployeeStoreProcedure;
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
	
	/*
	 * SPRING JDBC INSERT
	 * **/
	public void addEmployee(int id, String name, int salary) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Employee");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("name", name);
		parameters.put("salary", salary);
		
		if(!isEmployeeExist(id)) {
			jdbcInsert.execute(parameters);
			System.out.println("Data name: "+ name + " has been inserted...");
		}else
		{
			System.out.println("Employee data is exist...");
		}
	}
	
	public int updateEmployee(Employee emp) {
		String query = "update employee set name='"+emp.getName()+"',salary="+emp.getSalary()+" where id="+emp.getId();
		logger.log(Level.INFO, "Query= "+query);
		return jdbcTemplate.update(query);
	}
	
	public boolean isEmployeeExist(int id) {
		
		try {
			if(getEmployeeData(id).get(0) != null) {
				return true;
			}
		}catch(Exception ex) {
			
		}finally {
			
		}
		
		return false;
	}
	
	/*
	 * SPRING JDBC - CALL MYSQL STORE PROCEDURE
	 * **/
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployee(){
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("getAllEmployee").returningResultSet("emp", new EmployeeMapper());        
		Map<String, Object> queryData = jdbcCall.execute();		
        
		return (List<Employee>) queryData.get("emp");
	}
	
	/*
	 * SPRING STOREDPROCEDUE CLASS
	 * **/
	public int getEmployeeCount(){
		
		EmployeeStoreProcedure storeProcedure= new EmployeeStoreProcedure(jdbcTemplate.getDataSource(), "getEmployeeCount"); 
		
		return storeProcedure.execute();
	}
	
	/*
	 * JDBC TEMPLATE NAMED PARAMETER
	 * **/
	public void updateSalary(int employeeId, int salary) {
		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id", employeeId);
		in.addValue("salary", salary);
		String query = "update employee set salary = :salary where id = :id";
		NamedParameterJdbcTemplate jdbcNamedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcNamedTemplate.update(query, in);
		System.out.println("Record updated with id="+employeeId);
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
