package com.bogdan.proiect;

public class FilmAction extends Film{

    private final String sub_genre;

    public FilmAction(String director, String name, Rating rating, String sub_genre , Integer year) {
        super(name, year, director, rating,"Action");
        this.sub_genre = sub_genre; //Spy,superhero,disaster,martial arts, etc...
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("  sub-genre: ");
        stringBuilder.append(sub_genre);
        return stringBuilder.toString();
    }

    public String getSub_genre() {
        return sub_genre;
    }
}
