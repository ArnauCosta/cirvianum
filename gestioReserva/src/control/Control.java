package control;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import classes.*;
import com.toedter.calendar.JDateChooser;
import finestra.*;

public class Control {

    static Border borderInCorrecte = BorderFactory.createLineBorder(Color.RED, 1);
    static Hotel hotel = new Hotel("Hotel");
    static int numPers = 1; // numero de persones maximes d'una habitacio a la que podran accedir un grup de persones.
    static Fitxer f = new Fitxer(hotel);
    static ArrayList<Integer> idRef =  f.idRef(); // aquest array es fara servir per relacionar la llista de reservas no confirmades amb el seu id

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
        hotel.setNomHotel("nomHotel");
        f.desaNomHotel(nomHotel);
    }

    public Hotel getHotel() {return hotel;}

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
            idRef.add(novaReserva.getIdReserva());
            f.desaIdRef(novaReserva.getIdReserva());
            hotel.addReserva(novaReserva);
            f.desaReserva(novaReserva);
        } else {
            JOptionPane.showMessageDialog(null, "Habitacio no trovada, no es fara la reserva");
        }

    }

    public Habitacio comprovaHabitacions(Reserva reserva) {
        for (int i = 0; i < numPers + 1; i++) {
            for (Habitacio h : hotel.getLlistaHabitacio()) {
                if (h.getNumPersonesMax() == reserva.getNumPersones() + i) {
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
            client.setNom(Finestra.getInfoClient()[1]);
            client.setCog(Finestra.getInfoClient()[2]);
            hotel.addNouClient(client);
            f.desaClient(client);
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
        char lletres[] = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
                'H', 'L', 'C', 'K', 'E'};
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
                if (((JTextField) e.getComponent()).getBorder().equals(borderInCorrecte)) {
                    Finestra.returnNomClient().setText(null);
                    Finestra.returnNomClient().setEnabled(true);
                    Finestra.returnCogClient().setText(null);
                    Finestra.returnCogClient().setEnabled(true);
                } else {
                    comprovaClientExistent(((JTextField) e.getComponent()).getText());
                }
                comprovaValidesaDniICanviaBorder(((JTextField) e.getComponent()).getText(), (JTextField) e.getComponent());
                break;

            case "tbNom":       // ja que el nom i el cognom utilitzen el mateix regex, he utilitzat el case del nom sensa posar un break, aixi tambe utilitzara el regex.

            case "tbCog":
                setBorderIfRegex("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", (JTextField) e.getComponent());
                break;

            case "tbNumP":

            case "tbNumN":
                setBorderIfRegex("^([1-9]|[1-9][0-9]|100)$", (JTextField) e.getComponent()); // reservas i habitacions de 1 a 100 persones
                break;
        }
    }

    public static LocalDate arrayToLocalDate(String[] arrayData) {
        int[] arrayInt = new int[3];
        for (int i = 0; i < arrayData.length; i++) {
            arrayInt[i] = Integer.parseInt(arrayData[i]);
        }
        return LocalDate.of(arrayInt[0], arrayInt[1], arrayInt[2]);
    }


    public static Client getClientByDni(String dni) {
        for (Client c : hotel.getLlistaClients()) {
            if(c.getDni().equals(dni)){
                return c;
            }
        }
        return null;
    }

    public static Habitacio getHabitacioByNum (int numHab) {
        for (Habitacio h : hotel.getLlistaHabitacio()) {
            if(h.getNumHabitacio()==numHab){
                return h;
            }
        }
        return null;
    }

    public void comprovaClientExistent(String dni) {
        for (Client c : hotel.getLlistaClients()) {
            if (c.getDni().equals(dni)) {
                Finestra.returnNomClient().setText(c.getNom());
                Finestra.returnNomClient().setEnabled(false);

                Finestra.returnCogClient().setText(c.getCog());
                Finestra.returnCogClient().setEnabled(false);
                break;
            }
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

    public void canviBorderCrearHabitacio(JTextField camp) {
        if (camp.getText().isEmpty()) {
            camp.setBorder(UIManager.getBorder("TextField.border"));
        } else if (!comprovaSiStringEsNumeric(camp.getText())) {
            camp.setBorder(borderInCorrecte);
        } else {
            camp.setBorder(UIManager.getBorder("TextField.border"));
        }
    }

    public boolean comprovaSiStringEsNumeric(String text) {
        if (text.matches("^[0-9]+")) {
            return true;
        }
        return false;
    }

    public void creaHabitacio(int numHabitacio, int numPersones) {

        Habitacio habitacio = hotel.getHabitacio(numHabitacio);
        if (habitacio != null) {
            int opcio = JOptionPane.showConfirmDialog(null, "El numero d'habitacio ja existeix. Capacitat Actual: "
                    + habitacio.getNumHabitacio() + " persones. Vols Actualitzar-la");
            switch (opcio) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Habitacio actualitzada correctament");
                    habitacio.setNumPersonesMax(numPersones);
                    f.actualitzaHabitacio(numHabitacio, numPersones);
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
            JOptionPane.showMessageDialog(null, "Habitacio afegida correctament");
            f.desaHabitacio(habitacio);
        }
    }

    public boolean comprovaHabitacio(Reserva reserva) {
        for (Reserva r : hotel.getLlistaReserva()) {
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

    public void eliminarReservaPendent(MouseEvent e, JTable taula, DefaultTableModel model) {
        if (e.getClickCount() == 2) {
            int a = taula.rowAtPoint(e.getPoint());
            int id = idRef.get(a);
            String[] botons = {"Confirmar-la", "Descartar-la", "Cancelar"};
            int opcio = JOptionPane.showOptionDialog(null, "Que vols fer amb aquesta reserva?", "", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, botons, botons[0]);

            switch (opcio) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Reserva confirmada correctament");

                    Finestra.getBotoDateChooser().setDate(new Date());
                    cercaReservaPerId(id).setConfirmada(true);
                    refreshReservaConfirmada(Finestra.getModelC(), Finestra.getBotoSE().getModel().isSelected(), getLocalDateFromJCalendar(Finestra.getBotoDateChooser().getJCalendar()));
                    model.removeRow(a);
                    idRef.remove(a);
                    f.eliminaIdRef(id);
                    f.eliminaReserva(id);
                    f.desaReserva(cercaReservaPerId(id));

                    break;
                case 1:
                    model.removeRow(a);
                    hotel.eliminaReserva(id);
                    idRef.remove(a);
                    f.eliminaIdRef(id);
                    f.eliminaReserva(id);
                    JOptionPane.showMessageDialog(null, "Reserva eliminada correctament");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Operacio cancelada");
                    break;
            }
        }
    }

    public Reserva cercaReservaPerId(int id) {
        for (Reserva r : hotel.getLlistaReserva()) {
            if (r.getIdReserva() == id) {
                return r;
            }
        }
        return null;
    }

    public void refreshReservaConfirmada(DefaultTableModel modelC, boolean entradaSortida, LocalDate data) {
        modelC.setRowCount(0);
        //entradaSortida:    true = sortida       false = entrada
        for (Reserva r : hotel.getLlistaReserva()) {
            if (r.isConfirmada()) {
                if (entradaSortida) {
                    if (r.getDiaSortida().equals(data)) {
                        modelC.addRow(r.reservaToArray());
                    }
                } else {
                    if (r.getDiaEntrada().equals(data)) {
                        modelC.addRow(r.reservaToArray());
                    }
                }
            }
        }
    }

    public void refreshReservaPendent(DefaultTableModel model) {
        model.setRowCount(0);
        //entradaSortida:    true = sortida       false = entrada
        for (Integer id : idRef) {
            model.addRow(cercaReservaPerId(id).reservaToArray());
        }
    }


    public void cercaClientPerNom(String text, DefaultListModel<Client> listModelC) {
        for (Client c : hotel.getLlistaClients()) {
            if (!text.isEmpty()) {
                if (c.toStringClie().contains(text)) {
                    listModelC.addElement(c);
                }
            }
        }
    }

    public void ompleReservesDeClient(Client c, DefaultListModel<Reserva> listModelR) {
        for (Reserva r : hotel.getLlistaReserva()) {
            if (r.getClient().equals(c)) {
                listModelR.addElement(r);
            }
        }
    }

    public void eliminaReserva(Reserva r) {
        int i = 0;
        for (int v : idRef) {
            if (v == r.getIdReserva()) {
                i = idRef.indexOf(v);
            }
        }

        if (r.isConfirmada()) {
            hotel.getLlistaReserva().remove(r);
            f.eliminaReserva(idRef.get(i));
            refreshReservaConfirmada(Finestra.getModelC(), Finestra.getBotoSE().getModel().isSelected(), getLocalDateFromJCalendar(Finestra.getBotoDateChooser().getJCalendar()));
        } else {
            Finestra.returnModelP().removeRow(i);
            f.eliminaIdRef(idRef.get(i));
            f.eliminaReserva(idRef.get(i));
            idRef.remove(i);
            hotel.getLlistaReserva().remove(r);
        }
    }
}


