package org.salgar.byte_object.driver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byteobject.test.utility.Factories.CustomerFactory;
import org.salgar.byte_object.vo.Address;
import org.salgar.byte_object.vo.Customer;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CustomerTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Long id = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		
		Customer customer = CustomerFactory.createCustomer(id, firstname, lastname, birthDate);
		
		Assert.assertEquals(customer.getId(), id);
		Assert.assertEquals(customer.getFirstname(), firstname);
		Assert.assertEquals(customer.getLastname(), lastname);
		Assert.assertEquals(customer.getBirthDate().getTime(), birthDate.getTime());	
	}
	
	@Test(groups = {"normal"})
	public void serializedTest() {
		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
		
		Long id = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(id, firstname, lastname, birthDate);
		customer.setAddress(address);
		
		Customer serialized = new Customer(customer.toByte());
		
		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getFirstname(), firstname);
		Assert.assertEquals(serialized.getLastname(), lastname);
		Assert.assertEquals(serialized.getBirthDate().getTime(), birthDate.getTime());
		
		Assert.assertEquals(serialized.getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getAddress().getCity(), city);
		Assert.assertEquals(serialized.getAddress().getCountry(), country);
	}

	@Test(groups = {"normal"})
	public void streamTest() throws IOException {
		Long addressId = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);

		Long id = 456467548L;
		String firstname = "Dirk";
		String lastname = "Pitt";
		Date birthDate = new Date();
		birthDate.setTime(System.currentTimeMillis() - 1000000);
		Customer customer = CustomerFactory.createCustomer(id, firstname, lastname, birthDate);
		customer.setAddress(address);

		ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
		customer.toByte(bos);

		Customer serialized = new Customer(bos.toByteArray());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getFirstname(), firstname);
		Assert.assertEquals(serialized.getLastname(), lastname);
		Assert.assertEquals(serialized.getBirthDate().getTime(), birthDate.getTime());

		Assert.assertEquals(serialized.getAddress().getId(), addressId);
		Assert.assertEquals(serialized.getAddress().getStreet(), street);
		Assert.assertEquals(serialized.getAddress().getHouseNo(), houseNo);
		Assert.assertEquals(serialized.getAddress().getPLZ(), plz);
		Assert.assertEquals(serialized.getAddress().getCity(), city);
		Assert.assertEquals(serialized.getAddress().getCountry(), country);
	}
}