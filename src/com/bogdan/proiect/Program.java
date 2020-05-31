package com.bogdan.proiect;

import java.sql.Date;
import java.util.Objects;

public class Program {
    private static int totalNumberPrograms = 0;
    private int program_id;
    private Date date;
    private int theatre;
    private int film_id;

    public Program(Date date, int theatre, int film_id) {
        this.program_id = ++totalNumberPrograms;
        this.date = date;
        this.theatre = theatre;
        this.film_id = film_id;
    }

    public Program(int program_id, Date date, int theatre, int film_id) {
        this.program_id = program_id;
        this.date = date;
        this.theatre = theatre;
        this.film_id = film_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return theatre == program.theatre &&
                Objects.equals(date, program.date);
    }

    public static void setTotalNumberPrograms(int number) {
        totalNumberPrograms = number;
    }

    public static int getTotalNumberPrograms() {
        return totalNumberPrograms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, theatre);
    }

    public Program(Date date, int theatre) {
        this.date = date;
        this.theatre = theatre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTheatre() {
        return theatre;
    }

    public void setTheatre(int theatre) {
        this.theatre = theatre;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getProgram_id() {
        return program_id;
    }
}
