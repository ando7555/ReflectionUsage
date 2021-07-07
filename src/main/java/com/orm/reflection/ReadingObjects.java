package com.orm.reflection;

import com.orm.reflection.models.Person;
import com.orm.reflection.orm.EntityManager;
import com.orm.reflection.orm.impl.ManagedEntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ReadingObjects {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        BeanManager beanManager = BeanManager.getInstance();
        EntityManager<Person> entityManager = beanManager.getInstance(ManagedEntityManager.class);

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
