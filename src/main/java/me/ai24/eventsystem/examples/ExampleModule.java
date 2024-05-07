package me.ai24.eventsystem.examples;

import me.ai24.eventsystem.eventsystem.EventBus;

public class ExampleModule {

    public ExampleModule() {
        EventBus.BUS.register(new ExampleClassListener<>(this));
    }
}
