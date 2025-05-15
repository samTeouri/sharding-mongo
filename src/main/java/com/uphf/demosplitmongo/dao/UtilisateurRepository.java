package com.uphf.demosplitmongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.uphf.demosplitmongo.entity.Utilisateur;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Utilisateur findByEmail(String email); // Recherche par email
}