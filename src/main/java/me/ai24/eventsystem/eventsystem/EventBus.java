package me.ai24.eventsystem.eventsystem;

import me.ai24.eventsystem.eventsystem.listeners.AbstractListener;
import me.ai24.eventsystem.eventsystem.util.IEventBus;
import me.ai24.eventsystem.eventsystem.util.BusListManager;

import java.util.Arrays;
import java.util.List;

/**
 * The event bus interface.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class EventBus implements IEventBus {

    /**
     * The event bus instance.
     */
    public static final EventBus BUS = new EventBus();

    private final BusListManager busListManager = new BusListManager();

    private EventBus() {}

    /**
     * Posts an event to the event bus.
     * @param event the event to post
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void post(T event) {
        AbstractListener[] listeners;
        int eventCount;
        boolean cancellable = event instanceof CancellableEvent;

        if (event instanceof Event e) {
            listeners = busListManager.getListeners(e.getEventIndex());
            eventCount = busListManager.getListenersCount(e.getEventIndex());
        } else {
            Class<?> eventClass = event.getClass();
            listeners = busListManager.getListeners(eventClass);
            eventCount = busListManager.getListenersCount(eventClass);
        }

        for (int i = 0; i < eventCount && !(cancellable && ((CancellableEvent) event).isCancelled()); i++) {
            listeners[i].invoke(event);
        }
    }

    /**
     * Gets the listener index for a class.
     * @param clazz the class to get the listener index for
     * @return the listener index
     */
    public int getListenerIndex(Class<?> clazz) {
        return busListManager.getOrCreateEvent(clazz);
    }

    /**
     * Registers a listener to the event bus.
     * @param listener the listener to register
     */
    public void register(AbstractListener<?> listener) {
        busListManager.addListener(listener);
    }

    /**
     * Unregisters a listener from the event bus.
     * @param listener the listener to unregister
     */
    public void unregister(AbstractListener<?> listener) {
        busListManager.removeListener(listener);
    }

    /**
     * Unregisters all listeners with a certain event
     * @param event the event to unregister
     */
    public void unregisterEvent(Class<?> event) {
        busListManager.removeEvent(event);
    }

    /**
     * Checks if a listener is registered.
     * @param listener the listener to check
     * @return true if the listener is registered, false otherwise
     */
    public boolean isListenerRegistered(AbstractListener<?> listener) {
        return busListManager.isListenerRegistered(listener);
    }

    /**
     * Gets all listeners for a class.
     * @param clazz the class to get listeners for
     * @return a list of listeners
     */
    public List<AbstractListener<?>> getListeners(Class<?> clazz) {
        return Arrays.asList(busListManager.getListeners(clazz));
    }

    /**
     * Updates the priority of all listeners.
     * This should only be called after all initial listeners have been registered.
     * This method MUST be called.
     */
    public void updateAll() {
        busListManager.updateAll();
    }

    /**
     * Resets the update status of the event bus.
     */
    public void resetUpdateStatus() {
        busListManager.resetUpdate();
    }
}
