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
@Table(name = "renting")
@Proxy(lazy=false)
public class Renting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;
    private int state;
    private int paymentState;
    
    /*COLOCAR O TRATAMENTO DO TIPO DATE -> TIMESTAMP*/
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    /*
    @Temporal(TemporalType.DATE)
    private Date currentDate;
    */
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE } )
    @JoinColumn(name = "id_client")
    Client owner;
    
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "id_vacancy")
    Vacancy vacancy;
    
    transient public static final int PAYMENT_STATE_OK = 0x01;
    transient public static final int PAYMENT_STATE_PENDING = 0x00;
    transient public static final int SATATE_VALID = 0x01;
    transient public static final int STATE_EXPIRED = 0x00;
    transient public static final int STATE_CANCELED = 0x02;
    
    public Renting(@NotNull Client client, @NotNull Vacancy vacancy, @NotNull Date startDate,
            @NotNull Date endDate){
    
            this.setOwner(client);
            this.setVacancy(vacancy);
            setStartDate(startDate);
            setEndDate(endDate);
            
    }
    protected Renting(){}
    
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState = paymentState;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /*
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
    */
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
    
    
    
}
