/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Client;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class ClientDAO implements Dao<Client>{
    private Session m_session;
    private Transaction m_transaction;
    
    @Override
    public Client insert(Client data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.saveOrUpdate(data);
           
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
        } finally{
            m_session.close();
        }
        return data;
    }
    
    @Override
    public void remove(Client data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        try{
            m_session.delete(data);
            m_transaction.commit();
            
        } catch(RuntimeException ex){
             System.out.println("ClientDAO::remove() " + ex);
            m_transaction.rollback();
        } finally{
            m_session.close();
        }
    }
    
    @Override
    public void remove(long id){
        String SQL = "DELETE FROM cliente where id = :clientId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("clientId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ClientDAO::remove(long id)");
        } finally{
            m_session.close();
        }
    
    }
    @Override
    public Client update(Client data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        try{
            m_session.update(data);
            m_transaction.commit();
        } catch( RuntimeException ex ){
            m_transaction.rollback();
            System.out.println("ClientDAO::update(Client data) " + ex);
        } finally{
            m_session.close();
        }
        
        return data;
    }

    @Override
    public Client getById(long id) {
        
        Client client = null;
        
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            client = (Client) m_session.load(Client.class, id);
            m_transaction.commit();
        }
        catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ClientDAO::getById() " + ex);
        } finally{
            m_session.close();  
        }
        System.out.println("GETTING: " + client.getId() + " " + client.getName());
        return client;
    }

    @Override
    public List<Client> getAllWithPaging(int start, int size) {
        String SQL = "SELECT * FROM client";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        List<Client> clientList = new ArrayList<>();
        
        try {
           SQLQuery query = m_session.createSQLQuery(SQL);
           query.addEntity(Client.class);
           
           if (start >= 0 && size > 0){
               query.setFirstResult(start);
               query.setMaxResults(size);
           }
           
           Iterator iterator = query.list().iterator();
           
           while( iterator.hasNext() ){
               clientList.add( (Client) iterator.next() );
           }
           
           m_transaction.commit();
           
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ClientDAO::getAll() " + ex);
            
        } finally{
            m_session.close();
        }
         return clientList;
    }

    @Override
    public List<Client> insert(List<Client> dataList) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            for(Client client: dataList){
                m_session.saveOrUpdate(client);
            }
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ClientDAO::insert(list) " + ex);
            
        } finally{
            m_session.close();
        }
         return dataList;
    }


}
