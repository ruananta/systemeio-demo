package org.ruananta.systemeio.service;

import org.ruananta.systemeio.entity.Product;
import org.ruananta.systemeio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(long id) {
        return this.productRepository.findById(id);
    }
}
