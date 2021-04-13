package webshop.repository;

import org.springframework.data.repository.CrudRepository;
import webshop.model.Customer;

public interface CustomerDAO extends CrudRepository<Customer, Long> {
}
