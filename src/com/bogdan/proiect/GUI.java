package com.bogdan.proiect;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GUI {
    menuFrame menu;

    public GUI() {
        menu = new menuFrame(this);
        Database.getDatabase();
    }

    public void openAddFilm(){
        addFilmFrame f = new addFilmFrame();
    }

    public void openDisplayFilms(){
        displayFilmsFrame f = new displayFilmsFrame();
    }

    public void openAddProgram() {
        addProgramFrame f = new addProgramFrame();
    }

    public void openDisplayProgram() {
        displayProgramFrame f = new displayProgramFrame();
    }

    public void openDisplayTickets() {
        displayTicketsFrame f = new displayTicketsFrame();
    }

    public void openSellTicket() {
        sellTicketFrame f = new sellTicketFrame();
    }

    public void backToMenu(){
        menu.setVisible(true);
    }

    public void openDelete() {
        deleteFrame f = new deleteFrame();
    }
}

class menuFrame extends JFrame {
    JButton displayFilm;
    JButton addFilm;
    JButton displayProgram;
    JButton addProgram;
    JButton displayTickets;
    JButton sellTicket;
    JButton delete;
    ArrayList<JButton> menuButtons;

    public menuFrame(GUI gui){
        super("Menu");
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel("MENU");
        label.setBounds(260,50,150,20);
        add(label);
        menuButtons = new ArrayList<JButton>();

        displayFilm = new JButton("Display Films");
        menuButtons.add(displayFilm);
        displayFilm.addActionListener(actionEvent -> gui.openDisplayFilms());

        addFilm = new JButton("Add Film");
        menuButtons.add(addFilm);
        addFilm.addActionListener(actionEvent -> gui.openAddFilm());

        displayProgram = new JButton("Display Program");
        displayProgram.addActionListener(actionEvent -> gui.openDisplayProgram());
        menuButtons.add(displayProgram);

        addProgram = new JButton("Add Program");
        addProgram.addActionListener(actionEvent -> gui.openAddProgram());
        menuButtons.add(addProgram);

        displayTickets = new JButton("Display Tickets");
        displayTickets.addActionListener(actionEvent -> gui.openDisplayTickets());
        menuButtons.add(displayTickets);

        sellTicket = new JButton("Sell Ticket");
        sellTicket.addActionListener(actionEvent -> gui.openSellTicket());
        menuButtons.add(sellTicket);

        delete = new JButton("Delete something");
        delete.addActionListener(actionEvent -> gui.openDelete());
        menuButtons.add(delete);

        int y = 100;
        // adauga butoanele
        for(JButton button:menuButtons) {
            button.setBounds(200,y,150,20);
            y += 22;
            add(button);
        }
        // afiseaza
        setLayout(null);
        setVisible(true);
    }

}

class addFilmFrame extends JFrame {

    JTextField name;
    JLabel labelName;
    JTextField year;
    JLabel labelYear;
    JTextField director;
    JLabel labelDirector;
    JComboBox<String> rating;
    JLabel labelRating;
    JTextField sub_genre;
    JLabel labelSub_genre;
    JTextField extraInfo;
    JLabel labelExtraInfo;
    JButton submit;
    JComboBox<String> cb;

    public addFilmFrame() {
        super("Add Film");
        setSize(600,400);

        labelName = new JLabel("Name");
        name = new JTextField();
        labelName.setBounds(100,30,100,20);
        name.setBounds(100,50,100,20);
        add(name); add(labelName);

        labelYear = new JLabel("Year");
        year = new JTextField();
        labelYear.setBounds(200,30,100,20);
        year.setBounds(200,50,100,20);
        add(year); add(labelYear);

        labelDirector = new JLabel("Director");
        director = new JTextField();
        labelDirector.setBounds(100,80,100,20);
        director.setBounds(100,100,100,20);
        add(director); add(labelDirector);

        labelRating = new JLabel("Rating");
        labelRating.setBounds(200,80,100,20);
        rating = new JComboBox<String>(Arrays.stream(Rating.values()).map(Enum::name).toArray(String[]::new));
        rating.setBounds(200,100,90,20);
        add(rating); add(labelRating);

        labelSub_genre = new JLabel("Sub genre");
        sub_genre = new JTextField();
        labelSub_genre.setBounds(100,140,100,20);
        sub_genre.setBounds(100,160,100,20);
        add(sub_genre); add(labelSub_genre);

        labelExtraInfo = new JLabel();
        extraInfo = new JTextField();
        labelExtraInfo.setBounds(200,140,100,20);
        extraInfo.setBounds(200,160,100,20);
        add(extraInfo); add(labelExtraInfo);
        extraInfo.setVisible(false);
        labelExtraInfo.setVisible(false);

        String genres[]={"Action","SF","Historical","Comedy"};
        cb = new JComboBox<>(genres);
        cb.setBounds(5, 50,90,20);
        cb.addActionListener(actionEvent -> {
            switch (cb.getSelectedIndex()) {
                case 0:
                case 3:
                    extraInfo.setVisible(false);
                    labelExtraInfo.setVisible(false);
                    break;
                case 1:
                    extraInfo.setVisible(true);
                    labelExtraInfo.setVisible(true);
                    labelExtraInfo.setText("Landscape");
                    break;
                case 2:
                    extraInfo.setVisible(true);
                    labelExtraInfo.setVisible(true);
                    labelExtraInfo.setText("Period");
                    break;
            }
        });
        add(cb);

        submit = new JButton("Submit");
        submit.addActionListener(actionEvent -> {
            Film film;
            Rating ratingValue = Rating.valueOf(rating.getItemAt(rating.getSelectedIndex()));
            switch (cb.getSelectedIndex()){
                case 0:
                    film = new FilmAction(name.getText(),Integer.parseInt(year.getText()),director.getText(),ratingValue,sub_genre.getText());
                    break;
                case 1:
                    film = new FilmSF(name.getText(),Integer.parseInt(year.getText()),director.getText(),ratingValue,sub_genre.getText(),extraInfo.getText());
                    break;
                case 2:
                    film = new FilmHistorical(name.getText(),Integer.parseInt(year.getText()),director.getText(),ratingValue,sub_genre.getText(),extraInfo.getText());
                    break;
                case 3:
                    film = new FilmComedy(name.getText(),Integer.parseInt(year.getText()),director.getText(),ratingValue,sub_genre.getText());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cb.getSelectedIndex());
            }
            try {
                Database.getDatabase().saveFilm(film);
                Audit.write("Added Film",Thread.currentThread().getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
            }
        });
        submit.setBounds(160,240,80,30);
        add(submit);
        setLayout(null);
        setVisible(true);
    }

}

class displayFilmsFrame extends JFrame {

    JTable table;

    public displayFilmsFrame() {
        super("Films");
        setSize(600,400);
        JScrollPane panel = new JScrollPane();
        panel.setBounds(0,0,580,300);
        panel.setBackground(Color.WHITE);

        ResultSet rs = Database.getDatabase().loadFilms();
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
            Audit.write("Display Films",Thread.currentThread().getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        setLayout(null);
        setVisible(true);
    }
}

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
                data.get(i).add(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.UK));
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

class sellTicketFrame extends JFrame {
    JList list;
    JScrollPane listChooser;
    JPanel panel;
    JButton sellButton;
    JLabel labelProgramId;
    JSpinner programId;
    JLabel filmName;
    JLabel theatre;
    JLabel date;

    public sellTicketFrame() {
        super("Sell Ticket");
        setSize(600,400);

        String[] data = {
                        "7/1","7/2","7/3","7/4","7/5","7/6","7/7","7/8","7/9","7/10 ","7/11 ","7/12 ","7/13 ","7/14 ",
                        "6/1","6/2","6/3","6/4","6/5","6/6","6/7","6/8","6/9","6/10 ","6/11 ","6/12 ","6/13 ","6/14 ",
                        "5/1","5/2","5/3","5/4","5/5","5/6","5/7","5/8","5/9","5/10 ","5/11 ","5/12 ","5/13 ","5/14 ",
                        "4/1","4/2","4/3","4/4","4/5","4/6","4/7","4/8","4/9","4/10 ","4/11 ","4/12 ","4/13 ","4/14 ",
                        "3/1","3/2","3/3","3/4","3/5","3/6","3/7","3/8","3/9","3/10 ","3/11 ","3/12 ","3/13 ","3/14 ",
                        "2/1","2/2","2/3","2/4","2/5","2/6","2/7","2/8","2/9","2/10 ","2/11 ","2/12 ","2/13 ","2/14 ",
                        "1/1","1/2","1/3","1/4","1/5","1/6","1/7","1/8","1/9","1/10 ","1/11 ","1/12 ","1/13 ","1/14 "};

        list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        listChooser = new JScrollPane(list);
        listChooser.setPreferredSize(new Dimension(250, 80));
        listChooser.setBounds(160,0,400,300);
        add(listChooser);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(150,300));
        panel.setBounds(5,0,150,300);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelProgramId = new JLabel("Program id");
        panel.add(labelProgramId);
        programId = new JSpinner(new SpinnerNumberModel(0,0,Program.getTotalNumberPrograms(),1));
        programId.addChangeListener(changeEvent -> {
           int id = (int) ((JSpinner)changeEvent.getSource()).getValue();
           Program p = Database.getDatabase().getProgramById(id);
            if (p != null) {
                String fName = Database.getDatabase().getFilmNameById(p.getFilm_id());
                filmName.setText(fName);
                date.setText(p.getDate().toString());
                theatre.setText(String.valueOf("theatre " + p.getTheatre()));
            }
        });
        programId.setMaximumSize(new Dimension(30,20));
        panel.add(programId);

        filmName = new JLabel();
        panel.add(filmName);
        date = new JLabel();
        panel.add(date);
        theatre = new JLabel();
        panel.add(theatre);

        sellButton = new JButton("Sell");
        sellButton.addActionListener(actionEvent -> {
            try {
                if (list.getSelectedIndex() < 0) throw new RuntimeException("Nici un loc selectat");
                Object[] rowAndSeat = Arrays.stream(data[list.getSelectedIndex()].split("/")).map(s -> {return Integer.valueOf(s);}).toArray();
                Ticket t = new Ticket((int)programId.getValue(),(int)rowAndSeat[0],(int)rowAndSeat[1]);

                Database.getDatabase().sellTicket(t);
                Audit.write("Sold Ticket",Thread.currentThread().getName());
            } catch (SQLException | RuntimeException e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(sellButton);
        add(panel);

        setLayout(null);
        setVisible(true);
    }


}

class deleteFrame extends JFrame {
    JLabel idLabel;
    JTextField idField;
    JRadioButton film;
    JRadioButton program;
    JRadioButton ticket;
    ButtonGroup group;
    JButton delete;

    public deleteFrame() {
        super("Delete something");
        setSize(600, 400);

        idLabel = new JLabel("Object id");
        idLabel.setBounds(200,50,100,20);
        add(idLabel);
        idField = new JTextField();
        idField.setBounds(200,80,50,20);
        add(idField);

        film = new JRadioButton("Film");
        film.setBounds(200,120,100,20);
        program = new JRadioButton("Program");
        program.setBounds(200,140,100,20);
        ticket = new JRadioButton("Ticket");
        ticket.setBounds(200,160,100,20);

        group = new ButtonGroup();
        group.add(film); group.add(program); group.add(ticket);
        add(film); add(program); add(ticket);

        delete = new JButton("DELETE");
        delete.setBounds(200,200,100,20);
        delete.addActionListener(actionEvent -> {
            try {
                int id = Integer.parseInt(idField.getText());
                try {
                    int res = 0;
                    if (film.isSelected()) res = Database.getDatabase().deleteFilmbyId(id);
                    if (program.isSelected()) res = Database.getDatabase().deleteProgrambyId(id);
                    if (ticket.isSelected()) res =  Database.getDatabase().deleteTicketbyId(id);
                    if (res > 0) {
                        JOptionPane.showMessageDialog(this,"Element deleted","Succes",JOptionPane.INFORMATION_MESSAGE);
                        Audit.write("Delete item",Thread.currentThread().getName());
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this,e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,"A number must be entered!","Eroare",JOptionPane.ERROR_MESSAGE);
            }
        });
        add(delete);

        setLayout(null);
        setVisible(true);
    }
}