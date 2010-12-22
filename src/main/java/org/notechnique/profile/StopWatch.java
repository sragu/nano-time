package org.notechnique.profile;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.valueOf;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class StopWatch {
    private long startTime;
    private long endTime;
    private String taskName;

    public StopWatch(String taskName) {
        this.taskName = taskName;
    }

    StopWatch(String taskName, long startTime, long endTime) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StopWatch start() {
        this.startTime = System.nanoTime();
        return this;
    }

    public StopWatch stop() {
        this.endTime = System.nanoTime();
        return this;
    }

    public long getElapsed(TimeUnit timeUnit) {
        return timeUnit.convert(endTime - startTime, NANOSECONDS);
    }

    public BigDecimal getElapsedPrecision(TimeUnit timeUnit) {
        return valueOf(endTime - startTime).divide(valueOf(NANOSECONDS.convert(1, timeUnit)));
    }

    public void prettyPrint(PrintStream writeTo, TimeUnit tu) {
        writeTo.println(String.format("Task: %s took %s %s", taskName, getElapsedPrecision(tu).toPlainString(), tu));
    }

    public Appendable prettyPrint(Appendable writeTo, TimeUnit tu) throws IOException {
        return writeTo.append(String.format("Task: %s took %s %s", taskName, getElapsedPrecision(tu).toPlainString(), tu));
    }
}
