package org.salgar.byte_object.hazelcast.server;

import com.hazelcast.config.Config;

import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.salgar.byte_object.serialisation.ByteObjectSerialisationStreamer;
import org.salgar.byte_object.vo.Order;

public class ByteobjectHazelcastServer {
    public static void main(String[] args) {
        Config config = new Config();
        SerializerConfig serializerConfigOrder = new SerializerConfig()
                                                    .setImplementation(new ByteObjectSerialisationStreamer())
                                                    .setTypeClass(Order.class);

        config.getSerializationConfig().addSerializerConfig(serializerConfigOrder);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}