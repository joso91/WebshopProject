package webshop.service;

import webshop.model.Product;

public interface ProductService {

    public Product createUpdate(Product product);

    public Product read(Long id);

    public void delete(Long id);
}
