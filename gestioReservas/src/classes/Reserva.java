package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
	LocalDate diaEntrada, diaSortida;
	int numNits, numPersones;
	Habitacio habitacio;
	Client client;

	public LocalDate getDiaSortida() {
		return diaSortida;
	}

	public void setDiaSortida(LocalDate diaSortida) {
		this.diaSortida = diaSortida;
	}
	
	public void calculaDiaSortida() {
		this.diaSortida = diaEntrada.plusDays(numNits);
	}

	public Reserva(Client client) {
		this.client = client;
	}

	public LocalDate getDiaEntrada() {
		return diaEntrada;
	}

	public void setDiaEntrada(LocalDate diaEntrada) {
		this.diaEntrada = diaEntrada;
	}

	public int getNumNits() {
		return numNits;
	}

	public void setNumNits(int numNits) {
		this.numNits = numNits;
	}

	public int getNumPersones() {
		return numPersones;
	}

	public void setNumPersones(int numPersones) {
		this.numPersones = numPersones;
	}

	public Habitacio getHabitacio() {
		return habitacio;
	}

	public void setHabitacio(Habitacio habitacio) {
		this.habitacio = habitacio;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String[] reservaToArray() {
		String[] array = new String[4];
		array[0] = this.diaEntrada.getDayOfMonth() + "/" + this.diaEntrada.getMonthValue() + "/"
				+ this.diaEntrada.getYear();
		array[1] = client.getDni();
		array[2] = Integer.toString(this.numPersones);
		array[3] = Integer.toString(this.habitacio.getNumHabitacio());
		return array;
	}



}