package org.salgar.byte_object.hazelcast.java_serialisation.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastJavaSerialisationServer {
    public static void main(String[] args) {
        Config config = new Config();
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}