package com.cg.service;

import com.cg.model.Product;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    void add(Product product);
    Product getById(Long id);
    void update(Product product);
    void delete(Long id);

}
