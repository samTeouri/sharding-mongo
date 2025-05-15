package com.uphf.demosplitmongo.dao.primary;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uphf.demosplitmongo.entity.Produit;

@Repository
public interface PrimaryRepository extends MongoRepository<Produit, ObjectId> {
}
