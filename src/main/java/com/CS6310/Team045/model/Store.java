package com.CS6310.Team045.model;
import com.CS6310.Team045.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "stores")
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "revenue")
    private Integer revenue;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
    private List<Item> items;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "store")
    private List<Drone> drones;
    //@OneToMany
    //private List<Drone> pilots;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "store")
    //@JoinColumn(name = "orders_id")

    private List<Order> orders;

    //public Store(){}

    public Store(String name, Integer revenue){
        this.name = name;
        this.revenue = revenue;
    }
    public void addRevenue(Integer cost){
        this.revenue += cost;
    }




}
