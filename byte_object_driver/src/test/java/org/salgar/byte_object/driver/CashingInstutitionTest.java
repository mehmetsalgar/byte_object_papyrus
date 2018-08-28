package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byte_object.vo.Address;
import org.salgar.byte_object.vo.CashingInstitution;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

@Test(groups = {"normal"})
public class CashingInstutitionTest {
    @Test(groups = {"normal"})
    public void initialTest() {
        Long id = 23423L;
        String name = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(id_address, street, houseNo, plz, city, country);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id, name, address);

        Assert.assertEquals(cashingInstitution.getId(), id);
        Assert.assertEquals(cashingInstitution.getName(), name);
        Assert.assertEquals(cashingInstitution.getAddress().getId(), id_address);
        Assert.assertEquals(cashingInstitution.getAddress().getStreet(), street);
        Assert.assertEquals(cashingInstitution.getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(cashingInstitution.getAddress().getPLZ(), plz);
        Assert.assertEquals(cashingInstitution.getAddress().getCity(), city);
        Assert.assertEquals(cashingInstitution.getAddress().getCountry(), country);
    }

    @Test(groups = {"normal"})
    public void serializedTest() {
        Long id = 23423L;
        String name = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(id_address, street, houseNo, plz, city, country);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id, name, address);

        CashingInstitution serialized = new CashingInstitution(cashingInstitution.toByte());

        Assert.assertEquals(serialized.getId(), id);
        Assert.assertEquals(serialized.getName(), name);
        Assert.assertEquals(serialized.getAddress().getId(), id_address);
        Assert.assertEquals(serialized.getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getAddress().getCity(), city);
        Assert.assertEquals(serialized.getAddress().getCountry(), country);
    }

    @Test(groups = {"stream"})
    public void streamTest() throws IOException {
        Long id = 23423L;
        String name = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(id_address, street, houseNo, plz, city, country);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id, name, address);

        FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
        cashingInstitution.toByte(bos);

        CashingInstitution serialized = new CashingInstitution(bos.toByteArray());

        Assert.assertEquals(serialized.getId(), id);
        Assert.assertEquals(serialized.getName(), name);
        Assert.assertEquals(serialized.getAddress().getId(), id_address);
        Assert.assertEquals(serialized.getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getAddress().getCity(), city);
        Assert.assertEquals(serialized.getAddress().getCountry(), country);
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferTest() throws IOException {
        Long id = 23423L;
        String name = "VISA";

        Long id_address = Long.valueOf(654564L);
        String street = "Sesame Str";
        String houseNo = "11B";
        Integer plz = 64346;
        String city = "Atlantis";
        String country = "Neptune";

        Address address = Factories.AddressFactory.createAddress(id_address, street, houseNo, plz, city, country);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id, name, address);

        ByteBuffer byteBuffer = cashingInstitution.toByteBuffer();

        CashingInstitution serialized = new CashingInstitution(byteBuffer.array());

        Assert.assertEquals(serialized.getId(), id);
        Assert.assertEquals(serialized.getName(), name);
        Assert.assertEquals(serialized.getAddress().getId(), id_address);
        Assert.assertEquals(serialized.getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getAddress().getCity(), city);
        Assert.assertEquals(serialized.getAddress().getCountry(), country);
    }
}