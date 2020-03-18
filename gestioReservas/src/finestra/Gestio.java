package finestra;

import java.awt.Color;
import control.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

public class Gestio{
	
	static final String font = "Liberation Serif";
	static DefaultTableModel model;
	static JTable taula;
	
	protected static void panellGestio(JPanel panellGestio) {
		labelsGestio(panellGestio);
		taulaGestio(panellGestio);
		dateGestio(panellGestio);
		listenersGestio(panellGestio);
	}

	private static void listenersGestio(JPanel panellGestio) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static void dateGestio(JPanel panellGestio) {
		JDateChooser data = new JDateChooser();
		data.setBounds(210, 320, 120, 20);
		panellGestio.add(data);
	}

	private static void taulaGestio(JPanel panellGestio) {
		// taula pendents
		
        model = new DefaultTableModel();
        model.addColumn("Reserva");
        model.addColumn("Dia");
        model.addColumn("Persones");
        model.addColumn("Habitació");

        taula = new JTable(model);
        taula.setBounds(30,100,300,200);
        panellGestio.add(taula);
        
        
        JScrollPane scroll = new JScrollPane(taula,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30,100,300,200);
        panellGestio.add(scroll);
        
        // taula confirmades
        
        DefaultTableModel modelConfirm = new DefaultTableModel();
        modelConfirm.addColumn("Reserva");
        modelConfirm.addColumn("Dia");
        modelConfirm.addColumn("Persones");
        modelConfirm.addColumn("Habitació");

        JTable taulaConfirm = new JTable(modelConfirm);
        taulaConfirm.setBounds(30,360,300,200);
        panellGestio.add(taulaConfirm);
        
        
        JScrollPane scrollConfirm = new JScrollPane(taulaConfirm,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollConfirm.setBounds(30,360,300,200);
        panellGestio.add(scrollConfirm);
	}

	private static void labelsGestio(JPanel panellGestio) {
		
        JLabel jlGestio = new JLabel("Gestió");
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
	
	public static void afegeixReservaPendent(String[] reserva) {
		model.addRow(reserva);
	}
	
}
