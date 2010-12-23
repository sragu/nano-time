package org.notechnique.profile;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class StopWatchTest {

    @Test
    public void testStopWatch_getElapsed() throws InterruptedException, IOException {
        StopWatch watch = new StopWatch("task1");
        watch.start();
        Thread.sleep(10);
        watch.stop();
        assertTrue("Elapsed time profiled should be greater than actual Thread.sleep", watch.getElapsed(MILLISECONDS) >= 10L);
        watch.prettyPrint(System.out, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testStopWatch_getElapsed_withStep() throws InterruptedException, IOException {
        StopWatch watch = new StopWatch("task2");
        watch.start();
        Thread.sleep(10);
        watch.step();
        Thread.sleep(10);
        watch.stop();
        assertTrue("Elapsed time profiled should be greater than actual Thread.sleep", watch.getElapsed(MILLISECONDS) >= 20);
        watch.prettyPrint(System.out, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testStopWatch_getPrecision() throws InterruptedException {
        StopWatch watch = new StopWatch("", 100, 200, new ArrayList());
        assertEquals("100", watch.getElapsedPrecision(NANOSECONDS).toPlainString());
    }

    @Test
    public void testStopWatch_getPrecision_toMilliSeconds() throws InterruptedException {
        StopWatch watch = new StopWatch("", 1000, 2000, new ArrayList());
        assertEquals("0.001", watch.getElapsedPrecision(MILLISECONDS).toPlainString());
    }

    @Test
    public void testStopWatch_prettyPrint() throws InterruptedException, IOException {
        StopWatch watch = new StopWatch("profiling", 100, 200, new ArrayList());
        assertEquals("Task: profiling took 0.0001 MILLISECONDS", watch.prettyPrint(new StringWriter(), MILLISECONDS).toString());
    }

    @Test
    public void testStopWatch_prettyPrint_WithIntervals() throws InterruptedException, IOException {
        StopWatch watch = new StopWatch("profiling", 100, 200, asList(140L, 180L));
        assertEquals("Task: profiling took 100 [40, 40, 20] NANOSECONDS", watch.prettyPrint(new StringWriter(), NANOSECONDS).toString());
    }

}
