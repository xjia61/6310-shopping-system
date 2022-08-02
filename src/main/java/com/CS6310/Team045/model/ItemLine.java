package com.CS6310.Team045.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
//@ToString
@Entity
@Table(name = "itemLine")
public class ItemLine implements Serializable {
    @Id
    @Column(name = "item")
    //@ManyToOne
    private String item;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",nullable = false)
    @JsonBackReference
    private Order order;
    private String storestoreName;
    private String orderorderId;


    public ItemLine(String item, Integer quantity,Order order){
        this.item = item;
        this.quantity = quantity;
        this.order =order;
    }


    /*public String itemLineToString(){
        int weight = quantity*item.getWeight();
        String res = "item_name:" + item.getName()+",total_quantity:"+quantity+",total_cost:"+ quantity*unitPrice
                +",total_weight:"+weight;
        return res;
    }*/

}
