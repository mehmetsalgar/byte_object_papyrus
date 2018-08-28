package org.salgar.byte_object;

import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.salgar.byte_object.vo.*;
import org.salgar.byteobject.test.utility.Factories;

import java.util.*;

public class MyBenchmarkOrders extends AbstractBenchmark {
    private static final Logger log = Logger.getLogger(MyBenchmarkOrders.class);

    private static final int NUMBER_OF_ITERATIONS = 20000;

    @State(Scope.Benchmark)
    public static class MySetupOrders {
        public Orders orders;

        @Setup
        public void initialize() {
            orders = new Orders();
            List ordersList = new ArrayList<>(NUMBER_OF_ITERATIONS);
            for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                ordersList.add(createOrder(i));
            }
            orders.setOrder(ordersList);
        }
    }

    @Benchmark()
    @BenchmarkMode(Mode.AverageTime)
    public void testMethod(MySetupOrders setup) {
        int length = serialize(setup.orders);
    }

    int serialize(Orders orders) {
        byte[] serializedBytes = orders.toByte();

        Orders serialized = new Orders(serializedBytes);

        Order order = serialized.getOrder().get(75)
;
        Container containerDeser = order.getContainer();
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
        ProductOrderContainer productOrderContainer = Factories.ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = Factories.ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        Order order = Factories.OrderFactory.createOrder(orderIdIncr, now, error, event, message, processed, stage);

        Customer customer = Factories.CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>(2);
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>(2);

        Bill bill = Factories.BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        calendar2.add(Calendar.DAY_OF_MONTH, 30);

        Bill bill2 = Factories.BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>(2);

        PaymentReminder paymentReminder = Factories.PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        PaymentReminder paymentReminder2 = Factories.PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>(1);

        Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Identifier identifier = Factories.IdentifierFactory.createIdentifier(identifierId, someText);

        Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>(1);
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Identifier identifier2 = Factories.IdentifierFactory.createIdentifier(identifier2Id, someOtherText);
        Address c_address1 = Factories.AddressFactory.createAddress(c_addressId1, c_street1, c_houseNo1, c_plz1, c_city1, c_country1);
        List<Address> c_addresses1 = new ArrayList<Address>(1);
        c_addresses1.add(c_address1);
        identifier2.setAddress(c_addresses1);

        Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();

        identifierMap.put(key, identifier);
        identifierMap.put(key2, identifier2);

        Container container = Factories.ContainerFactory.createContainer(conntainerId, identifierMap);

        ProductOrderContainer c_productOrderContainer = Factories.ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = Factories.ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
        c_productOrderContainer2.setProduct(c_product2);

        List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>(2);
        c_products.add(c_productOrderContainer);
        c_products.add(c_productOrderContainer2);
        container.setProductordercontainer(c_products);
        order.setContainer(container);

        return order;
    }
}