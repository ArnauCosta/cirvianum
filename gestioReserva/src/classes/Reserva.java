package classes;

import java.time.LocalDate;

public class Reserva {
    static int reservaIdCounter = 0; //servirea per incrementar el id de reserva en cada reserva que es fagi.
    LocalDate diaEntrada, diaSortida;
    int numNits, numPersones, idReserva; //es el id amb els que s'identificaran las reserves, s'utilitzara per buscarles a l'array i a las llistas
    Habitacio habitacio;
    Client client;
    boolean confirmada;


    public Reserva(Client client) {
        this.client = client;
        this.confirmada = false;
        this.idReserva = reservaIdCounter++;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public LocalDate getDiaSortida() {
        return diaSortida;
    }

    public void setDiaSortida(LocalDate diaSortida) {
        this.diaSortida = diaSortida;
    }

    public void calculaDiaSortida() {
        this.diaSortida = diaEntrada.plusDays(numNits);
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
        String[] array = new String[5];
        array[0] = this.diaEntrada.getDayOfMonth() + "/" + this.diaEntrada.getMonthValue() + "/"
                + this.diaEntrada.getYear();
        array[1] = client.getDni();
        array[2] = Integer.toString(this.numPersones);
        array[3] = Integer.toString(this.habitacio.getNumHabitacio());
        array[4] = Integer.toString(this.idReserva);
        return array;
    }

    @Override
    public String toString() {
        return this.idReserva+" | Hab: "+this.habitacio.getNumHabitacio()+" | "+this.diaEntrada;
    }

}