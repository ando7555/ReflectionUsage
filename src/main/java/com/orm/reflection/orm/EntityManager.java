package com.orm.reflection.orm;

import com.orm.reflection.orm.impl.H2EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {
    static <T> EntityManager<T> of(Class<T> aClass) {
        return new H2EntityManager<>();
    }

    void persist(T t) throws SQLException, IllegalAccessException, ClassNotFoundException;

    T find(Class<T> clss, Object primaryKey) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
