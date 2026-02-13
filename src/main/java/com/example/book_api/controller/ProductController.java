package com.example.book_api.controller;

import com.example.book_api.dto.ProductRequest;
import com.example.book_api.entity.Product;
import com.example.book_api.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProduct() {
        return this.productService.getAllProducts();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product addProduct(
            @Valid @RequestBody ProductRequest productRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .stream()
                    .forEach(fieldError -> {
                        throw new ValidationException(
                                fieldError.getField() + " : " + fieldError.getDefaultMessage());
                    });
        }
        return this.productService.createProduct(productRequest);
    }

    @GetMapping(path = "/search", params = { "name", "stock" })
    public List<Product> searchProductByNameAndStock(
            @RequestParam String name,
            @RequestParam int stock) {
        return this.productService.getProductByNameAndStock(name, stock);
    }

    @GetMapping(path = "/search", params = { "name", "price" })
    public List<Product> searchProductByNameAndPrice(
            @RequestParam String name,
            @RequestParam int price) {
        return this.productService.getProductByNameAndPrice(name, price);
    }

    @GetMapping(path = "/out-of-stock")
    public List<Product> getOutOfStock() {
        return this.productService.getProductOutOfStock();
    }

}
