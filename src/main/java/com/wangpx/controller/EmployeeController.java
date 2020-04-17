package com.wangpx.controller;


import com.wangpx.pojo.Employee;
import com.wangpx.service.EmployeeService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(value = "/getEmp",method = RequestMethod.GET)
    public List<Employee> getEmp() {

        return employeeService.getEmp();
    }

    @RequestMapping(value = "/ins" , method = RequestMethod.PUT)
    public int insEmp(Employee employee) {
        return employeeService.insEmp(employee);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public int delEmp(Integer id) {
        return employeeService.delEmp(id);
    }

    @RequestMapping(value = "/upda",method = RequestMethod.POST)
    public int upEmp(Employee employee) {
        return employeeService.update(employee);
    }
}
