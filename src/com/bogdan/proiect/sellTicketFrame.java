package com.bogdan.proiect;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

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
