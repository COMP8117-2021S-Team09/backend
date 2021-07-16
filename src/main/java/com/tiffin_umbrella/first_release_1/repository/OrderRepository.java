package com.tiffin_umbrella.first_release_1.repository;

import com.tiffin_umbrella.first_release_1.entity.Order;
import java.util.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
	Collection<Order> findBySeller_Id(String seller_id);
}
