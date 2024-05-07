package me.ai24.eventsystem.eventsystem.listeners;

public abstract class AbstractListener<T> {
    private static int listenerIdentifier = 0;

    private final int id = listenerIdentifier++;
    protected final Class<T> targetClass;
    protected final int priority;

    public AbstractListener(Class<T> targetClass, int priority) {
        this.targetClass = targetClass;
        this.priority = priority;
    }

    /**
     * Invokes the event
     */
    public abstract void invoke(T event);

    /**
     * Returns the class of the event
     * @return class
     */
    public Class<?> getClazz() {
        return targetClass;
    }

    /**
     * Returns the priority of the listener
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Checks if the listener is equal to another listener
     * @param obj the listener to compare
     * @return true if the listeners are equal, false otherwise
     */
    public final boolean equals(Object obj) {
        return obj instanceof AbstractListener<?> listener && listener.id == id;
    }
}
