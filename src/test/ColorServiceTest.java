package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import app.model.Category;
import app.model.Color;
import app.repository.ColorRepository;
import app.service.ColorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ColorServiceTest {
    private ColorService colorService;
    private ColorRepository colorRepository;

    @Before
    public void setUp() {
        colorService = new ColorService();
        colorRepository = new ColorRepository();
    }

    private List<Color> dataColor() {
        List<Color> colors = new ArrayList<>();
        Color color1 = new Color(1, "Pink", true);
        Color color2 = new Color(0, "Pink", true);
        Color color3 = new Color(0, "Pinkgggggggggggggggggggggggggggggggggg" +
                "ggggggggggggggggggggg" +
                "" +
                "", true);
        Color color4 = new Color(1, "Null", true);
        colors.add(color1);
        colors.add(color2);
        colors.add(color3);
        colors.add(color4);
        return colors;
    }

    @Test
    public void testAddColor() {
        List<Color> colors = dataColor();
        for (Color color : colors) {
            if (color.getName().isEmpty()) {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    colorService.addColor(color);
                });
            }
        }
    }


    @Test
    public void testChangeStatus() {
        Color color = new Color(1, "Red", true);
        colorRepository.add(color);

        boolean statusChanged = colorService.changeStatus("Red");
        assertTrue(statusChanged);

        Color updatedColor = colorRepository.findByName("Red");
        assertNotNull(updatedColor);
        assertFalse(updatedColor.getDeleted());

        boolean nonExistentStatusChanged = colorService.changeStatus("NonExistentColor");
        assertFalse(nonExistentStatusChanged);

        try {
            colorService.changeStatus(null);
            fail("IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testFindByName() {
        Color color = new Color(2, "Blue", true);
        colorRepository.add(color);

        Color foundColor = colorService.findByName("Blue");
        assertNotNull(foundColor);
        assertEquals("Blue", foundColor.getName());
        try {
            colorService.findByName(null);
            fail("IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testFindById() {
        Color color = new Color(3, "Tím", true);
        colorRepository.add(color);

        Color foundColor = colorService.findById(3);
        assertNotNull(foundColor);
        assertEquals("Tím", foundColor.getName());
        assertThrows(IllegalArgumentException.class, () -> colorService.findById(-1));
    }

    @After
    public void tearDown() {
        colorService = null;
        colorRepository = null;
    }
}
