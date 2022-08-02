package com.CS6310.Team045.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "pilots")
public class Pilot extends User implements Serializable {

    @Column(name = "account")
    private String account;
    @Column(name = "tax_id")
    private String taxID;
    @Column(name = "license_id")
    private String licenseID;
    @Column(name = "experience")
    private Integer experience;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "control_id")
    private Drone control;

    //public Pilot(){}

    public Pilot(String account, String firstName, String lastName, String phoneNumber, String taxID,
                 String licenseID, Integer experience) {
        super(firstName, lastName, phoneNumber);
        this.account = account;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.experience = experience;
        control = new Drone();
    }
    public void addExp(){
        this.experience++;
    }
/*
    public void assign(Drone drone){
        if (this.control != null) {
            this.control.removePilot();
        }
        this.control = drone;
    }
    public void remove(){
        this.control = null;
    }*/

}
