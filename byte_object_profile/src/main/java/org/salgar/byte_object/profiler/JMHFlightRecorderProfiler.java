package org.salgar.byte_object.profiler;

import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.profile.ExternalProfiler;
import org.openjdk.jmh.results.*;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class JMHFlightRecorderProfiler implements ExternalProfiler {
    private static volatile String benchmarkName;
    private static final String DUMP_FOLDER = System.getProperty("jmh.stack.profiles",
            FileSystems.getDefault().getPath("").toAbsolutePath().toString());
    private static final String DEFAULT_OPTIONS = System.getProperty("jmh.fr.options",
            "defaultrecording=true,settings=Profile.jfc");

    private volatile String dumpFile;

    @Override
    public Collection<String> addJVMInvokeOptions(BenchmarkParams params) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<String> addJVMOptions(BenchmarkParams params) {
        final String id = params.id();
        benchmarkName = id;
        dumpFile = DUMP_FOLDER + '/' + id + ".jfr";
        String flightRecorderOptions =  "dumponexit=true,dumponexitpath=" + dumpFile;
        /*return Arrays.asList("-XX:+FlightRecorder",
                "-XX:FlightRecorderOptions=" + flightRecorderOptions);*/
        return Arrays.asList("-XX:+FlightRecorder",
                "-XX:FlightRecorderOptions=" + flightRecorderOptions,
                "-XX:StartFlightRecording=" + DEFAULT_OPTIONS);
    }

    @Override
    public void beforeTrial(BenchmarkParams benchmarkParams) {
    }

    @Override
    public Collection<? extends Result> afterTrial(BenchmarkResult br, long pid, File stdOut, File stdErr) {
        NoResult r = new NoResult("Profile saved to " + dumpFile + ", results: " + br
                + ", stdOutFile = " + stdOut + ", stdErrFile = " + stdErr);
        return Collections.singleton(r);
    }

    @Override
    public boolean allowPrintOut() {
        return true;
    }

    @Override
    public boolean allowPrintErr() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Java Flight Recording profiler runs for every benchmark.";
    }

    private static final class NoResult extends Result<NoResult> {
        private final String output;

        public NoResult(final String output) {
            super(ResultRole.SECONDARY, "JFR", of(Double.NaN), "N/A", AggregationPolicy.SUM);
            this.output = output;
        }

        @Override
        protected Aggregator<NoResult> getThreadAggregator() {
            return new NoResultAggretor();
        }

        @Override
        protected Aggregator<NoResult> getIterationAggregator() {
            return new NoResultAggretor();
        }

        private static class NoResultAggretor implements Aggregator<NoResult> {
            @Override
            public NoResult aggregate(Collection<NoResult> results) {
                StringBuilder sb = new StringBuilder();
                for (NoResult noResult : results ) {
                    sb.append(noResult.output);
                }
                return new NoResult(sb.toString());
            }
        }
    }

    @Override
    public String toString() {
        return "JmhFlightRecorderProfiler{" + "dumpFile=" + dumpFile + '}';
    }
}