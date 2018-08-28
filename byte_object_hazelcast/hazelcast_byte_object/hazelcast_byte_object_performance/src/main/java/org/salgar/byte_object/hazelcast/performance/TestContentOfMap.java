package org.salgar.byte_object.hazelcast.performance;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.salgar.byte_object.serialisation.ByteObjectSerialisationStreamer;
import org.salgar.byte_object.vo.Order;

public class TestContentOfMap {
    public static void main(String[] args) {
        getOneRecord();
    }

    public static void getOneRecord() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("localhost:5701");
        SerializerConfig serializerConfigOrder = new SerializerConfig()
                .setImplementation(new ByteObjectSerialisationStreamer())
                .setTypeClass(Order.class);
        clientConfig.getSerializationConfig().addSerializerConfig(serializerConfigOrder);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap map = client.getMap("orders");

        Order orderNew = new Order();
        orderNew.setId(345346456L);

        map.put(345346456L, orderNew);

        Order order = (Order) map.get(Long.valueOf(355));

        Long quamtity = order.getContainer().getProductordercontainer().get(0).getQuantity();

        System.out.println("we are here!");
    }
}