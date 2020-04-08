package com.bogdan.proiect;

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

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setYear(int year) {
        this.year = year;
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
