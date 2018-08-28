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

package org.salgar.byte_object.colfer;

import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.colfer.vo.byte_object.*;
import org.salgar.byte_object.io.FastByteArrayOutputStream;

import java.util.*;

public class MyBenchmark extends AbstractBenchmark {
    private static final Logger log = Logger.getLogger(MyBenchmark.class);

    private static final int NUMBER_OF_ITERATIONS = 30000;

    @State(Scope.Benchmark)
    public static class MySetup {
        public List<Order> orders;

        @Setup
        public void initialize() {
            orders = new ArrayList<>(NUMBER_OF_ITERATIONS);
            for (long i = 0; i < NUMBER_OF_ITERATIONS; i++) {
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
            int length = serialize(setup.orders.get(i));
            //log.info("length: " + length);
        }
    }

    private int serialize(Order order) {
        Order serializedOrder = null;

        try {

            FastByteArrayOutputStream bos = new FastByteArrayOutputStream(1024);
            byte[] buff = new byte[1024];
            order.marshal(bos, buff);

            byte[] serialized = bos.toByteArray();
            //log.info("Serialized size: " + serialized.length);

//            Order.Unmarshaller unmarshaller = new Order.Unmarshaller(new ByteArrayInputStream(serialized), new byte[1]);
//            serializedOrder = unmarshaller.next();

            serializedOrder = new Order();
            serializedOrder.unmarshal(serialized, 0);

            if (!prodcutPrice2.equals(serializedOrder.getContainer()
                    .getProductOrderContainer()[1].getProduct().getPrice())) {
                throw new IllegalStateException("Price is not same");
            }

            if (!amount2.equals(serializedOrder.getBills()[1].getAmount())) {
                throw new IllegalStateException("Amount is not same");
            }

            Identifier identifier_deser = null;
            for (int n = serializedOrder.getContainer().getIdentifiermap().length, i = 0; i < n; i++) {
                identifier_deser = serializedOrder.getContainer().getIdentifiermap()[i];

                if (identifier2Id.equals(identifier_deser.getId()) && !c_plz1.toString().equals(identifier_deser.getAddresses()[0].getPLZ())) {
                    throw new IllegalStateException("PLZ is not same");
                }
            }
            return serialized.length;
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
        return 0;
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