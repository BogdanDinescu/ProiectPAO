package com.bogdan.proiect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Program {
    private Date date;
    private int theatre;

    public Program(){
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        boolean ok;
        do {
            ok = false;
            try {
                System.out.println("Data (zz/LL/aaaa HH)");
                String dateString = scanner.nextLine();
                dateString = dateString+":00:00";
                this.date = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Format incorect");
                ok = true;
            }
        } while (ok);
        System.out.println("Numarul salii:");
        this.theatre = scanner.nextInt();
        scanner.nextLine();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return theatre == program.theatre &&
                Objects.equals(date, program.date);
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
}
