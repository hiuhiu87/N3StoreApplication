/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Color;
import app.repository.ColorRepository;
import org.junit.After;
import org.junit.Before;

import java.util.List;

/**
 * @author Admin
 */
public class ColorService {
    private ColorService colorService;
    private ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.getAll();
    }

    public String addColor(Color color) throws IllegalArgumentException, RuntimeException {
        if (color.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên Màu Trống");
        }
        color.setDeleted(Boolean.FALSE);
        int resultAdd = colorRepository.add(color);
        if (resultAdd > 0) {
            return "Thêm Màu Thành Công";
        } else {
            throw new RuntimeException("Đã Xảy Ra Lỗi");
        }
    }


    public boolean changeStatus(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Tên màu không được null");
        }
        return colorRepository.updateStatus(name) > 0;
    }


    public Color findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Tên màu không được null");
        }
        return colorRepository.findByName(name);

    }

    public Color findById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Tên màu không được null");
        }
        return colorRepository.findById(id);
    }

}
