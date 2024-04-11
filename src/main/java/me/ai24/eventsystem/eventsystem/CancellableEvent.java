package me.ai24.eventsystem.eventsystem;

public class CancellableEvent extends Event {

    private boolean cancelled;

    /**
     * Cancel the event
     */
    public void cancel() {
        this.cancelled = true;
    }

    /**
     * Check if the event is cancelled
     * @return true if the event is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return this.cancelled;
    }
}
