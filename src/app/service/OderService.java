/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Oders;
import app.repository.OderRepository;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OderService {
    
    OderRepository oderRepository = new OderRepository();
    
    public List<Oders> getAllOders(){
        return oderRepository.getAllOders();
    }
    
    public List<Oders> getAllOdersByStatus(int status){
        return oderRepository.getAllListByStatus(status);
    }
    
    
}
