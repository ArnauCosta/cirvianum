package finestra;

import classes.*;
import finestra.*;
import java.awt.Color;
import control.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import classes.Hotel;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

public class Back{
	
	static final String font = "Liberation Serif";
	static Border borderInCorrecte;
	static JButton guardaNom;
	static JTextField tbNomH;



    protected static void panellBack(JPanel panellBack) {
    	botoBack(panellBack);
    	borderBack(panellBack);
    	textFieldBack(panellBack);
		labelsBack(panellBack);
		llistaBack(panellBack);
		listenersBack(panellBack);
	}
    
	private static void listenersBack(JPanel panellBack) {

		ActionListener listenerBotoHotel = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((JFrame) SwingUtilities.windowForComponent(panellBack)).setTitle(tbNomH.getText()); // aixo es per utilitzar el frame en el que estan els panells des de l'arxiu del label.
				Control.crearHotel(tbNomH.getText());
			}
		};
		
		guardaNom.addActionListener(listenerBotoHotel);
		
		
	}

	private static void borderBack(JPanel panellBack) {
        borderInCorrecte = BorderFactory.createLineBorder(Color.RED, 1);
	}

	private static void botoBack(JPanel panellBack) {
		guardaNom = new JButton("Guarda");
		guardaNom.setBounds(20, 100, 320, 30);
		panellBack.add(guardaNom);
		
		JButton guardaHabi = new JButton("Guarda");
		guardaHabi.setBounds(20, 205, 320, 30);
		panellBack.add(guardaHabi);
		
		JButton jbElimina = new JButton("Elimina");
		jbElimina.setBounds(20, 530, 320, 30);
		panellBack.add(jbElimina);
	}

	private static void textFieldBack(JPanel panellBack) {
        tbNomH = new JTextField();
        tbNomH.setBounds(160,65,180,25);
        panellBack.add(tbNomH);
        
        
        JTextField tbNum = new JTextField();
        tbNum.setBounds(70, 170, 100,25);
        panellBack.add(tbNum);
        
        JTextField tbPers = new JTextField();
        tbPers.setBounds(240, 170, 100,25);
        panellBack.add(tbPers);
        
        JTextField tbNomC = new JTextField();
        tbNomC.setBounds(130, 280, 210, 20);
        panellBack.add(tbNomC);
	}

	private static void llistaBack(JPanel panellBack) {
		
        DefaultListModel<String> listModel = new DefaultListModel<String>(); //    IMPORTANT, canviar de string a usuaris quan s'implementin
        
		JList<String> jlClient = new JList<String>(listModel);
        jlClient.setBounds(20, 310, 150, 205);
        panellBack.add(jlClient);
		
		
		JList<String> jlHabitacio = new JList<String>(listModel);
        jlHabitacio.setBounds(190, 310, 150, 205);
        panellBack.add(jlHabitacio);
		
	}

	private static void labelsBack(JPanel panellBack) {

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
}