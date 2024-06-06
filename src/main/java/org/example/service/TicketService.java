package org.example.service;

import org.example.database.DatabaseManager;
import org.example.model.Ticket;

import java.util.ArrayList;

public class TicketService implements TicketServiceInterface {
    private DatabaseManager databaseManager;
    private ArrayList<Ticket> tickets;

    public TicketService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.tickets = new ArrayList<>();
    }

    @Override
    public void sendTicket(Ticket ticket) {
        databaseManager.saveTicket(ticket);
    }

    @Override
    public void deleteTicket(int ticketNumber) {
        databaseManager.deleteTicket(ticketNumber);
    }

    @Override
    public void fetchAllTickets() {
        tickets = databaseManager.getAllTickets();
    }

    @Override
    public ArrayList<Ticket> getAllTickets() {
        return tickets;
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        return databaseManager.updateTicket(ticket);
    }

    @Override
    public int getNumberOfTickets() {
        return databaseManager.getNombreTicketsEnCoursOuverts();
    }
}