package com.bogdan.proiect;

import javax.swing.*;
import java.sql.SQLException;

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
