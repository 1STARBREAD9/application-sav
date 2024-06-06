package org.example.view;

import org.example.model.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EtatTicket {
    private JFrame frame;
    private String[] columnNames;
    private DefaultTableModel tableModel;
    private JTable tableauTickets;
    private JScrollPane scrollPane;
    private JLabel nombreTicketsLabel;
    private JButton fermerButton;

    public EtatTicket() {
        frame = new JFrame("État des tickets");
        columnNames = new String[]{"Nom", "Prénom", "Téléphone", "Réclamation", "Date", "Numéro de ticket", "État du ticket"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableauTickets = new JTable(tableModel);
        scrollPane = new JScrollPane(tableauTickets);
        nombreTicketsLabel = new JLabel("Nombre de tickets : 0"); // TODO - get number of tickets from db
        fermerButton = new JButton("Fermer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.add(scrollPane);
        frame.add(nombreTicketsLabel, BorderLayout.SOUTH);
        frame.add(fermerButton, BorderLayout.EAST);

        initListeners();
    }

    private void initListeners() {
        fermerButton.addActionListener(e -> {
            frame.dispose();
        });
    }

    public void updateTable(ArrayList<Ticket> tickets, int numberOfTickets) {
        for (Ticket ticket : tickets) {
            tableModel.addRow(new Object[]{ticket.getNom(), ticket.getPrenom(), ticket.getTelephone(), ticket.getReclamation(), ticket.getDate(), ticket.getNumeroTicket(), ticket.getEtatTicket()});
        }
        nombreTicketsLabel.setText("Nombre de tickets : " + numberOfTickets);
    }

    public void display() {
        frame.setVisible(true);
    }
}
