package control;

import finestra.*;
import classes.*;
import classes.Client;
import finestra.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JCalendar;

public class Control {

	static Border borderInCorrecte = BorderFactory.createLineBorder(Color.RED, 1);
	static Hotel hotel;

	public static void setBorderIfRegex(String regex, JTextField textField) { // aquesta funcio se li pasa una regex i
																				// un text field i si el contingut del
																				// text field no compleix la regex posa
																				// un border.
		if (textField.getText().matches(regex)) {
			textField.setBorder(UIManager.getBorder("TextField.border"));
		} else {
			textField.setBorder(borderInCorrecte);
		}

	}

	public static void crearHotel(String nomHotel) {
		hotel = new Hotel(nomHotel);
	}

	public static void afegirGestio() {

		Client clientRegistre = retornaClientPerUtilitzar(Finestra.getInfoClient()[0]);

		// Creacio de una nova reserva la qual es desara en el array de dintre hotel.
		Reserva novaReserva = new Reserva(clientRegistre);
		Object[] arrayReserva = Finestra.getInfoReserva();
		novaReserva.setDiaEntrada((LocalDate)arrayReserva[2]);
//		novaReserva.setHabitacio(habitacio);     aqui afegire la habitacio, pero de moment no las he creat, aixi que ho comento ja que encara no utilitzare habitacions.
		novaReserva.setNumNits(Integer.parseInt((String)arrayReserva[1]));
		novaReserva.setNumPersones(Integer.parseInt((String)arrayReserva[0]));
		hotel.setReservaP(novaReserva);
		Finestra.afegeixReservaPendent(novaReserva.reservaToArray());
	}

	public static Client retornaClientPerUtilitzar(String dni) {
		Client client = existeixClient(dni);
		if (client == null) {
			// si el client no existeix es creara i es desara en el array de dintre hotel.
			client = new Client(dni);
			client.setCog(Finestra.getInfoClient()[1]);
			client.setNom(Finestra.getInfoClient()[2]);
			hotel.setNouClient(client);
		}

		return client;
	}

	public static LocalDate getLocalDateFromJCalendar(JCalendar calendari) {
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

	public static void comprovaValidesaDniICanviaBorder(String dni, JTextField textField) {
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

	public static Border getBorderIncorrecte() {
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

}