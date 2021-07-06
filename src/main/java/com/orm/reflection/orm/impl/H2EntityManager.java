package com.orm.reflection.orm.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2EntityManager<T> extends AbstractEntityManager<T> {

    @Override
    public Connection buildConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:C:\\Users\\andranik.khachatryan\\IdeaProjects\\some-app\\Creating-ORM\\db-files\\db-pluralsight", "sa", "");
    }
}
