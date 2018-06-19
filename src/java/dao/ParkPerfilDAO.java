/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ParkPerfil;
import model.Parking;
import model.Vacancy;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class ParkPerfilDAO implements Dao<ParkPerfil>{
    
    private Session m_session;
    private Transaction m_transaction;
    
    @Override
    public ParkPerfil insert(ParkPerfil data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {
            m_session.save(data);
            m_transaction.commit();

        } catch (RuntimeException ex) {

            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::insert(ParkPerfil data) " + ex);
        } finally {
            m_session.close();
        }
        
        return data;
    }

    @Override
    public List<ParkPerfil> insert(List<ParkPerfil> dataList) {
        
        try {
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
              
            for(ParkPerfil data: dataList)
                m_session.save(data);
                m_transaction.commit();

        } catch (RuntimeException ex) {

            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::insert(ParkPerfil data) " + ex);
        } finally {
            m_session.close();
        }
        return dataList;
    }

    @Override
    public void remove(ParkPerfil data) {
        
        try {
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            m_session.delete(data);
            m_transaction.commit();
        } catch (RuntimeException ex) {
            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::remove(ParkPerfil data) " + ex);
        } finally {
            m_session.close();
        }
    }

    @Override
    public void remove(long id) {
        String SQL = "DELETE FROM parkperfil where id = :perfilId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("perfilId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkPerfil::remove(long id)");
        } finally{
            m_session.close();
        }
    }

    @Override
    public ParkPerfil update(ParkPerfil data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();

        try {
            m_session.update(data);
            m_transaction.commit();

        } catch (RuntimeException ex) {

            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::update(ParkPerfil data) " + ex);
        } finally {
            m_session.close();
        }
        return data;
    }

    @Override
    public ParkPerfil getById(long id) {
         m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        ParkPerfil data = null;
        
        try{
            data = (ParkPerfil) m_session.load(ParkPerfil.class, id);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::getById(id) " + ex);
        } finally{
            m_session.close();
        }
        
        return data;
    }

    @Override
    public List<ParkPerfil> getAllWithPaging(int start, int size) {
        List<ParkPerfil> list = new ArrayList<>();
        List results = null;
        Iterator iterator = null;
        
        String SQL = "SELECT * FROM parkperfil";
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(ParkPerfil.class);
            
            if (start >=0 && size > 0){
                query.setFirstResult(start);
                query.setMaxResults(size);
            }
            results = query.list();
            iterator = results.iterator();
            
            while(iterator.hasNext()){
                ParkPerfil perfil = (ParkPerfil) iterator.next();
                list.add(perfil);
            }
            
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("ParkPerfilDAO::getAll() " + ex);
        } finally{
            m_session.close();
        }
        
        return list;
    }
    
}
