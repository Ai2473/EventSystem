package me.ai24.eventsystem;

import me.ai24.eventsystem.eventsystem.EventBus;
import me.ai24.eventsystem.eventsystem.util.EventClassScanner;
import me.ai24.eventsystem.examples.DummyEvent;
import me.ai24.eventsystem.examples.ExampleLambdaListener;
import me.ai24.eventsystem.examples.ExampleMethodListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        List<Integer> measurements = new ArrayList<>();

        int tests = 5;
        for (int c = 0; c < tests; c++) {
            long start = System.currentTimeMillis();

            for (int i = 0; i < Settings.MAX_LISTENERS; i++) {
                new ExampleLambdaListener();
            }

            EventBus.BUS.updateAll();

            for (int i = 0; i < 10000; i++) {
                EventBus.BUS.post(new DummyEvent());
            }

            EventBus.BUS.unregisterEvent(DummyEvent.class);

            measurements.add((int) (System.currentTimeMillis() - start));

            EventBus.BUS.resetUpdateStatus();
        }

        System.out.println("Average lambda listeners time: " + (int) measurements.stream().mapToInt(Integer::intValue).average().orElse(0) + "ms");

        measurements.clear();
        EventBus.BUS.unregisterEvent(DummyEvent.class);

        for (int c = 0; c < tests; c++) {
            long start = System.currentTimeMillis();

            for (int i = 0; i < Settings.MAX_LISTENERS; i++) {
                EventClassScanner.scan(new ExampleMethodListener());
            }

            EventBus.BUS.updateAll();

            for (int i = 0; i < 10000; i++) {
                EventBus.BUS.post(new DummyEvent());
            }

            EventBus.BUS.unregisterEvent(DummyEvent.class);

            measurements.add((int) (System.currentTimeMillis() - start));

            EventBus.BUS.resetUpdateStatus();
        }

        System.out.println("Average method listeners time: " + (int) measurements.stream().mapToInt(Integer::intValue).average().orElse(0) + "ms");

        EventBus.BUS.unregisterEvent(DummyEvent.class);
        new ExampleLambdaListener();

        if (EventBus.BUS.getListeners(DummyEvent.class).stream().filter(Objects::nonNull).count() != 1) {
            throw new RuntimeException("Error: listeners count has to be 1");
        }

        System.out.println("DummyEvent count: " + DummyEvent.getCount());
    }
}
