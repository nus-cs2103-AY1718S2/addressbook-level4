package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an event related to a person
 */
public class Event {
    protected String name;
    private String venue;
    private String startTime;
    private String endTime;

    /**
     * Default constructor, creating a blank Event.
     */
    public Event() {
        this("blank", "blank", "0000", "2359");
    }

    /**
     * Every field must be present and not null
     */
    public Event(String name, String venue, String start, String end) {
        requireAllNonNull(name, start, end);
        this.name = name;
        this.venue = venue;
        this.startTime = start;
        this.endTime = end;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
