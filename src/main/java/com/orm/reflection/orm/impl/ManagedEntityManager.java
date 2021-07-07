package com.orm.reflection.orm.impl;

import com.orm.reflection.annotations.Inject;
import com.orm.reflection.orm.EntityManager;
import com.orm.reflection.util.ColumnField;
import com.orm.reflection.util.MetaModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

public class ManagedEntityManager<T> implements EntityManager<T> {

    private AtomicLong idGenerator = new AtomicLong(0L);

    @Inject
    private Connection connection;


    @Override
    public void persist(T t) throws SQLException, IllegalAccessException, ClassNotFoundException {
        MetaModel metaModel = MetaModel.of(t.getClass());
        String sql = metaModel.buildInsertRequest();
       try(PreparedStatement preparedStatement = preparedStatementWith(sql).andParameters(t);) {
           preparedStatement.executeUpdate();
       }
    }

    private PreparedStatementWrapper preparedStatementWith(String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(preparedStatement);
    }

    private class PreparedStatementWrapper{

        private PreparedStatement preparedStatement;

        public PreparedStatementWrapper(PreparedStatement preparedStatement) {
            this.preparedStatement = preparedStatement;
        }

        public PreparedStatement andParameters(T t) throws SQLException, IllegalAccessException {
            MetaModel metaModel = MetaModel.of(t.getClass());
            Class<?> type = metaModel.getPrimaryKey().getType();
            if (type == long.class){
                long id = idGenerator.incrementAndGet();
                preparedStatement.setLong(1, id);
                Field field = metaModel.getPrimaryKey().getField();
                field.setAccessible(true);
                field.set(t, id);
            }
            for (int columnIndex = 0; columnIndex < metaModel.getColumns().size(); columnIndex++){
                ColumnField columnField = (ColumnField) metaModel.getColumns().get(columnIndex);
                Class<?> fieldType = columnField.getType();
                Field field = columnField.getField();
                field.setAccessible(true);
                Object value   = field.get(t);
                if (fieldType == int.class){
                    preparedStatement.setInt(columnIndex + 2, (int) value);
                } else if(fieldType == String.class){
                    preparedStatement.setString(columnIndex + 2, (String) value);
                }
            }
            return preparedStatement;
        }

        public PreparedStatement andPrimaryKey(Object primaryKey) throws SQLException {
            if (primaryKey.getClass() == Long.class){
                preparedStatement.setLong(1, (Long) primaryKey);
            }
            return preparedStatement;
        }
    }

    @Override
    public T find(Class<T> clss, Object primaryKey) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MetaModel metaModel = MetaModel.of(clss);
        String sql = metaModel.buildSelectRequest();
        try(PreparedStatement preparedStatement = preparedStatementWith(sql).andPrimaryKey(primaryKey);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildInstanceFrom(clss, resultSet);
        }
    }

    private T buildInstanceFrom(Class<T> clss, ResultSet resultSet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        MetaModel metaModel = MetaModel.of(clss);

        T t = clss.getConstructor().newInstance();
        Field primaryKeyField = metaModel.getPrimaryKey().getField();
        String primaryKeyColumnName = metaModel.getPrimaryKey().getName();
        Class<?> primaryKeyType = primaryKeyField.getType();

        resultSet.next();
        if (primaryKeyType == long.class) {
            long primaryKey = resultSet.getInt(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            primaryKeyField.set(t, primaryKey);
        }
            for (ColumnField columnField : metaModel.getColumns()) {
                Field field = columnField.getField();
                field.setAccessible(true);
                Class<?> columnType = columnField.getType();
                String columnFieldName = columnField.getName();
                if (columnType == int.class) {
                    int value = resultSet.getInt(columnFieldName);
                    field.set(t, value);
                } else if (columnType == String.class) {
                    String value = resultSet.getString(columnFieldName);
                    field.set(t, value);
                }
            }

        return t;

    }


}