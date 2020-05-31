package com.bogdan.proiect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Audit {
    public static void write(String action,String thread){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("audit.csv",true))){
            Date date = new Date();
            StringBuffer stringBuffer = new StringBuffer(date.toString());
            stringBuffer.append(",");
            stringBuffer.append(action);
            stringBuffer.append(',');
            stringBuffer.append(thread);
            stringBuffer.append(System.lineSeparator());
            writer.write(stringBuffer.toString());
        } catch (IOException e) {
            System.out.println("Eroare la scriere");
        }
    }
}
