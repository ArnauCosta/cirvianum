package classes;

import java.util.ArrayList;

public class Hotel {

    private String nomHotel;
    private ArrayList<Habitacio> llistaHabitacio = new ArrayList<Habitacio>();
    private ArrayList<Client> llistaClients;
    private ArrayList<Reserva> llistaReserva;


    public Hotel(String nomHotel) {
        this.nomHotel = nomHotel;
        llistaHabitacio = new ArrayList<Habitacio>();
        llistaClients = new ArrayList<Client>();
        llistaReserva = new ArrayList<Reserva>();
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public ArrayList<Habitacio> getLlistaHabitacio() {
        return llistaHabitacio;
    }

    public void setLlistaHabitacio(ArrayList<Habitacio> llistaHabitacio) {
        this.llistaHabitacio = llistaHabitacio;
    }

    public ArrayList<Client> getLlistaClients() {
        return llistaClients;
    }

    public void setLlistaClients(ArrayList<Client> llistaClients) {
        this.llistaClients = llistaClients;
    }

    public ArrayList<Reserva> getLlistaReserva() {
        return llistaReserva;
    }

    public void setLlistaReservaP(ArrayList<Reserva> llistaReservaP) {
        this.llistaReserva = llistaReservaP;
    }

    public void addReserva(Reserva ReservaP) {
        this.llistaReserva.add(ReservaP);
    }

    public void addNouClient(Client client) {
        this.llistaClients.add(client);
    }

    public void eliminaReserva(int i) {this.llistaReserva.remove(i);}

    public void addHabitacio(Habitacio habitacio) {
        this.llistaHabitacio.add(habitacio);
    }

    public Habitacio getHabitacio(int numHabitacio) {
        for (Habitacio h : this.getLlistaHabitacio()) {
            if (h.getNumHabitacio() == numHabitacio) {
                return h;
            }
        }
        return null;
    }
}