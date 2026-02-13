package com.example.book_api.repository;

import com.example.book_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// DAO
public interface ProductRepository extends JpaRepository<Product, Long> {

	// SELECT * FROM Product WHERE name LIKE '%foo%' AND stock > x order by stock
	// desc
	List<Product> findByNameContainingAndStockGreaterThanOrderByStockDesc(String name, int stock);

	@Query(value = "SELECT * FROM product WHERE stock = 0", nativeQuery = true)
	List<Product> checkOutOfStock();

	@Query(value = "SELECT * FROM product WHERE name LIKE %:product_name% AND price > :price", nativeQuery = true)
	List<Product> searchNameAndPrice(@Param("product_name") String name, int price);
}
