package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byte_object.vo.ProductStatistics;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byte_object.vo.Product;
import org.salgar.byte_object.vo.ProductStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

@Test
public class ProductTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);
		
		Assert.assertEquals(product.getId(), id);
		Assert.assertEquals(product.getName(), name);
		Assert.assertEquals(product.getDescription(), description);
		Assert.assertEquals(product.getPrice(), price);
		Assert.assertEquals(product.getProductstatus(), productStatus);
		
		Product serialized = new Product(product.toByte());
		
		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getName(), name);
		Assert.assertEquals(serialized.getDescription(), description);
		Assert.assertEquals(serialized.getPrice(), price);
		Assert.assertEquals(serialized.getProductstatus(), productStatus);
	}

	@Test(groups = {"stream"})
	public void streamTest() throws IOException {
		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);

		Assert.assertEquals(product.getId(), id);
		Assert.assertEquals(product.getName(), name);
		Assert.assertEquals(product.getDescription(), description);
		Assert.assertEquals(product.getPrice(), price);
		Assert.assertEquals(product.getProductstatus(), productStatus);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		product.toByte(bos);

		Product serialized = new Product(bos.toByteArray());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getName(), name);
		Assert.assertEquals(serialized.getDescription(), description);
		Assert.assertEquals(serialized.getPrice(), price);
		Assert.assertEquals(serialized.getProductstatus(), productStatus);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferTest() throws IOException {
		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);

		Assert.assertEquals(product.getId(), id);
		Assert.assertEquals(product.getName(), name);
		Assert.assertEquals(product.getDescription(), description);
		Assert.assertEquals(product.getPrice(), price);
		Assert.assertEquals(product.getProductstatus(), productStatus);

		ByteBuffer byteBuffer = ByteBuffer.allocate(product.calculateLength());
		product.toByte(byteBuffer);

		Product serialized = new Product(byteBuffer.array());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getName(), name);
		Assert.assertEquals(serialized.getDescription(), description);
		Assert.assertEquals(serialized.getPrice(), price);
		Assert.assertEquals(serialized.getProductstatus(), productStatus);
	}

	@Test(groups = {"stream"})
	public void streamProductStatisticsTest() throws IOException {
		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		Long weekAvergae = 100L;
		Long monthAverage = 500L;
		Long yearAverage = 250L;
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);

		Assert.assertEquals(product.getId(), id);
		Assert.assertEquals(product.getName(), name);
		Assert.assertEquals(product.getDescription(), description);
		Assert.assertEquals(product.getPrice(), price);
		Assert.assertEquals(product.getProductstatus(), productStatus);

		ProductStatistics producStatistics = Factories.ProducStatisticstFactory.createProductStatisticstFactory(weekAvergae ,monthAverage, yearAverage);
		product.setProductstatistics(producStatistics);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		product.toByte(bos);

		Product serialized = new Product(bos.toByteArray());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getName(), name);
		Assert.assertEquals(serialized.getDescription(), description);
		Assert.assertEquals(serialized.getPrice(), price);
		Assert.assertEquals(serialized.getProductstatus(), productStatus);

		Assert.assertEquals(serialized.getProductstatistics().getWeeklyAverge(), weekAvergae);
		Assert.assertEquals(serialized.getProductstatistics().getMonthlyAverage(), monthAverage);
		Assert.assertEquals(serialized.getProductstatistics().getYearlyAverage(), yearAverage);
	}

	@Test(groups = {"byte_buffer"})
	public void byteBufferProductStatusticsTest() throws IOException {
		Integer id = Integer.valueOf(44644565);
		String name = "Double Sided Sword";
		String description = "Darth Maul's light saber";
		Long price = Long.valueOf(234245356L);
		Long weekAvergae = 100L;
		Long monthAverage = 500L;
		Long yearAverage = 250L;
		ProductStatus productStatus = ProductStatus.IN_STOCK;
		Product product = Factories.ProductFactory.createProduct(id, name, description, price, productStatus);

		Assert.assertEquals(product.getId(), id);
		Assert.assertEquals(product.getName(), name);
		Assert.assertEquals(product.getDescription(), description);
		Assert.assertEquals(product.getPrice(), price);
		Assert.assertEquals(product.getProductstatus(), productStatus);

		ProductStatistics producStatistics = Factories.ProducStatisticstFactory.createProductStatisticstFactory(weekAvergae ,monthAverage, yearAverage);
		product.setProductstatistics(producStatistics);


		ByteBuffer byteBuffer = ByteBuffer.allocate(product.calculateLength());
		product.toByte(byteBuffer);

		Product serialized = new Product(byteBuffer.array());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getName(), name);
		Assert.assertEquals(serialized.getDescription(), description);
		Assert.assertEquals(serialized.getPrice(), price);
		Assert.assertEquals(serialized.getProductstatus(), productStatus);

		Assert.assertEquals(serialized.getProductstatistics().getWeeklyAverge(), Long.valueOf(100));
		Assert.assertEquals(serialized.getProductstatistics().getWeeklyAverge(), Long.valueOf(500));
		Assert.assertEquals(serialized.getProductstatistics().getWeeklyAverge(), Long.valueOf(350));
	}
}