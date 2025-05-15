package com.uphf.demosplitmongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.uphf.demosplitmongo.entity.Commande;
import com.uphf.demosplitmongo.entity.Utilisateur;
import com.uphf.demosplitmongo.service.MongoShardsPersonalizedService;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {
    "com.uphf.demosplitmongo.dao.europe",
    "com.uphf.demosplitmongo.dao.asia",
    "com.uphf.demosplitmongo.dao.global"
})
public class DemoSplitMongoApplication {

    private final MongoShardsPersonalizedService mongoShardsPersonalizedService;

    public DemoSplitMongoApplication(MongoShardsPersonalizedService mongoShardsPersonalizedService) {
        this.mongoShardsPersonalizedService = mongoShardsPersonalizedService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoSplitMongoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // mongoShardsPersonalizedService.saveUtilisateur(new Utilisateur("John", "Doe", "john.doe@example.com", "US"));
            // mongoShardsPersonalizedService.saveCommande(new Commande("CMD123", List.of("Produit1", "Produit2"), "john.doe@example.com"));
        };
    }

}
