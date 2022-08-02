package com.CS6310.Team045.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.CS6310.Team045.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@ToString
@Table(name = "drone")
public class Drone implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "tripsbefore_refueling")
    private Integer tripsBeforeRefueling;
    @Column(name = "current_load")
    private Integer currentLoad=0;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "store")
    private Store store;
    private String storeName;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "control")
    //@JoinColumn(name = "account")
    private Pilot controlledBy;
    private String pilotAct;

    @JsonIgnore
    @OneToMany(mappedBy = "designatedDrone")
    private List<Order> orders;
    private String orderorderId;

    private Integer num_orders = 0;

    //public Drone(){}

    public Drone(String id, Integer capacity, Integer tripsBeforeRefueling) {
        this.id = id;
        this.capacity = capacity;
        this.tripsBeforeRefueling = tripsBeforeRefueling;
    }

    public Drone(String id, Integer capacity, Integer tripsBeforeRefueling, Store store) {
        this.id = id;
        this.capacity = capacity;
        this.tripsBeforeRefueling = tripsBeforeRefueling;
        this.store = store;
    }

    public Drone(String id, Integer capacity, Integer tripsBeforeRefueling, String storeName) {
        this.id = id;
        this.capacity = capacity;
        this.tripsBeforeRefueling = tripsBeforeRefueling;
        this.storeName = storeName;
    }

    public void assign(Pilot pilot){

        this.controlledBy = pilot;

    }
    public void removePilot(){
        this.controlledBy = null;
    }

    public void addCurrentLoad(Integer load){
        currentLoad += load;
    }
//    public void deductCurrentLoad(Integer load){
//        currentLoad -= load;
//    }
    public void deductFuel(){
        this.tripsBeforeRefueling--;
    }

    public void removeOrder(Order order){
        this.currentLoad -= order.getWeight();
        this.orders.remove(order);
    }
    public void addOrder(Order order){
        this.orders.add(order);
    }

    public void adjust(){
        this.num_orders = orders.size();
        this.pilotAct = this.controlledBy.getAccount();
    }

}
