package com.example.j8583;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;
public class Beeper {

private final ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {
            Runnable beeper = () -> System.out.println("beep");
            ScheduledFuture<?> beeperHandle =
                    scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
            Runnable canceller = () -> beeperHandle.cancel(false);
            scheduler.schedule(canceller, 1, HOURS);
        }

}
