package com.example.betaapplication;

import java.sql.*;

public class DataBaseFunction {
    UserInformation userInformation = new UserInformation();
    private String query;
    private ResultSet resultSet;
    private Statement statement;

    public Connection connectToDataBase() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "BetaApplicationDB", "postgres","1234");
    }

    public boolean userAuthentication(Connection connection, String userLogin, String userPassword) throws SQLException {
        
        query = String.format("select * from users where userlogin='%s'", userLogin);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        try {

            resultSet.next();
            String userPasswordFromDB = resultSet.getString("userpassword");
            double userEarnPerHour = resultSet.getDouble("userearnperhour");
            double userEarnPerOrder = resultSet.getDouble("userearnperorder");

            userInformation.setUserLogin(userLogin);
            userInformation.setEarnPerHour(userEarnPerHour);
            userInformation.setEarnPerOrder(userEarnPerOrder);

            return userPassword.equals(userPasswordFromDB);

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean createNewUser(Connection connection, String login, String password, double earnPerHour, double earnPerOrder) throws SQLException {

        statement = connection.createStatement();

        query = "select MAX(index) as maxIndex from users";
        resultSet = statement.executeQuery(query);
        resultSet.next();

        query = String.format("insert into users values ('%s', '%s', '%s', '%s', '%s')", resultSet.getInt("maxIndex") + 1, login, password, earnPerHour, earnPerOrder);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            return false;
        }

        query = String.format("create table %s " +
                "(index integer, " +
                "daydate varchar(255) unique, " +
                "daystart varchar(255)," +
                "dayend varchar(255)," +
                "dayhours varchar(255)," +
                "dayearn double precision, primary key (index))", login + "days");

        statement.executeUpdate(query);

        query = String.format("create table %s " +
                "(index integer, " +
                "orderdate varchar(255), " +
                "ordernumber integer, " +
                "orderdistance double precision, " +
                "orderearn double precision)", login + "orders");

        statement.executeUpdate(query);


        return true;
    }

    public boolean insertIntoDaysTable(Connection connection, String dayDate, String dayStartTime, String dayEndTime, String dayTimeInDateFormat, double dayEarn) throws SQLException {

        statement = connection.createStatement();

        query = String.format("select MAX(index) as maxIndex from %s", userInformation.getUserLogin() + "days");
        resultSet = statement.executeQuery(query);
        resultSet.next();

        query = String.format("insert into %s (index, daydate, daystart, dayend, dayhours, dayearn) " +
                "values('%s', '%s', '%s', '%s', '%s', '%s')",
                userInformation.getUserLogin() + "days",
                resultSet.getInt("maxIndex") + 1,
                dayDate, dayStartTime, dayEndTime, dayTimeInDateFormat, dayEarn);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
    public boolean insertIntoOrdersTable(Connection connection, String orderDate, double orderDistance) throws SQLException {

        double orderEarn = orderDistance * userInformation.getEarnPerOrder();
        orderEarn = (int)(Math.round(orderEarn * 100))/100.0;

        statement = connection.createStatement();

        query = String.format("select MAX(ordernumber) as maxOrderNumber from %s where orderdate = '%s'", userInformation.getUserLogin() + "orders", orderDate);
        resultSet = statement.executeQuery(query);
        resultSet.next();

        int orderNumber = resultSet.getInt("maxOrderNumber");

        query = String.format("select MAX(index) as maxIndex from %s", userInformation.getUserLogin() + "orders");
        resultSet = statement.executeQuery(query);
        resultSet.next();

        query = String.format("insert into %s (index, orderdate, orderdistance, orderearn, ordernumber) " +
                        "values('%s', '%s', '%s', '%s', '%s')",
                userInformation.getUserLogin() + "orders",
                resultSet.getInt("maxIndex") + 1,
                orderDate,
                orderDistance,
                orderEarn,
                ++orderNumber);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public ResultSet selectDaysInfoFromDB(Connection connection, String dayDate) throws SQLException {

        statement = connection.createStatement();

        query = String.format("select * from %s where daydate = '%s'", userInformation.getUserLogin() + "days", dayDate);
        resultSet = statement.executeQuery(query);

        return resultSet;
    }
    public ResultSet selectOrdersInfoFromDB(Connection connection, String dayDate) throws SQLException {

        statement = connection.createStatement();

        query = String.format("select " +
                "max(ordernumber) as maxOrderNumber, " +
                "sum(orderdistance) as sumOrderDistance, " +
                "sum(orderearn) as sumOrderEarn " +
                "from %s where orderdate = '%s'", userInformation.getUserLogin() + "orders", dayDate);
        resultSet = statement.executeQuery(query);

        return resultSet;
    }
}
