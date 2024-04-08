package test;

import app.model.Company;
import app.repository.CompanyRepository;
import app.service.CompanyService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompanyServiceTest {
    private CompanyService companyService;
    private CompanyRepository companyRepository;

    @Before
    public void setUp() {
        companyService = new CompanyService();
        companyRepository = new CompanyRepository();
    }

    private List<Company> dataCompanyList() {
        List<Company> listCompany = new ArrayList<>();
        Company company1 = new Company(1, "ABC", true);
        listCompany.add(company1);
        Company company2 = new Company(1, "ABC", true);
        listCompany.add(company2);
        Company company3 = new Company(1, "Awwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", true);
        listCompany.add(company3);
        Company company4 = new Company(1, "Abc", true);
        listCompany.add(company4);
        Company company5 = new Company(22, "Null", true);
        listCompany.add(company5);
        return listCompany;
    }

    @Test
    public void testAddCategory() {
        List<Company> categories = dataCompanyList();
        for (Company company : categories) {
            try {
                companyService.addCompany(company);
                if (company.getName().trim().isEmpty()) {
                    fail("Expected IllegalArgumentException for empty name");
                } else if (company.getName().length() > 255) {
                    fail("Expected IllegalArgumentException for long name");
                }
            } catch (IllegalArgumentException e) {
                if (company.getName().trim().isEmpty()) {
                    assertEquals("Tên Danh Mục Không Được Để Chóng", e.getMessage());
                } else if (company.getName().length() > 255) {
                    assertEquals("Tên Danh Mục Quá Dài", e.getMessage());
                } else {
                    fail("Unexpected exception thrown: " + e.getMessage());
                }
            } catch (RuntimeException e) {
                assertEquals("Đã Xảy Ra Lỗi", e.getMessage());
            } catch (Exception e) {
                fail("Unexpected exception thrown: " + e.getMessage());
            }
        }
    }


    private List<String> dataTest() {
        List<String> dataNameStatus = new ArrayList<>();
        dataNameStatus.add("abc");
        dataNameStatus.add("ABC");
        dataNameStatus.add("ABC");
        return dataNameStatus;
    }

    @Test
    public void testChangeStatus() {
        List<String> dataNameStatus = dataTest();
        for (String name : dataNameStatus) {
            if (name == null) {
                assertThrows(IllegalArgumentException.class, () -> companyService.changeStatus(name));
            } else {
                int result = companyRepository.updateStatus(name);
                boolean expectedResult = result > 0;
                assertEquals(expectedResult, companyService.changeStatus(name));
            }
        }
    }


    @Before
    public void tearDown() {
        companyService = null;
        companyRepository = null;
    }


}
