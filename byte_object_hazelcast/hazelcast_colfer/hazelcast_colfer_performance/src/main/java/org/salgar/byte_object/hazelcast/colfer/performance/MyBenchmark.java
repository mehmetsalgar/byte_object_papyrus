/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.salgar.byte_object.hazelcast.colfer.performance;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.colfer.vo.byte_object.*;
import org.salgar.byte_object.hazelcast.colfer.serialisation.ColferSerialisation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyBenchmark {
    private static final Logger log = Logger.getLogger(MyBenchmark.class);

    private static final Long quantity = 45345345L;
    private static final Integer productid = Integer.valueOf(446453453);
    private static final String productName = "Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword; Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword";
    private static final String productDescription = "Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber";
    private static final Long prodcutPrice = Long.valueOf(234245356L);
    //private static final ProductStatus productStatus = ProductStatus.IN_STOCK;
    private static final Long quantity2 = 636846L;
    private static final Integer productid2 = Integer.valueOf(123123124);
    private static final String productName2 = "Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest";
    private static final String productDescription2 = "Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal";
    private static final Long prodcutPrice2 = Long.valueOf(974545L);
    //private static final ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
    private static final long orderId = 34535435L;
    private static final Date now = new Date(System.currentTimeMillis());
    private static final String error = "This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error! This is my first Error!";
    private static final String event = "This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event! This is my first Event!";
    private static final String message = "This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message! This is my first Message!";
    private static final Boolean processed = Boolean.TRUE;
    private static final Integer stage = 62;
    private static final Long addressId = Long.valueOf(654564L);
    private static final String street = "Sesame Str";
    private static final String houseNo = "11B";
    private static final Integer plz = 64346;
    private static final String city = "Atlantis";
    private static final String country = "Neptune";
    private static final Long customerId = 456467548L;
    private static final String firstname = "Dirk";
    private static final String lastname = "Pitt";
    private static final Date birthDate = new Date(System.currentTimeMillis() - 1000000);
    private static final Long billId = 1456356563L;
    private static final Long amount = 4534579786L;
    private static final Calendar calendar = Calendar.getInstance();
    private static final Date latestPaymentDay = calendar.getTime();
    private static final Long billId2 = 435345768L;
    private static final Long amount2 = 87645653L;
    private static final Calendar calendar2 = Calendar.getInstance();
    private static final Date latestPaymentDay2 = calendar.getTime();
    private static final Long id_reminder = 4744573L;
    //private static final ReminderRank rank = ReminderRank.INITIAL;
    private static final Long interest = 5L;
    private static final Long id_cashInstitutuion = 23423L;
    private static final String nameCashInstitutuion = "VISA";

    private static final Long id_address = Long.valueOf(654564L);
    private static final String street1 = "Sesame Str";
    private static final String houseNo1 = "11B";
    private static final Integer plz1 = 64346;
    private static final String city1 = "Atlantis";
    private static final String country1 = "Neptune";

    private static final Long id_reminder2 = 4744573L;
    //private static final ReminderRank rank2 = ReminderRank.INITIAL;
    private static final Long interest2 = 5L;

    private static final Integer identifierId = Integer.valueOf(6546543);
    private static final String someText = "We are developing interesting things here!";

    private static final Long c_addressId = Long.valueOf(654564L);
    private static final String c_street = "Sesame Str";
    private static final String c_houseNo = "11B";
    private static final Integer c_plz = 64346;
    private static final String c_city = "Atlantis";
    private static final String c_country = "Neptune";

    private static final Integer identifier2Id = Integer.valueOf(5445643);
    private static final String someOtherText = "Big data here we are coming";

    private static final Long c_addressId1 = Long.valueOf(2187234);
    private static final String c_street1 = "One way to Hell Str";
    private static final String c_houseNo1 = "999";
    private static final Integer c_plz1 = 54331;
    private static final String c_city1 = "Skovia";
    private static final String c_country1 = "Harlamd Worl";

    //private static final String key = "Halli Hallo";
    //private static final String key2 = "All hail big data";

    private static final Long c_quantity = 45345345L;

    private static final Integer c_productid = Integer.valueOf(44644565);
    private static final String c_productName = "Double Sided Sword";
    private static final String c_productDescription = "Darth Maul's light saber";
    private static final Long c_prodcutPrice = Long.valueOf(234245356L);
    //private static final ProductStatus c_productStatus = ProductStatus.IN_STOCK;

    private static final Long c_quantity2 = 636846L;

    private static final Integer c_productid2 = Integer.valueOf(123123124);
    private static final String c_productName2 = "Tennis Racquest";
    private static final String c_productDescription2 = "Stan the Animal";
    private static final Long c_prodcutPrice2 = Long.valueOf(974545L);
    //private static final ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;

    private static final Integer conntainerId = Integer.valueOf(92473864);

    private static final int NUMBER_OF_ITERATIONS = 30000;

    @State(Scope.Benchmark)
    public static class MySetup {
        private HazelcastInstance client;
        public List<Order> orders;

        @Setup
        public void initialize() {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getNetworkConfig().addAddress("localhost:5701");
            SerializerConfig serializerConfigOrder = new SerializerConfig()
                    .setImplementation(new ColferSerialisation())
                    .setTypeClass(Order.class);
            clientConfig.getSerializationConfig().addSerializerConfig(serializerConfigOrder);

            client = HazelcastClient.newHazelcastClient(clientConfig);

            this.orders = new ArrayList<>(NUMBER_OF_ITERATIONS);
            for (long i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                orders.add(createOrder(i));
            }
        }

        @TearDown
        public void destroy() {
            client.shutdown();
        }
    }

    @Benchmark()
    @BenchmarkMode(Mode.AverageTime)
    public void testMethod(MySetup setup) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.

        IMap map = setup.client.getMap("orders");

        List<Order> orders = setup.orders;
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            serialize(orders.get(i), map);
        }
    }

    private void serialize(Order order, IMap map) {
        map.put(order.getId(), order);

        Order serializedOrder = (Order) map.get(order.getId());

        //for(int j = 0;  j < 1000; j++) {
        if (!prodcutPrice2.equals(serializedOrder.getContainer()
                .getProductOrderContainer()[1].getProduct().getPrice())) {
            throw new IllegalStateException("Price is not same");
        }

        if(!amount2.equals(order.getBills()[1].getAmount())) {
            throw new IllegalStateException("Amount is not same");
        }

        Identifier identifier_deser = null;
        for (int n = serializedOrder.getContainer().getIdentifiermap().length, i = 0; i < n; i++) {
            identifier_deser = serializedOrder.getContainer().getIdentifiermap()[i];

            if (identifier2Id.equals(identifier_deser.getId()) && !c_plz1.toString().equals(identifier_deser.getAddresses()[0].getPLZ())) {
                throw new IllegalStateException("PLZ is not same");
            }
        }
        //}
    }

    private static Order createOrder(long orderIdIncr) {
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        ProductOrderContainer productOrderContainer = new ProductOrderContainer();
        productOrderContainer.setQuantity(quantity);

        Product product = new Product();
        product.setId(productid);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(prodcutPrice);
        product.setProductStatus("IN_STOCK");

        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = new ProductOrderContainer();
        productOrderContainer2.setQuantity(quantity2);

        Product product2 = new Product();
        product2.setId(productid2);
        product2.setName(productName2);
        product2.setDescription(productDescription2);
        product2.setPrice(prodcutPrice2);
        product2.setProductStatus("OUT");
        productOrderContainer2.setProduct(product2);

        Order order = new Order();
        order.setId(orderIdIncr);
        order.setBuyDate(now.toInstant());
        order.setError(error);
        order.setEvent(event);
        order.setMessage(message);
        order.setProcessaed(processed);
        order.setStage(stage);

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setBirthDate(birthDate.toInstant());
        order.setCustomer(customer);

        Address address = new Address();
        address.setId(addressId);
        address.setStreet(street);
        address.setHouseNo(houseNo);
        address.setPLZ(plz.toString());
        address.setCountry(country);
        customer.setAddress(address);

        ProductOrderContainer[] products = new ProductOrderContainer[2];
        products[0] = productOrderContainer;
        products[1] = productOrderContainer2;
        order.setProducts(products);

        Bill[] bills = new Bill[2];

        Bill bill = new Bill();
        bill.setId(billId);
        bill.setAmount(amount);
        bill.setLatestPayementDay(latestPaymentDay.toInstant());
        bills[0] = bill;

        calendar2.add(Calendar.DAY_OF_MONTH, 30);

        Bill bill2 = new Bill();
        bill2.setId(billId2);
        bill2.setAmount(amount2);
        bill2.setLatestPayementDay(latestPaymentDay2.toInstant());
        bills[1] = bill2;

        order.setBills(bills);

        PaymentReminder[] reminders = new PaymentReminder[2];

        PaymentReminder paymentReminder = new PaymentReminder();
        paymentReminder.setId(id_reminder);
        paymentReminder.setRank("FIRST");
        paymentReminder.setInterest(interest);
        reminders[0] = paymentReminder;

        PaymentReminder paymentReminder2 = new PaymentReminder();
        paymentReminder2.setId(id_reminder2);
        paymentReminder2.setRank("SECOND");
        paymentReminder2.setInterest(interest2);
        reminders[1] = paymentReminder2;

        order.setPaymentreminders(reminders);

        CashingInstitution[] cashInstitutions = new CashingInstitution[1];

        Address address1 = new Address();
        address.setId(id_address);
        address.setStreet(street1);
        address.setHouseNo(houseNo1);
        address.setPLZ(plz1.toString());
        address.setCity(city1);
        address.setCountry(country1);

        CashingInstitution cashingInstitution = new CashingInstitution();
        cashingInstitution.setId(id_cashInstitutuion);
        cashingInstitution.setName(nameCashInstitutuion);
        cashingInstitution.setAddress(address1);

        cashInstitutions[0] = cashingInstitution;

        bill2.setCashingInstitution(cashInstitutions);

        Identifier identifier = new Identifier();
        identifier.setId(identifierId);
        identifier.setSomeText(someText);

        Address c_address = new Address();
        c_address.setId(c_addressId);
        c_address.setStreet(c_street);
        c_address.setHouseNo(c_houseNo);
        c_address.setPLZ(c_plz.toString());
        c_address.setCity(c_city);
        c_address.setCountry(c_country);

        Address[] c_addresses = new Address[1];
        c_addresses[0] = c_address;
        identifier.setAddresses(c_addresses);

        Identifier identifier2 = new Identifier();
        identifier2.setId(identifier2Id);
        identifier2.setSomeText(someOtherText);

        Address c_address1 = new Address();
        c_address1.setId(c_addressId1);
        c_address1.setStreet(c_street1);
        c_address1.setHouseNo(c_houseNo1);
        c_address1.setPLZ(c_plz1.toString());
        c_address1.setCity(c_city1);
        c_address1.setCountry(c_country1);

        Address[] c_addresses1 = new Address[1];
        c_addresses1[0] = c_address1;
        identifier2.setAddresses(c_addresses1);

        Identifier[] identifierMap = new Identifier[2];

        identifierMap[0] = identifier;
        identifierMap[1] = identifier2;

        Container container = new Container();
        container.setId(conntainerId);
        container.setIdentifiermap(identifierMap);

        ProductOrderContainer c_productOrderContainer = new ProductOrderContainer();
        c_productOrderContainer.setQuantity(c_quantity);

        Product c_product = new Product();
        c_product.setId(c_productid);
        c_product.setName(c_productName);
        c_product.setDescription(c_productDescription);
        c_product.setPrice(c_prodcutPrice);
        c_product.setProductStatus("OUT_OF_STOCK");
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = new ProductOrderContainer();
        c_productOrderContainer2.setQuantity(c_quantity2);

        Product c_product2 = new Product();
        c_product2.setId(c_productid2);
        c_product2.setName(c_productName2);
        c_product2.setDescription(c_productDescription2);
        c_product2.setPrice(c_prodcutPrice2);
        c_product2.setProductStatus("IN_STOCK");

        c_productOrderContainer2.setProduct(c_product2);

        ProductOrderContainer[] c_products = new ProductOrderContainer[2];
        c_products[0] = c_productOrderContainer;
        c_products[1] = c_productOrderContainer2;
        container.setProductOrderContainer(c_products);
        order.setContainer(container);

        return order;
    }
}