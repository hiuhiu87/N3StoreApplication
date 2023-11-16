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

    public List<Oders> getAllOders() {
        return oderRepository.getAllOders();
    }

    public List<Oders> getPaginatedOders(int offset, int limit) {
        return oderRepository.getPaginatedOders(offset, limit);
    }

    public List<Oders> getAllOdersByStatus(int status) {
        return oderRepository.getAllListByStatus(status);
    }

    public int countOder() {
        return oderRepository.countOder();
    }

    public void importDataFromExcel(String file) {
        oderRepository.importDataFromExcel(file);
    }
    
    public List<Oders> getAllListPrintOders(){
        return oderRepository.getAllListPrintOder();
    }
    
    public int updateDeleted(boolean deleted , int id){
        return oderRepository.updateDeleted(deleted, id);
    }

}
