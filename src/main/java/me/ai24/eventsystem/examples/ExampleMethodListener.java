package me.ai24.eventsystem.examples;

import me.ai24.eventsystem.eventsystem.util.EventListener;

public class ExampleMethodListener {

    @EventListener
    public void onDummyEvent(DummyEvent event) {
        event.test();
    }
}
