package org.salgar.byteobject.test.utility;

import org.salgar.byte_object.vo.*;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Factories {
	public static class OrderFactory {
		public static Order createOrder(long id, Date buyDate, String error, String event, String message,
				Boolean processed, Integer stage) {
			Order order = new Order();
			order.setId(id);
			order.setBuyDate(buyDate);
			order.setError(error);
			order.setEvent(event);
			order.setMessage(message);
			order.setProcessed(processed);
			order.setStage(stage);

			return order;
		}
	}

	public static class CustomerFactory {
		public static Customer createCustomer(Long id, String firstname, String lastname, Date birthDate) {
			Customer customer = new Customer();
			customer.setId(id);
			customer.setFirstname(firstname);
			customer.setLastname(lastname);
			customer.setBirthDate(birthDate);
			return customer;
		}
	}
	
	public static class AddressFactory {
		public static Address createAddress(Long id, String street, String houseNo, Integer plz, String city,
				String country) {
			Address address = new Address();
			address.setId(id);
			address.setStreet(street);
			address.setHouseNo(houseNo);
			address.setPLZ(plz);
			address.setCity(city);
			address.setCountry(country);
			
			return address;
		}
	}
	
	public static class ProductFactory {
		public static Product createProduct(Integer id, String name, String description, Long price,
				ProductStatus productStatus) {
			Product product = new Product();
			product.setId(id);
			product.setName(name);
			product.setDescription(description);
			product.setPrice(price);
			product.setProductstatus(productStatus);
			
			return product;
		}
	}

	public static class ProducStatisticstFactory {
		public static ProductStatistics createProductStatisticstFactory(Long weeklyAverage, Long monthlyAverage, Long yearlyAverage) {
			ProductStatistics productStatistics = new ProductStatistics();
			productStatistics.setWeeklyAverge(weeklyAverage);
			productStatistics.setMonthlyAverage(monthlyAverage);
			productStatistics.setYearlyAverage(yearlyAverage);

			return productStatistics;
		}
	}
	
	public static class ProductOrderContainerFactory {
		public static ProductOrderContainer createProductOrderContainer(Long quantity) {
			ProductOrderContainer productOrderContainer = new ProductOrderContainer();
			productOrderContainer.setQuantity(quantity);
			
			return productOrderContainer;
		}
	}
	
	public static class BillFactory {
		public static Bill createBill(Long id, Long amount, Date latestPaymentDay) {
			Bill bill = new Bill();
			bill.setId(id);
			bill.setAmount(amount);
			bill.setLatestPaymentDay(latestPaymentDay);
			
			return bill;
		}
	}
	
	public static class PaymentReminderFactory {
		public static PaymentReminder createPaymentReminder(Long id, ReminderRank rank, Long interest) {
			PaymentReminder paymentReminder = new PaymentReminder();
			paymentReminder.setId(id);
			paymentReminder.setRank(rank);
			paymentReminder.setInterest(interest);
			return paymentReminder;
		}
	}
	
	public static class CashInstutitionFactory {
		public static CashingInstitution createCashInstutition(Long id, String name, Address address) {
			CashingInstitution cashingInstitution = new CashingInstitution();
			cashingInstitution.setId(id);
			cashingInstitution.setName(name);
			cashingInstitution.setAddress(address);
			
			return cashingInstitution;
		}
	}
	
	public static class ContainerFactory {
		public static Container createContainer(Integer id, Map<String, Identifier> identifiermap) {
			Container container = new Container();
			container.setId(id);
			container.setIdentifiermap(identifiermap);

			return container;
		}

		/*public static Container createContainer(Integer id, Set<Identifier> identifiermap) {
			Container container = new Container();
			container.setId(id);
			container.setIdentifiermap(identifiermap);

			return container;
		}*/
	}
	
	public static class IdentifierFactory {
		public static Identifier createIdentifier(Integer id, String someText) {
			Identifier identifier = new Identifier();
			identifier.setId(id);
			identifier.setSomeText(someText);
			
			return identifier;
		}
	}
}