package com.flaxishop.flexishop.business.repository;

import com.flaxishop.flexishop.business.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
