package org.salgar.byte_object.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byteobject.test.utility.Factories.ContainerFactory;
import org.salgar.byteobject.test.utility.Factories.IdentifierFactory;
import org.salgar.byteobject.test.utility.Factories.ProductOrderContainerFactory;
import org.salgar.byte_object.vo.Address;
import org.salgar.byte_object.vo.Container;
import org.salgar.byte_object.vo.Identifier;
import org.salgar.byte_object.vo.Product;
import org.salgar.byte_object.vo.ProductOrderContainer;
import org.salgar.byte_object.vo.ProductStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ContainerTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		
		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);
		
		byte[] byteContainer = container.toByte();
		
		Container serialized = new Container(byteContainer);
		
		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
	}
	
	@Test(groups = {"normal"})
	public void secondKeyTest() {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		String key = "Halli Hallo";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2= "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);
		
		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);
		
		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);
		
		byte[] byteContainer = container.toByte();
		
		Container serialized = new Container(byteContainer);
		
		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
		
		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
	}
	
	@Test(groups = {"normal"})
	public void withProductContainerTest() {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);
		
		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);
		
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
	
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);
		
		byte[] byteContainer = container.toByte();
		
		Container serialized = new Container(byteContainer);
		
		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}
	
	@Test(groups = {"normal"})
	public void withProductContainerWithSecondKeyTest() {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
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
	
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);
		
		byte[] byteContainer = container.toByte();
		
		Container serialized = new Container(byteContainer);
		
		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
		
		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}
	
	@Test(groups = {"normal"})
	public void withProductContainerWithSecondKeyWithAddressTest() {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);
		
		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		identifier.setAddress(addresses);
		
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
	
		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);
		
		byte[] byteContainer = container.toByte();
		
		Container serialized = new Container(byteContainer);
		
		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());
		
		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
		
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), country);
		
		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
		
		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);
		
		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"stream"})
	public void streamTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		container.toByte(bos);

		byte[] byteContainer = bos.toByteArray();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
	}

	@Test(groups = {"stream"})
	public void streamSecondKeyTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		String key = "Halli Hallo";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2 = "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		container.toByte(bos);

		byte[] byteContainer = bos.toByteArray();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
	}

	@Test(groups = {"stream"})
	public void streamWithProductContainerWithSecondKeyTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

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

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		container.toByte(bos);

		byte[] byteContainer = bos.toByteArray();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"stream"})
	public void streamWithProductContainerWithSecondKeyWithAddressTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		identifier.setAddress(addresses);

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

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		container.toByte(bos);

		Container serialized = new Container(bos.toByteArray());

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		String key = "Halli Hallo";
		identifierMap.put(key, identifier);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		ByteBuffer byteBuffer =  container.toByteBuffer();

		byte[] byteContainer = byteBuffer.array();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferSecondKeyTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		String key = "Halli Hallo";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Integer identifier2Id = Integer.valueOf(5445643);
		String someOtherText = "Big data here we are coming";
		String key2 = "All hail big data";
		Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

		Integer conntainerId = Integer.valueOf(92473864);
		Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();
		identifierMap.put(key, identifier);
		identifierMap.put(key2, identifier2);

		Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

		ByteBuffer byteBuffer =  container.toByteBuffer();

		byte[] byteContainer = byteBuffer.array();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferWithProductContainerWithSecondKeyTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

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

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);

		ByteBuffer byteBuffer = container.toByteBuffer();

		byte[] byteContainer = byteBuffer.array();

		Container serialized = new Container(byteContainer);

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferithProductContainerWithSecondKeyWithAddressTest() throws IOException {
		Integer identifierId = Integer.valueOf(6546543);
		String someText = "We are developing interesting things here!";
		Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		identifier.setAddress(addresses);

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

		List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
		products.add(productOrderContainer);
		products.add(productOrderContainer2);
		container.setProductordercontainer(products);


		ByteBuffer byteBuffer = container.toByteBuffer();

		Container serialized = new Container(byteBuffer.array());

		Assert.assertEquals(serialized.getId(), conntainerId);
		Assert.assertNotNull(serialized.getIdentifiermap());

		Map<String, Identifier> serializedIdentifierMap = serialized.getIdentifiermap();
		Identifier serializedIdentifier = serializedIdentifierMap.get(key);
		Assert.assertNotNull(serializedIdentifier);
		Assert.assertEquals(serializedIdentifier.getId(), identifierId);
		Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), addressId);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), street);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), houseNo);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), plz);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), city);
		Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), country);

		Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
		Assert.assertNotNull(serializedIdentifier2);
		Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
		Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

		ProductOrderContainer productOrderContainer1_1 = serialized.getProductordercontainer().get(0);
		Product product1_1 = productOrderContainer1_1.getProduct();
		Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
		Assert.assertEquals(product1_1.getId(), productid);
		Assert.assertEquals(product1_1.getName(), productName);
		Assert.assertEquals(product1_1.getDescription(), productDescription);
		Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
		Assert.assertEquals(product1_1.getProductstatus(), productStatus);

		ProductOrderContainer productOrderContainer2_2 = serialized.getProductordercontainer().get(1);
		Product product2_2 = productOrderContainer2_2.getProduct();
		Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
		Assert.assertEquals(product2_2.getId(), productid2);
		Assert.assertEquals(product2_2.getName(), productName2);
		Assert.assertEquals(product2_2.getDescription(), productDescription2);
		Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
		Assert.assertEquals(product2_2.getProductstatus(), productStatus2);
	}
}