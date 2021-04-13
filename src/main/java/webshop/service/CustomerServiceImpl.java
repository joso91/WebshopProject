package webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.model.Customer;
import webshop.repository.CustomerDAO;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public Customer createUpdate(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    public Customer read(Long id) {
        return customerDAO.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        customerDAO.deleteById(id);
    }
}
