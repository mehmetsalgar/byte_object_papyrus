package org.salgar.byte_object.driver;

import java.util.Calendar;
import java.util.Date;

import org.salgar.byteobject.test.utility.Factories.BillFactory;
import org.salgar.byte_object.vo.Bill;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"normal"})
public class BillTest {
	@Test(groups = {"normal"})
	public void initialTest() {
		Long id = 1456356563L;
		Long amount = 4534579786L;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 15);
		Date latestPaymentDay = calendar.getTime();
		Bill bill = BillFactory.createBill(id, amount, latestPaymentDay);
		
		Assert.assertEquals(bill.getId(), id);
		Assert.assertEquals(bill.getAmount(), amount);
		Assert.assertEquals(bill.getLatestPaymentDay(), latestPaymentDay);
	}
}