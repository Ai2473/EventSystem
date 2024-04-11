package me.ai24.eventsystem.eventsystem.util;

public interface IEventBus {

    /**
     * Register an event listener
     */
    <T> void post(T event);
}
