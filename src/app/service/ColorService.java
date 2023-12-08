/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Color;
import app.repository.ColorRepository;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ColorService {

    private final ColorRepository colorRepository = new ColorRepository();

    public List<Color> getAllColors() {
        return colorRepository.getAll();
    }

    public String addColor(Color color) {
        if (color.getName().trim().isEmpty()) {
            return "Tên Màu Trống";
        }
        color.setDeleted(Boolean.FALSE);
        int resultAdd = colorRepository.add(color);
        if (resultAdd > 0) {
            return "Thêm Màu Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi";
        }
    }

    public boolean changeStatus(String name) {
        return colorRepository.updateStatus(name) > 0;
    }

    public Color findByName(String name) {
        return colorRepository.findByName(name);
    }

    public Color findById(int id) {
        return colorRepository.findById(id);
    }

}
