/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author scavenger
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Proxy;

/**
 * Classe que contem as atuais configurações de
 * um Park.
 *
 *  - Nome
 *  - Número total de vagas
 *  - Número de vagas Privadas
 *  - Número de vafas Rotativas
 *  - Tarifas
 *
 */
@Entity
@Table(name = "parkperfil")
@Proxy(lazy=false)
public class ParkPerfil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String perfilName;
    private int rotaryVacancys = 0;
    private int privateVacancyes = 0;

    public ParkPerfil(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPerfilName() {
        return perfilName;
    }

    public void setPerfilName(String perfilName) {
        this.perfilName = perfilName;
    }

    public int getRotaryVacancys() {
        return rotaryVacancys;
    }

    public void setRotaryVacancys(int rotaryVacancys) {

        this.rotaryVacancys = rotaryVacancys;
        //setTotalVacancyes(this.privateVacancyes + this.rotaryVacancys);
    }

    public int getPrivateVacancyes() {
        return privateVacancyes;
    }

    public void setPrivateVacancyes(int privateVacancyes) {
        this.privateVacancyes = privateVacancyes;
        //setTotalVacancyes(this.privateVacancyes + this.rotaryVacancys);
    }

}
