package org.example.service;

import org.example.model.Ticket;

import java.util.ArrayList;

public interface TicketServiceInterface {
    void sendTicket(Ticket ticket);
    void deleteTicket(int ticketNumber);
    void fetchAllTickets();
    ArrayList<Ticket> getAllTickets();
    boolean updateTicket(Ticket ticket);
    int getNumberOfTickets();
}
