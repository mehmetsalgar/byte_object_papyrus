package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byteobject.test.utility.Factories.PaymentReminderFactory;
import org.salgar.byte_object.vo.PaymentReminder;
import org.salgar.byte_object.vo.ReminderRank;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Test(groups = {"normal"})
public class PaymentReminderTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Long id = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;
		
		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id, rank, interest);
		
		Assert.assertEquals(paymentReminder.getId(), id);
		Assert.assertEquals(paymentReminder.getRank(), rank);
		Assert.assertEquals(paymentReminder.getInterest(), interest);
	}
	
	@Test(groups = {"normal"})
	public void serializedTest() {
		Long id = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;
		
		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id, rank, interest);
		
		PaymentReminder serialized = new PaymentReminder(paymentReminder.toByte());
		
		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getRank(), rank);
		Assert.assertEquals(serialized.getInterest(), interest);
	}

	@Test(groups = {"stream"})
	public void streamTest() throws IOException {
		Long id = 4744573L;
		ReminderRank rank = ReminderRank.INITIAL;
		Long interest = 5L;

		PaymentReminder paymentReminder = PaymentReminderFactory.createPaymentReminder(id, rank, interest);

		FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
		paymentReminder.toByte(bos);

		PaymentReminder serialized = new PaymentReminder(bos.toByteArray());

		Assert.assertEquals(serialized.getId(), id);
		Assert.assertEquals(serialized.getRank(), rank);
		Assert.assertEquals(serialized.getInterest(), interest);
	}
}