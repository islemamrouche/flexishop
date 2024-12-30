package com.flaxishop.flexishop.business.repository;

import com.flaxishop.flexishop.business.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
