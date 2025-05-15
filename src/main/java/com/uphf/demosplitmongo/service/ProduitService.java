package com.uphf.demosplitmongo.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uphf.demosplitmongo.entity.Produit;

@Service
public class ProduitService {
    private MongoShardsPersonalizedService mongoShardsPersonalizedService;

    public ProduitService(MongoShardsPersonalizedService mongoShardsPersonalizedService) {
        this.mongoShardsPersonalizedService = mongoShardsPersonalizedService;
    }

    public Produit createProduit(Produit produit) {
        return mongoShardsPersonalizedService.saveProduit(produit);
    }

    public Produit getProduit(String nom, String country) {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry(country);
        Query query = new Query(Criteria.where("nom").is(nom));
        return template.findOne(query, Produit.class);
    }

    public List<Produit> getAllProduits(String country) {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry(country);
        return template.findAll(Produit.class);
    }

    public Produit updateProduit(String nom, Produit produit) {
        return mongoShardsPersonalizedService.updateProduit(nom, produit);
    }

    public boolean deleteProduit(String nom, String country) {
        return mongoShardsPersonalizedService.deleteProduit(nom, country);
    }
}