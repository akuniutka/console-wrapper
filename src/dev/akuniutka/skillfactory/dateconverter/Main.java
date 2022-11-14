package dev.akuniutka.skillfactory.dateconverter;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Main {

    public static LocalTime convert(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Moscow"));
        return zonedDateTime.toLocalTime();
    }

    public static void main(String[] args) {
        Date date = new Date(59001);
        System.out.println(convert(date));
    }

}
