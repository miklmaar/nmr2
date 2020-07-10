package com.example.demo.repositories;

import com.example.demo.models.Order;

import java.util.List;

public interface IOrdersRepository {
    public boolean create(Order order);

    public boolean addMotorhomeToOrder(int OrderID, int mhID);

    public boolean deleteMotorhomeFromOrder(int OrderID, int mhID);

    public Order read(int id);

    public List<Order> readAll();

    public boolean update(Order order);

    public boolean delete(int id);

    public boolean finishOrder(Order order);
}
