package classes;

import java.util.ArrayList;

public class Hotel{
	
	String nomHotel;
	ArrayList<Habitacio> llistaHabitacio = new ArrayList<Habitacio>();
	ArrayList<Client> llistaClients;
	ArrayList<Reserva> llistaReservaP, llistaReservaC;
	
	
	
	public Hotel(String nomHotel) {
		this.nomHotel = nomHotel;
		llistaHabitacio = new ArrayList<Habitacio>();
		llistaClients = new ArrayList<Client>();
		llistaReservaP = new ArrayList<Reserva>();
		llistaReservaC = new ArrayList<Reserva>();
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
	public ArrayList<Reserva> getLlistaReservaP() {
		return llistaReservaP;
	}
	public void setLlistaReservaP(ArrayList<Reserva> llistaReservaP) {
		this.llistaReservaP = llistaReservaP;
	}
	public ArrayList<Reserva> getLlistaReservaC() {
		return llistaReservaC;
	}
	public void setLlistaReservaC(ArrayList<Reserva> llistaReservaC) {
		this.llistaReservaC = llistaReservaC;
	}
	
	public void setReservaC(Reserva ReservaC) {
		this.llistaReservaC.add(ReservaC);
	}
	
	public void setReservaP(Reserva ReservaP) {
		this.llistaReservaP.add(ReservaP);
	}
	
	public void setNouClient(Client client) {
		this.llistaClients.add(client);
	}
	
	public void setHabitacio (Habitacio habitacio) {
		this.llistaHabitacio.add(habitacio);
	}
	

	
}