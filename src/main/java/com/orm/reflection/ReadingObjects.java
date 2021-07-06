package com.orm.reflection;

import com.orm.reflection.models.Person;
import com.orm.reflection.orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ReadingObjects {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        EntityManager<Person> entityManager = EntityManager.of(Person.class);

        Person linda = entityManager.find(Person.class, 1L);
        Person james = entityManager.find(Person.class,2L);
        Person susan = entityManager.find(Person.class,3L);
        Person john = entityManager.find(Person.class,4L);

        System.out.println(linda);
        System.out.println(james);
        System.out.println(susan);
        System.out.println(john);
    }
}
