package org.notechnique.profile;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.valueOf;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class StopWatch {
    private long startTime;
    private long endTime;
    private String taskName;
    private List<Long> timestamps = new ArrayList<Long>();

    public StopWatch(String taskName) {
        this.taskName = taskName;
    }

    StopWatch(String taskName, long startTime, long endTime, List<Long> timestamps) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timestamps = timestamps;
    }

    public StopWatch start() {
        this.startTime = System.nanoTime();
        return this;
    }

    public void step() {
        this.timestamps.add(System.nanoTime());
    }

    public StopWatch stop() {
        this.endTime = System.nanoTime();
        return this;
    }

    public long getElapsed(TimeUnit timeUnit) {
        return timeUnit.convert(endTime - startTime, NANOSECONDS);
    }

    public BigDecimal getElapsedPrecision(TimeUnit timeUnit) {
        return getPrecisionDifference(this.startTime, this.endTime, timeUnit);
    }

    private BigDecimal getPrecisionDifference(long startTime, long endTime, TimeUnit timeUnit) {
        return valueOf(endTime - startTime).divide(valueOf(NANOSECONDS.convert(1, timeUnit)));
    }

    public String prettyPrintString(TimeUnit timeUnit) {
        return new StringBuffer().append(String.format("Task: %s took ", taskName))
                .append(getElapsedPrecision(timeUnit).toPlainString())
                .append(timestamps.isEmpty() ? " " : " " + getElapsedIntervalString(timeUnit) + " ")
                .append(timeUnit)
                .toString();
    }

    private String getElapsedIntervalString(TimeUnit timeUnit) {
        List<String> intervals = new ArrayList<String>();
        ListIterator<Long> iterator = timestamps.listIterator();
        long lastInterval = startTime;
        while (iterator.hasNext()) {
            Long currentInterval = iterator.next();
            intervals.add(getPrecisionDifference(lastInterval, currentInterval, timeUnit).toPlainString());
            lastInterval = currentInterval;
        }
        intervals.add(getPrecisionDifference(lastInterval, this.endTime, timeUnit).toPlainString());
        return Arrays.toString(intervals.toArray());
    }

    public Appendable prettyPrint(Appendable writeTo, TimeUnit timeUnit) throws IOException {
        return writeTo.append(prettyPrintString(timeUnit));
    }

    public void prettyPrint(PrintStream ps, TimeUnit timeUnit) {
        ps.println(prettyPrintString(timeUnit));
    }
}
