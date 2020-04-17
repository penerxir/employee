package com.wangpx.service;

import com.wangpx.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmp();

    int insEmp(Employee employee);

    int delEmp(Integer id);

    int update(Employee employee);
}
