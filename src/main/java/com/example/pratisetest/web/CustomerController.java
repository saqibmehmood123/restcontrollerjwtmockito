package com.example.pratisetest.web;
import com.example.pratisetest.dao.CustomerRepository;
import com.example.pratisetest.model.Customer;
import com.example.pratisetest.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;


    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerServices.getAllCustomers();
    }

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer)
    {
        return customerServices.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerServices.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerServices.deleteCustomer(id);
    }
}