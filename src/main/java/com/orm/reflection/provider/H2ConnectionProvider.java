package com.orm.reflection.provider;

import com.orm.reflection.annotations.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionProvider {

    @Provides
    public Connection buildConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:C:\\Users\\andranik.khachatryan\\IdeaProjects\\some-app\\Creating-ORM\\db-files\\db-pluralsight", "sa", "");
    }
}
