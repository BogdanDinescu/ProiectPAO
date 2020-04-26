package com.bogdan.proiect;


import java.util.*;

public class App {
    ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    HashMap<Program,Film> program = new HashMap<Program,Film>();
    ArrayList<Film> films = new ArrayList<Film>();

    public App() {
        System.out.println("Aplicatia s-a pornit");
        Database.getDatabase().loadData(films,"films.csv");
        Audit.write("Opened App");
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean ok = true;
        do {
            System.out.println("MENIU");
            System.out.println("1. Vezi filme");
            System.out.println("2. Adauga filme");
            System.out.println("3. Vezi program");
            System.out.println("4. Adauga program");
            System.out.println("5. Vezi bilete");
            System.out.println("6. Vinde bilet");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    displayFilms();
                    break;
                case 2:
                    addFilm();
                    break;
                case 3:
                    displayProgram();
                    break;
                case 4:
                    addProgram();
                    break;
                case 5:
                    displayTickets();
                    break;
                case 6:
                    sellTicket();
                    break;
                default:
                    System.out.println("Optiune incorecta");
            }

            System.out.println("Continuati?(true/false)");
            try {
                ok = scanner.nextBoolean();
                scanner.nextLine();
            } catch (InputMismatchException e){
                ok = false;
            }
        }while (ok);
        System.out.println("Salvare...");
        saveData();
        System.out.println("La revedere!");
        Audit.write("Exit App");
    }

    public void addProgram() {
        Scanner scanner = new Scanner(System.in);
        Film film = null;
        do {
            System.out.println("Film id");
            int id = scanner.nextInt();
            scanner.nextLine();
            film = getFilmById(id);
        }while (film == null);
        program.put(new Program(),film);
        Audit.write("Add Program");
    }

    public void displayProgram(){
        for(HashMap.Entry<Program,Film> entry:program.entrySet()){
            if(entry.getKey().getDate().after(new Date())){
                StringBuilder stringBuilder = new StringBuilder(entry.getKey().getDate().toString());
                stringBuilder.append(" theatre ");
                stringBuilder.append(entry.getKey().getTheatre());
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(entry.getValue());
                System.out.println(stringBuilder.toString());
            }
        }
        Audit.write("Display Program");
    }

    public void sellTicket(){
        boolean ok = false;
        Program p;
        do {
            ok = false;
            p = new Program();
            if (!program.containsKey(p)) {
                System.out.println("Nu exista film la ora aceea sau in sala aceea");
                ok = true;
            }
        } while (ok);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rand");
        int row = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Loc");
        int seat = scanner.nextInt();
        scanner.nextLine();
        tickets.add(new Ticket(program.get(p),p.getTheatre(),row,seat,p.getDate()));
        Audit.write("Sold Ticket");
    }

    public Film getFilmById(int id){
        Film rfilm = null;
        for(Film film:films){
            if(film.id() == id) {
                rfilm = film;
                break;
            }
        }
        return rfilm;
    }

    public void displayTickets(){
        for(Ticket ticket:tickets){
            System.out.println(ticket);
        }
        Audit.write("Display Tickets");
    }


    public void addFilm() throws IllegalStateException{
        Film film;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nume:");
        String name = scanner.nextLine();
        System.out.println("An:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Director:");
        String director = scanner.nextLine();
        String ratingString;
        Rating rating = Rating.AG;
        boolean ok = false;
        do {
            System.out.println("Rating:");
            ratingString = scanner.next();
            try {
                rating = Rating.valueOf(ratingString);
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
                continue;
            }
            ok = true;
        }while (!ok);

        scanner.nextLine();
        System.out.println("Gen");
        String genre = scanner.nextLine().toLowerCase();
        System.out.println("Sub gen:");
        String sub_genre = scanner.nextLine();
        switch (genre){
            case "action":
                film = new FilmAction(director,name,rating,sub_genre,year);
                break;
            case "sf":
                System.out.println("landscape:");
                String landscape = scanner.next();
                film = new FilmSF(director,landscape,name,rating,sub_genre,year);
                break;
            case "historical":
                System.out.println("Perioada:");
                String period = scanner.next();
                film = new FilmHistorical(director,period,name,rating,sub_genre,year);
                break;
            case "comedy":
                film = new FilmComedy(director,name,rating,sub_genre,year);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + genre);
        }
        films.add(film);
        Audit.write("Added Film");
    }

    public void displayFilms(){
        for(Film film:films){
            System.out.println(film);
            System.out.println("");
        }
        Audit.write("Display Films");
    }

    public void saveData(){
        Database.getDatabase().saveData(films,"films.csv");
    }
}
