/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Company;
import app.repository.CompanyRepository;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CompanyService {

    private final CompanyRepository companyRepository = new CompanyRepository();

    public List<Company> getAllCompany() {
        return companyRepository.getAll();
    }

    public String addCompany(Company company) {
        if (company.getName().trim().isEmpty()) {
            return "Tên Hãng Trống";
        }
        company.setDeleted(Boolean.FALSE);
        int resultAdd = companyRepository.add(company);
        if (resultAdd > 0) {
            return "Thêm Hãng Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi";
        }
    }

}
