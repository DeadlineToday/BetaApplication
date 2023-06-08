package com.example.betaapplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


public class SignInSceneController {

    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    void registrationButton() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("registration-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 220, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void signInKeyEvent(KeyEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (event.getCode().equals(KeyCode.ENTER)) {

            DataBaseFunction dataBaseFunction = new DataBaseFunction();
            Connection connection = dataBaseFunction.connectToDataBase();

            if (dataBaseFunction.userAuthentication(connection, loginTextField.getText(), passwordPasswordField.getText())) {

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-scene.fxml")));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();

            } else {

                errorMessageLabel.setText("Try again!");

                loginTextField.clear();
                passwordPasswordField.clear();

            }
        }
    }
}