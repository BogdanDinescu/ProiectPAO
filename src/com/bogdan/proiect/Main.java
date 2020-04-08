package com.bogdan.proiect;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.films.add(new FilmSF("Star Wars - A New Hope",1977,"George Lucas",Rating.AP12,"Space Opera","RayPunk"));
        app.films.add(new FilmHistorical("Save Private Ryan",1998,"Steven Spielberg",Rating.AP12,"War","WW2"));
        app.start();
    }
}
