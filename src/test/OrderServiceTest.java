package test;

import app.service.OrderService;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderServiceTest {

    private OrderService service = new OrderService();

    @Test
    public void testCancelOrder() {
        assertTrue(service.cancelOrder("HD11"));
    }

    @Test
    public void testGetAllOders() {
        assertNotNull(service.getAllOders());
        assertTrue(service.getAllOders().size() > 0);
    }

    @Test
    public void testGetPaginatedOders() {
        assertNotNull(service.getPaginatedOders(0, 5));
        assertTrue(service.getPaginatedOders(0, 5).size() == 5);
    }

    @Test
    public void testGetAllOdersByStatus() {
        assertNotNull(service.getAllOdersByStatus(3));
        assertTrue(service.getAllOdersByStatus(3).size() > 0);
    }

    @Test
    public void testCountOder() {
        assertEquals(service.countOder(), 11);
    }

    @Test
    public void testUpdateDeleted() {
        assertEquals(1, service.updateDeleted(true, 1));
    }

    @Test
    public void testGetAllOrderView() {
        assertNotNull(service.getAllOrderView());
        assertTrue(service.getAllOrderView().size() > 0);
    }

    @Test
    public void testFindByCode() {
        assertNotNull(service.findByCode("HD10"));
    }

    @Test
    public void testUpdateTotalMoney() {
        assertTrue(service.updateTotalMoney(10000000.0, 11));
    }

    @Test
    public void testPayOrder() {
        assertTrue(service.payOrder("Tiền Mặt", 100000000.0, 11));
    }

    @Test
    public void testGetDetailOrderResponse() {
        assertNotNull(service.getDetailOrderResponse("HD10"));
    }

    @Test
    public void testAddVoucher() {
        assertTrue(service.addVoucher(13, 100000.0, "HD11"));
    }

}
