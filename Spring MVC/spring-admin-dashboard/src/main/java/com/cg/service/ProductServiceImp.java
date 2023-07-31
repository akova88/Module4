package com.cg.service;

import com.cg.model.Product;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements IProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    /**
    public static List<Product> products = new ArrayList<Product>();
    public static Long maxID = 1L;

    static {
        products = new ArrayList<Product>();
        products.add(new Product(maxID++, "Iphone", 1000, 5, "img1" ));
        products.add(new Product(maxID++, "Iphone", 1100, 5, "img2" ));
        products.add(new Product(maxID++, "Iphone", 1200, 5, "img3" ));
        products.add(new Product(maxID++, "Iphone", 1400, 5, "img4" ));
        products.add(new Product(maxID++, "Iphone", 1660, 5, "img5" ));
    }
    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void add(Product product) {
        product.setId(maxID);
        products.add(product);
        maxID++;
    }

    @Override
    public Product getById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(products.get(i));
            }
        }
    }

    @Override
    public void update(Product product) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                index = i;
            }
        }
        if (index > -1) {
            products.set(index, product);
        }
    }
**/


}
