package com.uphf.demosplitmongo.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uphf.demosplitmongo.entity.Commande;

@Service
public class CommandeService {
    private final MongoShardsPersonalizedService mongoShardsPersonalizedService;

    public CommandeService(MongoShardsPersonalizedService mongoShardsPersonalizedService) {
        this.mongoShardsPersonalizedService = mongoShardsPersonalizedService;
    }

    public Commande createCommande(Commande commande) {
        mongoShardsPersonalizedService.saveCommande(commande);
        return commande;
    }

    public Commande getCommande(String numeroCommande) {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry("GLOBAL");
        Query query = new Query(Criteria.where("numeroCommande").is(numeroCommande));
        return template.findOne(query, Commande.class);
    }

    public List<Commande> getAllCommandes() {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry("GLOBAL");
        return template.findAll(Commande.class);
    }

    public Commande updateCommande(String numeroCommande, Commande commande) {
        return mongoShardsPersonalizedService.updateCommande(numeroCommande, commande);
    }

    public boolean deleteCommande(String numeroCommande) {
        return mongoShardsPersonalizedService.deleteCommande(numeroCommande, "GLOBAL");
    }
}