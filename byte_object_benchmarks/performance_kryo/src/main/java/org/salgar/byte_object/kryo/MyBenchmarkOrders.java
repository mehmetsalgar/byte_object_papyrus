package org.salgar.byte_object.kryo;

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


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byte_object.vo.*;

import static org.salgar.byteobject.test.utility.Factories.*;

import java.io.*;
import java.util.*;

public class MyBenchmarkOrders extends AbstractBenchmark {
    private static final Logger log = Logger.getLogger(MyBenchmarkOrders.class);

    private static final int NUMBER_OF_ITERATIONS = 30000;

    @State(Scope.Benchmark)
    public static class MySetup {
        public Kryo kryo;
        public List<Order> orders;

        @Setup
        public void initialize() {
            this.kryo = new Kryo();
            //kryo.setReferences(false);
            //kryo.setRegistrationRequired(true);
            //kryo.register(Order.class, new DeflateSerializer(kryo.getDefaultSerializer(Order.class)));

            orders = new ArrayList<>(NUMBER_OF_ITERATIONS);
            for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                orders.add(createOrder(i));
            }
        }
    }

    @Benchmark()
    @BenchmarkMode(Mode.AverageTime)
    public void testMethod(MySetup setup) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            Order order = setup.orders.get(i);
        }

        serialize(setup.orders, setup.kryo);
    }

    private int serialize(List<Order> orders, Kryo kryo) {
        List<Order> serializedOrders = null;

        try {
            FastByteArrayOutputStream bos = new FastByteArrayOutputStream(2048);

            Output output = new Output(bos);
//            Output output = new OutputChunked();
//            output.setOutputStream(bos);
            //kryo.writeObject(output, order);

            kryo.writeObject(output, orders);
            output.close();

            byte[] serialized = bos.toByteArray();
            //log.info("Serialized size: " + serialized.length);

            ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            Input input = new Input(bis);

            serializedOrders = kryo.readObject(input, ArrayList.class);

            Container container = serializedOrders.get(75).getContainer();
            container.getProductordercontainer().get(1).getProduct().getPrice();
            if(!prodcutPrice2.equals(container
                    .getProductordercontainer().get(1).getProduct().getPrice())) {
                throw new IllegalStateException("Price is not same");
            }
            Iterator<Identifier> identiferMap_deser = container.getIdentifiermap().iterator();
            Identifier identifier_deser = null;
            while (identiferMap_deser.hasNext()) {
                identifier_deser = identiferMap_deser.next();
            }
            if(identifier2Id.equals(identifier_deser.getId()) && !c_plz1.equals(identifier_deser.getAddress().get(0).getPLZ())) {
                throw new IllegalStateException("PLZ is not same");
            }
            input.close();

            return serialized.length;

        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
        return 0;
    }

    private static  Order createOrder(int orderIncr) {
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Product product = ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Product product2 = ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        Order order = OrderFactory.createOrder(orderIncr, now, error, event, message, processed, stage);

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

        return order;
    }
}
