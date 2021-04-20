package com.clinic.dentist.converters;

import com.clinic.dentist.models.Maintenance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetConverter {
    public static Set<Maintenance> getSet (Set<Maintenance> set){
        Set<Maintenance> s=new HashSet<>();
        Iterator<Maintenance> iterator = set.iterator();
        while (iterator.hasNext()) {
            Maintenance item = iterator.next();
            s.add(item);
        }
        return s;
    }
}
