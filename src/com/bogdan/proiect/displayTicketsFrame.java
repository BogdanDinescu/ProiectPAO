package com.bogdan.proiect;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

class displayTicketsFrame extends JFrame {
    JTable table;

    public displayTicketsFrame() {
        super("Tickets");
        setSize(600,400);
        JScrollPane panel = new JScrollPane();
        panel.setBounds(0,0,580,300);
        panel.setBackground(Color.WHITE);

        ResultSet rs = Database.getDatabase().loadTickets();
        try {
            if (rs == null) throw new NullPointerException("Zero elements");

            ResultSetMetaData metaData = rs.getMetaData();

            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            table = new JTable(data,columnNames);
            table.setBounds(0,0,580,300);
            panel.add(table);
            panel.setViewportView(table);
            add(panel);
            Audit.write("Display Tickets",Thread.currentThread().getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        setLayout(null);
        setVisible(true);
    }
}
