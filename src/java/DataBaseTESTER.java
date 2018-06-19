
import dao.ClientDAO;
import dao.ParkPerfilDAO;
import dao.ParkingDAO;
import dao.VacancyDAO;
import dao.VehicleDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Client;
import model.ParkPerfil;
import model.Parking;
import model.Vacancy;
import model.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;
import utility.test.InstanceGenerator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author scavenger
 */
public class DataBaseTESTER {
    public static void main(String args[]){
        
        /*
        ParkPerfil perfil = new ParkPerfil();
        perfil.setPerfilName("Perfil Teste");
        ParkPerfilDAO dao = new ParkPerfilDAO();
        dao.insert(perfil);
        System.out.println("PERFIL ID: " + perfil.getId());
        VacancyDAO vacancyDao = new VacancyDAO();
        
        for(int i=0; i < 25; i++){
            Vacancy v= new Vacancy();
            v.setNumber(i+1);
            v.setType(Vacancy.TYPE_ROTARY);
            v.setState(Vacancy.STATE_AVAILABLE);
            v.setPerfil(perfil);
            vacancyDao.insert(v);
           
        }*/
        
        VehicleDAO dao = new VehicleDAO();
    
        
       
        
        
    }
    
    
}
