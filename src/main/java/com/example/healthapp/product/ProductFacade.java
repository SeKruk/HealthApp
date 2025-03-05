package com.example.healthapp.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFacade {

@Autowired
   private  ProductRepository productRepository;

        public void addProduct(Product product) {
        System.out.println("Dodawanie produktu: " + product);
        productRepository.save(product);
        }

        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }
        public Product findAllById(Long id) {
            return productRepository.findById(id).orElse(null);
        }
}
