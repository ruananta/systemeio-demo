package org.ruananta.systemeio.service;

import org.ruananta.systemeio.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Optional<Product> findById(long id);
    public List<Product> findAll();
    boolean existsById(long id);
}
