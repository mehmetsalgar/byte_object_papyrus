package org.salgar.byte_object.serialisation;


import com.hazelcast.nio.serialization.ByteArraySerializer;
import org.salgar.byte_object.vo.Order;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteObjectSerialisationStreamer implements ByteArraySerializer<Order> {
    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
    }

    @Override
    public byte[] write(Order order) throws IOException {
        //System.out.println("Writing to serialize");
        ByteBuffer byteBuffer = ByteBuffer.allocate(order.calculateLength());
        order.toByte(byteBuffer);

        byte[] bytes = byteBuffer.array();
        //System.out.println("byte w:" + bytes.length);
        return bytes;
    }

    @Override
    public Order read(byte[] buffer) throws IOException {
        //System.out.println("Reading to serialize");

        //System.out.println("byte r:" + buffer.length);
        return new Order(buffer);
    }
}