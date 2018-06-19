/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author scavenger
 */
public interface Dao<T> {
    
    T insert(T data);
    List<T> insert(List<T> dataList);
    void remove(T data);
    void remove(long id);
    T update(T data);
    
    T getById(long id);
    List<T> getAllWithPaging(int start, int size);
}
