/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Vacancy;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author scavenger
 */
public class VacancyDAO implements Dao<Vacancy>{

    private Session m_session;
    private Transaction m_transaction;
    
    @Override
    public Vacancy insert(Vacancy data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.saveOrUpdate(data);
            m_transaction.commit();
        } catch (RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::insert(Vacancy data) " + ex);
        } finally{
            m_session.close();
        }
        return data;
    }

    @Override
    public List<Vacancy> insert(List<Vacancy> dataList) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        try{
            
            for(Vacancy v: dataList)
                m_session.saveOrUpdate(v);
            
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::insert(Vacancy data)" + ex);
            
        } finally{
            m_session.close();
        }
        return dataList;
    }

    @Override
    public void remove(Vacancy data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.delete(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::remove(Vacancy data)" + ex);
        } finally{
            m_session.close();
        }
    }
    
    @Override
    public void remove(long id){
        String SQL = "DELETE FROM vacancy where id = :vacancyId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.setParameter("vacancyId", id);
            query.executeUpdate();
            m_transaction.commit();
            
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::remove(long id)");
        } finally{
            m_session.close();
        }
    }

    @Override
    public Vacancy update(Vacancy data) {
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
        
        try{
            m_session.saveOrUpdate(data);
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::update(Vacancy data)" + ex);
        } finally{
            m_session.close();
        }
        return data;
    }

    @Override
    public Vacancy getById(long id) {
      Vacancy vacancy = null;
      m_session = HibernateUtil.getSessionFactory().openSession();
      m_transaction = m_session.beginTransaction();
      
      try{
        vacancy =(Vacancy) m_session.load(Vacancy.class, id);
        m_transaction.commit();
      } catch(RuntimeException ex){
          m_transaction.rollback();
          System.out.println("VacancyDAO::getById(long id)" + ex);
      } finally{
          m_session.close();
      }
      return vacancy;
    }
    
    public List<Vacancy> getByPerfilId(long id){
        List<Vacancy> list = new ArrayList<>();
        SQLQuery query;
        String SQL = "SELECT * FROM vacancy where perfilId = :perfilId";
        try{
            m_session = HibernateUtil.getSessionFactory().openSession();
            m_transaction = m_session.beginTransaction();
            query = m_session.createSQLQuery(SQL);
            query.setParameter("perfilId", id);
            query.addEntity(Vacancy.class);
            
            Iterator iterator = query.list().iterator();
            while(iterator.hasNext())
                list.add( (Vacancy) iterator.next());
            
            m_transaction.commit();
        
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::getByPerfilId(long id) " + ex );
        } finally{
            m_session.close();
        }
        return list;
        
    }

    @Override
    public List<Vacancy> getAllWithPaging(int start, int offset) {
        List<Vacancy> list = new ArrayList<>();
        List results = null;
        String SQL = "SELECT * FROM vacancy";
        
        m_session = HibernateUtil.getSessionFactory().openSession();
        m_transaction = m_session.beginTransaction();
    
        try{
            SQLQuery query = m_session.createSQLQuery(SQL);
            query.addEntity(Vacancy.class);
            if (start >= 0 && offset > 0){
                query.setFirstResult(start);
                query.setMaxResults(offset);
            }
            results = query.list();
            Iterator iterator = results.iterator();
            
            while(iterator.hasNext()){
                Vacancy v = (Vacancy) iterator.next();
                list.add(v);
            }
            
            m_transaction.commit();
        } catch(RuntimeException ex){
            m_transaction.rollback();
            System.out.println("VacancyDAO::getAll() " + ex);
        } finally{
            m_session.close();
        }
        return list;
    }
    
}
