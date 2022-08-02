package com.CS6310.Team045.model;
import java.io.Serializable;
import java.util.*;
import com.CS6310.Team045.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "designated_drone")
    private Drone designatedDrone;
    private String droneId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn (name = "customer_id", nullable = false)
    private Customer requestedBy;

    @ManyToOne
    @JsonBackReference
    @JoinColumn( name = "store_name", nullable = false)
    private Store store;
    private String storestoreName;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<ItemLine> items;

    // item, {quantity, unitPrice, totalCost, totalWeight}
    //private HashMap<Item, int[]> items = new HashMap<>();
    private int weight = 0;
    private int cost = 0;
    //public Order(){}
    public Order(String id, Customer requestedBy, Store store, Drone drone) {
        this.id = id;
        this.requestedBy = requestedBy;
        this.store =store;
        this.designatedDrone = drone;
    }


    /*
    public int orderWeight(){
        int res =0;
        for(ItemLine line : this.items){
            res += line.getQuantity()*line.getItem().getWeight();
        }
        return res;
    }*/



}
