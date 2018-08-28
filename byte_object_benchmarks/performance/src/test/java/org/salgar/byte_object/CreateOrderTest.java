package org.salgar.byte_object;


import org.testng.annotations.Test;

public class CreateOrderTest {
    @Test
    public void createOrder() {
        MyBenchmark mb = new MyBenchmark();

        mb.createOrder(1);
    }
}