package me.ai24.eventsystem.eventsystem.util;

import me.ai24.eventsystem.eventsystem.listeners.MethodListener;
import me.ai24.eventsystem.eventsystem.EventBus;

import java.lang.reflect.Method;


public class EventClassScanner {

    /**
     * Scan the object for event handlers
     * @param object the instance of the object to scan
     */
    public static void scan(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (isValid(method)) {
                EventBus.BUS.register(new MethodListener<>(method, object));
            }
        }
    }

    /**
     * Check if the method is valid for event handling
     * <a href="https://github.com/MeteorDevelopment/orbit/blob/91a8efc25126933285e90d6a819a844f5e8553d7/src/main/java/meteordevelopment/orbit/EventBus.java#L173">Method from orbit</a>
     * @param method the method to check
     * @return true if the method is valid
     */
    private static boolean isValid(Method method) {
        if (!method.isAnnotationPresent(EventListener.class)) return false;
        if (method.getReturnType() != void.class) return false;
        if (method.getParameterCount() != 1) return false;

        return !method.getParameters()[0].getType().isPrimitive();
    }
}
