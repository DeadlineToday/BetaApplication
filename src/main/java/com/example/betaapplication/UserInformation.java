package com.example.betaapplication;

public class UserInformation {
    private static String userLogin;
    private static double earnPerHour;
    private static double earnPerOrder;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        UserInformation.userLogin = userLogin;
    }

    public double getEarnPerHour() {
        return earnPerHour;
    }

    public void setEarnPerHour(double earnPerHour) {
        UserInformation.earnPerHour = earnPerHour;
    }

    public double getEarnPerOrder() {
        return earnPerOrder;
    }

    public void setEarnPerOrder(double earnPerOrder) {
        UserInformation.earnPerOrder = earnPerOrder;
    }
}
