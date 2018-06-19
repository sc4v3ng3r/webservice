/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author scavenger
 */

@Entity
@Table(name = "vacancy")
@Proxy(lazy=false)
public class Vacancy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "perfilId")
    private ParkPerfil perfil;
    
    private int type;
    private int number;
    private int state;
    
    transient public static final int STATE_AVAILABLE = 0x01;
    transient public static final int STATE_UNAVAILABLE = 0x00;
    
    transient public static final int TYPE_ROTARY = 0x01;
    transient public static final int TYPE_PRIVATE = 0x00;
    
    public Vacancy(){}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "VACANCY ID: " + id + " VACANCY NUMBER: " + number;
    }    

    public ParkPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(ParkPerfil perfil) {
        this.perfil = perfil;
    }
    
    
}
