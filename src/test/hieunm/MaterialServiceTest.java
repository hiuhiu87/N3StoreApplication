package test.hieunm;

import app.model.Material;
import app.service.MaterialService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MaterialServiceTest {

    public MaterialService materialService;

    @Before
    public void init() {
        materialService = new MaterialService();
    }

    @Test
    public void testAddMaterialSuccess() {
        Material material = new Material();
        material.setName("Test Material");
        String result = materialService.addMaterial(material);
        assertEquals("Thêm Chất Liệu Thành Công", result);
    }

    @Test
    public void testAddMaterialFail() {
        Material material = new Material();
        material.setName("");
        String result = materialService.addMaterial(material);
        assertNull(result);
    }

    @Test
    public void testUpdateMaterialSuccess() {
        Material materialInDb = materialService.findById(1);
        assertNotNull(materialInDb);
        materialInDb.setName("Test Material Updated");
        int result = materialService.updateMaterial(materialInDb);
        assertEquals(1, result);
    }

    @Test
    public void testUpdateMaterialFail() {
        Material materialInDb = materialService.findById(1);
        materialInDb.setName("");
        int result = materialService.updateMaterial(materialInDb);
        assertEquals(0, result);
    }
}