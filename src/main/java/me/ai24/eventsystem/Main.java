package me.ai24.eventsystem;

import me.ai24.eventsystem.eventsystem.EventBus;
import me.ai24.eventsystem.eventsystem.util.EventClassScanner;
import me.ai24.eventsystem.examples.DummyEvent;
import me.ai24.eventsystem.examples.ExampleLambdaListener;
import me.ai24.eventsystem.examples.ExampleMethodListener;
import me.ai24.eventsystem.examples.ExampleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final int tests = 50;
    private static final int eventsPostCounter = 1000;

    public static void main(String[] args) {
        List<Integer> measurements = new ArrayList<>();

        // Lambda listeners
        for (int c = 0; c < tests; c++) {
            for (int i = 0; i < Settings.MAX_LISTENERS; i++) {
                new ExampleLambdaListener();
            }
            measurements.add(execute());
        }

        System.out.println("Average lambda listeners time: " + (int) measurements.stream().mapToInt(Integer::intValue).average().orElse(0) + "ms");

        measurements.clear();
        EventBus.BUS.unregisterEvent(DummyEvent.class);

        // Method listeners
        for (int c = 0; c < tests; c++) {
            for (int i = 0; i < Settings.MAX_LISTENERS; i++) {
                EventClassScanner.scan(new ExampleMethodListener());
            }
            measurements.add(execute());
        }

        System.out.println("Average method listeners time: " + (int) measurements.stream().mapToInt(Integer::intValue).average().orElse(0) + "ms");

        measurements.clear();
        EventBus.BUS.unregisterEvent(DummyEvent.class);

        // Module listeners
        for (int c = 0; c < tests; c++) {
            for (int i = 0; i < Settings.MAX_LISTENERS; i++) {
                new ExampleModule();
            }
            measurements.add(execute());
        }

        System.out.println("Average class listener time: " + (int) measurements.stream().mapToInt(Integer::intValue).average().orElse(0) + "ms");

        EventBus.BUS.unregisterEvent(DummyEvent.class);
        new ExampleLambdaListener();

        if (EventBus.BUS.getListeners(DummyEvent.class).stream().filter(Objects::nonNull).count() != 1) {
            throw new RuntimeException("Error: listeners count has to be 1");
        }

        System.out.println("DummyEvent count: " + DummyEvent.getCount());
    }

    private static int execute() {
        long start = System.currentTimeMillis();
        EventBus.BUS.updateAll();

        for (int i = 0; i < eventsPostCounter; i++) {
            EventBus.BUS.post(new DummyEvent());
        }

        EventBus.BUS.unregisterEvent(DummyEvent.class);
        int timeValue = (int) (System.currentTimeMillis() - start);
        EventBus.BUS.resetUpdateStatus();
        return timeValue;
    }
}
