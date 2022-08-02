package com.CS6310.Team045.repository;

import com.CS6310.Team045.model.Order;
import org.hibernate.boot.JaccPermissionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findOrderByStore_name(String store);
    List<Order> findOrderByRequestedBy_account(String customer_id);

    Optional<Order> findOrderByStore_nameAndId(String store, String orderId);



}
