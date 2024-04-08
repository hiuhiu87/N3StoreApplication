package test;

import app.model.Product;
import app.request.AddProductRequest;
import app.response.ProductResponse;
import app.service.ProductService;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ProductServiceTest {

    private ProductService service = new ProductService();

    @Test
    public void testAddProductInValid1() {
        AddProductRequest addProductRequest = new AddProductRequest("  ", "category name", "companyName");
        String result = service.addProduct(addProductRequest);
        assertEquals("Tên Sản Phẩm Không Được Để Trống", result);
    }

    @Test
    public void testAddProductInValid2() {
        AddProductRequest addProductRequest = new AddProductRequest("Name 1", "category name", "companyName");
        String result = service.addProduct(addProductRequest);
        assertEquals("Không Tìm Thấy Danh Mục !", result);
    }

    @Test
    public void testAddProductInValid3() {
        AddProductRequest addProductRequest = new AddProductRequest("Name 1", "Giày Thể Thao", "companyName");
        String result = service.addProduct(addProductRequest);
        assertEquals("Không Tìm Thấy Thương Hiệu!", result);
    }

    @Test
    public void testAddProductValid() {
        AddProductRequest addProductRequest = new AddProductRequest("Name 1", "Giày Thể Thao", "Nike");
        String result = service.addProduct(addProductRequest);
        assertEquals("Thêm Thành Công", result);
    }

    @Test
    public void testGetAllProductResponse() {
        List<ProductResponse> list = service.getAllProductResponse();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> list = service.getAllProducts();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetAllProductPaging() {
        List<ProductResponse> list = service.getAllProductPaging(0, 5);
        assertNotNull(list);
        assertTrue(list.size() == 5);
    }

    @Test
    public void testCountProductRecord() {
        int result = service.countProductRecord();
        assertTrue(result > 0);
    }

    @Test
    public void testChangeStatusValid1() {
        assertTrue(service.changeStatus("Nike Runner"));
    }

    @Test
    public void testChangeStatusValid2() {
        assertTrue(service.changeStatus("Nike Runner"));
    }

}
