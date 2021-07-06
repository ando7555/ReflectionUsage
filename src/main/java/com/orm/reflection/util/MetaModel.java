package com.orm.reflection.util;

import com.orm.reflection.annotations.Column;
import com.orm.reflection.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetaModel {

    private Class<?> clss;

    public static MetaModel of(Class<?> clss){
        return new MetaModel(clss);
    }

    public MetaModel(Class<?> clss){
      this.clss = clss;
    }

    public PrimaryKeyField getPrimaryKey() {
        Field[] declaredFields = clss.getDeclaredFields();
        for (Field field : declaredFields){
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null){
                PrimaryKeyField primaryKeyField = new PrimaryKeyField(field);
                return primaryKeyField;
            }
        }
        throw new IllegalArgumentException("No primary key found in class" + clss.getSimpleName());
    }

    public List<ColumnField> getColumns() {
        List<ColumnField> columnFields = new ArrayList<>();
        Field[] declaredFields = clss.getDeclaredFields();
        for (Field field : declaredFields){
            Column column = field.getAnnotation(Column.class);
            if (column != null){
                ColumnField columnField = new ColumnField(field);
                columnFields.add(columnField);
            }
        }
        return columnFields;
    }

    public String buildQuestionMarksElement(){
         buildColumnNames();
        int columnsCount = getColumns().size() + 1;
        return  IntStream.range(0, columnsCount)
                .mapToObj(index -> "?")
                .collect(Collectors.joining(", "));
    }

    public String buildInsertRequest() {
        String columnElement = buildColumnNames();
        String questionMarksElement = buildQuestionMarksElement();

        return  "insert into " + this.clss.getSimpleName() + "(" + columnElement + ") values (" + questionMarksElement + ")";
    }

    private String buildColumnNames() {
        String primaryKeyColumnName = getPrimaryKey().getName();
        List<String> columnNames = getColumns().stream().map(ColumnField::getName).collect(Collectors.toList());
        columnNames.add(0, primaryKeyColumnName);
        return String.join(", ",columnNames);
    }

    public String buildSelectRequest() {
        String columnElement = buildColumnNames();
        return "select " + columnElement + " from "  + this.clss.getSimpleName() +
                " where "  + getPrimaryKey().getName() + " = "  + " ?";
    }
}
