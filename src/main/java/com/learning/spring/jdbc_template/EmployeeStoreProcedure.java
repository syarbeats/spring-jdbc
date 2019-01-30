package com.learning.spring.jdbc_template;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EmployeeStoreProcedure extends StoredProcedure {
	
	public EmployeeStoreProcedure(DataSource dataSource, String storeproc) {
		super(dataSource, storeproc);
		declareParameter(new SqlOutParameter("out_empCount", Type.INT));
		compile();
	}

	public int execute(){
		
		Map<String, Object> employeeCount = super.execute();
		
		return (int) employeeCount.get("out_empCount");
	}

}
