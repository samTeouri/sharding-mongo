package com.uphf.demosplitmongo.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uphf.demosplitmongo.entity.Utilisateur;

@Service
public class UtilisateurService {
    private final MongoShardsPersonalizedService mongoShardsPersonalizedService;

    public UtilisateurService(MongoShardsPersonalizedService mongoShardsPersonalizedService) {
        this.mongoShardsPersonalizedService = mongoShardsPersonalizedService;
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return mongoShardsPersonalizedService.saveUtilisateur(utilisateur);
    }

    public Utilisateur getUtilisateur(String email, String country) {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry(country);
        Query query = new Query(Criteria.where("email").is(email));
        return template.findOne(query, Utilisateur.class);
    }

    public List<Utilisateur> getAllUtilisateurs(String country) {
        MongoTemplate template = mongoShardsPersonalizedService.getTemplateForCountry(country);
        return template.findAll(Utilisateur.class);
    }

    public Utilisateur updateUtilisateur(String email, Utilisateur utilisateur) {
        return mongoShardsPersonalizedService.updateUtilisateur(email, utilisateur);
    }

    public boolean deleteUtilisateur(String email, String country) {
        return mongoShardsPersonalizedService.deleteUtilisateur(email, country);
    }
}