package com.wangpx.pojo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Repository("empValidator")
public class EmpValidator implements Serializable, Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors,"name","用户名不能为空");
        ValidationUtils.rejectIfEmpty(errors,"address","地址不能为空");
        ValidationUtils.rejectIfEmpty(errors,"gender","性别不能为空");
        ValidationUtils.rejectIfEmpty(errors,"id","id不能为空");

        Employee employee = (Employee) target;

        if (employee.getName().length() > 10) {
            errors.rejectValue("name",null,"姓名不能超过10个字符");
        }

        if (employee.getId() == null) {
            errors.rejectValue("id",null,"id不能为空");
        }

        if (employee.getGender() >3 ||employee.getGender()<0) {
            errors.rejectValue("gender",null,"性别格式错误");
        }
    }
}
