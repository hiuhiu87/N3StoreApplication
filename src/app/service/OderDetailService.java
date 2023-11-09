/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.OderDetail;
import app.repository.OderDetailRepository;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OderDetailService {
     OderDetailRepository oderDetailRepository = new OderDetailRepository();
     
     public List<OderDetail> getAllOderDetails(){
      return oderDetailRepository.getAllOdersDetails();
     }
     
      public List<OderDetail> getPaginatedOders(int offset, int limit) {
        return oderDetailRepository.getAllOdersDetailsPhanTrang(offset, limit);
    }
      
     public int countOderDetail(){
       return oderDetailRepository.countOderDetail();
    }
}
