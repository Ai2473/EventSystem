package me.ai24.eventsystem.eventsystem.util;

import me.ai24.eventsystem.Settings;
import me.ai24.eventsystem.eventsystem.listeners.AbstractListener;

import java.util.Arrays;

/**
 * Manages the list of listeners for the event class.
 * Should not be used outside the event bus.
 */
public class BusListManager {
    private final AbstractListener<?>[][] listeners = new AbstractListener[Settings.MAX_EVENTS][Settings.MAX_LISTENERS];
    private final int[] listenersIndexes = new int[Settings.MAX_EVENTS];

    private final Class<?>[] events = new Class[Settings.MAX_EVENTS];
    private int eventIndex = 0;

    // initUpdate is used to handle the late registration of listeners
    private boolean updateFlag = false;

    // returns the index of the event class in the events array
    public int getOrCreateEvent(Class<?> clazz) {
        int index = getEventIndex(clazz);
        return (index == -1 ? createEventListener(clazz) : index);
    }

    // returns the index of the event class in the events array
    private int getEventIndex(Class<?> clazz) {
        for (int i = 0; i < eventIndex; i++) {
            if (events[i] == clazz) {
                return i;
            }
        }
        return -1;
    }

    // creates a new event listener for the given class
    private int createEventListener(Class<?> clazz) {
        events[eventIndex] = clazz;
        eventIndex++;
        return eventIndex - 1;
    }

    // given the event class, returns all the listeners for that class
    public AbstractListener<?>[] getListeners(Class<?> clazz) {
        return listeners[getOrCreateEvent(clazz)];
    }

    // given the event class index, returns all the listeners for that class. no check
    public AbstractListener<?>[] getListeners(int index) {
        return listeners[index];
    }

    /**
     * Given the event class, returns the number of listeners for that class
     * @param clazz the event class
     * @return the number of listeners
     */
    public int getListenersCount(Class<?> clazz) {
        return listenersIndexes[getOrCreateEvent(clazz)];
    }

    /**
     * Given the event class, returns the number of listeners for that class
     * @param index the event index
     * @return the number of listeners
     */
    public int getListenersCount(int index) {
        return listenersIndexes[index];
    }

    /**
     * Adds a listener to the list of listeners for the given event class
     * @param listener the listener to add
     */
    public void addListener(AbstractListener<?> listener) {
        int eventClassIndex = getOrCreateEvent(listener.getClazz());
        int listenerIndex = listenersIndexes[eventClassIndex];

        listenersIndexes[eventClassIndex]++;
        listeners[eventClassIndex][listenerIndex] = listener;

        if (updateFlag) {
            updatePriority(eventClassIndex);
        }
    }

    /**
     * Removes a listener from the list of listeners for the given event class
     * @param listener the listener to remove
     */
    public void removeListener(AbstractListener<?> listener) {
        int index = getOrCreateEvent(listener.getClazz());
        for (int i = 0; i < listenersIndexes[index]; i++) {
            if (listeners[index][i].equals(listener)) {
                listeners[index][i] = null;
                listenersIndexes[index]--;
                updatePriority(index);
                break;
            }
        }
    }

    /**
     * Checks if a listener is already registered for the given event class
     * @param listener the listener to check
     * @return true if the listener is registered, false otherwise
     */
    public boolean isListenerRegistered(AbstractListener<?> listener) {
        int index = getOrCreateEvent(listener.getClazz());
        for (int i = 0; i < listenersIndexes[index]; i++) {
            if (listeners[index][i].equals(listener)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all listeners for the given event class
     * @param event the event class to remove the listeners from
     */
    public void removeEvent(Class<?> event) {
        int index = getEventIndex(event);
        if (index == -1) return;
        for (int i = 0; i < listenersIndexes[index]; i++) {
            listeners[index][i] = null;
        }
        listenersIndexes[index] = 0;
    }

    /**
     * Sorts all the listeners for each event class by priority
     */
    public void updateAll() {
        for (int i = 0; i < eventIndex; i++) {
            updatePriority(i);
        }
        updateFlag = true;
    }

    /**
     * Resets the update flag
     */
    public void resetUpdate() {
        updateFlag = false;
    }

    /**
     * sorts the listeners for each event class by priority
     * @param eventIndex the index of the event class
     */
    private void updatePriority(int eventIndex) {
        Arrays.sort(listeners[eventIndex], (a, b) -> {
            if (a == null && b == null) return 0;
            if (a == null) return 1;
            if (b == null) return -1;
            return Integer.compare(b.getPriority(), a.getPriority());
        });
    }
}
