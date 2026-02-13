package com.example.book_api.service;

import com.example.book_api.dto.ProductRequest;
import com.example.book_api.entity.Product;
import com.example.book_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productsRepository;

    ProductServiceImpl(ProductRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product data = new Product();
        data.setName(productRequest.getName()).setPrice(productRequest.getPrice())
                .setStock(productRequest.getStock());

        return productsRepository.save(data);
    }

    @Override
    public List<Product> getProductByNameAndStock(String name, int stock) {
        return productsRepository.findByNameContainingAndStockGreaterThanOrderByStockDesc(name, stock);
    }

    @Override
    public List<Product> getProductOutOfStock() {
        return productsRepository.checkOutOfStock();
    }

    @Override
    public List<Product> getProductByNameAndPrice(String name, int price) {
        return productsRepository.searchNameAndPrice(name, price);
    }
}
