package com.bogdan.proiect;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilmHistorical extends Film{
    private final String sub_genre;
    private String period;

    public FilmHistorical(String name, int year, String director, Rating rating, String sub_genre, String period) {
        super(name, year, director, rating, "Historical");
        this.sub_genre = sub_genre; // war, biographic, alternate-history
        this.period = period; // medieval, WW1, WW2
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("  sub-genre: ");
        stringBuilder.append(sub_genre);
        stringBuilder.append("  Historical period: ");
        stringBuilder.append(period);
        return stringBuilder.toString();
    }

    @Override
    public PreparedStatement getSqlStatement() throws SQLException {
        PreparedStatement stm = super.getSqlStatement();
        stm.setString(7,sub_genre);
        stm.setString(8,period);
        return stm;
    }
}
