package com.learning.spring.jdbc_template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learning.spring.jdbc_template.dao.EmployeeDAO;
import com.learning.spring.jdbc_template.entity.Employee;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-config.xml");
        EmployeeDAO empDao= (EmployeeDAO) ctx.getBean("empDao");
        //int status =  empDao.saveEmployee(new Employee(102, "Amitabachan", 35000));
        //System.out.println("Status: "+status);
        
        /*
         * Query using Row Mapper
         * */
        List<Employee> employeeList = empDao.getListEmployee();
        
        for(Employee emp : employeeList) {
        	System.out.println("ID: "+emp.getId());
        	System.out.println("Name: "+emp.getName());
        	System.out.println("Salary: "+emp.getSalary());
        	System.out.println("");
        }
        
        List<Employee> emp = empDao.getEmployeeData(104);
        System.out.println("Name: "+ emp.get(0).getName());
        System.out.println("Working Hstory: "+ emp.get(0).getWorking_history());
        System.out.println("");
        
        /*
         * Query using ResultSetExtractor
         * */
        List<Employee> employeeList2 = empDao.getListEmployees();
        
        for(Employee emp2 : employeeList2) {
        	System.out.println("ID: "+emp2.getId());
        	System.out.println("Name: "+emp2.getName());
        	System.out.println("Salary: "+emp2.getSalary());
        	System.out.println("");
        }
        
        /*
         * Update data using NamedParameter
         * **/
        empDao.updateSalary(102, 45000);
        
        /*
         * Create data using JDBC INSERT
         * **/
        empDao.addEmployee(104, "Narendra", 37000);
        System.out.println("");
        
        /*
         * Call MYSQL STORE PROCEDURE
         * **/
        List<Employee> employeeList3 = empDao.getAllEmployee();
        System.out.println("Call MYSQL STORE PROCEDURE");
        System.out.println("==========================");
        
        for(Employee emp3 : employeeList3) {
        	System.out.println("ID: "+emp3.getId());
        	System.out.println("Name: "+emp3.getName());
        	System.out.println("Salary: "+emp3.getSalary());
        	System.out.println("");
        }
        
        System.out.println("Call MYSQL STORE PROCEDURE USING STORE PROCEDURE CLASS");
        System.out.println("======================================================");
        System.out.println("Employee Count: "+empDao.getEmployeeCount());
        System.out.println("");
        
        /*
         * Handling binary file using spring jdbc
         * **/
        System.out.println("Update Employee Photo");
        System.out.println("=====================");
        Path path = Paths.get("F:\\Data\\Rief.PNG");
        try {
			empDao.updateEmployeePhoto(102, Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("");
        
        /*
         * Handling Character Large Object using spring jdbc
         * **/
        System.out.println("Update Employee Working Summary");
        System.out.println("===============================");
        Path path2 = Paths.get("F:\\Data\\JanKoum.txt");
        try {
			empDao.updateEmployeWorkingHistory(104, new String(Files.readAllBytes(path2)));
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("");
        
        /*
         * BATCH UPDATE IN SPRING JDBC
         * **/
        System.out.println("Employee Batch Update");
        System.out.println("====================");
        Employee emp1 = new Employee();
        emp1.setId(102);
        emp1.setSalary(55000);
        
        Employee emp2 = new Employee();
        emp2.setId(103);
        emp2.setSalary(65000);
        
        Employee emp3 = new Employee();
        emp3.setId(104);
        emp3.setSalary(75000);
        
        List<Employee> empList = new ArrayList<Employee>();
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);
        
        empDao.batchUpdateEmployee(empList);
        
    }
}
