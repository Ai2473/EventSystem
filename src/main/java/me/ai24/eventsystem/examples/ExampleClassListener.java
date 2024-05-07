package me.ai24.eventsystem.examples;

import me.ai24.eventsystem.eventsystem.listeners.ClassListener;

public class ExampleClassListener<M> extends ClassListener<M, DummyEvent> {

    public ExampleClassListener(M module) {
        super(module, DummyEvent.class);
    }

    @Override
    public void invoke(DummyEvent event) {
        event.test();
    }
}
