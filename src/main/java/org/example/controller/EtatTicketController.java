package org.example.controller;

import org.example.database.DatabaseManager;
import org.example.service.TicketService;
import org.example.view.EtatTicket;

public class EtatTicketController {
    private EtatTicket etatTicket;
    private DatabaseManager databaseManager;
    private TicketService ticketService;

    public EtatTicketController() {
        this.databaseManager = new DatabaseManager();
        this.ticketService = new TicketService(databaseManager);
        this.etatTicket = new EtatTicket();
        ticketService.fetchAllTickets();
        etatTicket.updateTable(ticketService.getAllTickets(), ticketService.getNumberOfTickets());
    }

    public void display() {
        etatTicket.display();
    }
}
