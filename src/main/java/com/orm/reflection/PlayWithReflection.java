package com.orm.reflection;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PlayWithReflection {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
       String person = "com.synisys.reflection.models.Person";
       Class<?> personClass = Class.forName(person);

        System.out.println("Person class: " + personClass);

        Field [] fields = personClass.getFields();
        System.out.println("Fields:");
        System.out.println(Arrays.toString(fields));

        Field [] declaredFields = personClass.getDeclaredFields();
        System.out.println("Declared fields:");
        System.out.println(Arrays.toString(declaredFields));

        System.out.println("Declared methods:");
        Method[] declaredMethods = personClass.getDeclaredMethods();
        for (Method method : declaredMethods){
            System.out.println(method);
        }

        System.out.println("Methods:");
        Method[] methods = personClass.getMethods();
        for (Method method : methods){
            System.out.println(method);
        }

        Arrays.stream(declaredMethods)
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .forEach(System.out::println);
    }
}
