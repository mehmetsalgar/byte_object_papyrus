package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byteobject.test.utility.Factories.ProductOrderContainerFactory;
import org.salgar.byte_object.vo.Product;
import org.salgar.byte_object.vo.ProductOrderContainer;
import org.salgar.byte_object.vo.ProductStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class ProductContainerTest {
	@Test(groups = {"normal"})
	public void intialTest() {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);
		productOrderContainer.setProduct(product);

		Assert.assertEquals(productOrderContainer.getQuantity(), quantity);
		Assert.assertEquals(productOrderContainer.getProduct().getId(), id);
		Assert.assertEquals(productOrderContainer.getProduct().getName(), name);
		Assert.assertEquals(productOrderContainer.getProduct().getDescription(), description);
		Assert.assertEquals(productOrderContainer.getProduct().getPrice(), price);
		Assert.assertEquals(productOrderContainer.getProduct().getProductstatus(), productStatus);

		ProductOrderContainer serialized = new ProductOrderContainer(productOrderContainer.toByte());

		Assert.assertEquals(serialized.getQuantity(), quantity);
		Assert.assertEquals(serialized.getProduct().getId(), id);
		Assert.assertEquals(serialized.getProduct().getName(), name);
		Assert.assertEquals(serialized.getProduct().getDescription(), description);
		Assert.assertEquals(serialized.getProduct().getPrice(), price);
		Assert.assertEquals(serialized.getProduct().getProductstatus(), productStatus);
	}

	@Test(groups = {"stream"})
	public void streamTest() throws IOException {
		Long quantity = 45345345L;
		ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);
		productOrderContainer.setProduct(product);

		Assert.assertEquals(productOrderContainer.getQuantity(), quantity);
		Assert.assertEquals(productOrderContainer.getProduct().getId(), id);
		Assert.assertEquals(productOrderContainer.getProduct().getName(), name);
		Assert.assertEquals(productOrderContainer.getProduct().getDescription(), description);
		Assert.assertEquals(productOrderContainer.getProduct().getPrice(), price);
		Assert.assertEquals(productOrderContainer.getProduct().getProductstatus(), productStatus);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		productOrderContainer.toByte(bos);

		ProductOrderContainer serialized = new ProductOrderContainer(bos.toByteArray());

		Assert.assertEquals(serialized.getQuantity(), quantity);
		Assert.assertEquals(serialized.getProduct().getId(), id);
		Assert.assertEquals(serialized.getProduct().getName(), name);
		Assert.assertEquals(serialized.getProduct().getDescription(), description);
		Assert.assertEquals(serialized.getProduct().getPrice(), price);
		Assert.assertEquals(serialized.getProduct().getProductstatus(), productStatus);
	}
}