package com.bogdan.proiect;

public class FilmComedy extends Film{

    private final String sub_genre;

    public FilmComedy(String name, Integer year, String director, Rating rating, String sub_genre) {
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
}
