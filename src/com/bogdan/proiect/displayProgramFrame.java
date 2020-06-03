package com.bogdan.proiect;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

class displayProgramFrame extends JFrame {

    JTable table;
    Vector<Vector<Object>> data;

    public displayProgramFrame() {
        super("Program");
        setSize(1000,400);

        JScrollPane panel = new JScrollPane();
        panel.setBounds(0,0,980,300);
        panel.setBackground(Color.WHITE);

        ResultSet rs = Database.getDatabase().loadProgram();
        try {
            if (rs == null) throw new NullPointerException("Zero elements");


            Calendar calendar = Calendar.getInstance();
            data = new Vector<Vector<Object>>();
            for (int i = 0; i<7; i++) {
                data.add(new Vector<Object>());
                data.get(i).add(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.UK));
                calendar.add(Calendar.DATE,1);
            }

            while (rs.next()) {
                int nr_program = rs.getInt("PROGRAM_ID");
                Date date = new Date((rs.getTimestamp("START_DATE")).getTime());
                String name = rs.getString("NAME");
                int row = rs.getInt("DAY");
                calendar.setTime(date);
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String minute = String.valueOf(calendar.get(Calendar.MINUTE));
                if (minute.equals("0")) minute += "0";
                data.get(row).add(nr_program + " " + hour + ':' + minute + ' ' + name);
            }

            Vector <Object> columnNames = new Vector<Object>();
            columnNames.add("Day");
            for (int i = 0; i<5; i++) columnNames.add("");
            table = new JTable(data, columnNames);
            table.setBounds(0,0,980,300);
            panel.add(table);
            panel.setViewportView(table);
            add(panel);
            Audit.write("Display Program",Thread.currentThread().getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        setLayout(null);
        setVisible(true);
    }
}
