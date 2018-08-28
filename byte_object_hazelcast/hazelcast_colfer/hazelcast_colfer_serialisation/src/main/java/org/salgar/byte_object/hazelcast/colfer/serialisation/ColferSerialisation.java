package org.salgar.byte_object.hazelcast.colfer.serialisation;

import com.hazelcast.nio.serialization.ByteArraySerializer;
import org.salgar.byte_object.colfer.vo.byte_object.Order;

import java.io.IOException;

public class ColferSerialisation implements ByteArraySerializer<Order> {
    @Override
    public byte[] write(Order order) throws IOException {
        org.salgar.byte_object.io.FastByteArrayOutputStream bos = new org.salgar.byte_object.io.FastByteArrayOutputStream(4096);
        byte[] buff = new byte[4096];
        order.marshal(bos, buff);

        byte[] serialized = bos.toByteArray();
        return serialized;
    }

    @Override
    public Order read(byte[] buffer) throws IOException {
        Order order = new Order();
        order.unmarshal(buffer, 0);
        return order;
    }

    @Override
    public int getTypeId() {
        return 11;
    }

    @Override
    public void destroy() {

    }
}