package test;

import app.service.OrderDetailService;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderDetailServiceTest {

    private OrderDetailService service = new OrderDetailService();

    @Test
    public void testGetAllOderDetails() {
        assertNotNull(service.getAllOderDetails("HD11"));
    }

    @Test
    public void testGetPaginatedOders() {
        assertNotNull(service.getPaginatedOders(0, 3, "HD10"));
        assertTrue(service.getPaginatedOders(0, 3, "HD10").size() == 2);
    }

    @Test
    public void testCountOderDetail() {
        assertTrue(service.countOderDetail("HD10") == 2);
    }

    @Test
    public void testDeleteFromCartCancelOrder() {
        assertFalse(service.deleteFromCartCancelOrder(1));
    }

}
