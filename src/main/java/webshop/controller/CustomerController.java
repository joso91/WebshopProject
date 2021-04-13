package webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.model.Customer;
import webshop.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/create-customer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createUpdate(customer), HttpStatus.CREATED);
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Long id) {
        if (customerService.read(id) != null) {
            customer.setId(id);
            return new ResponseEntity<>(customerService.createUpdate(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-customer/{id}")
    public ResponseEntity<Customer> readCustomer(@PathVariable Long id) {
        Customer customer = customerService.read(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
