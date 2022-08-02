package com.CS6310.Team045.services;

import com.CS6310.Team045.exception.BaseException;
import com.CS6310.Team045.model.*;
import com.CS6310.Team045.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;


@Service
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemLineRepository itemLineRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private DroneRepository droneRepository;


    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private AuthUserRepository authUserRepository;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    //display all customer
    @Cacheable(value = "Customer")
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    //get a customer by id
    @Cacheable(value = "Customer", key = "#id")
    public Optional<Customer> findById(long id){
        return customerRepository.findById(id);
    }

    @Cacheable(value = "Customer", key = "#account")
    public Optional<Customer> findbyName(String account){
        Optional<Customer> opt = customerRepository.findByAccount(account);
        return opt;
    }

    // create a customer
    public void make_customer(Customer customer) throws Exception {
        Optional<Customer> opt = customerRepository.findByAccount(customer.getAccount());
        if(opt.isPresent()){
            throw new Exception("ERROR:customer_identifier_already_exists");
        } else {
            customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
            customerRepository.save(customer);
            // register to auth_user table;
            AuthUser authUser = new AuthUser(customer.getAccount(), customer.getPassword(), "USER");
            authUserRepository.save(authUser);
        }
    }

    @Cacheable(value="Store", key="#store")
    public Optional<Store> findStore(String store){
        return storeRepository.findStoreByName(store);
    }

    //create an order
    public String start_order(String storeName, String orderId, String customerAccount, String droneId) throws Exception {

//        System.out.println(storeName);
//        System.out.println(orderId);
//        System.out.println(droneId);
//        System.out.println(customerAccount);


        Optional<Store> store = findStore(storeName);
        if (store.isEmpty()) {
            throw new Exception("ERROR:store_identifier_does_not_exist");
        }

        Optional<Order> orderOpt = orderRepository.findOrderByStore_nameAndId(storeName,orderId);
        if (orderOpt.isPresent()) {
            throw new Exception("ERROR:order_identifier_already_exists");
        }

        Optional<Drone> droneOpt = droneRepository.findByStore_nameAndId(storeName,droneId);
        if (droneOpt.isEmpty()) {
            throw new Exception("ERROR:drone_identifier_does_not_exist");
        }

        Optional<Customer> customerOptional = customerRepository.findByAccount(customerAccount);
//        System.out.println(customerOptional);

        if(customerOptional.isEmpty()){
            throw new Exception("ERROR:customer_identifier_does_not_exist");
        }

        Order order = new Order(orderId, customerOptional.get(),store.get(), droneOpt.get());
        orderRepository.save(order);
        return orderId;

    }


    //show all orders at a given store
    @Cacheable("Order")
    public List<Order> display_orders(String store) throws Exception {
        if(storeRepository.findStoreByName(store).isPresent()){
            return orderRepository.findOrderByStore_name(store);
        }
        throw new Exception("ERROR:store_identifier_does_not_exist");

    }

    //show all orders of current customer
    @Cacheable("OrderOfUser")
    public List<Order> display_orders_of_customer(String customerAct) throws Exception {
        if(customerRepository.findByAccount(customerAct).isPresent()){
//            List<Order> orders = orderRepository.findAll();

            return orderRepository.findOrderByRequestedBy_account(customerAct);
        }
        throw new Exception("ERROR:customer_identifier_does_not_exist");

    }


    //add an item to the desigen order
    public void request_item(String storeName, String orderId, String itemName, Integer quantity) throws Exception {

        Optional<Store> store = findStore(storeName);
        if (store.isEmpty()) {
            throw new Exception("ERROR:store_identifier_does_not_exist");
        }

        Optional<Order> orderOpt = orderRepository.findOrderByStore_nameAndId(storeName,orderId);
        if (orderOpt.isEmpty()) {
            throw new Exception("ERROR:order_identifier_does_not_exist");
        }


        Optional<Item> optItem = itemRepository.findByStore_nameAndName(storeName, itemName);

        if (optItem.isEmpty()) {
            throw new Exception("ERROR:item_identifier_does_not_exist");
        }

        List<ItemLine> itemLines = orderOpt.get().getItems();
//        System.out.println(itemLines);
        for(ItemLine itemLine:itemLines){
//                    System.out.println(itemLine.getItem());

            if(itemLine.getItem().equals(itemName))
            {
                throw new Exception("ERROR:item_already_ordered");
            }
        }

        int unitPirce = optItem.get().getUnitPrice();
        int lineweight = quantity*optItem.get().getWeight();
        int linecost = unitPirce* quantity;
        Drone drone = orderOpt.get().getDesignatedDrone();
        Customer customer = orderOpt.get().getRequestedBy();
        if(linecost + customer.getOutstandingOrders() > customer.getCredits()){
                        System.out.println(linecost);
            System.out.println(customer.getOutstandingOrders());
            System.out.println(customer.getCredits());
            throw new Exception("ERROR:customer_cant_afford_new_item");
        }
        if(lineweight + drone.getCurrentLoad() > drone.getCapacity()) {
//            System.out.println(lineweight);
//            System.out.println(drone.getCurrentLoad());
//            System.out.println(drone.getCapacity());
            throw new Exception("ERROR:drone_cant_carry_new_item");
        }
        ItemLine itemLine = new ItemLine(itemName,quantity,orderOpt.get());
        customer.addOutstandingOrders(linecost);
        drone.addCurrentLoad(lineweight);
        customerRepository.save(customer);
        droneRepository.save(drone);
        itemLineRepository.save(itemLine);
    }
    //public void purchase(String store, String order){}
    // show all items under each order
    //public Map<String, ArrayList<>> display_orders(String storeName) {
    //}


    //purchase order
    public void purchase(String storeName, String orderId) throws Exception{

        Optional<Store> store = findStore(storeName);
        if (store.isEmpty()) {
            throw new Exception("ERROR:store_identifier_does_not_exist");
        }

        Optional<Order> orderOpt = orderRepository.findOrderByStore_nameAndId(storeName,orderId);
        if (orderOpt.isEmpty()) {
            throw new Exception("ERROR:order_identifier_does_not_exist");
        }
        if(orderOpt.get().getDesignatedDrone().getControlledBy()==null){
            throw new Exception("ERROR:pilot_does_not_exist");
        }
        Store s = store.get();
        Customer customer = orderOpt.get().getRequestedBy();
        Drone drone = orderOpt.get().getDesignatedDrone();

        Pilot pilot = drone.getControlledBy();
        int cost =0;
        for(ItemLine line:orderOpt.get().getItems()){
            cost += line.getQuantity()*itemRepository.findByStore_nameAndName( storeName, line.getItem()).get().getUnitPrice();
        }
//        int cost = orderOpt.get().orderCost();
        int weight = OrderWeight(orderOpt.get());
        customer.pay(cost);
//        drone.deductCurrentLoad(weight);
        s.addRevenue(cost);
        drone.deductFuel();
        drone.removeOrder(orderOpt.get());
        pilot.addExp();
        customerRepository.save(customer);
        droneRepository.save(drone);
        storeRepository.save(s);
        pilotRepository.save(pilot);
        orderRepository.delete(orderOpt.get());
    }


    //cancel order
    public void cancel_order(String store, String orderId) throws Exception{
//        System.out.println("<<<<<<<<<>>>>>>>>>");
//        System.out.println(store);
//        System.out.println(orderId);

        Optional<Order> order = orderRepository.findOrderByStore_nameAndId(store,orderId);
        if(storeRepository.findStoreByName(store).isEmpty()) {
            throw new Exception("ERROR:store_identifier_does_not_exist");
        }
            if(order.isPresent()){

                orderRepository.delete(order.get());
            } else {
                throw new Exception("ERROR:order_identifier_does_not_exist");
            }


    }

    public int OrderWeight(Order order){
        int weight =0;
        for(ItemLine l:order.getItems()){
            Optional<Item> i = itemRepository.findByStore_nameAndName(l.getItem(),order.getStore().getName());
            if(i.isPresent()){
                weight += l.getQuantity()*i.get().getWeight();
            }

        }
        return weight;
    }






}
