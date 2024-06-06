package org.example.service;

import org.example.database.DatabaseManager;


public class TicketNumberService implements TicketNumberInterface {
    private int latestTicketNumber;
    private DatabaseManager databaseManager;

    public TicketNumberService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.latestTicketNumber = getLatestTicketNumber();
    }

    @Override
    public int generateTicketNumber() {
        return ++latestTicketNumber;
    }

    @Override
    public int getLatestTicketNumber() {
        return databaseManager.getLatestTicketNumber();
    }
}