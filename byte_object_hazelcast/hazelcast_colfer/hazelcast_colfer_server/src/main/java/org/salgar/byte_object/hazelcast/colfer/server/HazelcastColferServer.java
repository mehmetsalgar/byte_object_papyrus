package org.salgar.byte_object.hazelcast.colfer.server;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.salgar.byte_object.colfer.vo.byte_object.Order;
import org.salgar.byte_object.hazelcast.colfer.serialisation.ColferSerialisation;

public class HazelcastColferServer {
    public static void main(String[] args) {
        Config config = new Config();
        SerializerConfig serializerConfigOrder = new SerializerConfig()
                .setImplementation(new ColferSerialisation())
                .setTypeClass(Order.class);

        config.getSerializationConfig().addSerializerConfig(serializerConfigOrder);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}