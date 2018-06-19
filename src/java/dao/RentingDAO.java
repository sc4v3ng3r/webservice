/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Renting;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class RentingDAO implements Dao<Renting>{
    private Session m_session;
    private Transaction m_transaction;
    
    @Override
    public Renting insert(Renting data) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.saveOrUpdate(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::insert(Renting data) " + ex);
        } finally{
            m_session.close();
        }
        return data;
    }

    @Override
    public List<Renting> insert(List<Renting> dataList) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            for(Renting data: dataList)
                m_session.saveOrUpdate(data);
            
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::insert(List<Renting> dataList) " + ex);
        } finally{
            m_session.close();
        }
        
        return dataList;
    }

    @Override
    public void remove(Renting data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.delete(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::remove(Renting data) " + ex);
        } finally{
            m_session.close();
        }
    }
    
    @Override
    public void remove(long id){
        String SQL = "DELETE FROM renting where id = :rentingId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("rentingId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::remove(long id)");
        } finally{
            m_session.close();
        }
    }

    @Override
    public Renting update(Renting data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.update(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::update(Renting data) " + ex);
        } finally{
            m_session.close();
        }
        return data;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Renting getById(long id) {
        Renting data = null;
        
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            data = (Renting) m_session.load(Renting.class, id);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::getById(long id) " + ex);
        } finally{
            m_session.close();
        }
        return data;
    }

    @Override
    public List<Renting> getAllWithPaging(int start, int offset) {
        List<Renting> list = new ArrayList<>();
        List results;
        String SQL = "SELECT * FROM renting";
        
        try {
            
            m_session  = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Renting.class);
            
            if (start >= 0 && offset > 0){
                query.setFirstResult(start);
                query.setMaxResults(offset);
            }
            
            results = query.list();
            Iterator iterator = results.iterator();
            
            while(iterator.hasNext())
                list.add( (Renting)iterator.next() );
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("RentingDAO::getAll() " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
}
