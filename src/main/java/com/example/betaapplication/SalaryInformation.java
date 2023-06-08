package com.example.betaapplication;

public class SalaryInformation {



    private String dayDate, dayStart, dayEnd, dayTime;
    private double dayEarn, orderDistance, orderEarn, salary;
    private int orderOfNumber;


    public SalaryInformation(String dayDate, String dayStart, String dayEnd, String dayTime, double dayEarn, int orderOfNumber, double orderDistance, double orderEarn, double salary) {
        this.dayDate = dayDate;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.dayTime = dayTime;
        this.dayEarn = dayEarn;
        this.orderOfNumber = orderOfNumber;
        this.orderDistance = orderDistance;
        this.orderEarn = orderEarn;
        this.salary = salary;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public double getDayEarn() {
        return dayEarn;
    }

    public void setDayEarn(double dayEarn) {
        this.dayEarn = dayEarn;
    }

    public double getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(double orderDistance) {
        this.orderDistance = orderDistance;
    }

    public double getOrderEarn() {
        return orderEarn;
    }

    public void setOrderEarn(double orderEarn) {
        this.orderEarn = orderEarn;
    }

    public int getOrderOfNumber() {
        return orderOfNumber;
    }

    public void setOrderOfNumber(int orderOfNumber) {
        this.orderOfNumber = orderOfNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
