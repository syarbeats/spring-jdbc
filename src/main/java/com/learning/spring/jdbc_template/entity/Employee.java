package com.learning.spring.jdbc_template.entity;

import java.util.List;

public class Employee {
	
	private int id;
	private String name;
	private int salary;
	private List<Employee> listEmployee;
	private String working_history;
	
	public Employee() {
		
	}
	
	public Employee(int id, String name, int salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}

	public List<Employee> getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}

	public String getWorking_history() {
		return working_history;
	}

	public void setWorking_history(String working_history) {
		this.working_history = working_history;
	}
	
}
