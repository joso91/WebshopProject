package webshop.service;

import webshop.model.Customer;

public interface CustomerService {

    public Customer createUpdate(Customer customer);

    public Customer read(Long id);

    public void delete(Long id);
}
