package com.example.betaapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalTimeStringConverter;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class AddDaySceneController implements Initializable {

    DataBaseFunction dataBaseFunction = new DataBaseFunction();
    TimeFormatting timeFormatting = new TimeFormatting();

    @FXML
    private Label addDayMessageLabel;

    @FXML
    private DatePicker addDayDatePicker;

    @FXML
    private ChoiceBox<String> endDayHours;

    @FXML
    private ChoiceBox<String> endDayMinutes;

    @FXML
    private ChoiceBox<String> startDayHours;

    @FXML
    private ChoiceBox<String> startDayMinutes;

    @FXML
    void addDaySaveButton() throws SQLException, ClassNotFoundException, ParseException {

        if (addDayDatePicker.getValue() != null &&
                startDayHours.getValue() != null &&
                startDayMinutes.getValue() != null &&
                endDayHours.getValue() != null &&
                endDayMinutes.getValue() != null) {

            Connection connection = dataBaseFunction.connectToDataBase();
            String startDayTime = startDayHours.getValue() + ":" + startDayMinutes.getValue();
            String endDayTime = endDayHours.getValue() + ":" + endDayMinutes.getValue();

            if (dataBaseFunction.insertIntoDaysTable(
                    connection,
                    String.valueOf(addDayDatePicker.getValue()),
                    startDayTime,
                    endDayTime,
                    timeFormatting.getDayHoursOnDateFormat(startDayTime, endDayTime),
                    timeFormatting.getUserEarningPerHours(startDayTime, endDayTime))) {

                addDayMessageLabel.setTextFill(Color.GREEN);
                addDayMessageLabel.setText("Saved!!!");
            } else {
                addDayMessageLabel.setTextFill(Color.RED);
                addDayMessageLabel.setText("Day exist!!!");
            }
        } else {
            addDayMessageLabel.setTextFill(Color.RED);
            addDayMessageLabel.setText("Try again!!!");
        }
        startDayHours.setValue(null);
        startDayMinutes.setValue(null);
        endDayHours.setValue(null);
        endDayMinutes.setValue(null);
    }


    @FXML
    private Spinner<LocalTime> timeTest;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] hoursList = new String[24];
        String[] minuteList = new String[60];

        for (int index = 0; index <= 23; index++) {

            String indexString = String.valueOf(index);

            if (index < 10) {
                hoursList[index] = "0" + indexString;
            } else {
                hoursList[index] = indexString;
            }
        }

        for (int index = 0; index <= 59; index++) {

            String indexString = String.valueOf(index);

            if (index < 10) {
                minuteList[index] = "0" + indexString;
            } else {
                minuteList[index] = indexString;
            }
        }

        startDayHours.getItems().addAll(hoursList);
        startDayMinutes.getItems().addAll(minuteList);

        endDayHours.getItems().addAll(hoursList);
        endDayMinutes.getItems().addAll(minuteList);

        SpinnerValueFactory<LocalTime> spinnerValueFactory = new SpinnerValueFactory<LocalTime>() {

            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
            }
            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusHours(steps));
                }

            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusHours(steps));
                }
            }

        };
        timeTest.setValueFactory(spinnerValueFactory);
        timeTest.setEditable(true);
        //timeTest.getStyleClass().clear();
    }
}

