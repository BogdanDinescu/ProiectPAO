package com.bogdan.proiect;

import javax.swing.*;
import java.util.Arrays;

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
