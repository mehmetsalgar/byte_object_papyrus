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

package org.salgar.byte_object.hazelcast.java_serialisation.performance;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.vo.*;

import java.util.*;

import static org.salgar.byteobject.test.utility.Factories.*;

public class MyBenchmark {
    //private static final Logger log = Logger.getLogger(MyBenchmark.class);

    private static final Long quantity = 45345345L;
    private static final Integer productid = Integer.valueOf(446453453);
    private static final String productName = "Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword; Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword Double Sided Sword";
    private static final String productDescription = "Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber Darth Maul's light saber";
    private static final Long prodcutPrice = Long.valueOf(234245356L);
    private static final ProductStatus productStatus = ProductStatus.IN_STOCK;
    private static final Long quantity2 = 636846L;
    private static final Integer productid2 = Integer.valueOf(123123124);
    private static final String productName2 = "Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest Tennis Racquest";
    private static final String productDescription2 = "Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal Stan the Animal";
    private static final Long prodcutPrice2 = Long.valueOf(974545L);
    private static final ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
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
    private static final ReminderRank rank = ReminderRank.INITIAL;
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
    private static final ReminderRank rank2 = ReminderRank.INITIAL;
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

    private static final String key = "Halli Hallo";
    private static final String key2 = "All hail big data";

    private static final Long c_quantity = 45345345L;

    private static final Integer c_productid = Integer.valueOf(44644565);
    private static final String c_productName = "Double Sided Sword";
    private static final String c_productDescription = "Darth Maul's light saber";
    private static final Long c_prodcutPrice = Long.valueOf(234245356L);
    private static final ProductStatus c_productStatus = ProductStatus.IN_STOCK;

    private static final Long c_quantity2 = 636846L;

    private static final Integer c_productid2 = Integer.valueOf(123123124);
    private static final String c_productName2 = "Tennis Racquest";
    private static final String c_productDescription2 = "Stan the Animal";
    private static final Long c_prodcutPrice2 = Long.valueOf(974545L);
    private static final ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;

    private static final Integer conntainerId = Integer.valueOf(92473864);




    @State(Scope.Benchmark)
    public static class MySetup {
        public HazelcastInstance client;

        @Setup
        public void initialize() {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getNetworkConfig().addAddress("localhost:5701");

            client = HazelcastClient.newHazelcastClient(clientConfig);
        }

        @TearDown
        public void destroy() {
            client.shutdown();
        }
    }

    @Benchmark()
    @BenchmarkMode(Mode.AverageTime)
    public void testMethod(MySetup setup) {
        IMap map = setup.client.getMap("orders");

        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        List<Order> orders = new ArrayList<>(30000);
        for (int i = 0; i < 30000; i++) {
            orders.add(createOrder(i, map));
        }
    }

    Order createOrder(int orderIdIncr, IMap map) {
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Product product = ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Product product2 = ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        Order order = OrderFactory.createOrder(orderIdIncr, now, error, event, message, processed, stage);

        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Address address = AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        calendar2.add(Calendar.DAY_OF_MONTH, 30);

        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

        PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>();

        Address address1 = AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

        Address c_address = AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>();
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);
        Address c_address1 = AddressFactory.createAddress(c_addressId1, c_street1, c_houseNo1, c_plz1, c_city1, c_country1);
        List<Address> c_addresses1 = new ArrayList<Address>();
        c_addresses1.add(c_address1);
        identifier2.setAddress(c_addresses1);

        Set<Identifier> identifierMap = new HashSet<>();

        identifierMap.add(identifier);
        identifierMap.add(identifier2);

        Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

        ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Product c_product = ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Product c_product2 = ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
        c_productOrderContainer2.setProduct(c_product2);

        List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
        c_products.add(c_productOrderContainer);
        c_products.add(c_productOrderContainer2);
        container.setProductordercontainer(c_products);
        order.setContainer(container);

        map.put(order.getId(), order);

        Order serializedOrder = (Order) map.get(order.getId());

        serializedOrder.getContainer().getProductordercontainer().get(1).getProduct().getPrice();
        if (!prodcutPrice2.equals(serializedOrder.getContainer()
                .getProductordercontainer().get(1).getProduct().getPrice())) {
            throw new IllegalStateException("Price is not same");
        }
        Iterator<Identifier> identiferMap_deser = serializedOrder.getContainer().getIdentifiermap().iterator();
        Identifier identifier_deser = null;
        while (identiferMap_deser.hasNext()) {
            identifier_deser = identiferMap_deser.next();
        }
        if (identifier2Id.equals(identifier_deser.getId()) && !c_plz1.equals(identifier_deser.getAddress().get(0).getPLZ())) {
            throw new IllegalStateException("PLZ is not same");
        }

        return serializedOrder;
    }
}