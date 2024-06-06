package org.example.model;

import java.util.Date;

public class Ticket {
    private String nom;
    private String prenom;
    private String telephone;
    private String reclamation;
    private String date;
    private int numeroTicket;
    private String etatTicket;

    public Ticket() {}

    public Ticket(String nom, String prenom, String telephone, String reclamation, String date, int numeroTicket, String etatTicket) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.reclamation = reclamation;
        this.date = date;
        this.numeroTicket = numeroTicket;
        this.etatTicket = etatTicket;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtatTicket() {
        return etatTicket;
    }

    public void setEtatTicket(String etatTicket) {
        this.etatTicket = etatTicket;
    }

    public int getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(int numeroTicket) {
        this.numeroTicket = numeroTicket;
    }
}
