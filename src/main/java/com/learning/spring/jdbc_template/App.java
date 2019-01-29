package com.learning.spring.jdbc_template;

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
        
        List<Employee> emp = empDao.getEmployeeData(102);
        System.out.println("Name: "+ emp.get(0).getName());
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
    }
}