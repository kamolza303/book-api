package com.example.book_api.service;

import com.example.book_api.dto.ProductRequest;
import com.example.book_api.entity.Product;

import java.util.List;

public interface ProductService {

	List<Product> getAllProducts();

	Product createProduct(ProductRequest productRequest);

	List<Product> getProductByNameAndStock(String name, int stock);

	List<Product> getProductOutOfStock();

	List<Product> getProductByNameAndPrice(String name, int price);

}
