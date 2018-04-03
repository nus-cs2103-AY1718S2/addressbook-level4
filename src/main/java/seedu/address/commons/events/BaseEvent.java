package seedu.address.commons.events;

/**
 * The base class for all listEvent classes.
 */
public abstract class BaseEvent {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     *
     * For example, the listEvent manager post method will call any posted listEvent's toString and print it in the console.
     */
    public abstract String toString();

}
