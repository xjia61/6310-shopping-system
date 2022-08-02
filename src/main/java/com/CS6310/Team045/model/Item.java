package com.CS6310.Team045.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "store",referencedColumnName = "name", nullable = false)
    private Store store;

    @Column(name = "unitPrice")
    private Integer unitPrice;

    private String storeName;
    //@OneToMany
    //private List<ItemLine> lines;


    public Item(String name, Integer weight, Integer unitPrice,Store store){
        this.name = name;
        this.weight = weight;
        this.store = store;
        this.unitPrice = unitPrice;

    }

//    public Item(){}


}
