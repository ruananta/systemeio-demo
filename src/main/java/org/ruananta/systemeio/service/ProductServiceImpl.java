package org.ruananta.systemeio.service;

import jakarta.annotation.PostConstruct;
import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initDB() {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                    new Product("Iphone", "Описание Iphone", new BigDecimal("100.00")),
                    new Product("Наушники", "Описание наушников", new BigDecimal("20.0")),
                    new Product("Чехол", "Описание чехла", new BigDecimal("10.0"))
            );
            productRepository.saveAll(products);
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public boolean existsById(long id) {
        return this.productRepository.existsById(id);
    }

}
