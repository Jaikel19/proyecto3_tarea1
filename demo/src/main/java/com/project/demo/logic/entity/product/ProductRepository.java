package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE %?1%")
    List<Product> findProductsWithCharacterInName(String character);
    @Query("SELECT u FROM Product u WHERE u.name = ?1")
    Optional<Product> findByName(String name);

}
