package org.example.controller;

import org.example.database.DatabaseManager;
import org.example.model.Ticket;
import org.example.service.TicketNumberService;
import org.example.service.TicketService;
import org.example.view.EtatTicket;
import org.example.view.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainScreenController {
    private MainScreen mainScreen;
    private DatabaseManager databaseManager;
    private TicketService ticketService;

    public MainScreenController() {
        this.databaseManager = new DatabaseManager();
        this.ticketService = new TicketService(databaseManager);
        this.mainScreen = new MainScreen();
        initListeners();
        ticketService.fetchAllTickets();
        mainScreen.updateTable(ticketService.getAllTickets());
        mainScreen.setVisible(true);
    }

    private void initListeners() {
        mainScreen.getEnvoyerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket ticket = new Ticket();
                TicketNumberService ticketNumberService = new TicketNumberService(databaseManager);
                ticket.setNom(mainScreen.getNomField().getText());
                ticket.setPrenom(mainScreen.getPrenomField().getText());
                ticket.setTelephone(mainScreen.getTelephoneField().getText());
                ticket.setReclamation(mainScreen.getReclamationArea().getText());
                ticket.setDate(mainScreen.getDateField().getText());
                ticket.setNumeroTicket(ticketNumberService.generateTicketNumber());
                ticket.setEtatTicket(mainScreen.getEtatTicketComboBox().getSelectedItem().toString());

                ticketService.sendTicket(ticket);

                mainScreen.addTicketToTable(ticket);
            }
        });

        // TODO - add verification if it is deleted from db
        mainScreen.getSupprimerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = mainScreen.getTableau().getSelectedRow();
                if (selectedRow != -1) {
                    int ticketNumber = Integer.parseInt((String) mainScreen.getTableau().getValueAt(selectedRow, 5));
                    ticketService.deleteTicket(ticketNumber);
                    mainScreen.removeTicketFromTable(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(mainScreen, "Veuillez sélectionner un ticket à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainScreen.getActualiserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketService.fetchAllTickets();
                mainScreen.updateTable(ticketService.getAllTickets());
            }
        });

        mainScreen.getMettreAJourButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = mainScreen.getTableau().getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve the ticket number from the selected row in the Swing table model
                    int numeroTicket = Integer.parseInt(mainScreen.getTableau().getValueAt(selectedRow, 5).toString());

                    // Retrieve the data from the selected row
                    String nom = mainScreen.getTableau().getValueAt(selectedRow, 0).toString();
                    String prenom = mainScreen.getTableau().getValueAt(selectedRow, 1).toString();
                    String telephone = mainScreen.getTableau().getValueAt(selectedRow, 2).toString();
                    String reclamation = mainScreen.getTableau().getValueAt(selectedRow, 3).toString();
                    String date = mainScreen.getTableau().getValueAt(selectedRow, 4).toString();
                    String etatTicket = mainScreen.getTableau().getValueAt(selectedRow, 6).toString();

                    // Create a new Ticket object with the updated data
                    Ticket updatedTicket = new Ticket();
                    updatedTicket.setNom(nom);
                    updatedTicket.setPrenom(prenom);
                    updatedTicket.setTelephone(telephone);
                    updatedTicket.setReclamation(reclamation);
                    updatedTicket.setDate(date);
                    updatedTicket.setNumeroTicket(numeroTicket);
                    updatedTicket.setEtatTicket(etatTicket);

                    // Update the data in the database
                    boolean isUpdated = ticketService.updateTicket(updatedTicket);
                    if (isUpdated) {
                        JOptionPane.showMessageDialog(mainScreen, "Les données ont été mises à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

                        // Update the corresponding row in the Swing table model
                        mainScreen.updateTicketInTable(selectedRow, updatedTicket);
                    } else {
                        JOptionPane.showMessageDialog(mainScreen, "Erreur lors de la mise à jour des données.", "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainScreen, "Veuillez sélectionner une ligne à mettre à jour.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        mainScreen.getVoirTicketsEnCoursButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                afficherTicketsEnCoursAvecDate(); // Appelez la méthode pour afficher les tickets en cours avec leur date
                EtatTicketController etatTicketController = new EtatTicketController();
                etatTicketController.display();
            }
        });

        mainScreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    databaseManager.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mainScreen.getHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = mainScreen.getTableau().columnAtPoint(e.getPoint());
                if (columnIndex == 6) { // "État du ticket" est la 7e colonne (indice 6 en partant de 0)
//                    afficherFenetreEtatTicket();
                    EtatTicketController etatTicketController = new EtatTicketController();
                    etatTicketController.display();
                }
            }
        });
    }
}
