package me.ai24.eventsystem.eventsystem.listeners;

import java.util.function.Consumer;

public class LambdaListener<T> extends AbstractListener<T> {
    private final Consumer<T> consumer;

    /**
     * Listener for a lambda consumer
     * @param targetClass The class of the event
     * @param consumer The consumer to be invoked
     */
    public LambdaListener(Class<T> targetClass, Consumer<T> consumer) {
        this(targetClass, 0, consumer);
    }

    /**
     * Listener for a lambda consumer
     * @param targetClass The class of the event
     * @param priority The priority of the listener
     * @param consumer The consumer to be invoked
     */
    public LambdaListener(Class<T> targetClass, int priority, Consumer<T> consumer) {
        super(targetClass, priority);
        this.consumer = consumer;
    }

    @Override
    public void invoke(T event) {
        consumer.accept(event);
    }
}
