package com.example.betaapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginAndPasswordValidation {


    public boolean isValidPassword(String password) {

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=\\S+$).{4,20}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean isValidLogin(String login) {

        String regex = "(?=.*[a-z])"
                + "(?=\\S+$).{3,20}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(login);

        return matcher.matches();
    }
}
