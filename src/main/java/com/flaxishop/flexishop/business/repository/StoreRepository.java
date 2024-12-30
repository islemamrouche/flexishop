package com.flaxishop.flexishop.business.repository;

import com.flaxishop.flexishop.business.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
