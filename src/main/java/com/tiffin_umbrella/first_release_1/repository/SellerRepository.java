package com.tiffin_umbrella.first_release_1.repository;

import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends MongoRepository<SellerEntity, String> {

    Optional<SellerEntity> findByContact_Email(final String email);
}
