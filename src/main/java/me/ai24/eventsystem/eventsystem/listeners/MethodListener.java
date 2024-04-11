package me.ai24.eventsystem.eventsystem.listeners;

import me.ai24.eventsystem.eventsystem.util.EventListener;

import java.lang.reflect.Method;

public class MethodListener<T> extends AbstractListener<T> {
    private final Object object;
    private final Method method;

    /**
     * Listener for a class method
     * @param method The method to be invoked
     * @param object The instance of the object where the method is located
     */
    @SuppressWarnings("unchecked")
    public MethodListener(Method method, T object) {
        super((Class<T>) method.getParameterTypes()[0], method.getAnnotation(EventListener.class).priority());
        this.object = object;
        this.method = method;
    }

    @Override
    public void invoke(T event) {
        try {
            method.invoke(object, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
