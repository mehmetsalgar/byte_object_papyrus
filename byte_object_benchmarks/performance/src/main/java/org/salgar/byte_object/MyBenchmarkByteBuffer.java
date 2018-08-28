package org.salgar.byte_object;

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

import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.vo.*;

import static org.salgar.byteobject.test.utility.Factories.*;

import java.nio.ByteBuffer;
import java.util.*;

public class MyBenchmarkByteBuffer extends AbstractBenchmark {
    private static final Logger log = Logger.getLogger(MyBenchmarkByteBuffer.class);

    private static final int NUMBER_OF_ITERATIONS = 30000;

    @State(Scope.Benchmark)
    public static class MySetup {
        public List<Order> orders;
        public ByteBuffer byteBuffer;

        @Setup
        public void initialize() {
            log.info("Initializing....");
            this.orders = new ArrayList<>(NUMBER_OF_ITERATIONS);
            for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                this.orders.add(createOrder(i));
            }
        }
    }

    @Benchmark()
    @BenchmarkMode(Mode.AverageTime)
    public void testMethod(MySetup setup) {
        List<Order> local = setup.orders;
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            Order order = local.get(i);
            if(setup.byteBuffer == null) {
                setup.byteBuffer = ByteBuffer.allocate(order.calculateLength());
            }
            int length = serialize(order, setup.byteBuffer);
            //log.info("length: " + length) ;
        }
    }

    int serialize(Order order, ByteBuffer byteBuffer) {
        byteBuffer.clear();
        order.toByte(byteBuffer);

        byte[] serializedBytes = byteBuffer.array();
        Order serialized = new Order(serializedBytes);

        Container containerDeser = serialized.getContainer();
        ProductOrderContainer productOrderContainerDeser = containerDeser
                .getProductordercontainer().get(1);

        Product productDeser = productOrderContainerDeser.getProduct();
        if (!prodcutPrice2.equals(productDeser.getPrice())) {
            throw new IllegalStateException("Price is not same");
        }

        if(!amount2.equals(order.getBills().get(1).getAmount())) {
            throw new IllegalStateException("Price is not same");
        }
        Identifier identifier_deser = containerDeser.getIdentifiermap().get(key2);

        if (identifier2Id.equals(identifier_deser.getId())
                && !c_plz1.equals(identifier_deser.getAddress().get(0).getPLZ())) {
            throw new IllegalStateException("PLZ is not same");
        }

        return serializedBytes.length;
    }

    static Order createOrder(int orderIdIncr) {
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

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>(2);
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>(2);

        Bill bill = BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        calendar2.add(Calendar.DAY_OF_MONTH, 30);

        Bill bill2 = BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>(2);

        PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        PaymentReminder paymentReminder2 = PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>(1);

        Address address1 = AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

        Address c_address = AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>(1);
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);
        Address c_address1 = AddressFactory.createAddress(c_addressId1, c_street1, c_houseNo1, c_plz1, c_city1, c_country1);
        List<Address> c_addresses1 = new ArrayList<Address>(1);
        c_addresses1.add(c_address1);
        identifier2.setAddress(c_addresses1);

        Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();

        identifierMap.put(key, identifier);
        identifierMap.put(key2, identifier2);

        Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

        ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Product c_product = ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Product c_product2 = ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
        c_productOrderContainer2.setProduct(c_product2);

        List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>(2);
        c_products.add(c_productOrderContainer);
        c_products.add(c_productOrderContainer2);
        container.setProductordercontainer(c_products);
        order.setContainer(container);

        return order;
    }
}