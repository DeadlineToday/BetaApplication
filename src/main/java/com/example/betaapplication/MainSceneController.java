package com.example.betaapplication;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MainSceneController {


    UserInformation userInformation = new UserInformation();
    DataBaseFunction dataBaseFunction = new DataBaseFunction();
    TimeFormatting timeFormatting = new TimeFormatting();

    @FXML
    private DatePicker endDate;

    @FXML
    private MenuBar mainSceneMenuBar;

    @FXML
    private DatePicker startDate;

    @FXML
    private TableView<SalaryInformation> tableView;

    @FXML
    private TableColumn<SalaryInformation, String> dayDateColumn;

    @FXML
    private TableColumn<SalaryInformation, String> dayEarnColumn;

    @FXML
    private TableColumn<SalaryInformation, String> dayTimeColumn;

    @FXML
    private TableColumn<SalaryInformation, String> endDayTimeColumn;

    @FXML
    private TableColumn<SalaryInformation, String> startDayTimeColumn;

    @FXML
    private TableColumn<SalaryInformation, String> orderDistanceColumn;

    @FXML
    private TableColumn<SalaryInformation, String> orderEarnColumn;

    @FXML
    private TableColumn<SalaryInformation, String> orderNumberColumn;

    @FXML
    private TableColumn<SalaryInformation, String> salaryColumn;

    @FXML
    private TableColumn<?, ?> deleteColumn;

    @FXML
    void logOutMenu() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sign-in-scene.fxml")));
        Stage stage = (Stage) ((Node) mainSceneMenuBar).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Beta Application!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void showAll(ActionEvent event) {

    }

    @FXML
    void addDay() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-day-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 325, 130);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Day");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void addOrder() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-order-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 265, 130);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Order");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteDay(ActionEvent event) {

    }

    @FXML
    void deleteOrder(ActionEvent event) {

    }

    @FXML
    void showDays(ActionEvent event) {

    }

    @FXML
    void showOrders(ActionEvent event) {

    }

    ObservableList<SalaryInformation> salaryInformationList = FXCollections.observableArrayList();
    @FXML
    void showButton() throws SQLException, ClassNotFoundException {

        Connection connection = dataBaseFunction.connectToDataBase();

        salaryInformationList.clear();

        double daySalarySum = 0;
        int ordersNumberSum = 0;
        double ordersDistanceSum = 0;
        double ordersSalarySum = 0;


        if (startDate.getValue() != null && endDate.getValue() != null) {

            String dayDate = String.valueOf(startDate.getValue());
            Date dayDateForLoop = timeFormatting.convertToDateViaSqlDate(startDate.getValue());

            while (dayDateForLoop.compareTo(timeFormatting.convertToDateViaSqlDate(endDate.getValue())) <= 0) {


                ResultSet daysResultSet = dataBaseFunction.selectDaysInfoFromDB(connection, dayDate);
                ResultSet ordersResultSet = dataBaseFunction.selectOrdersInfoFromDB(connection, dayDate);

                if (daysResultSet.next() && ordersResultSet.next()) {


                    daySalarySum = daySalarySum + daysResultSet.getDouble("dayearn");
                    ordersNumberSum = ordersNumberSum + ordersResultSet.getInt("maxOrderNumber");
                    ordersDistanceSum = ordersDistanceSum + ordersResultSet.getDouble("sumOrderDistance");
                    ordersSalarySum = ordersSalarySum + ordersResultSet.getDouble("sumOrderEarn");

                    salaryInformationList.add(new SalaryInformation(
                            daysResultSet.getString("daydate"),
                            daysResultSet.getString("daystart"),
                            daysResultSet.getString("dayend"),
                            daysResultSet.getString("dayhours"),
                            daysResultSet.getDouble("dayearn"),
                            ordersResultSet.getInt("maxOrderNumber"),
                            ordersResultSet.getDouble("sumOrderDistance"),
                            ordersResultSet.getDouble("sumOrderEarn"),
                            daysResultSet.getDouble("dayearn")+ ordersResultSet.getDouble("sumOrderEarn")));


                    dayDateColumn.setCellValueFactory(new PropertyValueFactory<>("dayDate"));
                    startDayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayStart"));
                    endDayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayEnd"));
                    dayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayTime"));
                    dayEarnColumn.setCellValueFactory(new PropertyValueFactory<>("dayEarn"));
                    orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderOfNumber"));
                    orderDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("orderDistance"));
                    orderEarnColumn.setCellValueFactory(new PropertyValueFactory<>("orderEarn"));
                    salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));


                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dayDateForLoop);
                calendar.add(Calendar.DATE, 1);
                dayDateForLoop = calendar.getTime();

                dayDate = String.valueOf(timeFormatting.formattingDate(dayDateForLoop));

            }

            salaryInformationList.add(new SalaryInformation("Result:",null,null,null, daySalarySum, ordersNumberSum, ordersDistanceSum, ordersSalarySum, daySalarySum + ordersSalarySum));

            dayDateColumn.setCellValueFactory(new PropertyValueFactory<>("dayDate"));
            startDayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayStart"));
            endDayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayEnd"));
            dayTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dayTime"));
            dayEarnColumn.setCellValueFactory(new PropertyValueFactory<>("dayEarn"));
            orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderOfNumber"));
            orderDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("orderDistance"));
            orderEarnColumn.setCellValueFactory(new PropertyValueFactory<>("orderEarn"));
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

            tableView.setItems(salaryInformationList);
        }
    }
}

