package org.ruananta.systemeio.service;

import org.ruananta.systemeio.entity.Product;

import java.util.Optional;

public interface ProductService {
    public Optional<Product> findById(long id);
}
