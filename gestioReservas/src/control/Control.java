package control;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JCalendar;

import classes.*;
import finestra.*;

public class Control {

	static Border borderInCorrecte = BorderFactory.createLineBorder(Color.RED, 1);
	static Hotel hotel;
	static int numPers = 1; // numero de persones maximes d'una habitacio a la que podran accedir un grup de
							// persones.

	public void setBorderIfRegex(String regex, JTextField textField) { // aquesta funcio se li pasa una regex i
																		// un text field i si el contingut del
																		// text field no compleix la regex posa
																		// un border.
		if (textField.getText().matches(regex)) {
			textField.setBorder(UIManager.getBorder("TextField.border"));
		} else {
			textField.setBorder(borderInCorrecte);
		}

	}

	public void crearHotel(String nomHotel) {
		hotel = new Hotel(nomHotel);
	}

	public void afegirGestio() {

		Client clientRegistre = retornaClientPerUtilitzar(Finestra.getInfoClient()[0]);

		// Creacio de una nova reserva la qual es desara en el array de dintre hotel.
		Reserva novaReserva = new Reserva(clientRegistre);
		Object[] arrayReserva = Finestra.getInfoReserva();
		novaReserva.setDiaEntrada((LocalDate) arrayReserva[2]);
		novaReserva.setNumNits(Integer.parseInt((String) arrayReserva[1]));
		novaReserva.setNumPersones(Integer.parseInt((String) arrayReserva[0]));
		novaReserva.calculaDiaSortida();
		Habitacio habitacio = comprovaHabitacions(novaReserva);
		if (habitacio != null) {
			novaReserva.setHabitacio(habitacio);
			Finestra.afegeixReservaPendent(novaReserva.reservaToArray());
			hotel.addReservaP(novaReserva);
		} else {
			JOptionPane.showMessageDialog(null, "Habitació no trovada, no es fara la reserva");
		}
	}

	public Habitacio comprovaHabitacions(Reserva reserva) {
		for (int i = 0; i < numPers; i++) {
			for (Habitacio h : hotel.getLlistaHabitacio()) {
				System.out.println("Habitacio per entrar");
				if (h.getNumPersonesMax() == reserva.getNumPersones() + i) {

					System.out.println("Habitacio ha entrat habitacio");

					reserva.setHabitacio(h);
					if (comprovaHabitacio(reserva)) {
						return h;
					}
				}
			}
		}

		return null;
	}

	public static Client retornaClientPerUtilitzar(String dni) {
		Client client = existeixClient(dni);
		if (client == null) {
			// si el client no existeix es creara i es desara en el array de dintre hotel.
			client = new Client(dni);
			client.setCog(Finestra.getInfoClient()[1]);
			client.setNom(Finestra.getInfoClient()[2]);
			hotel.addNouClient(client);
		}

		return client;
	}

	public LocalDate getLocalDateFromJCalendar(JCalendar calendari) {
		long ms = calendari.getDate().getTime();
		return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Client existeixClient(String dni) {

		for (Client client : hotel.getLlistaClients()) {
			if (client.getDni().equals(dni)) {
				return client;
			}
		}
		return null;
	}

	public void comprovaValidesaDniICanviaBorder(String dni, JTextField textField) {
		String numDniString = dni.substring(0, 8);
		int numDni = Integer.parseInt(numDniString);
		char lletres[] = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
				'H', 'L', 'C', 'K', 'E' };
		int modulNumDni = numDni % 23;
		char lletra = dni.charAt(8);
		if (!(lletres[modulNumDni] == Character.toUpperCase(lletra))) {
			textField.setBorder(borderInCorrecte);
		}

	}

	public void comprovaCampsClient(KeyEvent e) {
		switch (e.getComponent().getName()) {
		case "tbDni":
			setBorderIfRegex("^[0-9]{8,8}[A-Za-z]$", (JTextField) e.getComponent());
			comprovaValidesaDniICanviaBorder(((JTextField) e.getComponent()).getText(), (JTextField) e.getComponent());
			break;

		case "tbNom":
			setBorderIfRegex("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", (JTextField) e.getComponent());
			break;

		case "tbCog":
			setBorderIfRegex("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", (JTextField) e.getComponent());
			break;

		case "tbNumP":
			setBorderIfRegex("^([1-9]|[1-9][0-9]|100)$", (JTextField) e.getComponent()); // reservas de 1 a 100 persones
			break;

		case "tbNumN":
			setBorderIfRegex("^([1-9]|[1-9][0-9]|100)$", (JTextField) e.getComponent()); // reservas de 1 a 100
																							// habitacions
			break;

		}
	}

	public void comprovaTotsElsCampsSonCorrectes(JPanel panellClient, JButton reserva) {
		boolean totCorrecte = true;
		for (Component component : panellClient.getComponents()) {
			if (component instanceof JTextField) {
				if ((((JTextField) component).getText().isEmpty())) { // comprova si esta buit, si ho esta, escontara
																		// malament encara que no tingui el border
																		// incorrecte
					totCorrecte = false;
					break;
				}
				if (((JTextField) component).getBorder().equals(getBorderIncorrecte())) {
					totCorrecte = false;
					break;
				}
			}
		}
		if (totCorrecte) {
			reserva.setEnabled(true);
		} else {
			reserva.setEnabled(false);
		}
	}

	public Border getBorderIncorrecte() {
		return borderInCorrecte;
	}

	public static Component getComponentByName(String nom, JPanel panellClient) { // aquesta part de codi no s'utilitza,
																					// pero potser util mes endevant.
		for (Component component : panellClient.getComponents()) { // Primer volia utilitzarla per tindre el switch case
																	// de client en el fitxer de control, pero vaig
																	// tindre problemes.
			if (component.getName().equals(nom)) {
				return component;
			}
		}
		return null;
	}

	public void creaHabitacio(int numHabitacio, int numPersones) {

		Habitacio habitacio = hotel.cercaHabitacioPerNum(numHabitacio);
		if (habitacio != null) {
			int opcio = JOptionPane.showConfirmDialog(null, "El número d'habitació ja existeix. Capacitat Actual:"
					+ habitacio.getNumHabitacio() + " persones. Vols Actualitzar-la");
			switch (opcio) {
			case 0:
				JOptionPane.showMessageDialog(null, "Habitació actualitzada correctament");
				habitacio.setNumPersonesMax(numPersones);
				break;
			case 1:
				break;
			case 2:
				break;
			}

		} else {
			habitacio = new Habitacio(numHabitacio);
			habitacio.setNumPersonesMax(numPersones);
			hotel.addHabitacio(habitacio);
			JOptionPane.showMessageDialog(null, "Habitació afegida correctament");
		}
	}

	public boolean comprovaHabitacio(Reserva reserva) {
		for (Reserva r : hotel.getLlistaReservaP()) {
			if (r.getHabitacio().getNumHabitacio() == reserva.getHabitacio().getNumHabitacio()) {

				if (reserva.getDiaEntrada().equals(r.getDiaEntrada())) {
					return false;
				} else if (reserva.getDiaEntrada().isAfter(r.getDiaEntrada())
						&& reserva.getDiaEntrada().isBefore(r.getDiaSortida())) {
					return false;
				} else if (reserva.getDiaSortida().isAfter(r.getDiaSortida())
						&& reserva.getDiaSortida().isBefore(r.getDiaSortida())) {
					return false;
				} else if (reserva.getDiaEntrada().isBefore(r.getDiaEntrada())
						&& reserva.getDiaSortida().isAfter(r.getDiaSortida())) {
					return false;
				}

			}
		}

		return true;

	}

}