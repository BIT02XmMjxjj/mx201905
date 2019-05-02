package com.mindex.challenge.data;

public class Compensation {
    private String employeeId;
    private Integer salary;
    private String effectiveDate;

    public Compensation() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employee) {
        this.employeeId = employee;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer newSalary) {
        this.salary = newSalary;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        // TODO - verify its a valid date format
        this.effectiveDate = effectiveDate;
    }
}
