package com.tiffin_umbrella.first_release_1.repository;

import com.tiffin_umbrella.first_release_1.entity.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<ImageEntity, String> {
}