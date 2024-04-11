package me.ai24.eventsystem.examples;

import me.ai24.eventsystem.eventsystem.EventBus;
import me.ai24.eventsystem.eventsystem.listeners.LambdaListener;

import java.util.Random;

public class ExampleLambdaListener {

    public ExampleLambdaListener() {
        EventBus.BUS.register(new LambdaListener<>(DummyEvent.class, new Random().nextInt(), DummyEvent::test));
    }
}
