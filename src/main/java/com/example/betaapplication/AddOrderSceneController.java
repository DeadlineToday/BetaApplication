package com.example.betaapplication;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class AddOrderSceneController {
    DataBaseFunction dataBaseFunction = new DataBaseFunction();
    @FXML
    private DatePicker addOrderDatePicker;

    @FXML
    private TextField addOrderDistanceTextField;

    @FXML
    private Label addOrderMessageLabel;

    @FXML
    void addOrderSaveButton() throws SQLException, ClassNotFoundException {

        if (addOrderDatePicker.getValue() != null && !addOrderDistanceTextField.getText().isEmpty()) {

            Connection connection = dataBaseFunction.connectToDataBase();

            if (dataBaseFunction.insertIntoOrdersTable(connection, String.valueOf(addOrderDatePicker.getValue()), Double.parseDouble(addOrderDistanceTextField.getText()))) {
                addOrderMessageLabel.setTextFill(Color.GREEN);
                addOrderMessageLabel.setText("Saved!!!");
            } else {
                addOrderMessageLabel.setTextFill(Color.RED);
                addOrderMessageLabel.setText("Day exist!!!");
            }
        } else {
            addOrderMessageLabel.setTextFill(Color.RED);
            addOrderMessageLabel.setText("Try again!!!");
        }

        addOrderDistanceTextField.clear();
    }
}
