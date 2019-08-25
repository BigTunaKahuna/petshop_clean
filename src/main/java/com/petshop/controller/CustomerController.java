package com.petshop.controller;

import com.petshop.dto.CustomerDTO;
import com.petshop.models.Customer;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    // REQUEST:GET @PATH: /customer/{id}
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable(value = "id") Long id) {
        return customerService.getCustomerById(id);
    }

    // REQUEST:GET @PATH: /customer/all
    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // REQUEST:POST @PATH: /customer/vet/{vetId}
    @PostMapping("/vet/{vetId}")
    public Customer saveCustomer(@PathVariable(value = "vetId") Long vetId, @RequestBody Customer customer) {
        return customerService.saveCustomer(vetId, customer);

    }

    // REQUEST:PUT @PATH: /customer/{customerId}/{newVetId} - Transfer to
    // another doctor
    @PutMapping("/{customerId}/{newVetId}")
    public @Valid Customer updateCustomer(@PathVariable(value = "newVetId") Long newVetId,
                                          @PathVariable(value = "customerId") Long customerId, @Valid @RequestBody Customer customer) {
        return customerService.updateVetForCustomer(newVetId, customerId, customer);
    }

    // REQUEST:PUT @PATH: /customer/{customerId} - Change data of a customer
    @PutMapping("/{customerId}")
    public Customer updateVetForCustomer(@PathVariable(value = "customerId") Long customerId,
                                         @RequestBody Customer customer) {
        return customerService.updateCustomer(customerId, customer);

    }

    // REQUEST:DELETE @PATH: /customer/{customerId}
    @DeleteMapping("/{customerId}")
    public void deleteCustomerById(@PathVariable(value = "customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
