package webshop.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.model.Customer;
import webshop.service.CustomerService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void createCustomer() {
        Customer customer = new Customer("John1", "Doe1", "john1.doe1@gmail.com");
        Customer savedCustomer = customerService.createUpdate(customer);
        assertNotNull(savedCustomer);
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer("John2", "Doe2", "john2.doe2@gmail.com");
        Customer savedCustomer = customerService.createUpdate(customer);
        savedCustomer.setEmail("john.doe@hotmail.com");
        Customer updatedCustomer = customerService.createUpdate(savedCustomer);
        assertEquals(updatedCustomer.getEmail(), "john.doe@hotmail.com");
    }

    @Test
    public void readCustomer() {
        Customer customer = new Customer("John3", "Doe3", "john3.doe3@gmail.com");
        Long id = customerService.createUpdate(customer).getId();
        Customer savedCustomer = customerService.read(id);
        assertNotNull(savedCustomer);
    }

    @Test
    public void deleteCustomer() {
        Customer customer = new Customer("John4", "Doe4", "john4.doe4@gmail.com");
        Long id = customerService.createUpdate(customer).getId();
        customerService.delete(id);
        Customer deletedCustomer = customerService.read(id);
        assertNull(deletedCustomer);
    }
}
