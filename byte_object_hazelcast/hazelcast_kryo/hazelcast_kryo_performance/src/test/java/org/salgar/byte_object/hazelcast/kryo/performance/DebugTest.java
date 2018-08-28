package org.salgar.byte_object.hazelcast.kryo.performance;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import info.jerrinot.subzero.SubZero;
import org.salgar.byte_object.vo.*;
import org.salgar.byteobject.test.utility.Factories;
import org.testng.annotations.Test;

import java.util.*;

@Test
public class DebugTest {
    private static final Long quantity = 45345345L;
    private static final Integer productid = Integer.valueOf(44644565);
    private static final String productName = "Double Sided Sword";
    private static final String productDescription = "Darth Maul's light saber";
    private static final Long prodcutPrice = Long.valueOf(234245356L);
    private static final ProductStatus productStatus = ProductStatus.IN_STOCK;
    private static final Long quantity2 = 636846L;
    private static final Integer productid2 = Integer.valueOf(123123124);
    private static final String productName2 = "Tennis Racquest";
    private static final String productDescription2 = "Stan the Animal";
    private static final Long prodcutPrice2 = Long.valueOf(974545L);
    private static final ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
    private static final long orderId = 34535435L;
    private static final Date now = new Date(System.currentTimeMillis());
    private static final String error = "This is my first Error!";
    private static final String event = "This is my first Event!";
    private static final String message = "This is my first Message!";
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

    @Test(enabled = false)
    public void performanceTest() {
        HazelcastInstance client;
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("localhost:5701");

        SubZero.useAsGlobalSerializer(clientConfig);

        client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap map = client.getMap("orders");
        createOrder(1, map);
    }

    Order createOrder(int orderIdIncr, IMap map) {
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        ProductOrderContainer productOrderContainer = Factories.ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Product product = Factories.ProductFactory.createProduct(productid, productName + ":" + orderIdIncr, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = Factories.ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2 + ":" + orderIdIncr, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        Order order = Factories.OrderFactory.createOrder(orderIdIncr, now, error, event, message, processed, stage);

        Customer customer = Factories.CustomerFactory.createCustomer(customerId, firstname + ":" + orderIdIncr, lastname, birthDate);

        order.setCustomer(customer);

        Address address = Factories.AddressFactory.createAddress(addressId, street + ":" + orderIdIncr, houseNo, plz, city, country);
        customer.setAddress(address);

        List<ProductOrderContainer> products = new ArrayList<ProductOrderContainer>();
        products.add(productOrderContainer);
        products.add(productOrderContainer2);
        order.setProducts(products);

        List<Bill> bills = new ArrayList<Bill>();

        Bill bill = Factories.BillFactory.createBill(billId, amount, latestPaymentDay);
        bills.add(bill);

        calendar2.add(Calendar.DAY_OF_MONTH, 30);

        Bill bill2 = Factories.BillFactory.createBill(billId2, amount2, latestPaymentDay2);
        bills.add(bill2);

        order.setBills(bills);

        List<PaymentReminder> reminders = new ArrayList<PaymentReminder>();

        PaymentReminder paymentReminder = Factories.PaymentReminderFactory.createPaymentReminder(id_reminder, rank, interest);
        reminders.add(paymentReminder);

        PaymentReminder paymentReminder2 = Factories.PaymentReminderFactory.createPaymentReminder(id_reminder2, rank2, interest2);
        reminders.add(paymentReminder2);

        order.setPaymentreminder(reminders);

        List<CashingInstitution> cashInstitutions = new ArrayList<>();

        Address address1 = Factories.AddressFactory.createAddress(id_address, street1 + ":" + orderIdIncr, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion + ":" + orderIdIncr, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Identifier identifier = Factories.IdentifierFactory.createIdentifier(identifierId, someText + ":" + orderIdIncr);

        Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street + ":" + orderIdIncr, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>();
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Identifier identifier2 = Factories.IdentifierFactory.createIdentifier(identifier2Id, someOtherText+ ":" + orderIdIncr );
        Address c_address1 = Factories.AddressFactory.createAddress(c_addressId1, c_street1 + ":" + orderIdIncr, c_houseNo1, c_plz1, c_city1, c_country1);
        List<Address> c_addresses1 = new ArrayList<Address>();
        c_addresses1.add(c_address1);
        identifier2.setAddress(c_addresses1);

        Set<Identifier> identifierMap = new HashSet<>();

        identifierMap.add(identifier);
        identifierMap.add(identifier2);

        Container container = Factories.ContainerFactory.createContainer(conntainerId, identifierMap);

        ProductOrderContainer c_productOrderContainer = Factories.ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName + ":" + orderIdIncr, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = Factories.ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2 + ":" + orderIdIncr, c_productDescription2, c_prodcutPrice2, c_productStatus2);
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