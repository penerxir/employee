package com.wangpx.service.impl;

import com.wangpx.mapper.EmployeeMapper;
import com.wangpx.pojo.Employee;
import com.wangpx.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getEmpById(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    @Override
    public List<Employee> getEmp() {
        return employeeMapper.getEmp();
    }

    @Override
    public int insEmp(Employee employee) {
        return employeeMapper.insEmp(employee);
    }



    @Override
    public int delEmp(Integer id) {
        return employeeMapper.delEmp(id);
    }

    @Override
    public int update(Employee employee) {
        return employeeMapper.update(employee);
    }
}
