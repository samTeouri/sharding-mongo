package com.uphf.demosplitmongo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "commandes")
public class Commande {
    @Id
    private ObjectId id;

    @Indexed(unique=true)
    private String numeroCommande;
    
    private List<String> nomsProduits;
    private String emailClient;

    public Commande(String numeroCommande, List<String> nomsProduits, String emailClient) {
        this.numeroCommande = numeroCommande;
        this.nomsProduits = nomsProduits;
        this.emailClient = emailClient;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public List<String> getNomsProduits() {
        return nomsProduits;
    }

    public void setNomsProduits(List<String> nomsProduits) {
        this.nomsProduits = nomsProduits;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
}