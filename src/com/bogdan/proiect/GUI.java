package com.bogdan.proiect;

import javax.swing.*;
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

