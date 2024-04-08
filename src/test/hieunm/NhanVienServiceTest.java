package test.hieunm;

import app.model.NhanVien;
import app.service.NhanVienService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NhanVienServiceTest {

    public NhanVienService nhanVienService;

    @Before
    public void init() {
        nhanVienService = new NhanVienService();
    }

    @Test
    public void testAddNhanVienSuccess() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen("Test NhanVien");
        nhanVien.setEmail("test@email.com");
        nhanVien.setSdt("1234567890");
        nhanVien.setDiaChi("Test Address");
        nhanVien.setRoLe(false);
        int result = nhanVienService.addNhanVien(nhanVien);
        assertEquals(1, result);
    }

    @Test
    public void testAddNhanVienFail1() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen(null);
        nhanVien.setEmail("test1@email.com");
        nhanVien.setSdt("1234567890");
        nhanVien.setDiaChi("Test Address");
        nhanVien.setRoLe(false);
        int result = nhanVienService.addNhanVien(nhanVien);
        assertEquals(0, result);
    }

    @Test
    public void testAddNhanVienFail2() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen("Test NhanVien");
        nhanVien.setEmail("");
        nhanVien.setSdt("1234567890");
        nhanVien.setDiaChi("Test Address");
        nhanVien.setRoLe(false);
        int result = nhanVienService.addNhanVien(nhanVien);
        assertEquals(0, result);
    }

    @Test
    public void testAddNhanVienFail3() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen("Test NhanVien");
        nhanVien.setEmail("test2@email.com");
        nhanVien.setSdt("");
        nhanVien.setDiaChi("Test Address");
        nhanVien.setRoLe(false);
        int result = nhanVienService.addNhanVien(nhanVien);
        assertEquals(0, result);
    }

    @Test
    public void testAddNhanVienFail4() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen("Test NhanVien");
        nhanVien.setEmail("test3@email.com");
        nhanVien.setSdt("1234567890");
        nhanVien.setDiaChi("");
        nhanVien.setRoLe(false);
        int result = nhanVienService.addNhanVien(nhanVien);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNhanVienSuccess() {
        NhanVien nhanVien = nhanVienService.findById(1);
        assertNotNull(nhanVien);
        nhanVien.setTen("Updated NhanVien");
        nhanVien.setEmail("updated@email.com");
        nhanVien.setSdt("0987654321");
        nhanVien.setDiaChi("Updated Address");
        nhanVien.setRoLe(true);
        int result = nhanVienService.updateNhanVien(nhanVien, 1);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateNhanVienFail1() {
        NhanVien nhanVien = nhanVienService.findById(1);
        nhanVien.setTen(null);
        int result = nhanVienService.updateNhanVien(nhanVien, 1);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNhanVienFail2() {
        NhanVien nhanVien = nhanVienService.findById(1);
        nhanVien.setEmail("");
        int result = nhanVienService.updateNhanVien(nhanVien, 1);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNhanVienFail3() {
        NhanVien nhanVien = nhanVienService.findById(1);
        nhanVien.setSdt("");
        int result = nhanVienService.updateNhanVien(nhanVien, 1);
        assertEquals(0, result);
    }

    @Test
    public void testUpdateNhanVienFail4() {
        NhanVien nhanVien = nhanVienService.findById(1);
        nhanVien.setDiaChi("");
        int result = nhanVienService.updateNhanVien(nhanVien, 1);
        assertEquals(0, result);
    }

}
