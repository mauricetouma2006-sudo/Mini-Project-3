package com.example.miniproject2;

public class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private double salary;
    private int annualLeave;

    public Employee(String firstName, String lastName, int age, double salary, int annualLeave) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.annualLeave = annualLeave;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(int annualLeave) {
        this.annualLeave = annualLeave;
    }
}
