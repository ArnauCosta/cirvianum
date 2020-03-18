package finestra;

import java.awt.Color;
import finestra.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

public class Finestra extends JFrame {

    public JPanel panell;
    public JPanel[] panells = new JPanel[3];

    public Finestra() {
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

    private void components() {
        afegeixPanell();
        
        Gestio.panellGestio((JPanel) getContentPane().getComponent(0));
        Client.panellClients((JPanel) getContentPane().getComponent(1));
        Back.panellBack((JPanel) getContentPane().getComponent(2));
    }
    
    private void afegeixPanell() {
    	int altura = this.getHeight();
    	int amplada = this.getWidth()/3;
    	
    	for (int i = 0; i < panells.length; i++) {
            panell = new JPanel();
            panell.setBackground(Color.LIGHT_GRAY);
            panell.setLayout(null);
            panell.setBounds(amplada*i, 0, amplada-2, altura);
            this.getContentPane().add(panell);
		}   
    }
}
 
	