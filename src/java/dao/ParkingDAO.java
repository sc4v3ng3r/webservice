/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import model.Parking;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class ParkingDAO implements Dao<Parking> {

    private Session m_session;
    private Transaction m_transaction;

    @Override
    public Parking insert(Parking data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {
            m_session.merge(data);
            m_transaction.commit();

        } catch (RuntimeException ex) {

            m_transaction.rollback();
            System.out.println("ParkingDAO::insert(Parking data) " + ex);
        } finally {
            m_session.close();
        }
        return data;
    }

    @Override
    public List<Parking> insert(List<Parking> dataList) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {

            for (Parking p : dataList) {
                m_session.merge(p);
            }
            m_transaction.commit();

        } catch (RuntimeException ex) {
            m_transaction.rollback();
            System.out.println("ParkingDAO::insert(List<Parking> dataList) " + ex);
        } finally {
            m_session.close();
        }

        return dataList;
    }

    @Override
    public void remove(Parking data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {
            m_session.delete(data);
            m_transaction.commit();
        } catch (RuntimeException ex) {
            m_transaction.rollback();
            System.out.println("ParkingDAO::remove(Parking data) " + ex);
        } finally {
            m_session.close();
        }
    }

    @Override
    public void remove(long id){
        String SQL = "DELETE FROM parking where id = :parkingId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("parkingId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::remove(long id)");
        } finally{
            m_session.close();
        }
    }
    @Override
    public Parking update(Parking data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {
            m_session.merge(data);
            m_transaction.commit();

        } catch (RuntimeException ex) {
            m_transaction.rollback();
            System.out.println("ParkingDAO::update(Parking data) " + ex);
        } finally {
            m_session.close();
        }
        
        return data;
    }

    @Override
    public Parking getById(long id) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        Parking data = null;
        
        try{
            data = (Parking) m_session.load(Parking.class, id);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getById(id) " + ex);
        } finally{
            m_session.close();
        }
        
        return data;
    }

    public List<Parking> getByVacancyId(long id, int start, int size){
        List<Parking> list = new ArrayList<>();
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parking WHERE id_vacancy = :id";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Parking.class);
            query.setParameter("id", id);
            if (start >= 0 && size > 0) {
                query.setFirstResult(start);
                query.setMaxResults(size);
            }
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                Parking parking = (Parking) iterator.next();
                list.add(parking);
            }
            
            m_transaction.commit();
            System.out.println("Park list size: " + list.size());
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getByVacancyId(long id) " + ex);
        } finally{
            m_session.close();
        }
   
        return list;
    }
    
    public List<Parking> getByVehicleId(long vehicleId){
        List<Parking> list = null;
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parking WHERE id_vehicle = :id";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Parking.class);
            query.setParameter("id", vehicleId);
            
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                Parking parking = (Parking) iterator.next();
                list.add(parking);
            }
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getByVehicleId(long vehicleId) " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
    public List<Parking> getByDate(Date date){
        List<Parking> list = null;
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parking WHERE entryTime = :date";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Parking.class);
            query.setDate("date", date);// deixa de lado a hora!
            
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                Parking parking = (Parking) iterator.next();
                list.add(parking);
            }
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getByDate(Date date) " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
    public List<Parking> getBetweenDates(Date start, Date end){
         List<Parking> list = null;
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parking WHERE entryTime >= ? and entryTime <= ?";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Parking.class);
            query.setDate(0, start);
            query.setDate(1, end);
            
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                Parking parking = (Parking) iterator.next();
                list.add(parking);
            }
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getBetweenDates(Date start, Date end) " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
    @Override
    public List<Parking> getAllWithPaging(int start, int offset) {
        List<Parking> list = new ArrayList<>();
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parking";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Parking.class);
            
            if (start >=0 && offset > 0){
                query.setFirstResult(start);
                query.setMaxResults(offset);
            }
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                Parking parking = (Parking) iterator.next();
                list.add(parking);
            }
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkingDAO::getAll() " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
}
