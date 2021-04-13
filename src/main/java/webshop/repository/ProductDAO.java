package webshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webshop.model.Product;

@Repository
public interface ProductDAO extends CrudRepository<Product, Long> {
}
