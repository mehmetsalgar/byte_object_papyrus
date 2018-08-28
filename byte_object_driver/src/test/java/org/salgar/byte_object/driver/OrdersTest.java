package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories.OrderFactory;
import org.salgar.byte_object.vo.Order;
import org.salgar.byte_object.vo.Orders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Boolean;
//import java.lang.Byte;
//import java.lang.Character;
//import java.lang.Double;
//import java.lang.Float;
//import java.lang.Integer;
//import java.lang.Long;
//import java.lang.Object;
//import java.lang.Short;

@Test
public class OrdersTest {
    @Test(groups = {"normal"})
    public void initialTest() {
        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Orders orders = new Orders();

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);
        List<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order);
        orders.setOrder(ordersList);
        
        Orders serialized = new Orders(orders.toByte());

        Order tmp = serialized.getOrder().get(0);

        Assert.assertEquals(Long.valueOf(tmp.getId()), Long.valueOf(id));
        Assert.assertEquals(tmp.getBuyDate().getTime(), now.getTime());
        Assert.assertEquals(tmp.getError(), error);
        Assert.assertEquals(tmp.getEvent(), event);
        Assert.assertEquals(tmp.getMessage(), message);
        Assert.assertEquals(tmp.getProcessed(), processed);
        Assert.assertEquals(tmp.getStage(), stage);
    }

    @Test(groups = {"normal"})
    public void initialTestTwoOrder() {
        Orders orders = new Orders();

        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);
        List<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order);
        orders.setOrder(ordersList);
        
        Orders serialized = new Orders(orders.toByte());

        Order tmp = serialized.getOrder().get(0);

        Assert.assertEquals(Long.valueOf(tmp.getId()), Long.valueOf(id));
        Assert.assertEquals(tmp.getBuyDate().getTime(), now.getTime());
        Assert.assertEquals(tmp.getError(), error);
        Assert.assertEquals(tmp.getEvent(), event);
        Assert.assertEquals(tmp.getMessage(), message);
        Assert.assertEquals(tmp.getProcessed(), processed);
        Assert.assertEquals(tmp.getStage(), stage);

        long id1 = 34535435L;
        Date now1 = new Date(System.currentTimeMillis());
        String error1 = "This is my second Error!";
        String event1 = "This is my second Event!";
        String message1 = "This is my second Message!";
        Boolean processed1 = Boolean.FALSE;
        Integer stage1 = 43;

        Order order1 = OrderFactory.createOrder(id1, now1, error1, event1, message1, processed1, stage1);
        serialized.getOrder().add(order1);
        
        Orders serialized1 = new Orders(serialized.toByte());

        Order tmp1 = serialized1.getOrder().get(1);

        Assert.assertEquals(Long.valueOf(tmp1.getId()), Long.valueOf(id1));
        Assert.assertEquals(tmp1.getBuyDate().getTime(), now1.getTime());
        Assert.assertEquals(tmp1.getError(), error1);
        Assert.assertEquals(tmp1.getEvent(), event1);
        Assert.assertEquals(tmp1.getMessage(), message1);
        Assert.assertEquals(tmp1.getProcessed(), processed1);
        Assert.assertEquals(tmp1.getStage(), stage1);
    }

    @Test(groups = {"normal"})
    public void initialTestTwoOrderWithId() {
        Orders orders = new Orders();
        long orders_id = 685785675L;
        orders.setId(orders_id);

        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);
        List<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order);
        orders.setOrder(ordersList);

        Orders serialized = new Orders(orders.toByte());

        Order tmp = serialized.getOrder().get(0);

        Assert.assertEquals(Long.valueOf(tmp.getId()), Long.valueOf(id));
        Assert.assertEquals(tmp.getBuyDate().getTime(), now.getTime());
        Assert.assertEquals(tmp.getError(), error);
        Assert.assertEquals(tmp.getEvent(), event);
        Assert.assertEquals(tmp.getMessage(), message);
        Assert.assertEquals(tmp.getProcessed(), processed);
        Assert.assertEquals(tmp.getStage(), stage);

        long id1 = 34535435L;
        long time = System.currentTimeMillis();
        Date now1 = new Date(time);
        String error1 = "This is my second Error!";
        String event1 = "This is my second Event!";
        String message1 = "This is my second Message!";
        Boolean processed1 = Boolean.FALSE;
        Integer stage1 = 37;

        Order order1 = OrderFactory.createOrder(id1, now1, error1, event1, message1, processed1, stage1);
        serialized.getOrder().add(order1);
        
        Orders serialized1 = new Orders(serialized.toByte());

        Order tmp1 = serialized1.getOrder().get(1);

        Assert.assertEquals(Long.valueOf(tmp1.getId()), Long.valueOf(id1));
        Assert.assertEquals(tmp1.getBuyDate().getTime(), time);
        Assert.assertEquals(tmp1.getError(), error1);
        Assert.assertEquals(tmp1.getEvent(), event1);
        Assert.assertEquals(tmp1.getMessage(), message1);
        Assert.assertEquals(tmp1.getProcessed(), processed1);
        Assert.assertEquals(tmp1.getStage(), stage1);

        Assert.assertEquals(serialized1.getId(), Long.valueOf(orders_id));
    }
    
    @Test(groups = {"null_check"})
    public void nullChecks() {
    	Orders orders = new Orders();
    	
    	Assert.assertNull(orders.getId());
    	Assert.assertNull(orders.getOrder());
    }
    
    @Test(groups = {"null_check"})
    public void nullChecksId() {
    	Orders orders = new Orders();
    	
    	long id = 456456456456L;
    	orders.setId(id);
    	
    	Assert.assertEquals(orders.getId(), Long.valueOf(id));
    	Assert.assertNull(orders.getOrder());
    }
    
    @Test(groups = {"null_check"})
    public void nullChecksOrders() {
    	Orders orders = new Orders();
    	
    	orders.setOrder(new ArrayList<Order>());
    	
    	Assert.assertNull(orders.getId());
    	Assert.assertNotNull(orders.getOrder());
    }
    
    @Test(groups = {"null_check"})
    public void nullChecksOrdersReset() {
    	Orders orders = new Orders();
    	
    	orders.setOrder(new ArrayList<Order>());
    	
    	Assert.assertNull(orders.getId());
    	Assert.assertNotNull(orders.getOrder());
    	
    	orders.setOrder(null);
    	
    	Assert.assertNull(orders.getId());
    	Assert.assertNull(orders.getOrder());
    }

    @Test(groups = {"stream"})
    public void streamTestTwoOrderWithId() throws IOException {
        Orders orders = new Orders();
        long orders_id = 685785675L;
        orders.setId(orders_id);

        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);
        List<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order);
        orders.setOrder(ordersList);

        FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
        orders.toByte(bos);

        Orders serialized = new Orders(bos.toByteArray());

        Order tmp = serialized.getOrder().get(0);

        Assert.assertEquals(Long.valueOf(tmp.getId()), Long.valueOf(id));
        Assert.assertEquals(tmp.getBuyDate().getTime(), now.getTime());
        Assert.assertEquals(tmp.getError(), error);
        Assert.assertEquals(tmp.getEvent(), event);
        Assert.assertEquals(tmp.getMessage(), message);
        Assert.assertEquals(tmp.getProcessed(), processed);
        Assert.assertEquals(tmp.getStage(), stage);

        long id1 = 34535435L;
        long time = System.currentTimeMillis();
        Date now1 = new Date(time);
        String error1 = "This is my second Error!";
        String event1 = "This is my second Event!";
        String message1 = "This is my second Message!";
        Boolean processed1 = Boolean.FALSE;
        Integer stage1 = 37;

        Order order1 = OrderFactory.createOrder(id1, now1, error1, event1, message1, processed1, stage1);
        serialized.getOrder().add(order1);

        Orders serialized1 = new Orders(serialized.toByte());

        Order tmp1 = serialized1.getOrder().get(1);

        Assert.assertEquals(Long.valueOf(tmp1.getId()), Long.valueOf(id1));
        Assert.assertEquals(tmp1.getBuyDate().getTime(), time);
        Assert.assertEquals(tmp1.getError(), error1);
        Assert.assertEquals(tmp1.getEvent(), event1);
        Assert.assertEquals(tmp1.getMessage(), message1);
        Assert.assertEquals(tmp1.getProcessed(), processed1);
        Assert.assertEquals(tmp1.getStage(), stage1);

        Assert.assertEquals(serialized1.getId(), Long.valueOf(orders_id));
    }

    @Test(groups = {"bytebuffer"})
    public void bytebufferTestTwoOrderWithId() throws IOException {
        Orders orders = new Orders();
        long orders_id = 685785675L;
        orders.setId(orders_id);

        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);
        List<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order);
        orders.setOrder(ordersList);

        ByteBuffer byteBuffer = orders.toByteBuffer();

        Orders serialized = new Orders(byteBuffer.array());

        Order tmp = serialized.getOrder().get(0);

        Assert.assertEquals(Long.valueOf(tmp.getId()), Long.valueOf(id));
        Assert.assertEquals(tmp.getBuyDate().getTime(), now.getTime());
        Assert.assertEquals(tmp.getError(), error);
        Assert.assertEquals(tmp.getEvent(), event);
        Assert.assertEquals(tmp.getMessage(), message);
        Assert.assertEquals(tmp.getProcessed(), processed);
        Assert.assertEquals(tmp.getStage(), stage);

        long id1 = 34535435L;
        long time = System.currentTimeMillis();
        Date now1 = new Date(time);
        String error1 = "This is my second Error!";
        String event1 = "This is my second Event!";
        String message1 = "This is my second Message!";
        Boolean processed1 = Boolean.FALSE;
        Integer stage1 = 37;

        Order order1 = OrderFactory.createOrder(id1, now1, error1, event1, message1, processed1, stage1);
        serialized.getOrder().add(order1);

        Orders serialized1 = new Orders(serialized.toByte());

        Order tmp1 = serialized1.getOrder().get(1);

        Assert.assertEquals(Long.valueOf(tmp1.getId()), Long.valueOf(id1));
        Assert.assertEquals(tmp1.getBuyDate().getTime(), time);
        Assert.assertEquals(tmp1.getError(), error1);
        Assert.assertEquals(tmp1.getEvent(), event1);
        Assert.assertEquals(tmp1.getMessage(), message1);
        Assert.assertEquals(tmp1.getProcessed(), processed1);
        Assert.assertEquals(tmp1.getStage(), stage1);

        Assert.assertEquals(serialized1.getId(), Long.valueOf(orders_id));
    }
}