package com.CS6310.Team045.controller;

import com.CS6310.Team045.model.*;
import com.CS6310.Team045.services.StoreServiceImpl;
import com.CS6310.Team045.services.CustomerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("admin/cs6310/team045")
public class StoreController {
    @Autowired
    private StoreServiceImpl storeService;

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping(value = "/admin_homepage")
    public ModelAndView hello(Model model) {
//        http://localhost:8080/admin/cs6310/team045/admin_homepage
//        String name = "jiangbei";
//        model.addAttribute("name", name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_homepage.html");
        return modelAndView;
    }
    @GetMapping(value = "/make_store_form")
    public ModelAndView ms() {
//        http://localhost:8080/admin/cs6310/team045/admin_homepage
//        String name = "jiangbei";
//        model.addAttribute("name", name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_store.html");
        modelAndView.addObject("store", new Store());

        return modelAndView;
    }

    //make store
    @PostMapping(value = "/make_store")
    public ModelAndView make_store(@ModelAttribute Store store){
//        http://localhost:8080/cs6310/team045/make_store?name=krogrr&revenue=33000
        String errMsg;
        try{
//            System.out.println(request);
//            String name = request.getParameter("name");
            String name = store.getName();
//            System.out.println(name);
            Integer revenue = store.getRevenue();
//            Integer revenue = Integer.parseInt(request.getParameter("revenue"));
//            System.out.println(revenue);
//            Integer revenue = Integer.parseInt(revenue_str);
//            System.out.println(name);
//            System.out.println(revenue);

//            Store store = new Store(name,revenue);
//                        System.out.println(store);

            storeService.makeStore(name,revenue);
//            System.out.println("OK, change_completed");
            errMsg = "OK, change_completed";
        }catch (Exception e){
//            System.out.println(e.getMessage());
//            System.out.println("Error:store_identifier_already_exists");
            errMsg = e.getMessage();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_store_msg.html");
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }

    //get all stores
    @GetMapping(value = "/stores")
    public ModelAndView findAllStores(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stores.html");
        modelAndView.addObject("stores", storeService.getAllStores());

        return modelAndView;
    }

//    //make store
//    @PostMapping(value = "/make_store")
//    public void make_store(HttpServletRequest request){
////        http://localhost:8080/cs6310/team045/make_store?name=krogrr&revenue=33000
//        try{
////            System.out.println(request);
//            String name = request.getParameter("name");
//            System.out.println(name);
//            Integer revenue = Integer.parseInt(request.getParameter("revenue"));
//            System.out.println(revenue);
////            Integer revenue = Integer.parseInt(revenue_str);
//            System.out.println(name);
//            System.out.println(revenue);
//
////            Store store = new Store(name,revenue);
////                        System.out.println(store);
//
//            storeService.makeStore(name,revenue);
//            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            System.out.println("Error:store_identifier_already_exists");
//        }
//    }
    @GetMapping(value = "/stock_item_form")
    public ModelAndView stock_item_form() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stock_item.html");
        modelAndView.addObject("item", new Item());
        return modelAndView;
    }

    @PostMapping(value = "/stock_item")
    public ModelAndView stock_item(@ModelAttribute Item item){
        String errMsg;
        try{
//            String name = store.getName();
//            Integer revenue = store.getRevenue();
            String name = item.getName();
            Integer weight = item.getWeight();
            Integer unitPrice = item.getUnitPrice();
            String storeName = item.getStoreName();
            storeService.addItem(name,weight,unitPrice, storeName);
            errMsg = "OK, change_completed";
        }catch (Exception e){
            errMsg = e.getMessage();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stock_item_msg.html");
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }

//    //add item
//    @PostMapping(value = "/sell_item")
//    public void sell_item(HttpServletRequest request){
////        System.out.println(item.getName());
////        System.out.println(item.getWeight());
////        System.out.println(item.getSname());
//
//        try{
//            String name = request.getParameter("name");
//            String weight = request.getParameter("weight");
//            String store = request.getParameter("store");
//            storeService.addItem(name,weight,store);
//            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

//    @RequestMapping(value = "/items")
//    public List<Item> findAllItems(HttpServletRequest request){
////        http://localhost:8080/cs6310/team045/display_items?name=kroger
//        String storeName = request.getParameter("name");
//
////        System.out.println(storeName);
//
//        try{
//            return storeService.display_items(storeName);
////            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
    @GetMapping(value = "/items_form")
    public ModelAndView items_form() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("items_input.html");
        modelAndView.addObject("stores", storeService.getAllStores());
        modelAndView.addObject("store", new Store());
        return modelAndView;
    }

    @PostMapping(value = "/items")
    public ModelAndView display_items(@ModelAttribute Store store) throws Exception {
        String errMsg;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("items_display.html");
        try{
            String storeName = store.getName();
//            System.out.println(storeName);
//            System.out.println(storeService.display_drones(storeName));
            modelAndView.addObject("items",  storeService.display_items(storeName));
            errMsg = "OK, change_completed";
        }catch (Exception e){
            errMsg = e.getMessage();
        }
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }
    @GetMapping(value = "/make_pilot_form")
    public ModelAndView mpf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_pilot.html");
        modelAndView.addObject("pilot", new Pilot());
        return modelAndView;
    }

    @PostMapping(value = "/make_pilot")
    public ModelAndView make_pilot(@ModelAttribute Pilot pilot){
        String errMsg;
        try{
            storeService.makePilot(pilot);
            errMsg = "OK, change_completed";
        }catch (Exception e){
            errMsg = e.getMessage();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_pilot_msg.html");
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }


//    //make pilot
//    @PostMapping(value = "/make_pilot")
//    public void make_pilot(@RequestBody Pilot pilot){
//        try{
//
//            storeService.makePilot(pilot);
//            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            System.out.println("Error:store_identifier_already_exists");
//        }
//    }



    @GetMapping(value = "/pilots")
    public ModelAndView findAllPilots(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pilots.html");
        modelAndView.addObject("pilots", storeService.display_pilots());

        return modelAndView;
    }

//    //display pilots
//    @GetMapping(value = "/pilots")
//    public List<Pilot> findAllPilots(){
//        return storeService.display_pilots();
//    }

    @GetMapping(value = "/make_drone_form")
    public ModelAndView mdf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_drone.html");
        modelAndView.addObject("drone", new Drone());
        modelAndView.addObject("storeName", "store_name_placeholder");
        return modelAndView;
    }

//    @PostMapping(value = "/make_drone")
//    public ModelAndView make_drone(@ModelAttribute Drone drone, @ModelAttribute String storeName){
//        System.out.println("drone is: ");
//        System.out.println(drone);
//        System.out.println("store name is: ");
//        System.out.println(storeName);
//        String errMsg;
//        try{
////            String storeName = storeName;
//            String droneId = drone.getId();
//            Integer capacity = drone.getCapacity();
//            Integer fuel = drone.getTripsBeforeRefueling();
//
//            storeService.make_drone(storeName, droneId,capacity,fuel);
//            errMsg = "OK, change_completed";
//        }catch (Exception e){
//            errMsg = e.getMessage();
//        }
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("make_drone_msg.html");
//        modelAndView.addObject("Message", errMsg);
//        return modelAndView;
//    }
@PostMapping(value = "/make_drone")
public ModelAndView make_drone(@ModelAttribute Drone drone){
//    System.out.println("drone is: ");
//    System.out.println(drone);
//    System.out.println("store name is: ");
//    System.out.println(storeName);
    String errMsg;
    try{
            String storeName = drone.getStoreName();
//        System.out.println(storeName);

        String droneId = drone.getId();
        Integer capacity = drone.getCapacity();
        Integer fuel = drone.getTripsBeforeRefueling();

        storeService.make_drone(storeName, droneId,capacity,fuel);
        errMsg = "OK, change_completed";
    }catch (Exception e){
        errMsg = e.getMessage();
    }
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("make_drone_msg.html");
    modelAndView.addObject("Message", errMsg);
    return modelAndView;
}

//    //make drone
//    @PostMapping(value = "/make_drone")
//    public void make_drone(HttpServletRequest request){
//        String storeName = request.getParameter("name");
//        String droneId = request.getParameter("droneId");
//        String capacity = request.getParameter("capacity");
//        String fuel = request.getParameter("fuel");
////        System.out.println(drone);
//        try{
//            storeService.make_drone(storeName, droneId,capacity,fuel);
//            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            System.out.println("Error:store_identifier_already_exists");
//        }
//    }

    @GetMapping(value = "/drones_form")
    public ModelAndView dronef() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drones_input.html");
        modelAndView.addObject("stores", storeService.getAllStores());
        modelAndView.addObject("store", new Store());

        return modelAndView;
    }

    @PostMapping(value = "/drones")
    public ModelAndView findAllDrones(@ModelAttribute Store store) throws Exception {
        String errMsg;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("drones_display.html");
        try{
            String storeName = store.getName();
//            System.out.println(storeName);
//            System.out.println(storeService.display_drones(storeName));
            modelAndView.addObject("drones", storeService.display_drones(storeName));
            errMsg = "OK, change_completed";
        }catch (Exception e){
//            System.out.println(e.getMessage());
            errMsg = e.getMessage();
        }
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }


//    //display drones
//    @RequestMapping(value = "/drones")
//    public List<Drone> findAllDrones(HttpServletRequest request){
////        http://localhost:8080/cs6310/team045/display_items?name=kroger
//        String storeName = request.getParameter("name");
//
////        System.out.println(storeName);
//
//        try{
//            return storeService.display_drones(storeName);
////            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

    @GetMapping(value = "/fly_drone_form")
    public ModelAndView fly_drone_form() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fly_drone.html");
        modelAndView.addObject("drone", new Drone());
        return modelAndView;
    }


    @PostMapping(value = "/fly_drone")
    public ModelAndView fly_drone(@ModelAttribute Drone drone){
        String errMsg;
        try{
            String storeName = drone.getStoreName();
            String pilotAccount = drone.getPilotAct();
            String droneId = drone.getId();
            String orderId = drone.getOrderorderId();
            storeService.flyDrone(storeName, pilotAccount, droneId);
            errMsg = "OK, change_completed";
        }catch (Exception e){
            errMsg = e.getMessage();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fly_drone_msg.html");
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }

//    //fly drone
//    @PostMapping(value = "/fly_drone")
//    public void fly_drone(HttpServletRequest request){
////        http://localhost:8080/cs6310/team045/fly_drones?storeName=kroger&pilotAccount=ffig8&droneId=1
//        //http://localhost:8080/cs6310/team045/fly_drones?storeName=target&pilotAccount=ffig8&droneId=1
//
//
//        try{
//            String storeName = request.getParameter("storeName");
//            String pilotAccount = request.getParameter("pilotAccount");
//            String droneId = request.getParameter("droneId");
//            //System.out.println(droneId);
//
//            storeService.flyDrone(storeName, pilotAccount, droneId);
//            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            System.out.println("Error:store_identifier_already_exists");
//        }
//    }


    @PostMapping(value = "/make_customer")
    public ModelAndView make_customers(@ModelAttribute Customer customer){
        String errMsg;
        try{
//        Customer customer = new Customer(account, password, firstName, lastName, phoneNumber, rating, credits);


            customerService.make_customer(customer);
            errMsg = "OK, change_completed";

        }

        catch (Exception e){
            errMsg = e.getMessage();

//            System.out.println(e.getMessage());
//            System.out.println("Error:store_identifier_already_exists");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_customer_msg.html");
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }


    @GetMapping(value = "/make_customer_form")
    public ModelAndView mc() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("make_customer.html");
        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @GetMapping(value = "/customers_form")
    public ModelAndView cf() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customers_form.html");
        modelAndView.addObject("storeName", "store_name_place_holder");
        return modelAndView;
    }

    //get all stores
    @GetMapping(value = "/customers")
    public ModelAndView display_customers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customers.html");
        modelAndView.addObject("customers", customerService.getCustomers());

        return modelAndView;
    }

//    @GetMapping(value = "/orders_form")
//    public ModelAndView ordsers_f() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("orders_form.html");
//        modelAndView.addObject("storeName", "store_name_place_holder");
//        return modelAndView;
//    }

    @GetMapping(value = "/orders_form")
    public ModelAndView orders_form() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders_input.html");
        modelAndView.addObject("stores", storeService.getAllStores());
        modelAndView.addObject("store", new Store());
        return modelAndView;
    }

    @PostMapping(value = "/orders")
    public ModelAndView display_orders(@ModelAttribute Store store) throws Exception {
        String errMsg;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders_display.html");
        try{
            String storeName = store.getName();
//            System.out.println(storeName);
//            System.out.println(storeService.display_drones(storeName));
            modelAndView.addObject("orders",  customerService.display_orders(storeName));
            errMsg = "OK, change_completed";
        }catch (Exception e){
            errMsg = e.getMessage();
        }
        modelAndView.addObject("Message", errMsg);
        return modelAndView;
    }


//    @PostMapping(value ="/orders")
//    public ModelAndView display_orders(@ModelAttribute String storeName){
//        List<Order> orders = null;
//        String errMsg;
//        try{
//
//            orders = customerService.display_orders(storeName);
//            errMsg = "OK, change_completed";
////            System.out.println("OK, change_completed");
//        }catch (Exception e){
//            errMsg = e.getMessage();
////            System.out.println(e.getMessage());
//        }
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("orders_msg.html");
//        modelAndView.addObject("Message", errMsg);
//        modelAndView.addObject("orders", orders);
//        return modelAndView;
//    }

}
