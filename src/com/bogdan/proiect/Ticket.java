package com.bogdan.proiect;

import java.util.Date;

public class Ticket {
    private static Integer totalNumberTickets = 0;
    private Integer number;
    private Film film;
    private int theatre;
    private Seat seat;
    private Date date;

    private class Seat{
        int rowNumber;
        int seatNumber;

        public Seat(Integer rowNumber, Integer seatNumber) {
            this.rowNumber = rowNumber;
            this.seatNumber = seatNumber;
        }

        @Override
        public String toString() {
            return "rowNumber=" + rowNumber +
                    ", seatNumber=" + seatNumber;
        }
    }

    public Ticket(Film film, Integer theatre, Integer rowNumber, Integer seatNumber, Date date) {
        this.number = totalNumberTickets++;
        this.film = film;
        this.theatre = theatre;
        this.seat = new Seat(rowNumber,seatNumber);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number=" + number +
                ", film=" + film +
                ", theatre=" + theatre +
                ", seat=" + seat +
                ", date=" + date +
                '}';
    }
}
