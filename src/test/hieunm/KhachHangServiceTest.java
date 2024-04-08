package test.hieunm;

import app.model.KhachHang;
import app.service.KhachHangService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class KhachHangServiceTest {

    public KhachHangService khachHangService;

    @Before
    public void init() {
        khachHangService = new KhachHangService();
    }

    @Test
    public void testAddKhachHangSuccess() {
        KhachHang khachHang = new KhachHang();
        khachHang.setFullName("Test Name");
        khachHang.setEmail("test@gmail.com");
        khachHang.setPhoneNumber("1234567890");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0001");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(1, result);
    }

    @Test
    public void testAddKhachHangFail1() {
        KhachHang khachHang = new KhachHang();
        String name = String.join("", Collections.nCopies(255, "a"));
        khachHang.setFullName(name);
        khachHang.setEmail("test1@gmail.com");
        khachHang.setPhoneNumber("1234567890");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0002");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(0, result);
    }

    @Test
    public void testAddKhachHangFail2() {
        KhachHang khachHang = new KhachHang();
        khachHang.setFullName(null);
        khachHang.setEmail("test2@gmail.com");
        khachHang.setPhoneNumber("1234527890");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0003");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(0, result);
    }

    @Test
    public void testAddKhachHangFail3() {
        KhachHang khachHang = new KhachHang();
        khachHang.setFullName("Test Name");
        khachHang.setEmail("");
        khachHang.setPhoneNumber("1234217890");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0004");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(0, result);
    }

    @Test
    public void testAddKhachHangFail4() {
        KhachHang khachHang = new KhachHang();
        khachHang.setFullName("Test Name");
        khachHang.setEmail("test3@gmail.com");
        khachHang.setPhoneNumber("");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0005");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(0, result);
    }

    @Test
    public void testAddKhachHangFail5() {
        KhachHang khachHang = new KhachHang();
        khachHang.setFullName("Test Name");
        khachHang.setEmail("test9@gmail.com");
        khachHang.setPhoneNumber("");
        khachHang.setAddress("Test Address");
        khachHang.setBirthDate(new Date(System.currentTimeMillis()));
        khachHang.setCode("KH0006");
        int result = khachHangService.addKhachHang(khachHang);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateKhachHangSuccess() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName("Test Name Updated");
        khachHangInDb.setEmail("mailSuccess@gmail.com");
        khachHangInDb.setPhoneNumber("0987654321");
        khachHangInDb.setAddress("Test Address Updated");
        khachHangInDb.setBirthDate(new Date(System.currentTimeMillis()));
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(1, result);
    }

    @Test
    public void testUpdateKhachHangFail1() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName(null);
        khachHangInDb.setEmail("");
        khachHangInDb.setPhoneNumber("0987654321");
        khachHangInDb.setAddress("Test Address Updated");
        khachHangInDb.setBirthDate(new Date(System.currentTimeMillis()));
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(0, result);
    }

    @Test
    public void testUpdateKhachHangFail2() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName("Test Name Updated");
        khachHangInDb.setEmail(null);
        khachHangInDb.setPhoneNumber("0987654321");
        khachHangInDb.setAddress("Test Address Updated");
        khachHangInDb.setBirthDate(new Date(System.currentTimeMillis()));
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(0, result);
    }

    @Test
    public void testUpdateKhachHangFail3() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName("Test Name Updated");
        khachHangInDb.setEmail("mailOk@gmail.com");
        khachHangInDb.setPhoneNumber(null);
        khachHangInDb.setAddress("Test Address Updated");
        khachHangInDb.setBirthDate(new Date(System.currentTimeMillis()));
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(0, result);
    }

    @Test
    public void testUpdateKhachHangFail4() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName("Test Name Updated");
        khachHangInDb.setEmail("okmail@gmail.com");
        khachHangInDb.setPhoneNumber("0987654321");
        khachHangInDb.setAddress(null);
        khachHangInDb.setBirthDate(new Date(System.currentTimeMillis()));
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(0, result);
    }

    @Test
    public void testUpdateKhachHangFail5() {
        KhachHang khachHangInDb = khachHangService.findById(1);
        khachHangInDb.setFullName("Test Name Updated");
        khachHangInDb.setEmail("mailtest@gmail.com");
        khachHangInDb.setPhoneNumber("0987654321");
        khachHangInDb.setAddress("Test Address Updated");
        khachHangInDb.setBirthDate(null);
        int result = khachHangService.updateSV(khachHangInDb, khachHangInDb.getCode());
        assertEquals(0, result);
    }

}
