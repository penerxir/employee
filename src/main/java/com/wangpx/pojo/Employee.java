package com.wangpx.pojo;


import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {

    private Integer id;
   /* @Pattern(regexp = "^(?:[1-9][0-9]?|1[01][0-9]|120)$",message = "年龄格式错误")
    @NotBlank(message = "年龄不能为空")*/
    private Integer age;
   /* @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^([\\u4E00-\\u9FA5]+|[a-zA-Z]+)$",message = "姓名格式错误")*/
    private String name;
   /* @NotBlank(message = "地址不能为空")*/
    private String address;
   /* @NotBlank(message = "性别不能为空")*/
    private Integer gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(age, employee.age) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(gender, employee.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, name, address, gender);
    }

    @Override
    public String
    toString() {
        return "Employee{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Employee() {
    }

    public Employee(Integer id, Integer age, String name, String address, Integer gender) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.address = address;
        this.gender = gender;
    }
}
