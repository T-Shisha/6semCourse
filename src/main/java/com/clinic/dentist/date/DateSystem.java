package com.clinic.dentist.date;

import com.clinic.dentist.models.Appointment;
import com.clinic.dentist.models.Dentist;
import com.clinic.dentist.repositories.AppointmentRepository;
import com.clinic.dentist.repositories.DentistRepository;
import com.clinic.dentist.services.AppointmentService;
import com.clinic.dentist.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateSystem {


    public static String NextDay() {
        Date thisDay = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd/MM/yyyy");
        String dat = formatForDateNow.format(thisDay);
        String[] today = dat.split("/");
        String[] next = new String[3];
        Calendar c = Calendar.getInstance();
        c.setTime(thisDay);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 6) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    next = next_day(today);
                } else next = next_day(next);
            }
        } else if (dayOfWeek == 7) {
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    next = next_day(today);
                } else next = next_day(next);
            }
        } else {
            next = next_day(today);
        }
        String now = next[2] + "-" + next[1] + "-" + next[0];
        return now;
    }

    private static boolean visoc(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            return true;
        return false;
    }

    private static boolean last_day(String[] date) {
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);

        if (visoc(year) && month == 2 && day == 29) {
            return true;
        }
        if (month == 2 && day == 28) {
            return true;
        }
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day == 31) {
            return true;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 30) {
            return true;
        }
        return false;
    }

    private static String[] next_day(String[] date) {
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);

        if (last_day(date)) {
            if (month != 12) {
                if (month + 1 < 10) {
                    date[0] = "01";
                    date[2] = "0" + Integer.toString(month + 1);
                    return date;

                } else {
                    date[0] = "01";
                    date[2] = Integer.toString(month + 1);
                    return date;
                }
            } else if (month == 12) {
                date[0] = "01";
                date[2] = "01";
                date[3] = Integer.toString(year + 1);
                return date;
            }
        } else {
            if (month < 10 && day + 1 < 10) {
                date[0] = "0" + Integer.toString(day + 1);
                date[1] = "0" + Integer.toString(month);
                return date;
            } else if (month < 10) {
                date[0] = Integer.toString(day + 1);
                date[1] = "0" + Integer.toString(month);
                return date;

            } else if (day + 1 < 10) {
                date[0] = "0" + Integer.toString(day + 1);
                return date;
            } else {
                date[0] = Integer.toString(day + 1);
                return date;
            }
        }
        return date;
    }

    public static Date getDateFromString(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String dateInString = NextDay();
        Date date = new Date();
        try {

            date = formatter.parse(dateInString);


        } catch (ParseException e) {

        }

        return date;
    }

    public static boolean checkWeekend(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDateFromString(date));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == 7 || dayOfWeek == 1) {
            return true;
        }
        return false;
    }

   /* public   boolean checkFreeDay(String date, Long id_dentist)////проверка на наличие в этот день записей
    {   int h=2;
        Dentist dentist = dentistService.findById(id_dentist);
        List<Appointment> orders = appointmentService.findByDentistAndDate(dentist, date);
        if (orders.size() != 0) {
            return false;
        }

        return true;
    }*/
}
