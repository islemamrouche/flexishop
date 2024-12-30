package com.flaxishop.flexishop.business.repository;

import com.flaxishop.flexishop.business.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByUuid(String uuid);
}
