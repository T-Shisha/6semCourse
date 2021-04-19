package com.clinic.dentist.comparators.time;

import com.clinic.dentist.models.Maintenance;

import java.util.Comparator;

public class TimeComparator  {
     public static int compare(String time1, String time2){

        String []str1 = time1.split(":");
        String[] str2 =  time2.split(":");
        if (Integer.parseInt(str1[0]) > Integer.parseInt(str2[0])) { return 1; }
        if (Integer.parseInt(str1[0]) < Integer.parseInt(str2[0])) { return -1; }
        if (Integer.parseInt(str1[0]) == Integer.parseInt(str2[0])) {
            if (Integer.parseInt(str1[1]) > Integer.parseInt(str2[1])) { return 1; }
            else if (Integer.parseInt(str1[1]) < Integer.parseInt(str2[1])) { return -1; }
        }
        return 0;
    }

}
