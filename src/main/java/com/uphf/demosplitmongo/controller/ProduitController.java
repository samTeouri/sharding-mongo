package com.uphf.demosplitmongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uphf.demosplitmongo.entity.Produit;
import com.uphf.demosplitmongo.service.ProduitService;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit createdProduit = produitService.createProduit(produit);
        return ResponseEntity.ok(createdProduit);
    }

    @GetMapping("/{nom}")
    public ResponseEntity<Produit> getProduit(@PathVariable String nom, @RequestParam String pays) {
        Produit produit = produitService.getProduit(nom, pays);
        if (produit != null) {
            return ResponseEntity.ok(produit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits(@RequestParam String pays) {
        return ResponseEntity.ok(produitService.getAllProduits(pays));
    }

    @PutMapping("/{nom}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable String nom,
            @RequestBody Produit produit) {
        
        Produit updatedProduit = produitService.updateProduit(nom, produit);
        if (updatedProduit != null) {
            return ResponseEntity.ok(updatedProduit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{nom}")
    public ResponseEntity<Void> deleteProduit(@PathVariable String nom, @RequestParam String pays) {
        if (produitService.deleteProduit(nom, pays)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}