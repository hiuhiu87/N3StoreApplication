/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Size;
import app.model.Sole;
import app.repository.SizeRepository;
import app.repository.SoleRepository;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SoleService {

    private final SoleRepository soleRepository = new SoleRepository();

    public List<Sole> getAllSoles() {
        return soleRepository.getAll();
    }

    public String addSole(Sole sole) {
        if (sole.getName().trim().isEmpty()) {
            return "Tên Đế Giày Trống";
        }
        sole.setDeleted(Boolean.FALSE);
        int resultAdd = soleRepository.add(sole);
        if (resultAdd > 0) {
            return "Thêm Đế Giày Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi";
        }
    }
    
    public boolean changeStatus(String name) {
        return soleRepository.updateStatus(name) > 0;
    }

}
