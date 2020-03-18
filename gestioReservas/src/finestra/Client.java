package finestra;

import control.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

public class Client{
	
	static final String font = "Liberation Serif";
	static JTextField tbDni, tbNom, tbCog, tbNumP, tbNumN;
	static JButton reserva;
	
	protected static void panellClients(JPanel panellClient) {
		labelsClients(panellClient);
		textFieldClients(panellClient);
		botonsClients(panellClient);
		calendariClients(panellClient);
		listenerClients(panellClient);
	}
	

	private static void listenerClients(JPanel panellClient) {
		
		
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


				switch (e.getComponent().getName()) {
				case "tbDni":
					Control.setBorderIfRegex("^[0-9]{8,8}[A-Za-z]$", tbDni);
					Control.comprovaValidesaDniICanviaBorder(tbDni.getText(), tbDni); //comprova el dni, si la lletra es correcte no fa res, si la lletra no es correcte posa el border en vermell
					break;
					
				case "tbNom":
					Control.setBorderIfRegex("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",  tbNom);
					break;
					
				case "tbCog":
					Control.setBorderIfRegex("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",  tbCog);
					break;
					
				case "tbNumP":
					Control.setBorderIfRegex("^([1-9]|[1-9][0-9]|100)$",  tbNumP); // reservas de 1 a 100 persones
					break;
					
				case "tbNumN":
					Control.setBorderIfRegex("^([1-9]|[1-9][0-9]|100)$", tbNumN); // reservas de 1 a 100 habitacions
					break;
								
				}
				
				
				boolean totCorrecte = true;
				for (Component component : panellClient.getComponents()) {
					if(component instanceof JTextField) {
						if((((JTextField) component).getText().isEmpty())) {  // comprova si esta buit, si ho esta, escontara malament encara que no tingui el border incorrecte
							totCorrecte = false;
							break;
						}
						if(((JTextField) component).getBorder().equals(Control.getBorderIncorrecte())) {
							totCorrecte = false;
							break;
						}
					}
				}
				if(totCorrecte) {
					reserva.setEnabled(true);
				}else {
					reserva.setEnabled(false);
				}
				
				
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
				Control.afegirGestio(panellClient);
				clearTextFieldClients(panellClient);
			}
		};
		reserva.addActionListener(listenerReserva);


		
	}


	private static void botonsClients(JPanel panellClient) {
		reserva = new JButton("Reserva");
		reserva.setBounds(20, 520, 320, 40);
		reserva.setEnabled(false);
		panellClient.add(reserva);
	}

	private static void calendariClients(JPanel panellClient) {
		JCalendar calendari = new JCalendar();
		calendari.setBounds(20,280,320,230);
		calendari.setName("calendari");
		panellClient.add(calendari);
	}

	private static void textFieldClients(JPanel panellClient) {
		
        tbDni = new JTextField();
        tbDni.setBounds(160,65,180,25);
        tbDni.setName("tbDni");
        panellClient.add(tbDni);
        
        tbNom = new JTextField();
        tbNom.setBounds(160,95,180,25);
        tbNom.setName("tbNom");
        panellClient.add(tbNom);
        
        tbCog = new JTextField();
        tbCog.setBounds(160,125,180,25);
        tbCog.setName("tbCog");
        panellClient.add(tbCog);
        
        tbNumP = new JTextField();
        tbNumP.setBounds(160,155,60,25);
        tbNumP.setName("tbNumP");
        panellClient.add(tbNumP);
        
        tbNumN = new JTextField();
        tbNumN.setBounds(160,185,60,25);
        tbNumN.setName("tbNumN");
        panellClient.add(tbNumN);
	}
	private static void clearTextFieldClients(JPanel panellClient) {
		for (Component component : panellClient.getComponents()) {	
			if(component instanceof JTextField) {
				((JTextField) component).setText(null);
			}else if(component instanceof JCalendar) {
				((JCalendar)component).setDate(null);
			}
		}
	}

	private static void labelsClients(JPanel panellClient) {		
		
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
}
