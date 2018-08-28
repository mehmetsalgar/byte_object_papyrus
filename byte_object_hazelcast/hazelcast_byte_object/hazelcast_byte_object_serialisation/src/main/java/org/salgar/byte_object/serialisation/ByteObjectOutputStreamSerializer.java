package org.salgar.byte_object.serialisation;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.apache.commons.io.IOUtils;
import org.salgar.byte_object.vo.Order;

import java.io.*;

public class ByteObjectOutputStreamSerializer implements StreamSerializer<Order> {
    @Override
    public void write(ObjectDataOutput out, Order order) throws IOException {
        order.toByte((OutputStream) out);
    }

    @Override
    public Order read(ObjectDataInput in) throws IOException {
        byte[] serialized = IOUtils.toByteArray((InputStream) in);
        return new Order(serialized);
    }

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    public void destroy() {

    }
}