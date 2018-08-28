package org.salgar.byte_object.hazelcast.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import info.jerrinot.subzero.SubZero;

public class HazelcastKryoServer {
    public static void main(String[] args) {
        Config config = new Config();
        SubZero.useAsGlobalSerializer(config);

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
}