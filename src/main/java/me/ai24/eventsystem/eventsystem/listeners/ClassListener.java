package me.ai24.eventsystem.eventsystem.listeners;

public abstract class ClassListener<C, T> extends AbstractListener<T> {
    protected final C ownerInstance;

    public ClassListener(C ownerInstance, Class<T> targetClass, int priority) {
        super(targetClass, priority);
        this.ownerInstance = ownerInstance;
    }

    public ClassListener(C ownerInstance, Class<T> targetClass) {
        this(ownerInstance, targetClass, 0);
    }
}
