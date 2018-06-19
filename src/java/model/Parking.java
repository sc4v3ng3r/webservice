/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author scavenger
 */
@Entity
@Table(name="parking" )
@Proxy(lazy=false)
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double value;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitTime;
    
    // COLOCAR A DATA QUE OCORREU???
        // ??????????????????????????????/
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "id_vehicle")
    private Vehicle vehicle;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "id_vacancy")
    private Vacancy vacancy;
    
    protected Parking(){}
    
    // construtor utilizado para TESTE!
    public Parking(@NotNull Date entryTime, @NotNull Vacancy vacancy, @NotNull Vehicle vehicle){
        setEntryTime(entryTime);
        
        vacancy.setState(Vacancy.STATE_UNAVAILABLE);
        setVacancy(vacancy);
        setVehicle(vehicle);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n ENTRY: " + this.entryTime + "\nVeiculo: " + this.vehicle.getLicensePlate();
    }
 
    
}