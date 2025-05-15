package com.uphf.demosplitmongo.controller;

import com.uphf.demosplitmongo.entity.Commande;
import com.uphf.demosplitmongo.service.CommandeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        return ResponseEntity.ok(commandeService.createCommande(commande));
    }

    @GetMapping("/{numeroCommande}")
    public ResponseEntity<Commande> getCommande(@PathVariable String numeroCommande) {
        return ResponseEntity.ok(commandeService.getCommande(numeroCommande));
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }

    @PutMapping("/{numeroCommande}")
    public ResponseEntity<Commande> updateCommande(@PathVariable String numeroCommande, @RequestBody Commande commande) {
        return ResponseEntity.ok(commandeService.updateCommande(numeroCommande, commande));
    }

    @DeleteMapping("/{numeroCommande}")
    public ResponseEntity<Void> deleteCommande(@PathVariable String numeroCommande) {
        commandeService.deleteCommande(numeroCommande);
        return ResponseEntity.noContent().build();
    }
}