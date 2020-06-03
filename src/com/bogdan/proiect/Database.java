package com.bogdan.proiect;

import java.sql.*;

public class Database {
    private static Database instance = null;
    private Connection connection;
    private Database(){}

    public static Database getDatabase(){
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                    try {
                        String dbURL = "jdbc:oracle:thin:@193.226.51.37:1521:o11g";
                        String username = "grupa41";
                        String password = "bazededate";
                        instance.connection = DriverManager.getConnection(dbURL, username, password);
                        Statement stm = instance.connection.createStatement();

                        ResultSet rs = stm.executeQuery("SELECT COUNT(*) NR FROM FILMS");
                        rs.next();
                        Film.setTotalNumberFilms(rs.getInt("NR"));

                        rs = stm.executeQuery("SELECT COUNT(*) NR FROM PROGRAM");
                        rs.next();
                        Program.setTotalNumberPrograms(rs.getInt("NR"));

                        rs = stm.executeQuery("SELECT COUNT(*) NR FROM TICKETS");
                        rs.next();
                        Ticket.setTotalNumberTickets(rs.getInt("NR"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    public static void exit() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        return getDatabase().connection;
    }

    public <T extends Film> void saveFilm(T film) throws SQLException{
        PreparedStatement stm = film.getSqlStatement();
        stm.execute();
    }

    public <T extends Film> ResultSet loadFilms() {
        try {
            Statement stm = this.connection.createStatement();
            return stm.executeQuery("SELECT * FROM FILMS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFilmNameById(int id) {
        try {
            PreparedStatement stm = this.connection.prepareStatement("SELECT NAME FROM FILMS WHERE FILM_ID = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                return rs.getString("NAME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ResultSet loadProgram() {
        try {
            Statement stm = this.connection.createStatement();
            return stm.executeQuery("SELECT PROGRAM_ID, START_DATE, NAME, ROUND(START_DATE-SYSDATE) DAY FROM PROGRAM JOIN FILMS USING(FILM_ID) WHERE START_DATE BETWEEN SYSDATE AND SYSDATE+7 ORDER BY START_DATE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveProgram(Program p) throws SQLException {
        PreparedStatement stm = this.connection.prepareStatement("INSERT INTO PROGRAM(program_id,start_date,theatre_id,film_id) VALUES (?,?,?,?)");
        stm.setInt(1,p.getProgram_id());
        stm.setDate(2,p.getDate());
        stm.setInt(3,p.getTheatre());
        stm.setInt(4,p.getFilm_id());
        stm.execute();
    }

    public Program getProgramById(int id) {
        try {
            PreparedStatement stm = this.connection.prepareStatement("SELECT * FROM PROGRAM WHERE PROGRAM_ID = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                return new Program(rs.getInt("PROGRAM_ID"),rs.getDate("START_DATE"),rs.getInt("THEATRE_ID"),rs.getInt("FILM_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sellTicket(Ticket t) throws SQLException {
        PreparedStatement stm = this.connection.prepareStatement("INSERT INTO TICKETS(ticket_id,program_id,row_num,seat_num) VALUES(?,?,?,?)");
        stm.setInt(1,t.getTicket_id());
        stm.setInt(2,t.getProgram_id());
        stm.setInt(3,t.getRow());
        stm.setInt(4,t.getSeat());
        stm.execute();
    }

    public ResultSet loadTickets() {
        try {
            Statement stm = this.connection.createStatement();
            return stm.executeQuery("SELECT * FROM TICKETS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteFilmbyId(int id) throws SQLException {
        PreparedStatement stm = this.connection.prepareStatement("DELETE FROM FILMS WHERE FILM_ID = ?");
        stm.setInt(1,id);
        return stm.executeUpdate();
    }

    public int deleteProgrambyId(int id) throws SQLException {
        PreparedStatement stm = this.connection.prepareStatement("DELETE FROM PROGRAM WHERE PROGRAM_ID = ?");
        stm.setInt(1,id);
        return stm.executeUpdate();
    }

    public int deleteTicketbyId(int id) throws SQLException {
        PreparedStatement stm = this.connection.prepareStatement("DELETE FROM TICKETS WHERE TICKET_ID = ?");
        stm.setInt(1,id);
        return stm.executeUpdate();
    }

}
