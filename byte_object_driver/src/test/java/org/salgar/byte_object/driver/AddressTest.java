package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byte_object.vo.Address;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

@Test
public class AddressTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Long id = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(id, street, houseNo, plz, city, country);
		
		Assert.assertEquals(address.getId(), id);
		Assert.assertEquals(address.getStreet(), street);
		Assert.assertEquals(address.getHouseNo(), houseNo);
		Assert.assertEquals(address.getPLZ(), plz);
		Assert.assertEquals(address.getCity(), city);
		Assert.assertEquals(address.getCountry(), country);
	}
	
	@Test(groups = {"normal"})
	public void serializedTest() {
		Long id = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";
		
		Address address = Factories.AddressFactory.createAddress(id, street, houseNo, plz, city, country);
		
		Assert.assertEquals(address.getId(), id);
		Assert.assertEquals(address.getStreet(), street);
		Assert.assertEquals(address.getHouseNo(), houseNo);
		Assert.assertEquals(address.getPLZ(), plz);
		Assert.assertEquals(address.getCity(), city);
		Assert.assertEquals(address.getCountry(), country);
		
		Address addressSerialized = new Address(address.toByte());
		
		Assert.assertEquals(addressSerialized.getId(), id);
		Assert.assertEquals(addressSerialized.getStreet(), street);
		Assert.assertEquals(addressSerialized.getHouseNo(), houseNo);
		Assert.assertEquals(addressSerialized.getPLZ(), plz);
		Assert.assertEquals(addressSerialized.getCity(), city);
		Assert.assertEquals(addressSerialized.getCountry(), country);
	}

	@Test(groups = {"stream"})
	public void streamTest() throws IOException {
		Long id = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(id, street, houseNo, plz, city, country);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);

		address.toByte(bos);

		byte[] serialised = bos.toByteArray();

		Address serialisedAddress = new Address(serialised);

		Assert.assertEquals(serialisedAddress.getId(), id);
		Assert.assertEquals(serialisedAddress.getStreet(), street);
		Assert.assertEquals(serialisedAddress.getHouseNo(), houseNo);
		Assert.assertEquals(serialisedAddress.getPLZ(), plz);
		Assert.assertEquals(serialisedAddress.getCity(), city);
		Assert.assertEquals(serialisedAddress.getCountry(), country);
	}

	@Test(groups = {"bytebuffer"})
	public void byteBufferTest() throws IOException {
		Long id = Long.valueOf(654564L);
		String street = "Sesame Str";
		String houseNo = "11B";
		Integer plz = 64346;
		String city = "Atlantis";
		String country = "Neptune";

		Address address = Factories.AddressFactory.createAddress(id, street, houseNo, plz, city, country);

		ByteBuffer byteBuffer = address.toByteBuffer();

		byte[] serialised = byteBuffer.array();

		Address serialisedAddress = new Address(serialised);

		Assert.assertEquals(serialisedAddress.getId(), id);
		Assert.assertEquals(serialisedAddress.getStreet(), street);
		Assert.assertEquals(serialisedAddress.getHouseNo(), houseNo);
		Assert.assertEquals(serialisedAddress.getPLZ(), plz);
		Assert.assertEquals(serialisedAddress.getCity(), city);
		Assert.assertEquals(serialisedAddress.getCountry(), country);
	}
}