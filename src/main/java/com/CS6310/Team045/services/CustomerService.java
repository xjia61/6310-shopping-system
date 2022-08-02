package com.CS6310.Team045.services;

import com.CS6310.Team045.model.Customer;
import com.CS6310.Team045.model.ItemLine;
import com.CS6310.Team045.model.Order;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    //display all customer
    public List<Customer> getCustomers();

    //get a customer by id
    public Optional<Customer> findById(long id);

    // create a customer
    public void make_customer(Customer customer);



    //create an order
    public void start_order(Order order);

    //show all orders at a given store
    public List<Order> display_orders(String store);

    //add an item to the desigen order
    public void request_item(String storeName, String orderId, String item, int quantity, int unitPirce);

    // soho all items under each order
    //public Map<String, ArrayList<>> display_orders(String storeName) {
    //}


    //purchase order
    //cancel order
    public void cancel_order(String store, String orderId);

}
