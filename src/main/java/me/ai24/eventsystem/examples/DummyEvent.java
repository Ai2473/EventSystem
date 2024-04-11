package me.ai24.eventsystem.examples;

import me.ai24.eventsystem.eventsystem.Event;

public class DummyEvent extends Event {
    private static long count = 0;

    public void test() {
        count++;
    }

    public static long getCount() {
        return count;
    }
}
