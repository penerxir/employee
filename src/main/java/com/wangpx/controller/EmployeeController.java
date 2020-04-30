package com.wangpx.controller;


import com.wangpx.exception.MyException;
import com.wangpx.pojo.EmpValidator;
import com.wangpx.pojo.Employee;
import com.wangpx.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("empValidator")
    private EmpValidator empValidator;




    private static Logger logger=Logger.getLogger(EmployeeController.class);
    @GetMapping("/getEmp")
    public List<Employee> getEmp() throws MyException {
        List<Employee> emp = employeeService.getEmp();

        if (emp.size() >0 ) {
            //logger.debug(emp);
            return emp;
        }
        throw new MyException("没有找到员工信息");
    }


    @GetMapping("/getEmpById/{id}")

    public Employee getEmpById(@PathVariable("id") Integer id,Errors errors) throws Exception {

        Employee employee = employeeService.getEmpById(id);



        empValidator.validate(employee,errors);

        if (errors.hasErrors()) {
            throw new MyException("查无此人");
        }


        return employee;
    }

    @PutMapping("/ins")
    public int insEmp(@Valid @ModelAttribute("employee") Employee employee) throws MyException {
        int i = employeeService.insEmp(employee);
        if (i>0) {
            return i;
        }
        throw new MyException("添加失败");
    }


    @DeleteMapping("/del/{id}")
    public int delEmp(@PathVariable("id") Integer id,Errors errors) throws Exception {
        int i = employeeService.delEmp(id);
        empValidator.validate(i,errors);

        if (errors.hasErrors()) {
            throw new MyException("删除失败");
        }
        return i;
    }

    @PostMapping("/update")
    public int upEmp( Employee employee) throws MyException {
        int i = employeeService.update(employee);
        if (i>0) {
            return i;
        }
        throw new MyException("修改失败");
    }

}
