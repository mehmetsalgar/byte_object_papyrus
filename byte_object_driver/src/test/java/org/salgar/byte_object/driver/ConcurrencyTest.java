package org.salgar.byte_object.driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.salgar.byteobject.test.utility.Factories;
import org.salgar.byteobject.test.utility.Factories.BillFactory;
import org.salgar.byteobject.test.utility.Factories.ContainerFactory;
import org.salgar.byteobject.test.utility.Factories.CustomerFactory;
import org.salgar.byteobject.test.utility.Factories.IdentifierFactory;
import org.salgar.byteobject.test.utility.Factories.OrderFactory;
import org.salgar.byteobject.test.utility.Factories.PaymentReminderFactory;
import org.salgar.byteobject.test.utility.Factories.ProductOrderContainerFactory;
import org.salgar.byte_object.vo.Address;
import org.salgar.byte_object.vo.Bill;
import org.salgar.byte_object.vo.CashingInstitution;
import org.salgar.byte_object.vo.Container;
import org.salgar.byte_object.vo.Customer;
import org.salgar.byte_object.vo.Identifier;
import org.salgar.byte_object.vo.Order;
import org.salgar.byte_object.vo.PaymentReminder;
import org.salgar.byte_object.vo.Product;
import org.salgar.byte_object.vo.ProductOrderContainer;
import org.salgar.byte_object.vo.ProductStatus;
import org.salgar.byte_object.vo.ReminderRank;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ConcurrencyTest {
    private static final Logger log = Logger.getLogger(ConcurrencyTest.class);

    private final Long quantity = 45345345L;
    private final Integer productid = Integer.valueOf(44644565);
    private final String productName = "Double Sided Sword";
    private final String productDescription = "Darth Maul's light saber";
    private final Long prodcutPrice = Long.valueOf(234245356L);
    private final ProductStatus productStatus = ProductStatus.IN_STOCK;
    private final Long quantity2 = 636846L;
    private final Integer productid2 = Integer.valueOf(123123124);
    private final String productName2 = "Tennis Racquest";
    private final String productDescription2 = "Stan the Animal";
    private final Long prodcutPrice2 = Long.valueOf(974545L);
    private final ProductStatus productStatus2 = ProductStatus.ON_THE_WAY;
    private final long orderId = 34535435L;
    private final Date now = new Date(System.currentTimeMillis());
    private final String error = "This is my first Error!";
    private final String event = "This is my first Event!";
    private final String message = "This is my first Message!";
    private final Boolean processed = Boolean.TRUE;
    private final Integer stage = 62;
    private final Long addressId = Long.valueOf(654564L);
    private final String street = "Sesame Str";
    private final String houseNo = "11B";
    private final Integer plz = 64346;
    private final String city = "Atlantis";
    private final String country = "Neptune";
    private final Long customerId = 456467548L;
    private final String firstname = "Dirk";
    private final String lastname = "Pitt";
    private final Date birthDate = new Date(System.currentTimeMillis() - 1000000);
    private final Long billId = 1456356563L;
    private final Long amount = 4534579786L;
    private final Calendar calendar = Calendar.getInstance();
    private final Date latestPaymentDay = calendar.getTime();
    private final Long billId2 = 435345768L;
    private final Long amount2 = 87645653L;
    private final Calendar calendar2 = Calendar.getInstance();
    private final Date latestPaymentDay2 = calendar.getTime();
    private final Long id_reminder = 4744573L;
    private final ReminderRank rank = ReminderRank.INITIAL;
    private final Long interest = 5L;
    private final Long id_cashInstitutuion = 23423L;
    private final String nameCashInstitutuion = "VISA";

    private final Long id_address = Long.valueOf(654564L);
    private final String street1 = "Sesame Str";
    private final String houseNo1 = "11B";
    private final Integer plz1 = 64346;
    private final String city1 = "Atlantis";
    private final String country1 = "Neptune";

    private final Long id_reminder2 = 4744573L;
    private final ReminderRank rank2 = ReminderRank.INITIAL;
    private final Long interest2 = 5L;

    private final Integer identifierId = Integer.valueOf(6546543);
    private final String someText = "We are developing interesting things here!";

    private final Long c_addressId = Long.valueOf(654564L);
    private final String c_street = "Sesame Str";
    private final String c_houseNo = "11B";
    private final Integer c_plz = 64346;
    private final String c_city = "Atlantis";
    private final String c_country = "Neptune";

    private final Integer identifier2Id = Integer.valueOf(5445643);
    private final String someOtherText = "Big data here we are coming";

    private final String key = "Halli Hallo";
    private final String key2 = "All hail big data";

    private final Long c_quantity = 45345345L;

    private final Integer c_productid = Integer.valueOf(44644565);
    private final String c_productName = "Double Sided Sword";
    private final String c_productDescription = "Darth Maul's light saber";
    private final Long c_prodcutPrice = Long.valueOf(234245356L);
    private final ProductStatus c_productStatus = ProductStatus.IN_STOCK;

    private final Long c_quantity2 = 636846L;

    private final Integer c_productid2 = Integer.valueOf(123123124);
    private final String c_productName2 = "Tennis Racquest";
    private final String c_productDescription2 = "Stan the Animal";
    private final Long c_prodcutPrice2 = Long.valueOf(974545L);
    private final ProductStatus c_productStatus2 = ProductStatus.ON_THE_WAY;

    private final Integer conntainerId = Integer.valueOf(92473864);


    @Test(groups = {"concureency"})
    public void initialConcurrencyTest() {
        final Order order = orderTest();
        int i = 0;
        try {
            while (i < 5) {
                Thread runner = new Thread(new CrashRunner(order));
                runner.setName("thread: " + i);
                runner.start();
                i++;
            }
            wait(300000);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }

    }

    class CrashRunner implements Runnable {
        private Order order;

        public CrashRunner(Order order) {
            this.order = order;
        }

        @Override
        public void run() {
            try {
                int i = 0;
                while (i < 100000) {
                    int size = ThreadLocalRandom.current().nextInt(1, 100);

                    char[] dummyText = new char[size];
                    Arrays.fill(dummyText, 'X');
                    String tmp = new String(dummyText);

                    order.setError("we are trying to corrupt with current thread: " + Thread.currentThread().getName() + "   " + tmp);

                    log.info("Setting from Thread: " + Thread.currentThread().getName() + " from pass: " + i);

                    assert1(order);
                    i++;
                }
                String conText = "It seems we managed";
                order.setError(conText);
                Assert.assertEquals(order.getError(), conText);
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
            }
        }
    }

    Order orderTest() {
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        ProductOrderContainer productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(quantity);

        Product product = Factories.ProductFactory.createProduct(productid, productName, productDescription, prodcutPrice, productStatus);
        productOrderContainer.setProduct(product);

        ProductOrderContainer productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(quantity2);

        Product product2 = Factories.ProductFactory.createProduct(productid2, productName2, productDescription2, prodcutPrice2, productStatus2);
        productOrderContainer2.setProduct(product2);

        Order order = OrderFactory.createOrder(orderId, now, error, event, message, processed, stage);

        Customer customer = CustomerFactory.createCustomer(customerId, firstname, lastname, birthDate);

        order.setCustomer(customer);

        Address address = Factories.AddressFactory.createAddress(addressId, street, houseNo, plz, city, country);
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

        Address address1 = Factories.AddressFactory.createAddress(id_address, street1, houseNo1, plz1, city1, country1);

        CashingInstitution cashingInstitution = Factories.CashInstutitionFactory.createCashInstutition(id_cashInstitutuion, nameCashInstitutuion, address1);

        cashInstitutions.add(cashingInstitution);

        bill2.setCashinginstitution(cashInstitutions);

        Identifier identifier = IdentifierFactory.createIdentifier(identifierId, someText);

        Address c_address = Factories.AddressFactory.createAddress(c_addressId, c_street, c_houseNo, c_plz, c_city, c_country);
        List<Address> c_addresses = new ArrayList<Address>();
        c_addresses.add(c_address);
        identifier.setAddress(c_addresses);

        Identifier identifier2 = IdentifierFactory.createIdentifier(identifier2Id, someOtherText);

        Map<String, Identifier> identifierMap = new HashMap<String, Identifier>();

        identifierMap.put(key, identifier);
        identifierMap.put(key2, identifier2);

        Container container = ContainerFactory.createContainer(conntainerId, identifierMap);

        ProductOrderContainer c_productOrderContainer = ProductOrderContainerFactory.createProductOrderContainer(c_quantity);

        Product c_product = Factories.ProductFactory.createProduct(c_productid, c_productName, c_productDescription, c_prodcutPrice, c_productStatus);
        c_productOrderContainer.setProduct(c_product);

        ProductOrderContainer c_productOrderContainer2 = ProductOrderContainerFactory.createProductOrderContainer(c_quantity2);

        Product c_product2 = Factories.ProductFactory.createProduct(c_productid2, c_productName2, c_productDescription2, c_prodcutPrice2, c_productStatus2);
        c_productOrderContainer2.setProduct(c_product2);

        List<ProductOrderContainer> c_products = new ArrayList<ProductOrderContainer>();
        c_products.add(c_productOrderContainer);
        c_products.add(c_productOrderContainer2);
        container.setProductordercontainer(c_products);
        order.setContainer(container);


        //byte[] serializedBytes = order.toByte();

        //Order serialized = new Order(serializedBytes);

        //---------------------

        return order;
    }

    void assert1(Order serialized) {
        Assert.assertEquals(Long.valueOf(serialized.getId()), Long.valueOf(orderId));
        Assert.assertEquals(serialized.getBuyDate(), now);
        //Assert.assertEquals(serialized.getError(), error);
        Assert.assertEquals(serialized.getEvent(), event);
        Assert.assertEquals(serialized.getMessage(), message);
        Assert.assertEquals(serialized.getProcessed(), processed);
        Assert.assertEquals(serialized.getStage(), stage);

        Assert.assertEquals(serialized.getCustomer().getId(), customerId);
        Assert.assertEquals(serialized.getCustomer().getFirstname(), firstname);
        Assert.assertEquals(serialized.getCustomer().getLastname(), lastname);
        Assert.assertEquals(serialized.getCustomer().getBirthDate().getTime(), birthDate.getTime());

        Assert.assertEquals(serialized.getCustomer().getAddress().getId(), addressId);
        Assert.assertEquals(serialized.getCustomer().getAddress().getStreet(), street);
        Assert.assertEquals(serialized.getCustomer().getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(serialized.getCustomer().getAddress().getPLZ(), plz);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCity(), city);
        Assert.assertEquals(serialized.getCustomer().getAddress().getCountry(), country);

        ProductOrderContainer productOrderContainer1_1 = serialized.getProducts().get(0);
        Product product1_1 = productOrderContainer1_1.getProduct();
        Assert.assertEquals(productOrderContainer1_1.getQuantity(), quantity);
        Assert.assertEquals(product1_1.getId(), productid);
        Assert.assertEquals(product1_1.getName(), productName);
        Assert.assertEquals(product1_1.getDescription(), productDescription);
        Assert.assertEquals(product1_1.getPrice(), prodcutPrice);
        Assert.assertEquals(product1_1.getProductstatus(), productStatus);

        ProductOrderContainer productOrderContainer2_2 = serialized.getProducts().get(1);
        Product product2_2 = productOrderContainer2_2.getProduct();
        Assert.assertEquals(productOrderContainer2_2.getQuantity(), quantity2);
        Assert.assertEquals(product2_2.getId(), productid2);
        Assert.assertEquals(product2_2.getName(), productName2);
        Assert.assertEquals(product2_2.getDescription(), productDescription2);
        Assert.assertEquals(product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(product2_2.getProductstatus(), productStatus2);

        Bill bill1 = serialized.getBills().get(1);
        Assert.assertEquals(bill1.getId(), billId2);
        Assert.assertEquals(bill1.getAmount(), amount2);
        Assert.assertEquals(bill1.getLatestPaymentDay(), latestPaymentDay2);

        Assert.assertEquals(bill1.getCashinginstitution().get(0).getId(), id_cashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getName(), nameCashInstitutuion);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getId(), id_address);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getStreet(), street);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getHouseNo(), houseNo);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getPLZ(), plz);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCity(), city);
        Assert.assertEquals(bill1.getCashinginstitution().get(0).getAddress().getCountry(), country);

        Assert.assertEquals(serialized.getPaymentreminder().get(0).getId(), id_reminder);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getRank(), rank);
        Assert.assertEquals(serialized.getPaymentreminder().get(0).getInterest(), interest);

        Assert.assertEquals(serialized.getPaymentreminder().get(1).getId(), id_reminder2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getRank(), rank2);
        Assert.assertEquals(serialized.getPaymentreminder().get(1).getInterest(), interest2);

        //ProductOrderContainer referential =  serialized.getProducts().get(1);
        //referential.getProduct().setPrice(35345345L);
        //Assert.assertEquals(serialized.getProducts().get(1).getProduct().getPrice(), Long.valueOf(35345345L));


        //---------------


        Assert.assertEquals(serialized.getContainer().getId(), conntainerId);
        Assert.assertNotNull(serialized.getContainer().getIdentifiermap());

        Map<String, Identifier> serializedIdentifierMap = serialized.getContainer().getIdentifiermap();
        Identifier serializedIdentifier = serializedIdentifierMap.get(key);
        Assert.assertNotNull(serializedIdentifier);
        Assert.assertEquals(serializedIdentifier.getId(), identifierId);
        Assert.assertEquals(serializedIdentifier.getSomeText(), someText);

        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getId(), c_addressId);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getStreet(), c_street);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getHouseNo(), c_houseNo);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getPLZ(), c_plz);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCity(), c_city);
        Assert.assertEquals(serializedIdentifier.getAddress().get(0).getCountry(), c_country);

        Identifier serializedIdentifier2 = serializedIdentifierMap.get(key2);
        Assert.assertNotNull(serializedIdentifier2);
        Assert.assertEquals(serializedIdentifier2.getId(), identifier2Id);
        Assert.assertEquals(serializedIdentifier2.getSomeText(), someOtherText);

        ProductOrderContainer c_productOrderContainer1_1 = serialized.getContainer().getProductordercontainer().get(0);
        Product c_product1_1 = c_productOrderContainer1_1.getProduct();
        Assert.assertEquals(c_productOrderContainer1_1.getQuantity(), c_quantity);
        Assert.assertEquals(c_product1_1.getId(), c_productid);
        Assert.assertEquals(c_product1_1.getName(), c_productName);
        Assert.assertEquals(c_product1_1.getDescription(), c_productDescription);
        Assert.assertEquals(c_product1_1.getPrice(), c_prodcutPrice);
        Assert.assertEquals(c_product1_1.getProductstatus(), c_productStatus);

        ProductOrderContainer c_productOrderContainer2_2 = serialized.getContainer().getProductordercontainer().get(1);
        Product c_product2_2 = c_productOrderContainer2_2.getProduct();
        Assert.assertEquals(c_productOrderContainer2_2.getQuantity(), c_quantity2);
        Assert.assertEquals(c_product2_2.getId(), productid2);
        Assert.assertEquals(c_product2_2.getName(), productName2);
        Assert.assertEquals(c_product2_2.getDescription(), productDescription2);
        Assert.assertEquals(c_product2_2.getPrice(), prodcutPrice2);
        Assert.assertEquals(c_product2_2.getProductstatus(), productStatus2);
    }
}	