package com.bogdan.proiect;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilmComedy extends Film{

    private final String sub_genre;

    public FilmComedy(String name, int year, String director, Rating rating, String sub_genre) {
        super(name, year, director, rating, "Comedy");
        this.sub_genre = sub_genre; // slapstick, dark comedy ...
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("  sub-genre: ");
        stringBuilder.append(sub_genre);
        return stringBuilder.toString();
    }

    @Override
    public PreparedStatement getSqlStatement() throws SQLException {
        PreparedStatement stm = super.getSqlStatement();
        stm.setString(7,sub_genre);
        return stm;
    }
}
