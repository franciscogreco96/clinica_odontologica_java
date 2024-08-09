package com.example.clinica_odontologica_proyecto_backEnd1.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class Util {

    public static Timestamp dateToTimestamp(Date date){
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public static java.sql.Date utilDateToSqlDate(LocalDate localDate1){
        LocalDate localDate = localDate1;
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        return sqlDate;
    }
}
