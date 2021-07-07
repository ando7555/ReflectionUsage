package com.orm.reflection.orm;

import com.orm.reflection.orm.impl.ManagedEntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {

    void persist(T t) throws SQLException, IllegalAccessException, ClassNotFoundException;

    T find(Class<T> clss, Object primaryKey) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
