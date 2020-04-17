package com.wangpx.pojo;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {

    private Integer id;
    private Integer age;
    private String name;
    private String address;
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
