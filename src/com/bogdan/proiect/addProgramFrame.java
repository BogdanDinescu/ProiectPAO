package com.bogdan.proiect;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

class addProgramFrame extends JFrame {

    JSpinner yearPiker;
    JSpinner monthPicker;
    JSpinner dayPicker;
    JSpinner hourPicker;
    JTextField theatre;
    JLabel labeltheatre;
    JTextField film_id;
    JLabel labelfilm;
    JButton submit;

    public addProgramFrame() {
        super("Add Program");
        setSize(600,400);
        int cur_year = Calendar.getInstance().get(Calendar.YEAR);
        yearPiker = new JSpinner(new SpinnerNumberModel(cur_year,cur_year,cur_year+10,1));
        ((JSpinner.DefaultEditor) yearPiker.getEditor()).getTextField().setEditable(false);
        yearPiker.setBounds(150,100,50,20);
        yearPiker.addChangeListener(this::changeDayPicker);
        add(yearPiker);

        int cur_month = Calendar.getInstance().get(Calendar.MONTH)+1;
        monthPicker = new JSpinner(new SpinnerNumberModel(cur_month,1,12,1));
        ((JSpinner.DefaultEditor) monthPicker.getEditor()).getTextField().setEditable(false);
        monthPicker.setBounds(200,100,50,20);
        monthPicker.addChangeListener(this::changeDayPicker);
        add(monthPicker);

        dayPicker = new JSpinner();
        changeDayPicker(null);
        dayPicker.setBounds(250,100,50,20);
        add(dayPicker);

        hourPicker = new JSpinner(new SpinnerNumberModel(0,0,24,1));
        ((JSpinner.DefaultEditor) hourPicker.getEditor()).getTextField().setEditable(false);
        hourPicker.setBounds(300,100,50,20);
        add(hourPicker);

        labeltheatre = new JLabel("Theatre number");
        labeltheatre.setBounds(160,120,100,20);
        theatre = new JTextField();
        theatre.setBounds(160,140,50,20);
        add(theatre); add(labeltheatre);

        labelfilm = new JLabel("Film id");
        labelfilm.setBounds(280,120,100,20);
        film_id = new JTextField();
        film_id.setBounds(280,140,50,20);
        add(film_id); add(labelfilm);


        submit = new JButton("Submit");
        submit.addActionListener(actionEvent -> {
            int year = (int) yearPiker.getValue();
            int month = (int) monthPicker.getValue();
            int day = (int) dayPicker.getValue();
            int hour = (int) hourPicker.getValue();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy kk"); //kk pt 24 ore
            String stringDate = String.valueOf(day) + '-' + String.valueOf(month) + '-' + String.valueOf(year) + ' ' + String.valueOf(hour);
            java.sql.Date sqlDate = null;
            try {
                sqlDate = new Date(df.parse(stringDate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int theatre_number = Integer.parseInt(theatre.getText());
            int film_number = Integer.parseInt(film_id.getText());
            Program program = new Program(sqlDate,theatre_number,film_number);
            try {
                Database.getDatabase().saveProgram(program);
                Audit.write("Added Program",Thread.currentThread().getName());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
            }
        });
        submit.setBounds(220,240,80,30);
        add(submit);

        setLayout(null);
        setVisible(true);
    }

    private void changeDayPicker(ChangeEvent changeEvent) {
        Calendar c = new GregorianCalendar();
        c.set((int) yearPiker.getValue(),(int) monthPicker.getValue()-1,1);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        SpinnerModel model = new SpinnerNumberModel(1,1,lastDay,1);
        dayPicker.setModel(model);
        ((JSpinner.DefaultEditor) dayPicker.getEditor()).getTextField().setEditable(false);
    }
}
