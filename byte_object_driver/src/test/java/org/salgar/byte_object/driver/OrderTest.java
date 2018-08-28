package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byteobject.test.utility.Factories.BillFactory;
import org.salgar.byteobject.test.utility.Factories.ContainerFactory;
import org.salgar.byteobject.test.utility.Factories.CustomerFactory;
import org.salgar.byteobject.test.utility.Factories.IdentifierFactory;
import org.salgar.byteobject.test.utility.Factories.OrderFactory;
import org.salgar.byteobject.test.utility.Factories.PaymentReminderFactory;
import org.salgar.byteobject.test.utility.Factories.ProductOrderContainerFactory;
import org.salgar.byte_object.vo.Address;
import org.salgar.byte_object.vo.Bill;
import org.salgar.byte_object.vo.CashingInstitution;
import org.salgar.byte_object.vo.Container;
import org.salgar.byte_object.vo.Customer;
import org.salgar.byte_object.vo.Identifier;
import org.salgar.byte_object.vo.Order;
import org.salgar.byte_object.vo.PaymentReminder;
import org.salgar.byte_object.vo.Product;
import org.salgar.byte_object.vo.ProductOrderContainer;
import org.salgar.byte_object.vo.ProductStatus;
import org.salgar.byte_object.vo.ReminderRank;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Test
public class OrderTest {
	@Test(groups = {"normal"})
    public void orderInitialTest() {

        long id = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(id, now, error, event, message, processed, stage);

        Assert.assertEquals(Long.valueOf(order.getId()), Long.valueOf(id));
        Assert.assertEquals(order.getBuyDate(), now);
        Assert.assertEquals(order.getError(), error);
        Assert.assertEquals(order.getEvent(), event);
        Assert.assertEquals(order.getMessage(), message);
        Assert.assertEquals(order.getProcessed(), processed);
        Assert.assertEquals(order.getStage(), stage);
    }
    
    @Test(groups = {"null_check"})
    public void orderNullTest() {
    	Order order = new Order();
    	
    	Assert.assertNull(order.getId());
    	Assert.assertNull(order.getBuyDate());
    	Assert.assertNull(order.getError());
    	Assert.assertNull(order.getEvent());
    	Assert.assertNull(order.getMessage());
    	Assert.assertNull(order.getProcessed());
    	Assert.assertNull(order.getStage());
    }
    
    @Test(groups = {"null_check"})
    public void orderOnlyMessageNotNullTest() {
    	Order order = new Order();
    	
    	String message = "This is my first Message!";
    	order.setMessage(message);
    	
    	Assert.assertNull(order.getId());
    	Assert.assertNull(order.getBuyDate());
    	Assert.assertNull(order.getError());
    	Assert.assertNull(order.getEvent());
    	Assert.assertNotNull(order.getMessage());
    	Assert.assertEquals(order.getMessage(), message);
    	Assert.assertNull(order.getProcessed());
    	Assert.assertNull(order.getStage());
    }
    
    @Test(groups = {"null_check"})
    public void orderOnlyEventNotNullTest() {
    	Order order = new Order();
    	
    	String event = "This is my first Event!";
    	order.setEvent(event);
    	
    	Assert.assertNull(order.getId());
    	Assert.assertNull(order.getBuyDate());
    	Assert.assertNull(order.getError());
    	Assert.assertNull(order.getMessage());
    	Assert.assertNotNull(order.getEvent());
    	Assert.assertEquals(order.getEvent(), event);
    	Assert.assertNull(order.getProcessed());
    	Assert.assertNull(order.getStage());
    }
    
    
    @Test(groups = {"null_check"})
    public void orderAlternatingNullTest() {
    	Order order = new Order();
    	
    	String event = "This is my first Event!";
    	order.setEvent(event);
    	order.setBuyDate(new Date());
    	
    	Assert.assertNull(order.getId());
    	Assert.assertNotNull(order.getBuyDate());
    	Assert.assertNull(order.getError());
    	Assert.assertNull(order.getMessage());
    	Assert.assertNotNull(order.getEvent());
    	Assert.assertEquals(order.getEvent(), event);
    	Assert.assertNull(order.getProcessed());
    	Assert.assertNull(order.getStage());
    	
    	order.setBuyDate(null);
    	
    	Assert.assertNull(order.getBuyDate());
    }
    
    @Test(groups = {"normal"})
    public void customerTest() {
    	long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		
		Order serialized = new Order(order.toByte());
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
    }
    
    @Test(groups = {"normal"})
    public void customerReferentialIntegrityTest() {
    	long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		
		Order serialized = new Order(order.toByte());
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
		
		Customer referential = serialized.getCustomer();
		
		referential.setLastname("Federer");
		Assert.assertEquals(serialized.getCustomer().getLastname(), "Federer");
    }
    
    @Test(groups = {"normal"})
    public void addressIntegrityTest() {
    	long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		Order serialized = new Order(order.toByte());
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
    }
    
    @Test(groups = {"normal"})
    public void addressReferentialIntegrityTest() {
    	long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		Order serialized = new Order(order.toByte());
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		Address referential = serialized.getCustomer().getAddress();
		referential.setStreet("Amazon Str.");
		
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), "Amazon Str.");
    }
    
    @Test(groups = {"normal"})
    public void productContainerTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		order.setProducts(products);
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		Assert.assertEquals(serialized.getProducts().get(0).getQuantity(), quantity);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getId(), productid);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getName(), productName);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getDescription(), productDescription);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), prodcutPrice);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getProductstatus(), productStatus);
		
		ProductOrderContainer referential =  serialized.getProducts().get(0);
		referential.getProduct().setPrice(999999999L);
		
		
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), Long.valueOf(999999999L));
    }
    
    @Test(groups = {"normal"})
    public void twoProductContainerTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		Assert.assertEquals(serialized.getProducts().get(0).getQuantity(), quantity);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getId(), productid);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getName(), productName);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getDescription(), productDescription);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), prodcutPrice);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getProductstatus(), productStatus);
		
		Assert.assertEquals(serialized.getProducts().get(1).getQuantity(), quantity2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getId(), productid2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getName(), productName2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getDescription(), productDescription2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), prodcutPrice2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getProductstatus(), productStatus2);
		
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		
		
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }
    
    @Test(groups = {"normal"})
    public void billTest() {
    	long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
        
        Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		
		List<Bill> bills = new ArrayList<Bill>();
		bills.add(bill);
		
		order.setBills(bills);	

		Assert.assertEquals(Long.valueOf(order.getId()), Long.valueOf(orderId));
        Assert.assertEquals(order.getBuyDate(), now);
        Assert.assertEquals(order.getError(), error);
        Assert.assertEquals(order.getEvent(), event);
        Assert.assertEquals(order.getMessage(), message);
        Assert.assertEquals(order.getProcessed(), processed);
        Assert.assertEquals(order.getStage(), stage);
		
		Assert.assertEquals(bill.getId(), billId);
		Assert.assertEquals(bill.getAmount(), amount);
		Assert.assertEquals(bill.getLatestPaymentDay(), latestPaymentDay);
    }
    
    @Test(groups = {"normal"})
    public void billComplexTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		
		List<Bill> bills = new ArrayList<Bill>();
		
		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);
		
		order.setBills(bills);
		
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
			
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
		
		Bill bill1 = serialized.getBills().get(0);
		Assert.assertEquals(bill1.getId(), billId);
		Assert.assertEquals(bill1.getAmount(), amount);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay);
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }
    
    @Test(groups = {"normal"})
    public void billComplexSecondBillTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		
		List<Bill> bills = new ArrayList<Bill>();
		
		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);
		
		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		//Calendar calendar2 = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);
		
		order.setBills(bills);
		
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
			
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
		
		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }
    
    @Test(groups = {"normal"})
    public void paymentReminderTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		
		List<Bill> bills = new ArrayList<Bill>();
		
		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);
		
		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		//Calendar calendar2 = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);
		
		order.setBills(bills);
		
		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();
		
		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;
		
		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);
		
		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;
		
		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);
		
		order.setPaymentreminder(reminders);
		
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
			
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
		
		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
		
		
    }
    
    @Test(groups = {"normal"})
    public void cashInstitutionTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		
		List<Bill> bills = new ArrayList<Bill>();
		
		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);
		
		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);
		
		order.setBills(bills);
		
		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();
		
		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;
		
		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);
		
		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;
		
		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);
		
		order.setPaymentreminder(reminders);
		
		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";
		
		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";
		
		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);
		
		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);
		
		cashInstitutions.add(cashingInstitution);
		
		bill2.setCashinginstitution(cashInstitutions);
		
		byte[] serializedBytes = order.toByte();
		Order serialized = new Order(serializedBytes);
			
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
		
		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);
		
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));	
    }
    
    @Test(groups = {"normal"})
    public void orderContainerTest() {
    	Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);
		
		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);
		
    	Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);
		
		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);
		
		long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);
    	
    	Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);
		
		order.setCustomer(customer);
		 	
    	Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);
		
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);
		
		List<Bill> bills = new ArrayList<Bill>();
		
		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);
		
		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);
		
		order.setBills(bills);
		
		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();
		
		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;
		
		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);
		
		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;
		
		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);
		
		order.setPaymentreminder(reminders);
		
		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";
		
		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";
		
		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);
		
		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);
		
		cashInstitutions.add(cashingInstitution);
		
		bill2.setCashinginstitution(cashInstitutions);
		
    	Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";
		
		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);
		
		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);
		
		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);
		
		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);
		
		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);
		
		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);
		
    	Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);
		
		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);
	
		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);
		
		
		byte[] serializedBytes = order.toByte();
		
		//---------------------
		
		Order serialized = new Order(serializedBytes);
		
		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);
        
		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
				
		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
		
		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);
		
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);
		
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);
		
		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    	
		
		//---------------
	
		
		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
		
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);
		
		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
		
		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);
		
		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
    }

	@Test(groups = {"double"})
	public void doubleSerialisationTest() {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";

		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);

		Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);

		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);


		byte[] serializedBytes = order.toByte();

		byte[] serializedBytes1 = order.toByte();

		Assert.assertEquals(serializedBytes1, serializedBytes);

		//---------------------

		Order serialized = new Order(serializedBytes1);

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


		//---------------


		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"stream"})
	public void streamCustomerTest() throws IOException {
		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
	}

	@Test(groups = {"stream"})
	public void streamCustomerReferentialIntegrityTest() throws IOException {
		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Customer referential = serialized.getCustomer();

		referential.setLastname("Federer");
		Assert.assertEquals(serialized.getCustomer().getLastname(), "Federer");
	}

	@Test(groups = {"stream"})
	public void streamProductContainerTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);


		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		order.setProducts(products);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getProducts().get(0).getQuantity(), quantity);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getId(), productid);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getName(), productName);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getDescription(), productDescription);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), prodcutPrice);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getProductstatus(), productStatus);

		ProductOrderContainer referential =  serialized.getProducts().get(0);
		referential.getProduct().setPrice(999999999L);


		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), Long.valueOf(999999999L));
	}

	@Test(groups = {"stream"})
	public void streamTwoProductContainerTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getProducts().get(0).getQuantity(), quantity);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getId(), productid);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getName(), productName);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getDescription(), productDescription);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), prodcutPrice);
		Assert.assertEquals(serialized.getProducts().get(0).getProduct().getProductstatus(), productStatus);

		Assert.assertEquals(serialized.getProducts().get(1).getQuantity(), quantity2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getId(), productid2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getName(), productName2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getDescription(), productDescription2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), prodcutPrice2);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getProductstatus(), productStatus2);


		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);


		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
	}

	@Test(groups = {"stream"})
	public void streamBillComplexTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		order.setBills(bills);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(0);
		Assert.assertEquals(bill1.getId(), billId);
		Assert.assertEquals(bill1.getAmount(), amount);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
	}

	@Test(groups = {"stream"})
	public void streamBillComplexSecondBillTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		//Calendar calendar2 = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
	}

	@Test(groups = {"stream"})
	public void streamPaymentReminderTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		//Calendar calendar2 = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
	}

	@Test(groups = {"stream"})
	public void streamCashInstitutionTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
	}

	@Test(groups = {"stream"})
	public void streamOrderContainerTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";

		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);

		Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);

		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);


		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		Order serialized = new Order(bos.toByteArray());

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


		//---------------


		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"stream"}, enabled = false)
	public void streamDoubleSerialisationTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";

		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);

		Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);

		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByte(bos);

		byte[] serializedBytes = bos.toByteArray();
		Order serialized = new Order(serializedBytes);

		FastByteArrayOutputStream bos1 = new FastByteArrayOutputStream(4096);
		serialized.toByte(bos1);

		byte[] serializedBytes1 = bos1.toByteArray();

		Assert.assertEquals(serializedBytes1, serializedBytes);

		//---------------------

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


		//---------------


		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
	}

    @Test(groups = {"bytebuffer"})
    public void byteBufferCustomerTest() throws IOException {
        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferCustomerReferentialIntegrityTest() throws IOException {
        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Customer referential = serialized.getCustomer();

        referential.setLastname("Federer");
        Assert.assertEquals(serialized.getCustomer().getLastname(), "Federer");
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferProductContainerTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);


        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        order.setProducts(products);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        Assert.assertEquals(serialized.getProducts().get(0).getQuantity(), quantity);
        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getId(), productid);
        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getName(), productName);
        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getDescription(), productDescription);
        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), prodcutPrice);
        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getProductstatus(), productStatus);

        ProductOrderContainer referential =  serialized.getProducts().get(0);
        referential.getProduct().setPrice(999999999L);


        Assert.assertEquals(serialized.getProducts().get(0).getProduct().getPrice(), Long.valueOf(999999999L));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferBillComplexTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        Long quantity2 = 636846L;
        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Integer productid2 = Integer.valueOf(123123124);
        String productName2 = "Tennis Racquest";
        String productDescription2 = "Stan the Animal";
        Long prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Long billId = 1456356563L;
        Long amount = 4534579786L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date latestPaymentDay = calendar.getTime();
        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        order.setBills(bills);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(0);
        Assert.assertEquals(bill1.getId(), billId);
        Assert.assertEquals(bill1.getAmount(), amount);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay);

        ProductOrderContainer referential =  serialized.getProducts().get(1);
        referential.getProduct().setPrice(35345345L);
        Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferBillComplexSecondBillTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        Long quantity2 = 636846L;
        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Integer productid2 = Integer.valueOf(123123124);
        String productName2 = "Tennis Racquest";
        String productDescription2 = "Stan the Animal";
        Long prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Long billId = 1456356563L;
        Long amount = 4534579786L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date latestPaymentDay = calendar.getTime();
        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        Long billId2 = 435345768L;
        Long amount2 = 87645653L;
        //Calendar calendar2 = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date latestPaymentDay2 = calendar.getTime();
        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(1);
        Assert.assertEquals(bill1.getId(), billId2);
        Assert.assertEquals(bill1.getAmount(), amount2);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

        ProductOrderContainer referential =  serialized.getProducts().get(1);
        referential.getProduct().setPrice(35345345L);
        Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferPaymentReminderTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        Long quantity2 = 636846L;
        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Integer productid2 = Integer.valueOf(123123124);
        String productName2 = "Tennis Racquest";
        String productDescription2 = "Stan the Animal";
        Long prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Long billId = 1456356563L;
        Long amount = 4534579786L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date latestPaymentDay = calendar.getTime();
        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        Long billId2 = 435345768L;
        Long amount2 = 87645653L;
        //Calendar calendar2 = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date latestPaymentDay2 = calendar.getTime();
        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

        Long id_reminder = 4744573L;
        ReminderRank rank = ReminderRank.INITIAL;
        Long interest = 5L;

        PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        Long id_reminder2 = 4744573L;
        ReminderRank rank2 = ReminderRank.INITIAL;
        Long interest2 = 5L;

        PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(1);
        Assert.assertEquals(bill1.getId(), billId2);
        Assert.assertEquals(bill1.getAmount(), amount2);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

        Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

        Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

        ProductOrderContainer referential =  serialized.getProducts().get(1);
        referential.getProduct().setPrice(35345345L);
        Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferCashInstitutionTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        Long quantity2 = 636846L;
        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Integer productid2 = Integer.valueOf(123123124);
        String productName2 = "Tennis Racquest";
        String productDescription2 = "Stan the Animal";
        Long prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Long billId = 1456356563L;
        Long amount = 4534579786L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date latestPaymentDay = calendar.getTime();
        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        Long billId2 = 435345768L;
        Long amount2 = 87645653L;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 30);
        Date latestPaymentDay2 = calendar.getTime();
        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

        Long id_reminder = 4744573L;
        ReminderRank rank = ReminderRank.INITIAL;
        Long interest = 5L;

        PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        Long id_reminder2 = 4744573L;
        ReminderRank rank2 = ReminderRank.INITIAL;
        Long interest2 = 5L;

        PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>();
        Long id_cashInstitutuion = 23423L;
        String nameCashInstitutuion = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street1 = "Sesame Str";
        String houseNo1 = "11B";
        Integer plz1 = 64346;
        String city1 = "Atlantis";
        String country1 = "Neptune";

        Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(1);
        Assert.assertEquals(bill1.getId(), billId2);
        Assert.assertEquals(bill1.getAmount(), amount2);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

        Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

        Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

        Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

        ProductOrderContainer referential =  serialized.getProducts().get(1);
        referential.getProduct().setPrice(35345345L);
        Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferOrderContainerTest() throws IOException {
        Long quantity = 45345345L;
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Integer productid = Integer.valueOf(44644565);
        String productName = "Double Sided Sword";
        String productDescription = "Darth Maul's light saber";
        Long prodcutPrice = Long.valueOf(234245356L);
        ProductStatus productStatus = ProductStatus.IN_STOCK;
        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        Long quantity2 = 636846L;
        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Integer productid2 = Integer.valueOf(123123124);
        String productName2 = "Tennis Racquest";
        String productDescription2 = "Stan the Animal";
        Long prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        long orderId = 34535435L;
        Date now = new Date(System.currentTimeMillis());
        String error = "This is my first Error!";
        String event = "This is my first Event!";
        String message = "This is my first Message!";
        Boolean processed = Boolean.TRUE;
        Integer stage = 62;

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Long customerId = 456467548L;
        String firstname = "Dirk";
        String lastname = "Pitt";
        Date birthDate = new Date();
        birthDate.setTime(System.currentTimeMillis() - 1000000);
        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Long addressId = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Long billId = 1456356563L;
        Long amount = 4534579786L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date latestPaymentDay = calendar.getTime();
        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        Long billId2 = 435345768L;
        Long amount2 = 87645653L;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 30);
        Date latestPaymentDay2 = calendar.getTime();
        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

        Long id_reminder = 4744573L;
        ReminderRank rank = ReminderRank.INITIAL;
        Long interest = 5L;

        PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        Long id_reminder2 = 4744573L;
        ReminderRank rank2 = ReminderRank.INITIAL;
        Long interest2 = 5L;

        PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>();
        Long id_cashInstitutuion = 23423L;
        String nameCashInstitutuion = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street1 = "Sesame Str";
        String houseNo1 = "11B";
        Integer plz1 = 64346;
        String city1 = "Atlantis";
        String country1 = "Neptune";

        Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Integer identifierId = Integer.valueOf(6546543);
        String someText = "We are developing interesting things here!";
        Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

        Long c_addressId = Long.valueOf(654564L);
        String c_street = "Sesame Str";
        String c_houseNo = "11B";
        Integer c_plz = 64346;
        String c_city = "Atlantis";
        String c_country = "Neptune";

        Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>();
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Integer identifier2Id = Integer.valueOf(5445643);
        String someOtherText = "Big data here we are coming";
        String key2= "All hail big data";
        Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

        Integer conntainerId = Integer.valueOf(92473864);
        Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
        String key = "Halli Hallo";
        identifierMap.put(key, identifier);
        identifierMap.put(key2, identifier2);

        Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

        Long c_quantity = 45345345L;
        ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Integer c_productid = Integer.valueOf(44644565);
        String c_productName = "Double Sided Sword";
        String c_productDescription = "Darth Maul's light saber";
        Long c_prodcutPrice = Long.valueOf(234245356L);
        ProductStatus c_productStatus = ProductStatus.IN_STOCK;
        Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        Long c_quantity2 = 636846L;
        ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Integer c_productid2 = Integer.valueOf(123123124);
        String c_productName2 = "Tennis Racquest";
        String c_productDescription2 = "Stan the Animal";
        Long c_prodcutPrice2 = Long.valueOf(974545L);
        ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
        Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
        c_productOrderContainer2.setProduct(c_product2);

        List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
        c_products.add(c_productOrderContainer);
        c_products.add(c_productOrderContainer2);
        container.setProductordercontainer(c_products);
        order.setContainer(container);


        ByteBuffer byteBuffer = order.toByteBuffer();

        Order serialized = new Order(byteBuffer.array());

        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(1);
        Assert.assertEquals(bill1.getId(), billId2);
        Assert.assertEquals(bill1.getAmount(), amount2);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

        Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

        Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

        Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

        ProductOrderContainer referential =  serialized.getProducts().get(1);
        referential.getProduct().setPrice(35345345L);
        Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


        //---------------


        Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
        Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

        Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
        Identifier serializedIdentifier = serializedIdentifierMap.get(key);
        Assert.assertNotNull(serializedIdentifier);
        Assert.assertEquals(serializedIdentifier.getId(), identifierId);
        Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

        Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
        Assert.assertNotNull(serializedIdentifier2);
        Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
        Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

        ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
        Product c_product1_1 = c_productOrderContainer1_1.getProduct();
        Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
        Assert.assertEquals(c_product1_1.getId(), c_productid);
        Assert.assertEquals(c_product1_1.getName(), c_productName);
        Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
        Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
        Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

        ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
        Product c_product2_2 = c_productOrderContainer2_2.getProduct();
        Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
        Assert.assertEquals(c_product2_2.getId(), productid2);
        Assert.assertEquals(c_product2_2.getName(), productName2);
        Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
        Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
    }

	@Test(groups = {"bytebuffer_version"})
	public void byteBufferOrderContainerVersionTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";

		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);

		Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);

		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);


		ByteBuffer byteBuffer = order.toByteBuffer();

		Order serialized = new Order(byteBuffer.array(), true);

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


		//---------------


		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"stream_version"})
	public void streamOrderContainerVersionTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer productid = Integer.valueOf(44644565);
		String productName = "Double Sided Sword";
		String productDescription = "Darth Maul's light saber";
		Long prodcutPrice = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
		productOrderContainer.setProduct(product);

		Long quantity2 = 636846L;
		ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

		Integer productid2 = Integer.valueOf(123123124);
		String productName2 = "Tennis Racquest";
		String productDescription2 = "Stan the Animal";
		Long prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
		Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
		productOrderContainer2.setProduct(product2);

		long orderId = 34535435L;
		Date now = new Date(System.currentTimeMillis());
		String error = "This is my first Error!";
		String event = "This is my first Event!";
		String message = "This is my first Message!";
		Boolean processed = Boolean.TRUE;
		Integer stage = 62;

		Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

		Long customerId = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

		order.setCustomer(customer);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		customer.setAddress(address);

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		order.setProducts(products);

		List<Bill> bills = new ArrayList<Bill>();

		Long billId = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
		bills.add(bill);

		Long billId2 = 435345768L;
		Long amount2 = 87645653L;
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH, 30);
		Date latestPaymentDay2 = calendar.getTime();
		Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
		bills.add(bill2);

		order.setBills(bills);

		List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

		Long id_reminder = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
		reminders.add(paymentReminder);

		Long id_reminder2 = 4744573L;
		ReminderRank rank2 = ReminderRank.INITIAL;
		Long interest2 = 5L;

		PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
		reminders.add(paymentReminder2);

		order.setPaymentreminder(reminders);

		List<CashingInstitution> cashInstitutions = new ArrayList<>();
		Long id_cashInstitutuion = 23423L;
		String nameCashInstitutuion = "VISA";

		Long id_address = Long.valueOf(654564L);
		String street1 = "Sesame Str";
		String houseNo1 = "11B";
		Integer plz1 = 64346;
		String city1 = "Atlantis";
		String country1 = "Neptune";

		Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

		CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

		cashInstitutions.add(cashingInstitution);

		bill2.setCashinginstitution(cashInstitutions);

		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long c_addressId = Long.valueOf(654564L);
		String c_street = "Sesame Str";
		String c_houseNo = "11B";
		Integer c_plz = 64346;
		String c_city = "Atlantis";
		String c_country = "Neptune";

		Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
		List<Address> c_addresses = new ArrayList<Address>();
		c_addresses.add(c_address);
		identifier.setAddress(c_addresses);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		Long c_quantity = 45345345L;
		ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

		Integer c_productid = Integer.valueOf(44644565);
		String c_productName = "Double Sided Sword";
		String c_productDescription = "Darth Maul's light saber";
		Long c_prodcutPrice = Long.valueOf(234245356L);
		ProductStatus c_productStatus = ProductStatus.IN_STOCK;
		Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
		c_productOrderContainer.setProduct(c_product);

		Long c_quantity2 = 636846L;
		ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

		Integer c_productid2 = Integer.valueOf(123123124);
		String c_productName2 = "Tennis Racquest";
		String c_productDescription2 = "Stan the Animal";
		Long c_prodcutPrice2 = Long.valueOf(974545L);
		ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;
		Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
		c_productOrderContainer2.setProduct(c_product2);

		List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
		c_products.add(c_productOrderContainer);
		c_products.add(c_productOrderContainer2);
		container.setProductordercontainer(c_products);
		order.setContainer(container);


		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		order.toByteWithVersion(bos);

		Order serialized = new Order(bos.toByteArray(), true);

		Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
		Assert.assertEquals(serialized.getBuyDate(), now);
		Assert.assertEquals(serialized.getError(), error);
		Assert.assertEquals(serialized.getEvent(), event);
		Assert.assertEquals(serialized.getMessage(), message);
		Assert.assertEquals(serialized.getProcessed(), processed);
		Assert.assertEquals(serialized.getStage(), stage);

		Assert.assertEquals(serialized.getCustomer().getId(), customerId);
		Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
		Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
		Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
		Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

		Bill bill1 = serialized.getBills().get(1);
		Assert.assertEquals(bill1.getId(), billId2);
		Assert.assertEquals(bill1.getAmount(), amount2);
		Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

		Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
		Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

		Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
		Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

		Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
		Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

		ProductOrderContainer referential =  serialized.getProducts().get(1);
		referential.getProduct().setPrice(35345345L);
		Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


		//---------------


		Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
		Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
		Product c_product1_1 = c_productOrderContainer1_1.getProduct();
		Assert.assertEquals(c_productOrderContainer1_1.getQuantity(),c_quantity);
		Assert.assertEquals(c_product1_1.getId(), c_productid);
		Assert.assertEquals(c_product1_1.getName(), c_productName);
		Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
		Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
		Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

		ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
		Product c_product2_2 = c_productOrderContainer2_2.getProduct();
		Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
		Assert.assertEquals(c_product2_2.getId(), productid2);
		Assert.assertEquals(c_product2_2.getName(), productName2);
		Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
		Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
	}
}