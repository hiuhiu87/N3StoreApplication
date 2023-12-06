/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Size;
import app.repository.SizeRepository;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SizeService {

    private final SizeRepository sizeRepository = new SizeRepository();

    public List<Size> getAllSizes() {
        return sizeRepository.getAll();
    }

    public String addSize(Size size) {
        if (size.getName().trim().isEmpty()) {
            return "Tên Kích Cỡ Trống";
        }
        size.setDeleted(Boolean.FALSE);
        int resultAdd = sizeRepository.add(size);
        if (resultAdd > 0) {
            return "Thêm Kính Cỡ Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi";
        }
    }

    public boolean changeStatus(String name) {
        return sizeRepository.updateStatus(name) > 0;
    }

    public Size findByName(String name) {
        return sizeRepository.findByName(name);
    }

    public Size findById(int id) {
        return sizeRepository.findById(id);
    }

}
