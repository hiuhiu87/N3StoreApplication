/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.KhachHang;
import app.repository.SellRepository;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class SellService {
    
    SellRepository sellRepository = new SellRepository();
    
    public List<KhachHang> getAllKhachHang(){
        return sellRepository.getAllKhachHang();
    }
    
    public int addKhachHang(KhachHang kh){
        return sellRepository.addKhachHang(kh);
    }
    
    public int updateKhachHang (KhachHang kh , int id){
        return sellRepository.updateKhachHang(kh, id);
    }
}
