package org.salgar.byte_object.driver;

import org.salgar.byte_object.io.FastByteArrayOutputStream;
import org.salgar.byte_object.vo.ProductStatistics;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

@Test
public class ProductStatisticsTest {

    @Test(groups = {"normal"})
    public void intialTest() {
        ProductStatistics productStatistics = new ProductStatistics();
        productStatistics.setWeeklyAverge(100L);
        productStatistics.setMonthlyAverage(500L);
        productStatistics.setYearlyAverage(350L);

        byte[] serializedBytes = productStatistics.toByte();

        ProductStatistics serialized = new ProductStatistics(serializedBytes);

        Assert.assertEquals(serialized.getWeeklyAverge(), Long.valueOf(100));
        Assert.assertEquals(serialized.getMonthlyAverage(), Long.valueOf(500));
        Assert.assertEquals(serialized.getYearlyAverage(), Long.valueOf(350));
    }

    @Test(groups = {"stream"})
    public void streamTest() throws IOException {
        ProductStatistics productStatistics = new ProductStatistics();
        productStatistics.setWeeklyAverge(100L);
        productStatistics.setMonthlyAverage(500L);
        productStatistics.setYearlyAverage(350L);

        FastByteArrayOutputStream bos = new FastByteArrayOutputStream(4096);
        productStatistics.toByte(bos);

        ProductStatistics serialized = new ProductStatistics(bos.toByteArray());

        Assert.assertEquals(serialized.getWeeklyAverge(), Long.valueOf(100));
        Assert.assertEquals(serialized.getMonthlyAverage(), Long.valueOf(500));
        Assert.assertEquals(serialized.getYearlyAverage(), Long.valueOf(350));
    }

    @Test(groups = {"bytebuffer"})
    public void byteBufferTest() throws IOException {
        ProductStatistics productStatistics = new ProductStatistics();
        productStatistics.setWeeklyAverge(100L);
        productStatistics.setMonthlyAverage(500L);
        productStatistics.setYearlyAverage(350L);

        ByteBuffer byteBuffer = ByteBuffer.allocate(productStatistics.calculateLength());
        productStatistics.toByte(byteBuffer);

        ProductStatistics serialized = new ProductStatistics(byteBuffer.array());

        Assert.assertEquals(serialized.getWeeklyAverge(), Long.valueOf(100));
        Assert.assertEquals(serialized.getMonthlyAverage(), Long.valueOf(500));
        Assert.assertEquals(serialized.getYearlyAverage(), Long.valueOf(350));
    }
}