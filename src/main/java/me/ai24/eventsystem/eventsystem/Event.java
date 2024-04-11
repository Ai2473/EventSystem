package me.ai24.eventsystem.eventsystem;

public class Event {
    private final int index = EventBus.BUS.getListenerIndex(this.getClass());

    public int getEventIndex() {
        return index;
    }
}
