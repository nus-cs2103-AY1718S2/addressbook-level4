package seedu.address.model.listevent;

import com.google.api.client.util.DateTime;

/**
 * Represents a event that is to be loaded in to do list window.
 */
public class ListEvent {

    private String title;
    private String location;
    private DateTime endTime;

    public ListEvent(String title, String location, DateTime endTime) {
        this.setTitle(title);
        this.setLocation(location);
        this.setEndTime(endTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String toReturn = "";
        if (title != null) {
            toReturn += "Event: " + title;
        }
        if (location != null) {
            toReturn += " Location: " + location;
        }
        if (endTime != null) {
            toReturn += " End at: " + endTime.toString();
        }
        return toReturn;
    }
}
