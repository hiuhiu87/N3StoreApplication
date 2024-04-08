/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Material;
import app.repository.MaterialRepository;

import java.util.List;

/**
 * @author Admin
 */
public class MaterialService {

    private final MaterialRepository materialRepository = new MaterialRepository();

    public List<Material> getAllMaterials() {
        return materialRepository.getAll();
    }

    public String addMaterial(Material material) {
        try {
            if (material.getName().trim().isEmpty()) {
                return "Tên Chất Liệu Trống";
            }
            material.setDeleted(Boolean.FALSE);
            int resultAdd = materialRepository.add(material);
            if (resultAdd > 0) {
                return "Thêm Chất Liệu Thành Công";
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public int updateMaterial(Material material) {
        try {
            if (material.getName().trim().isEmpty()) {
                return 0;
            }
            return materialRepository.update(material.getId(), material);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean changeStatus(String name) {
        return materialRepository.updateStatus(name) > 0;
    }

    public Material findByName(String name) {
        return materialRepository.findByName(name);
    }

    public Material findById(int id) {
        return materialRepository.findById(id);
    }

}
