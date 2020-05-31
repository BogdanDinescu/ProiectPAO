package com.bogdan.proiect;

public class Ticket {
    private static int totalNumberTickets = 0;
    private int ticket_id;
    private int program_id;
    private Seat seat;

    private class Seat{
        int rowNumber;
        int seatNumber;

        public Seat(int rowNumber, int seatNumber) {
            this.rowNumber = rowNumber;
            this.seatNumber = seatNumber;
        }

        @Override
        public String toString() {
            return "rowNumber=" + rowNumber +
                    ", seatNumber=" + seatNumber;
        }
    }

    public Ticket( int program_number, int rowNumber, int seatNumber) {
        this.ticket_id = ++totalNumberTickets;
        this.program_id = program_number;
        this.seat = new Seat(rowNumber,seatNumber);
    }

    public static void setTotalNumberTickets(int number) {
        totalNumberTickets = number;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", program_id=" + program_id +
                ", seat=" + seat +
                '}';
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public int getRow() {
        return seat.rowNumber;
    }

    public int getSeat() {
        return seat.seatNumber;
    }

}
