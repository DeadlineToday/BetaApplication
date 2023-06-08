package com.example.betaapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class RegistrationSceneController implements Initializable {
    @FXML
    private PasswordField registrationConfirmPasswordField;

    @FXML
    private TextField registrationLoginTextField;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private PasswordField registrationPasswordField;

    @FXML
    private Spinner<Double> earnPerHourSpinner;

    @FXML
    private Spinner<Double> earnPerKilometerSpinner;

    @FXML
    void registrationSaveButton() throws SQLException, ClassNotFoundException {
        LoginAndPasswordValidation loginAndPasswordValidation = new LoginAndPasswordValidation();
        if (!registrationLoginTextField.getText().isEmpty() &&
                !registrationPasswordField.getText().isEmpty() &&
                !registrationConfirmPasswordField.getText().isEmpty()) {

            if (registrationPasswordField.getText().equals(registrationConfirmPasswordField.getText())) {

                if (loginAndPasswordValidation.isValidLogin(registrationLoginTextField.getText()) &&
                        loginAndPasswordValidation.isValidPassword(registrationPasswordField.getText())) {

                    DataBaseFunction dataBaseFunction = new DataBaseFunction();
                    Connection connection = dataBaseFunction.connectToDataBase();

                    double earnPerHour = earnPerHourSpinner.getValue();
                    double earnPerKilometer = earnPerKilometerSpinner.getValue();

                    if (dataBaseFunction.createNewUser(connection, registrationLoginTextField.getText(), registrationPasswordField.getText(), earnPerHour, earnPerKilometer)) {
                        registrationMessageLabel.setTextFill(Color.GREEN);
                        registrationMessageLabel.setText("User created");
                    } else {
                        registrationMessageLabel.setTextFill(Color.RED);
                        registrationMessageLabel.setText("User already exist");
                    }


                } else {
                    registrationMessageLabel.setTextFill(Color.RED);
                    registrationMessageLabel.setText("Try again");
                }

            } else {
                registrationMessageLabel.setTextFill(Color.RED);
                registrationMessageLabel.setText("Passwords do not match");
            }

        } else {
            registrationMessageLabel.setTextFill(Color.RED);
            registrationMessageLabel.setText("All fields must be filled in");
        }

        registrationLoginTextField.clear();
        registrationPasswordField.clear();
        registrationConfirmPasswordField.clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory<Double> spinnerEarnPerKilometerValueFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.4 ,0.9, 0.4, 0.1);

        earnPerKilometerSpinner.setValueFactory(spinnerEarnPerKilometerValueFactory);

        SpinnerValueFactory<Double> spinnerEarnPerHourValueFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(14, 20, 14, 1);

        earnPerHourSpinner.setValueFactory(spinnerEarnPerHourValueFactory);
    }
}



