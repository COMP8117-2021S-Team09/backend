package com.tiffin_umbrella.first_release_1.repository;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {

}
