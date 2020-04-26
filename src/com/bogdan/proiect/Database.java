package com.bogdan.proiect;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Database<T> {
    private static Database instance = null;
    private Database(){}

    public static Database getDatabase(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void saveData(ArrayList<T> list,String fileName){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (T element:list) {
                List<Method> getters = Arrays.stream(element.getClass().getMethods()).filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
                getters.sort(Comparator.comparing(Method::getName));

                String field = getters.get(0).invoke(element,null).toString();
                field = field.replaceFirst("class ","");
                writer.write(field);
                for (int i = 1; i < getters.size(); i++) {
                    writer.write(',');
                    field = (getters.get(i).invoke(element,null)).toString();
                    writer.write(field);
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
        }catch (IOException e){
            System.out.println("Eroare la scriere");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void loadData(ArrayList<T> list, String fileName){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null){
                String[] fields = line.split(",");
                Class<?> c = Class.forName(fields[0]);
                fields = Arrays.copyOfRange(fields, 1, fields.length);
                Constructor<?>[] constructor = c.getDeclaredConstructors();
                Class<?>[] types = (constructor[0]).getParameterTypes();
                Object[] arg = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    switch (types[i].getSimpleName()){
                        case "String":
                            arg[i] = fields[i];
                            break;
                        case "Integer":
                            arg[i] = Integer.valueOf(fields[i]);
                            break;
                        case "Rating":
                            arg[i] = Rating.valueOf(fields[i]);
                            break;
                    }
                }
                list.add((T) constructor[0].newInstance(arg));
                line = reader.readLine();
            }
            reader.close();
        }catch (IOException e){
            System.out.println("Eroare la citire");
        }catch (ClassNotFoundException e){
            System.out.println("Clasa nu exista");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Film.setTotalNumberFilms(list.size());
    }
}
