package com.example.healthapp;

import com.example.healthapp.product.Product;
import com.example.healthapp.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testSaveAndFindProduct() {

        String productName = "Jablko" + System.currentTimeMillis();
        Product product = new Product(productName, 30.0, 1.0, 0.2, 7.0);

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findByName(productName);
        assertTrue(foundProduct.isPresent());
        assertEquals(30.0, foundProduct.get().getKcal());
        assertEquals(1.0, foundProduct.get().getProtein());
        assertEquals(0.2, foundProduct.get().getFat());
        assertEquals(7.0, foundProduct.get().getCarb());
    }
}