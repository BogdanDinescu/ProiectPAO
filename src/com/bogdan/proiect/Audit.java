package com.bogdan.proiect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Audit {
    public static void write(String action){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("audit.csv",true))){
            Date date = new Date();
            StringBuilder stringBuilder = new StringBuilder(date.toString());
            stringBuilder.append(",");
            stringBuilder.append(action);
            stringBuilder.append(System.lineSeparator());
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Eroare la scriere");
        }
    }
}
