/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Vehicle;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class VehicleDAO implements Dao<Vehicle>{
    private Session m_session;
    private Transaction m_transaction;
    
    
    @Override
    public Vehicle insert(Vehicle data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
           m_session.saveOrUpdate(data);
           m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::insert() " + ex);
        } finally{
            m_session.close();
        }
        return data;
    }

    @Override
    public List<Vehicle> insert(List<Vehicle> dataList) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        try{
            for(Vehicle v: dataList){
                m_session.saveOrUpdate(v);
            }
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::insert(dataList) " + ex);
        }
        finally{
            m_session.close();
        }
    
        return dataList;
    }

    @Override
    public void remove(Vehicle data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.delete(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::deelte(data) " + ex);
            
        } finally{
            m_session.close();
        }
    }
    @Override
    public void remove(long id){
        String SQL = "DELETE FROM vehicle where id = :vehicleId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("vehicleId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::remove(long id)");
        } finally{
            m_session.close();
        }
    }

    @Override
    public Vehicle update(Vehicle data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        try{
            m_session.saveOrUpdate(data);
            m_transaction.commit();
            
        } catch(RuntimeException ex){
           m_transaction.rollback();
            System.out.println("VehicleDAO::update(data) " + ex);
        } finally{
            m_session.close();
        }
        
        return data;
    }

    @Override
    public Vehicle getById(long id) {
        Vehicle vehicle = null;
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try {
            vehicle = (Vehicle) m_session.load(Vehicle.class, id);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::getById() " + ex);
        } finally{
            m_session.close();
        }
        
        return vehicle;
    }

    /**
     * 
     * @param start Valor do indice registro inicial
     * @param size valor do tamanho da pagina
     * @return 
     */
    @Override
    public List<Vehicle> getAllWithPaging(int start, int offset) {
        List<Vehicle> list = new ArrayList<>();
        String SQL = "SELECT * FROM vehicle";
        List results = null;
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
           SQLQuery query = m_session.createSQLQuery(SQL);
           query.addEntity(Vehicle.class);
           
           if (start >= 0 && offset > 0){
               query.setFirstResult(start);
               query.setMaxResults(offset);
           }
           
           results = query.list();
           Iterator i = results.iterator();
           
           while(i.hasNext()){
               Vehicle v = (Vehicle) i.next();
               list.add(v);
           }
           
           m_transaction.commit();
        } catch(RuntimeException ex){
            
            m_transaction.rollback();
            System.out.println("VehicleDAO::getAll() " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
    public Vehicle getByLicensePlate(final String licensePlate){
        Vehicle vehicle = null;
        String SQL = "SELECT * FROM vehicle where licensePlate = :plate";
        
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try {
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Vehicle.class);
            query.setParameter("plate", licensePlate);
            vehicle = (Vehicle) query.uniqueResult();
            
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VehicleDAO::getByLicensePlate(...) " + ex);
        
        } finally{
            m_session.close();
        }
        
        return vehicle;
    
    }
}
