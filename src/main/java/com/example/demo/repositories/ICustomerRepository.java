package com.example.demo.repositories;

import com.example.demo.models.Customer;

import java.util.List;

public interface ICustomerRepository {
    public boolean create(Customer customer);

    public Customer read(String cpr);

    public List<Customer> readAll();

    public boolean update(Customer customer);

    public boolean delete(String cpr);
}
