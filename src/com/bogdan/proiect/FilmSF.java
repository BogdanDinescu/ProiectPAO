package com.bogdan.proiect;

public class FilmSF extends Film {
    private final String sub_genre;
    private final String landscape;

    public FilmSF(String name, Integer year, String director, Rating rating, String sub_genre, String landscape) {
        super(name, year, director, rating, "Science Fiction");
        this.sub_genre = sub_genre; // dystopian, utopian
        this.landscape = landscape; // cyberpunk, steampunk, atomicpunk
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("  sub-genre: ");
        stringBuilder.append(sub_genre);
        stringBuilder.append(" ");
        stringBuilder.append(landscape);
        return stringBuilder.toString();
    }
}
