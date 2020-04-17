package com.wangpx.mapper;

import com.wangpx.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface EmployeeMapper {

    @Select("select * from employee")
    List<Employee> getEmp();

    @Insert("insert into employee values(default,#{age},#{name},#{gender},#{address}) ")
    int insEmp(Employee employee);

    @Delete("delete * from where id= #{id}")
    int delEmp(@Param("id") Integer id);


    @Update("update employee set name=#{name},address=#{address},gender=#{gender} where id =#{id} ")
    int update(Employee employee);
}
