package webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.model.Product;
import webshop.repository.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public Product createUpdate(Product product) {
        return productDAO.save(product);
    }

    @Override
    public Product read(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productDAO.deleteById(id);
    }
}
