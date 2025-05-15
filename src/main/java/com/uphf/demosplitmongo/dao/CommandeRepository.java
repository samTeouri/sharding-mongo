package com.uphf.demosplitmongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.uphf.demosplitmongo.entity.Commande;

public interface CommandeRepository extends MongoRepository<Commande, String> {
    Commande findByNumeroCommande(String numeroCommande); // Recherche par numéro de commande
}