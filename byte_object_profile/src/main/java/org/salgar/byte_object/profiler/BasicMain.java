package org.salgar.byte_object.profiler;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.file.FileSystems;

public class BasicMain {
    public static void main(String[] args) throws RunnerException {
        final String destinationFolder = System.getProperty("basedir",
                FileSystems.getDefault().getPath("").toAbsolutePath().toString()) + "/target";

        final String profile = System.getProperty("profiledir",
                FileSystems.getDefault().getPath("").toAbsolutePath().toString()) + "/Profile.jfc";

        Options opts = new OptionsBuilder()
                .addProfiler(JMHFlightRecorderProfiler.class)
                //.jvm(System.getProperty("JAVA_HOME"))
                .jvmArgs("-XX:MaxInlineLevel=12", "-Xmx6144m", "-Xms6144m", "-XX:+UnlockCommercialFeatures",
                        "-Djmh.stack.profiles=" + destinationFolder,
                        "-Djmh.fr.options=defaultrecording=true,settings=" + profile)
                .build();

        new Runner(opts).run();
    }
}