package com.example.betaapplication;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class TimeFormatting {

    UserInformation userInformation = new UserInformation();
    public double getUserEarningPerHours(String dayStartTime, String dayEndTime) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date startTime = format.parse(dayStartTime);
        Date endTime = format.parse(dayEndTime);

        long timeDifferenceInMinutes = ((endTime.getTime() - startTime.getTime()) / 1000) / 60;

        double dayHoursInDecimal = Math.toIntExact(timeDifferenceInMinutes / 60);
        double dayMinutesInDecimal = (timeDifferenceInMinutes - dayHoursInDecimal * 60) / 60;

        double dayHoursAndMinutesInDecimal = dayHoursInDecimal + dayMinutesInDecimal;

        return dayHoursAndMinutesInDecimal * userInformation.getEarnPerHour();
    }

    public String getDayHoursOnDateFormat(String dayStartTime, String dayEndTime) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date startTime = format.parse(dayStartTime);
        Date endTime = format.parse(dayEndTime);

        long timeDifferenceInMinutes = ((endTime.getTime() - startTime.getTime()) / 1000) / 60;

        String hoursDateFormat = String.valueOf(timeDifferenceInMinutes / 60);
        String minutesDateFormat = String.valueOf(timeDifferenceInMinutes - ((timeDifferenceInMinutes / 60) * 60));


        if (hoursDateFormat.length() < 2) {
            hoursDateFormat = "0" + hoursDateFormat;
        }
        if (minutesDateFormat.length() < 2) {
            minutesDateFormat = "0" + minutesDateFormat;
        }

        return hoursDateFormat + ":" + minutesDateFormat;
    }

    public String formattingDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
