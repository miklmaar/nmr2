package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.repositories.CustomerRepoImpl;
import com.example.demo.repositories.ICustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    private ICustomerRepository customerRepository;

    public CustomerController(){
        customerRepository = new CustomerRepoImpl();
    }

    //
    //
    //DISPLAYING ALL CUSTOMERS
    //
    //
    @GetMapping("customers")
    public String displayCustomers(Model model){
        indexRead(model);
        return "customers";
    }

    //
    //
    //DISPLAYING AND UPDATING SINGLE CUSTOMER
    //
    //
    @GetMapping("customer")
    public String displayCustomer(@RequestParam String cpr, Model model){
        Customer customer = customerRepository.read(cpr);
        model.addAttribute("customer", customer);
        return "customer";
    }

    @PostMapping("customerForm")
    public String updateCustomer(@ModelAttribute Customer customer, Model model){
        customerRepository.update(customer);
        indexRead(model);
        return "customers";
    }

    //
    //
    //ADDING NEW CUSTOMER
    //
    //
    @GetMapping("addCustomer")
    public String addNewCustomer(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "addCustomer";
    }

    @PostMapping("addCustomerForm")
    public String addingCustomer(@ModelAttribute Customer customer, Model model){
        customerRepository.create(customer);
        indexRead(model);
        return "customers";
    }

    //
    //
    //DELETE CUSTOMER
    //
    //
    @GetMapping("customerDelete")
    public String deleteCustomer(@RequestParam String cpr, Model model){
        boolean successfulDel = customerRepository.delete(cpr);
        indexRead(model);
        if(!successfulDel){
            model.addAttribute("delError", true);
            model.addAttribute("cpr", cpr);
        }
        return "customers";
    }

    //
    //
    //Generic method for reading all orders.
    //
    //
    public void indexRead(Model model) {
        model.addAttribute("customers", customerRepository.readAll());
    }
}
