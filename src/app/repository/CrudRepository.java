/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.repository;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface CrudRepository<T> {

    List<T> getAll();

    int add(T t);

    int update(Integer id, T t);

    T findByName(String name);

}
