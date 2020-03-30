package finestra;

import java.awt.Color;

import classes.Client;
import classes.Reserva;
import finestra.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import control.Control;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class Finestra extends JFrame {

    // variables globals
    static final String font = "Liberation Serif";
    public static JPanel panellGestio, panellClient, panellBack;
    static Control c;
    public JPanel panell;
    public JPanel[] panells = new JPanel[3];

    // variables gestio
    static DefaultTableModel model, modelC;
    static JTable taula, taulaConfirm;
    static JToggleButton selectorSortidaEntrada;
    static JCalendar calendari;
    static JDateChooser data;

    // variables Client
    static JTextField tbDni, tbNom, tbCog, tbNumP, tbNumN;
    static JButton reserva;

    // variables back
    static Border borderInCorrecte;
    static JButton guardaNom, guardaHabi, jbElimina;
    static JTextField tbNomH, tbNum, tbPers, tbNomC;
    static JList<Client> jlClient;
    static JList<Reserva> jlHabitacio;
    static DefaultListModel<Client> listModelC;
    static DefaultListModel<Reserva> listModelR;
    static JScrollPane scrollClient, scrollReserva;

    public Finestra() {
        c = new Control();
        this.setVisible(true);
        this.setSize(1100, 620);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Reservas");
        this.setResizable(true);
        this.getContentPane().setBackground(Color.black);
        this.setLocationRelativeTo(null);
        components();
    }

    // Panell gestio

    protected static void panellGestio() {
        labelsGestio();
        taulaGestio();
        botonsGestio();
        dateGestio();
        listenersGestio();
    }

    private static void botonsGestio() {
        selectorSortidaEntrada = new JToggleButton("Entrada");
        selectorSortidaEntrada.setBounds(210, 330, 120, 25);
        panellGestio.add(selectorSortidaEntrada);
    }

    private static void listenersGestio() {


        MouseListener listenerClickRow = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                c.eliminarReservaPendent(e, taula, model);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
        taula.addMouseListener(listenerClickRow);

        ActionListener listenerToggleButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectorSortidaEntrada.getModel().isSelected()){
                    selectorSortidaEntrada.setText("Sortida");
                }else{
                    selectorSortidaEntrada.setText("Entrada");
                }
                c.refreshReservaConfirmada(modelC, selectorSortidaEntrada.getModel().isSelected(), c.getLocalDateFromJCalendar(data.getJCalendar()));
            }
        };
        selectorSortidaEntrada.addActionListener(listenerToggleButton);

        PropertyChangeListener canviData = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                c.refreshReservaConfirmada(modelC, selectorSortidaEntrada.getModel().isSelected(), c.getLocalDateFromJCalendar(data.getJCalendar()));
            }
        };
        data.addPropertyChangeListener(canviData);

    }


    private static void dateGestio() {
        data = new JDateChooser();
        data.setBounds(210, 305, 120, 20);
        panellGestio.add(data);
    }

    private static void taulaGestio() {


        // Models per las dos taulas

        model = new DefaultTableModel();
        model.addColumn("Dia");
        model.addColumn("Dni");
        model.addColumn("Persones");
        model.addColumn("Habitacio");

        modelC = new DefaultTableModel();
        modelC.addColumn("Dia");
        modelC.addColumn("Dni");
        modelC.addColumn("Persones");
        modelC.addColumn("Habitacio");


        // taula pendents

        taula = new JTable(model);
        taula.setBounds(30, 100, 300, 200);
        taula.setEnabled(false);
        panellGestio.add(taula);

        JScrollPane scroll = new JScrollPane(taula, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30, 100, 300, 200);
        panellGestio.add(scroll);

        // taula confirmades

        taulaConfirm = new JTable(modelC);
        taulaConfirm.setBounds(30, 360, 300, 200);
        taulaConfirm.setEnabled(false);
        panellGestio.add(taulaConfirm);

        JScrollPane scrollConfirm = new JScrollPane(taulaConfirm, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollConfirm.setBounds(30, 360, 300, 200);
        panellGestio.add(scrollConfirm);
    }

    public static DefaultTableModel returnModelC(){return modelC;}
    public static DefaultTableModel returnModelP(){return model;}

    private static void labelsGestio() {

        JLabel jlGestio = new JLabel("Gestio");
        jlGestio.setFont(new Font(font, Font.BOLD, 25));
        jlGestio.setBounds(0, 0, panellGestio.getWidth(), 60);
        jlGestio.setHorizontalAlignment(SwingConstants.CENTER);
        jlGestio.setForeground(Color.black);

        JLabel jlPendents = new JLabel("Reserves pendents");
        jlPendents.setBounds(30, 70, 300, 20);
        jlPendents.setForeground(Color.black);
        jlPendents.setFont(new Font(font, Font.BOLD, 17));

        JLabel jlConfirmat = new JLabel("Reserves confirmades");
        jlConfirmat.setBounds(30, 320, 300, 20);
        jlConfirmat.setForeground(Color.black);
        jlConfirmat.setFont(new Font(font, Font.BOLD, 17));

        panellGestio.add(jlGestio);
        panellGestio.add(jlPendents);
        panellGestio.add(jlConfirmat);
    }

    public static JToggleButton getBotoSE() {return selectorSortidaEntrada;}
    public static JDateChooser getBotoDateChooser() {return data;}
    public static DefaultTableModel getModelC() {return modelC;}



    public static void afegeixReservaPendent(String[] reserva) {
        model.addRow(reserva);
    }

    // Fi panell gestio

    // panell client


    protected static void panellClients() {
        labelsClients();
        textFieldClients();
        botonsClients();
        calendariClients();
        listenerClients();
    }

    private static void listenerClients() {


        KeyListener listenerTextBox = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {

                //setBorderIfRegex es una funcio que se li pasa una regex i un text field i si el contingut del text field no compleix la regex posa un border

                c.comprovaCampsClient(e);
                c.comprovaTotsElsCampsSonCorrectes(panellClient, reserva);

            }
        };

        tbDni.addKeyListener(listenerTextBox);
        tbNom.addKeyListener(listenerTextBox);
        tbCog.addKeyListener(listenerTextBox);
        tbNumP.addKeyListener(listenerTextBox);
        tbNumN.addKeyListener(listenerTextBox);


        ActionListener listenerReserva = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                c.afegirGestio();
                clearTextFieldClients();
                reserva.setEnabled(false);
            }
        };
        reserva.addActionListener(listenerReserva);


    }



    private static void botonsClients() {
        reserva = new JButton("Reserva");
        reserva.setBounds(20, 520, 320, 40);
        reserva.setEnabled(false);
        panellClient.add(reserva);
    }

    private static void calendariClients() {
        calendari = new JCalendar();
        calendari.setBounds(20, 280, 320, 230);
        calendari.setName("calendari");
        calendari.setMinSelectableDate(Calendar.getInstance().getTime());
        panellClient.add(calendari);
    }

    private static void textFieldClients() {

        tbDni = new JTextField();
        tbDni.setBounds(160, 65, 180, 25);
        tbDni.setName("tbDni");
        panellClient.add(tbDni);

        tbNom = new JTextField();
        tbNom.setBounds(160, 95, 180, 25);
        tbNom.setName("tbNom");
        panellClient.add(tbNom);

        tbCog = new JTextField();
        tbCog.setBounds(160, 125, 180, 25);
        tbCog.setName("tbCog");
        panellClient.add(tbCog);

        tbNumP = new JTextField();
        tbNumP.setBounds(160, 155, 60, 25);
        tbNumP.setName("tbNumP");
        panellClient.add(tbNumP);

        tbNumN = new JTextField();
        tbNumN.setBounds(160, 185, 60, 25);
        tbNumN.setName("tbNumN");
        panellClient.add(tbNumN);
    }

    private static void clearTextFieldClients() {
        for (Component component : panellClient.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            }
        }
    }

    private static void labelsClients() {

        JLabel jlClient = new JLabel("Clients");
        jlClient.setFont(new Font(font, Font.BOLD, 25));
        jlClient.setBounds(0, 0, panellClient.getWidth(), 60);
        jlClient.setHorizontalAlignment(SwingConstants.CENTER);
        jlClient.setForeground(Color.black);
        panellClient.add(jlClient);

        JLabel jlDni = new JLabel("Dni:");
        jlDni.setBounds(20, 50, 60, 60);
        jlDni.setForeground(Color.black);
        jlDni.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlDni);

        JLabel jlNom = new JLabel("Nom:");
        jlNom.setBounds(20, 80, 60, 60);
        jlNom.setForeground(Color.black);
        jlNom.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlNom);

        JLabel jlCog = new JLabel("Cognom:");
        jlCog.setBounds(20, 110, 300, 60);
        jlCog.setForeground(Color.black);
        jlCog.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlCog);

        JLabel jlNumP = new JLabel("Num. Persones:");
        jlNumP.setBounds(20, 140, 300, 60);
        jlNumP.setForeground(Color.black);
        jlNumP.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlNumP);

        JLabel jlNumN = new JLabel("Num. Nits:");
        jlNumN.setBounds(20, 170, 300, 60);
        jlNumN.setForeground(Color.black);
        jlNumN.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlNumN);


        JLabel jlData = new JLabel("Data d'entrada:");
        jlData.setBounds(20, 220, 300, 60);
        jlData.setForeground(Color.black);
        jlData.setFont(new Font(font, Font.BOLD, 17));
        panellClient.add(jlData);
    }

    public static String[] getInfoClient() {    // aquesta funcio retorna la informacio del client (dni, nom, cognom) en un array simple de strings
        String[] array = new String[3];
        array[0] = tbDni.getText();
        array[1] = tbNom.getText();
        array[2] = tbCog.getText();
        return array;
    }

    public static Object[] getInfoReserva() {    // aquesta funcio retorna la informacio del client (dni, nom, cognom) en un array simple de strings
        Object[] array = new Object[4];            //Es un array de objectes ja que retornara un ahabitacio i un localdate
        array[0] = tbNumP.getText();
        array[1] = tbNumN.getText();
        array[2] = c.getLocalDateFromJCalendar(calendari);
        // aqui hauria de afegir tambe a l'array la habitacio pero com encara no la he creat no la afegire.
        return array;
    }


    // Fi panell client


    // panell back


    protected static void panellBack() {
        botoBack();
        borderBack();
        textFieldBack();
        labelsBack();
        llistaBack();
        listenersBack();
    }

    private static void listenersBack() {

        //listener boto de crear hotel

        ActionListener listenerBotoHotel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.windowForComponent(panellBack)).setTitle(tbNomH.getText()); // aixo es per utilitzar el frame en el que estan els panells des de l'arxiu del label.
                c.crearHotel(tbNomH.getText());
            }
        };
        guardaNom.addActionListener(listenerBotoHotel);


        //listener crear habitacio

        ActionListener listenerCrearHabitacio = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.creaHabitacio(Integer.parseInt(tbNum.getText()), Integer.parseInt(tbPers.getText()));
                clearHabitacio();
            }
        };
        guardaHabi.addActionListener(listenerCrearHabitacio);

        //Lintener refrsca clients cercats a la llista de clients

        KeyListener listenerConsultaR = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                listModelC.removeAllElements();
                c.cercaClientPerNom(tbNomC.getText(), listModelC);
                jlClient.setSelectedIndex(0);
            }
        };
        tbNomC.addKeyListener(listenerConsultaR);

        //listener canvi de client seleccionat a la llista de clients

        ListSelectionListener listenerClientSeleccionat = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listModelR.removeAllElements();
                c.ompleReservesDeClient(jlClient.getSelectedValue(),listModelR);
                jbElimina.setEnabled(false);
            }
        };
        jlClient.addListSelectionListener(listenerClientSeleccionat);

        ListSelectionListener listenerReservaSeleccionada = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jbElimina.setEnabled(true);
            }
        };
        jlHabitacio.addListSelectionListener(listenerReservaSeleccionada);

        //Listener boto eliminar

        ActionListener listenerElimina = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.ompleReservesDeClient(jlClient.getSelectedValue(),listModelR);
                c.eliminaReserva(jlHabitacio.getSelectedValue());
            }
        };
        jbElimina.addActionListener(listenerElimina);

    }



    private static void borderBack() {
        borderInCorrecte = BorderFactory.createLineBorder(Color.RED, 1);
    }

    public static void clearHabitacio() {
        tbNum.setText(null);
        tbPers.setText(null);
    }

    private static void botoBack() {
        guardaNom = new JButton("Guarda");
        guardaNom.setBounds(20, 100, 320, 30);
        panellBack.add(guardaNom);

        guardaHabi = new JButton("Guarda");
        guardaHabi.setBounds(20, 205, 320, 30);
        panellBack.add(guardaHabi);

        jbElimina = new JButton("Elimina");
        jbElimina.setBounds(20, 530, 320, 30);
        jbElimina.setEnabled(false);
        panellBack.add(jbElimina);
    }

    private static void textFieldBack() {
        tbNomH = new JTextField();
        tbNomH.setBounds(160, 65, 180, 25);
        panellBack.add(tbNomH);


        tbNum = new JTextField();
        tbNum.setBounds(70, 170, 100, 25);
        panellBack.add(tbNum);

        tbPers = new JTextField();
        tbPers.setBounds(240, 170, 100, 25);
        panellBack.add(tbPers);

        tbNomC = new JTextField();
        tbNomC.setBounds(130, 280, 210, 20);
        panellBack.add(tbNomC);
    }

    private static void llistaBack() {


        //Model llista

        listModelC = new DefaultListModel<Client>();
        listModelR = new DefaultListModel<Reserva>();

        // Scroll llista

        scrollClient = new JScrollPane(jlClient, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollClient.setBounds(20, 310, 150, 205);

        scrollReserva = new JScrollPane(jlHabitacio, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollReserva.setBounds(190, 310, 150, 205);

        //LLista

        jlClient = new JList<Client>(listModelC);
        jlClient.setBounds(20, 310, 150, 205);
        jlClient.add(scrollClient);
        panellBack.add(jlClient);


        jlHabitacio = new JList<Reserva>(listModelR);
        jlHabitacio.setBounds(190, 310, 150, 205);
        jlHabitacio.add(scrollReserva);
        panellBack.add(jlHabitacio);


    }

    private static void labelsBack() {

        JLabel jlBack = new JLabel("Back");
        jlBack.setFont(new Font(font, Font.BOLD, 25));
        jlBack.setBounds(0, 0, panellBack.getWidth(), 60);
        jlBack.setHorizontalAlignment(SwingConstants.CENTER);
        jlBack.setForeground(Color.black);
        panellBack.add(jlBack);

        JLabel jlNomH = new JLabel("Nom Hotel:");
        jlNomH.setBounds(20, 70, 300, 20);
        jlNomH.setForeground(Color.black);
        jlNomH.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlNomH);

        JLabel jlRegistre = new JLabel("Registre nova habitacio");
        jlRegistre.setBounds(20, 140, 300, 20);
        jlRegistre.setForeground(Color.black);
        jlRegistre.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlRegistre);

        JLabel jlNum = new JLabel("Num.");
        jlNum.setBounds(20, 170, 300, 20);
        jlNum.setForeground(Color.black);
        jlNum.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlNum);


        JLabel jlPers = new JLabel("Pers.");
        jlPers.setBounds(190, 170, 300, 20);
        jlPers.setForeground(Color.black);
        jlPers.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlPers);

        JLabel jlRes = new JLabel("Consulta Reserva.");
        jlRes.setBounds(20, 245, 300, 20);
        jlRes.setForeground(Color.black);
        jlRes.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlRes);

        JLabel jlNomC = new JLabel("Nom Client:");
        jlNomC.setBounds(20, 280, 300, 20);
        jlNomC.setForeground(Color.black);
        jlNomC.setFont(new Font(font, Font.BOLD, 17));
        panellBack.add(jlNomC);

    }

    private void components() {
        afegeixPanell();

        panellGestio = panells[0];
        panellClient = panells[1];
        panellBack = panells[2];

        panellGestio();
        panellClients();
        panellBack();

    }

    private void afegeixPanell() {
        int altura = this.getHeight();
        int amplada = this.getWidth() / 3;

        for (int i = 0; i < panells.length; i++) {
            panell = new JPanel();
            panell.setBackground(Color.LIGHT_GRAY);
            panell.setLayout(null);
            panell.setBounds(amplada * i, 0, amplada - 2, altura);
            panells[i] = panell;
            this.getContentPane().add(panell);
        }
    }

    //Fi panell back

}
