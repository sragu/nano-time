package org.notechnique.profile;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class StopWatchTest {

    @Test
    public void testStopWatch_getElapsed() throws InterruptedException {
        StopWatch watch =  new StopWatch("halo");
        watch.start();
        Thread.sleep(10);
        watch.stop();
        assertTrue("Elapsed time profiled should be greater than actual Thread.sleep", watch.getElapsed(MILLISECONDS) >= 10L);
    }

    @Test
    public void testStopWatch_getPrecision() throws InterruptedException {
        StopWatch watch =  new StopWatch("", 100, 200);
        assertEquals("100", watch.getElapsedPrecision(NANOSECONDS).toPlainString());
    }

    @Test
    public void testStopWatch_getPrecision_toMilliSeconds() throws InterruptedException {
        StopWatch watch =  new StopWatch("", 1000, 2000);
        assertEquals("0.001", watch.getElapsedPrecision(MILLISECONDS).toPlainString());
    }

    @Test
    public void testStopWatch_prettyPrint() throws InterruptedException, IOException {
        StopWatch watch =  new StopWatch("profiling", 100, 200);
        assertEquals("Task: profiling took 0.0001 MILLISECONDS", watch.prettyPrint(new StringWriter(), MILLISECONDS).toString());
        watch.prettyPrint(System.out, MILLISECONDS);
    }

}
