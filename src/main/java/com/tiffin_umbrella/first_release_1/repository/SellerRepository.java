package com.tiffin_umbrella.first_release_1.repository;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SellerRepository extends MongoRepository<SellerEntity,String>{
    SellerEntity findByContact_Email(final String email);
}
