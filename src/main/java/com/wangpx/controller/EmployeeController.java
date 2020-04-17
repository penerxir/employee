package com.wangpx.controller;


import com.wangpx.exception.MyException;
import com.wangpx.pojo.Employee;
import com.wangpx.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    private static Logger logger=Logger.getLogger(EmployeeController.class);
    @RequestMapping(value = "/getEmp",method = RequestMethod.GET)
    public List<Employee> getEmp() throws MyException {
        List<Employee> emp = employeeService.getEmp();
        if (emp !=null) {
            //logger.debug(emp);
            return emp;
        }
        throw new MyException("没有找到员工信息");


    }

    @RequestMapping(value = "/ins" , method = RequestMethod.PUT)
    public int insEmp(Employee employee) throws MyException {
        int i = employeeService.insEmp(employee);
        if (i>0) {
            return i;
        }
        throw new MyException("添加失败");
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public int delEmp(Integer id) throws MyException {
        int i = employeeService.delEmp(id);
        if (i>0) {
            return i;
        }
        throw new MyException("删除失败");
    }

    @RequestMapping(value = "/upda",method = RequestMethod.POST)
    public int upEmp(Employee employee) throws MyException {
        int i = employeeService.update(employee);
        if (i>0) {
            return i;
        }
        throw new MyException("修改失败");
    }

}
