package test;


import app.model.Category;
import app.repository.CategoryRepository;
import app.service.CategoryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    private CategoryService categoryService;

    private CategoryRepository categoryRepository;
    @Before
    public void setUp() {
        categoryService = new CategoryService();
        categoryRepository = new CategoryRepository();

    }

    private List<Category> prepareSampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Cate01", true));
        categories.add(new Category(0, "Cate01", true));
        categories.add(new Category((int) 1999999999999999999L, "Cate01", true)); // Using a large ID for testing
        categories.add(new Category(0, "Cate01fsdfghdksfhgdskfkdsfkdshfjkdshfjksdhfkjdshfkdjshfjkdshfdjkshfdksjfhdksjfhdsjkfhdksjfhdksjfhdjksfhkdjshfkhdskjfhkshkfdsjhfkjdshf", true)); // Testing long name
        categories.add(new Category(1, "Null", true));
        categories.add(new Category(1, "", true));
        return categories;
    }

    @Test
    public void testAddCategory() {
        List<Category> categories = prepareSampleCategories();
        for (Category category : categories) {
            try {
                categoryService.addCategory(category);
                if (category.getName().trim().isEmpty()) {
                    fail("Expected IllegalArgumentException for empty name");
                } else if (category.getName().length() > 255) {
                    fail("Expected IllegalArgumentException for long name");
                }
            } catch (IllegalArgumentException e) {
                if (category.getName().trim().isEmpty()) {
                    assertEquals("Tên Danh Mục Không Được Để Chóng", e.getMessage());
                } else if (category.getName().length() > 255) {
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




    private List<String> dataTest () {
        List<String> dataNameStatus = new ArrayList<>();
        dataNameStatus.add("test100");
        dataNameStatus.add("Category Test");
        dataNameStatus.add("Giày Long");
        return dataNameStatus;
    }

    @Test
    public void testChangeStatus() {
        List<String> dataNameStatus = dataTest();
        for (String name : dataNameStatus) {
            int result = categoryRepository.updateStatus(name);
            boolean expectedResult = result > 0;
            assertEquals(expectedResult, categoryService.changeStatus(name));
        }
    }
    @Test
    public void testGetAll() {
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.add(new Category(1, "Category 1", true));
        categoryRepository.add(new Category(2, "Category 2", true));
        categoryRepository.add(new Category(3, "Category 3", true));

        List<Category> allCategories = categoryService.getAll();
        assertNotNull(allCategories);
        assertFalse(allCategories.isEmpty());
    }


    @After
    public void tearDown() {
        categoryService = null;
        categoryRepository = null;
    }








}
