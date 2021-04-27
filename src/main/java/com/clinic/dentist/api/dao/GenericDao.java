package com.clinic.dentist.api.dao;

import java.util.List;

public interface GenericDao<T> {
    void save(T entity);

    T findById(long id);


    List<T> getAll();


}
