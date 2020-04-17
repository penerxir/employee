package com.wangpx.controller;


import com.wangpx.pojo.Employee;
import com.wangpx.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Employee> emp = employeeService.getEmp();
        if (emp !=null) {
            return emp;
        }
        throw new RuntimeException("没有找到员工信息");


    }

    @RequestMapping(value = "/ins" , method = RequestMethod.PUT)
    public int insEmp(Employee employee) {
        int i = employeeService.insEmp(employee);
        if (i>0) {
            return i;
        }
        throw new RuntimeException("添加失败");
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public int delEmp(Integer id) {
        int i = employeeService.delEmp(id);
        if (i>0) {
            return i;
        }
        throw new RuntimeException("删除失败");
    }

    @RequestMapping(value = "/upda",method = RequestMethod.POST)
    public int upEmp(Employee employee) {
        int i = employeeService.update(employee);
        if (i>0) {
            return i;
        }
        throw new RuntimeException("修改失败");
    }

}
