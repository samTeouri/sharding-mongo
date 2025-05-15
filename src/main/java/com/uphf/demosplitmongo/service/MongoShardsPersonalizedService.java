package com.uphf.demosplitmongo.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.uphf.demosplitmongo.config.AsiaMongoConfig;
import com.uphf.demosplitmongo.config.EuropeMongoConfig;
import com.uphf.demosplitmongo.config.GlobalMongoConfig;
import com.uphf.demosplitmongo.entity.Commande;
import com.uphf.demosplitmongo.entity.Produit;
import com.uphf.demosplitmongo.entity.Utilisateur;
@Component
public class MongoShardsPersonalizedService {

    private final EuropeMongoConfig europeMongoConfig;
    private final AsiaMongoConfig asiaMongoConfig;
    private final GlobalMongoConfig globalMongoConfig;

    public MongoShardsPersonalizedService(EuropeMongoConfig europeMongoConfig, AsiaMongoConfig asiaMongoConfig, GlobalMongoConfig globalMongoConfig) {
        this.europeMongoConfig = europeMongoConfig;
        this.asiaMongoConfig = asiaMongoConfig;
        this.globalMongoConfig = globalMongoConfig;
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        Query query = new Query(Criteria.where("email").is(utilisateur.getNom()));

        Utilisateur existingUtilisateur = globalMongoConfig.globalMongoTemplate().findOne(query, Utilisateur.class);
        if (existingUtilisateur != null) {
            throw new IllegalArgumentException("Un utilisateur avec le même email existe déjà dans la base globale : " + utilisateur.getEmail());
        }
        
        if (isEuropeanCountry(utilisateur.getPays())) {
            europeMongoConfig.europeMongoTemplate().save(utilisateur);
            globalMongoConfig.globalMongoTemplate().save(utilisateur);
        } else if (isAsianCountry(utilisateur.getPays())) {
            asiaMongoConfig.asiaMongoTemplate().save(utilisateur);
            globalMongoConfig.globalMongoTemplate().save(utilisateur);
        } else {
            globalMongoConfig.globalMongoTemplate().save(utilisateur);
        }

        return utilisateur;
    }

    public Utilisateur updateUtilisateur(String email, Utilisateur utilisateur) {
        Query query = new Query(Criteria.where("email").is(email));

        if (isEuropeanCountry(utilisateur.getPays())) {
            Utilisateur existingUtilisateur = europeMongoConfig.europeMongoTemplate().findOne(query, Utilisateur.class);
            if (existingUtilisateur != null) {
                utilisateur.setId(existingUtilisateur.getId());
                europeMongoConfig.europeMongoTemplate().save(utilisateur);
            }
        }

        if (isAsianCountry(utilisateur.getPays())) {
            Utilisateur existingUtilisateur = asiaMongoConfig.asiaMongoTemplate().findOne(query, Utilisateur.class);
            if (existingUtilisateur != null) {
                utilisateur.setId(existingUtilisateur.getId());
                asiaMongoConfig.asiaMongoTemplate().save(utilisateur);
            }
        }

        Utilisateur existingUtilisateurGlobal = globalMongoConfig.globalMongoTemplate().findOne(query, Utilisateur.class);
        if (existingUtilisateurGlobal != null) {
            utilisateur.setId(existingUtilisateurGlobal.getId());
            globalMongoConfig.globalMongoTemplate().save(utilisateur);
        }

        return utilisateur;
    }

    public boolean deleteUtilisateur(String email, String pays) {
        Query query = new Query(Criteria.where("email").is(email));
        boolean deleted = false;
        if (isEuropeanCountry(pays)) {
            deleted = europeMongoConfig.europeMongoTemplate().remove(query, Utilisateur.class).getDeletedCount() > 0;
        }
        if (isAsianCountry(pays)) {
            deleted = asiaMongoConfig.asiaMongoTemplate().remove(query, Utilisateur.class).getDeletedCount() > 0;
        }
        deleted = globalMongoConfig.globalMongoTemplate().remove(query, Utilisateur.class).getDeletedCount() > 0 || deleted;

        return deleted;
    }

    public Produit saveProduit(Produit produit) {
        Query query = new Query(Criteria.where("nom").is(produit.getNom()));

        Produit existingProduit = globalMongoConfig.globalMongoTemplate().findOne(query, Produit.class);
        if (existingProduit != null) {
            throw new IllegalArgumentException("Un produit avec le même nom existe déjà dans la base globale : " + produit.getNom());
        }

        if (isEuropeanCountry(produit.getPays())) {
            europeMongoConfig.europeMongoTemplate().save(produit);
        }

        if (isAsianCountry(produit.getPays())) {
            asiaMongoConfig.asiaMongoTemplate().save(produit);
        }

        globalMongoConfig.globalMongoTemplate().save(produit);

        return produit;
    }

    public Produit updateProduit(String nom, Produit produit) {
        Query query = new Query(Criteria.where("nom").is(nom));

        if (isEuropeanCountry(produit.getPays())) {
            Produit existingProduit = europeMongoConfig.europeMongoTemplate().findOne(query, Produit.class);
            if (existingProduit != null) {
                produit.setId(existingProduit.getId());
                europeMongoConfig.europeMongoTemplate().save(produit);
            }
        }

        if (isAsianCountry(produit.getPays())) {
            Produit existingProduit = asiaMongoConfig.asiaMongoTemplate().findOne(query, Produit.class);
            if (existingProduit != null) {
                produit.setId(existingProduit.getId());
                asiaMongoConfig.asiaMongoTemplate().save(produit);
            }
        }

        Produit existingProduitGlobal = globalMongoConfig.globalMongoTemplate().findOne(query, Produit.class);
        if (existingProduitGlobal != null) {
            produit.setId(existingProduitGlobal.getId());
            globalMongoConfig.globalMongoTemplate().save(produit);
        }

        return produit;
    }

    public boolean deleteProduit(String nom, String pays) {
        Query query = new Query(Criteria.where("nom").is(nom));
        boolean deleted = false;
        if (isEuropeanCountry(pays)) {
            deleted = europeMongoConfig.europeMongoTemplate().remove(query, Produit.class).getDeletedCount() > 0;
        }
        if (isAsianCountry(pays)) {
            deleted = asiaMongoConfig.asiaMongoTemplate().remove(query, Produit.class).getDeletedCount() > 0;
        }
        deleted = globalMongoConfig.globalMongoTemplate().remove(query, Produit.class).getDeletedCount() > 0 || deleted;

        return deleted;
    }

    public void saveCommande(Commande commande) {
        Query query = new Query(Criteria.where("numeroCommande").is(commande.getNumeroCommande()));

        Commande existingCommande = globalMongoConfig.globalMongoTemplate().findOne(query, Commande.class);
        if (existingCommande != null) {
            throw new IllegalArgumentException("Une commande avec le même numero existe déjà dans la base globale : " + commande.getNumeroCommande());
        }

        globalMongoConfig.globalMongoTemplate().save(commande);
    }

    public Commande updateCommande(String numeroCommande, Commande commande) {
        Query query = new Query(Criteria.where("numeroCommande").is(numeroCommande));

        Commande existingCommandeGlobal = globalMongoConfig.globalMongoTemplate().findOne(query, Commande.class);
        if (existingCommandeGlobal != null) {
            commande.setId(existingCommandeGlobal.getId());
            globalMongoConfig.globalMongoTemplate().save(commande);
        }

        return commande;
    }

    public boolean deleteCommande(String numeroCommande, String pays) {
        Query query = new Query(Criteria.where("numeroCommande").is(numeroCommande));
        boolean deleted = false;
        if (isEuropeanCountry(pays)) {
            deleted = europeMongoConfig.europeMongoTemplate().remove(query, Commande.class).getDeletedCount() > 0;
        }
        if (isAsianCountry(pays)) {
            deleted = asiaMongoConfig.asiaMongoTemplate().remove(query, Commande.class).getDeletedCount() > 0;
        }
        deleted = globalMongoConfig.globalMongoTemplate().remove(query, Commande.class).getDeletedCount() > 0 || deleted;

        return deleted;
    }

    public boolean isEuropeanUtilisateur(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        Utilisateur existingUtilisateur = europeMongoConfig.europeMongoTemplate().findOne(query, Utilisateur.class);
        return existingUtilisateur != null;
    }

    public boolean isAsianUtilisateur(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        Utilisateur existingUtilisateur = asiaMongoConfig.asiaMongoTemplate().findOne(query, Utilisateur.class);
        return existingUtilisateur != null;
    }

    public MongoTemplate getTemplateForCountry(String country) {
        if (isEuropeanCountry(country)) {
            return europeMongoConfig.europeMongoTemplate();
        } else if (isAsianCountry(country)) {
            return asiaMongoConfig.asiaMongoTemplate();
        } else {
            return globalMongoConfig.globalMongoTemplate();
        }
    }

    private boolean isEuropeanCountry(String country) {
        return "FR".equalsIgnoreCase(country) ||
               "DE".equalsIgnoreCase(country) ||
               "GR".equalsIgnoreCase(country) ||
               "IT".equalsIgnoreCase(country) ||
               "ES".equalsIgnoreCase(country);
    }

    private boolean isAsianCountry(String country) {
        return "JP".equalsIgnoreCase(country) ||
               "CN".equalsIgnoreCase(country) ||
               "SG".equalsIgnoreCase(country) ||
               "IN".equalsIgnoreCase(country) ||
               "KR".equalsIgnoreCase(country);
    }
}
