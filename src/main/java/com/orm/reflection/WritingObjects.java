package com.orm.reflection;

import com.orm.reflection.models.Person;
import com.orm.reflection.orm.EntityManager;
import com.orm.reflection.orm.impl.ManagedEntityManager;

import java.sql.SQLException;

public class WritingObjects {

    public static void main(String[] args) throws SQLException, IllegalAccessException, ClassNotFoundException {
        BeanManager beanManager = BeanManager.getInstance();

        EntityManager<Person> entityManager = beanManager.getInstance(ManagedEntityManager.class);

        Person linda = new Person("Linda", 31);
        Person james = new Person("James", 24);
        Person susan = new Person("James", 34);
        Person john = new Person("James", 33);

        entityManager.persist(linda);
        entityManager.persist(james);
        entityManager.persist(susan);
        entityManager.persist(john);

        System.out.println(linda);
        System.out.println(james);
        System.out.println(susan);
        System.out.println(john);
    }
}
