package com.bogdan.proiect;

public class FilmHistorical extends Film{
    private final String sub_genre;
    private String period;

    public FilmHistorical(String name, Integer year, String director, Rating rating, String sub_genre, String period) {
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
}