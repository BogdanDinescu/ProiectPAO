package com.bogdan.proiect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

enum Rating{
    AG, // Acord General
    AP12, // Acord Parental. Permis peste 12 ani
    R15, // Restrictionat. Permis peste 15 ani
    IM // Interzis minorilor
}
public class Film {
    private static int totalNumberFilms = 0;
    private int id;
    private String name;
    private int year;
    private String director;
    private Rating rating;
    private final String genre;

    public Film(String name, Integer year, String director, Rating rating,String genre) {
        this.id = ++totalNumberFilms;
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.genre = genre;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("id:");
        stringBuilder.append(id);
        stringBuilder.append(" ");
        stringBuilder.append(name);
        String newline = System.lineSeparator();
        stringBuilder.append(newline);
        stringBuilder.append("Year: ");
        stringBuilder.append(year);
        stringBuilder.append(newline);
        stringBuilder.append("Director: ");
        stringBuilder.append(director);
        stringBuilder.append("  Rating: ");
        stringBuilder.append(rating);
        stringBuilder.append(newline);
        stringBuilder.append("Genre: ");
        stringBuilder.append(genre);
        return stringBuilder.toString();
    }

    public int id(){
        return id;
    }

    public static void setTotalNumberFilms(int totalNumberFilms) {
        Film.totalNumberFilms = totalNumberFilms;
    }

    public PreparedStatement getSqlStatement() throws SQLException {
        PreparedStatement stm = Database.getConnection().prepareStatement("INSERT INTO FILMS (film_id,name,year,director,rating,genre,sub_genre,extra_info) VALUES(?,?,?,?,?,?,?,?)");
        stm.setInt(1,id);
        stm.setString(2,name);
        stm.setInt(3,year);
        stm.setString(4,director);
        stm.setString(5,rating.toString());
        stm.setString(6,genre);
        stm.setNull(7, Types.NULL);
        stm.setNull(8, Types.NULL);
        return stm;
    }

    public boolean isPermitedAtAge(int age){
        boolean result = false;
        switch (this.rating){
            case AG:
                result = true;
                break;
            case AP12:
                result = age >= 12;
                break;
            case R15:
                result = age >= 15;
                break;
            case IM:
                result = age >= 18;
                break;
        }
        return result;
    }
}
