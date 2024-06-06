package org.example.database;

import org.example.model.Ticket;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetsav?characterEncoding=UTF-8", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNombreTicketsEnCoursOuverts() {
        int nombreTickets = 0;
        try {
            String query = "SELECT COUNT(*) FROM dossier_sav WHERE etat_ticket = 'Ouvert'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                nombreTickets = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du nombre de tickets : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        return nombreTickets;
    }

    public void saveTicket(Ticket ticket) {
        try {
            String query = "INSERT INTO dossier_sav (nom, prenom, telephone, reclamation, date, numero_ticket, etat_ticket) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ticket.getNom());
            preparedStatement.setString(2, ticket.getPrenom());
            preparedStatement.setString(3, ticket.getTelephone());
            preparedStatement.setString(4, ticket.getReclamation());
            preparedStatement.setString(5, ticket.getDate());
            preparedStatement.setInt(6, ticket.getNumeroTicket());
            preparedStatement.setString(7, ticket.getEtatTicket());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du ticket : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getLatestTicketNumber() {
        try {
            int latestTicketNumber = 0;
            String query = "SELECT MAX(numero_ticket) FROM dossier_sav";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                latestTicketNumber = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            return latestTicketNumber;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du dernier numéro de ticket : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public void deleteTicket(int ticketNumber) {
        try {
            String query = "DELETE FROM dossier_sav WHERE numero_ticket = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ticketNumber);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du ticket : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Ticket> getAllTickets() {
        try {
            String query = "SELECT nom, prenom, telephone, reclamation, date, numero_ticket, etat_ticket FROM dossier_sav";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setNom(resultSet.getString("nom"));
                ticket.setPrenom(resultSet.getString("prenom"));
                ticket.setTelephone(resultSet.getString("telephone"));
                ticket.setReclamation(resultSet.getString("reclamation"));
                ticket.setDate(resultSet.getString("date"));
                ticket.setNumeroTicket(resultSet.getInt("numero_ticket"));
                ticket.setEtatTicket(resultSet.getString("etat_ticket"));
                tickets.add(ticket);
            }
            resultSet.close();
            statement.close();
            return tickets;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des tickets : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean updateTicket(Ticket ticket) {
        try {
            String query = "UPDATE dossier_sav SET nom = ?, prenom = ?, telephone = ?, reclamation = ?, date = ?, etat_ticket = ? WHERE numero_ticket = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ticket.getNom());
            preparedStatement.setString(2, ticket.getPrenom());
            preparedStatement.setString(3, ticket.getTelephone());
            preparedStatement.setString(4, ticket.getReclamation());
            preparedStatement.setString(5, ticket.getDate());
            preparedStatement.setString(6, ticket.getEtatTicket());
            preparedStatement.setInt(7, ticket.getNumeroTicket());
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du ticket : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
