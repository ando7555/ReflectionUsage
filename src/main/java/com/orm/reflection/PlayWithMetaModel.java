package com.orm.reflection;

import com.orm.reflection.models.Person;
import com.orm.reflection.util.ColumnField;
import com.orm.reflection.util.MetaModel;
import com.orm.reflection.util.PrimaryKeyField;

import java.util.List;

public class PlayWithMetaModel {

    public static void main(String[] args) {
        MetaModel metamodel =  MetaModel.of(Person.class);
        PrimaryKeyField primaryKeyField = metamodel.getPrimaryKey();
        List<ColumnField> columnFields = metamodel.getColumns();

        System.out.println("Primary key name = " + primaryKeyField.getName() +
                            ", type = " + primaryKeyField.getType().getSimpleName());

        for (ColumnField columnField : columnFields){
            System.out.println("Column name = " + columnField.getName() +
                    ", type = " + columnField.getType().getSimpleName());
        }
    }
}
