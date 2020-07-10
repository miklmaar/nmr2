package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.Motorhome;
import com.example.demo.models.Order;
import com.example.demo.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {
    private IOrdersRepository ordersRepository;
    private IMotorhomeRepository motorhomeRepository;
    private ICustomerRepository customerRepository;

    public OrderController(){
        ordersRepository = new OrderRepoImpl();
        motorhomeRepository = new MotorhomeRepoImpl();
        customerRepository = new CustomerRepoImpl();
    }

    //
    //
    //DISPLAYING ALL ORDERS
    //
    //
    @GetMapping("/orders")
    public String orderPage(@RequestParam int active, Model model){
        indexRead(model);
        model.addAttribute("active",active);
        return "orders";
    }

    //
    //
    //SHOWING/EDITING SPECIFIC ORDER
    //
    //

    @GetMapping("/order")
    public String getOrder(@RequestParam int id, Model model){
        Order order = ordersRepository.read(id);
        model.addAttribute("order", order);
        model.addAttribute("mhInOrder", order.getMhInOrder());
        return "order";
    }

    //
    //
    //ADD NEW ORDER
    //When adding a new order the user is send to a site
    //asking if it's a new or old customer. A customer is chosen
    //or made, and an order is created linked to said customer.
    //
    //
    @GetMapping("/linkCustomer")
    public String linkCustomer(Model model){
        indexRead(model);
        List<Customer> customerList = customerRepository.readAll();
        Customer customer = new Customer();
        model.addAttribute("customers", customerList);
        model.addAttribute("customer", customer);
        return "linkCustomer";
    }

    @PostMapping("/addedCustomer")
    public String addedCustomer(@ModelAttribute Customer customer, Model model){
        customerRepository.create(customer);
        indexRead(model);
        model.addAttribute("customers", customerRepository.readAll());
        return "linkCustomer";
    }

    @GetMapping("/addOrder")
    public String addOrder(@RequestParam String cpr, Model model){
        Order order = new Order();
        Customer customer = customerRepository.read(cpr);
        order.setCustomer(customer);
        model.addAttribute("order", order);
        return "addOrder";
    }

    @PostMapping("/addOrderForm")
    public String addOrderForm(@ModelAttribute Order order, Model model){
        Customer customer = customerRepository.read(order.getCustomer().getCPR());
        order.setCustomer(customer);
        ordersRepository.create(order);
        model.addAttribute("active", 0);
        indexRead(model);
        return "orders";
    }

    //
    //
    //Adding and deleting a motorhome to/from an order
    //
    //

    @GetMapping("/order/addmotorhome")
    public String addMotorhomeToOrder(@RequestParam int orderID, Model model){
        Order order = ordersRepository.read(orderID);
        Motorhome motorhome = new Motorhome();
        model.addAttribute("order",order);
        model.addAttribute("addmotorhome", true);
        model.addAttribute("mhInOrder", order.getMhInOrder());
        model.addAttribute("motorhomes", motorhomeRepository.readAll());
        model.addAttribute("motorhome", motorhome);
        return "order";
    }

    @PostMapping("/order/addmh")
    public String addingMotorhome(@ModelAttribute Motorhome motorhome, @ModelAttribute Order order, Model model){
        ordersRepository.addMotorhomeToOrder(order.getOrderID(), motorhome.getMotorhomeID());
        Order order1 = ordersRepository.read(order.getOrderID());
        model.addAttribute("order",ordersRepository.read(order.getOrderID()));
        model.addAttribute("mhInOrder", order1.getMhInOrder());
        return "order";
    }

    @GetMapping("/order/delete")
    public String deletingMotorhome(@RequestParam int mhid, @RequestParam int orderid, Model model){
        ordersRepository.deleteMotorhomeFromOrder(orderid, mhid);
        Order order = ordersRepository.read(orderid);
        model.addAttribute("order", order);
        model.addAttribute("mhInOrder", order.getMhInOrder());
        return "order";
    }

    //
    //
    //ORDER DELETION
    //
    //

    @GetMapping("/orderDelete")
    public String deleteOrder(@RequestParam int oid, Model model){
        ordersRepository.delete(oid);
        indexRead(model);
        model.addAttribute("active", 0);
        return "orders";
    }

    //
    //
    //REGISTER DROP-OFF FOR ORDER
    //
    //
    @GetMapping("/orderDropOff")
    public String orderDropOff(@RequestParam int oid, Model model){
        Order order = ordersRepository.read(oid);
        model.addAttribute("order", order);
        return "orderDropOff";
    }

    @PostMapping("/dropOffForm")
    public String droppedOff(@ModelAttribute Order order, Model model){
        Order order1 = ordersRepository.read(order.getOrderID());
        ordersRepository.finishOrder(order1);
        indexRead(model);
        model.addAttribute("active", 0);
        return "orders";
    }

    //
    //
    //Generic method for reading all orders.
    //
    //
    public void indexRead(Model model) {
        model.addAttribute("orders", ordersRepository.readAll());
    }
}
