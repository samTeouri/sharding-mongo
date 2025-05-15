package com.uphf.demosplitmongo.controller;

import java.util.List;

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

import com.uphf.demosplitmongo.entity.Utilisateur;
import com.uphf.demosplitmongo.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.createUtilisateur(utilisateur));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable String email, @RequestParam String pays) {
        return ResponseEntity.ok(utilisateurService.getUtilisateur(email, pays));
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(@RequestParam String pays) {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs(pays));
    }

    @PutMapping("/{email}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable String email, @RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.updateUtilisateur(email, utilisateur));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String email, @RequestParam String pays) {
        utilisateurService.deleteUtilisateur(email, pays);
        return ResponseEntity.noContent().build();
    }
}