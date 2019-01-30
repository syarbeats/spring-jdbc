package com.learning.spring.jdbc_template;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.RowMapper;

import com.learning.spring.jdbc_template.entity.Employee;

public class EmployeeMapper implements RowMapper<Employee> {

	@SuppressWarnings("deprecation")
	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Employee emp = new Employee();
		emp.setId(rs.getInt("id"));
		emp.setName(rs.getString("name"));
		emp.setSalary(rs.getInt("salary"));
		//System.out.println("Working History: "+rs.getString("working_history"));
		emp.setWorking_history(rs.getString("working_history"));	
		return emp;
	}

}
