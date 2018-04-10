package seedu.address.model.listevent;

import com.google.api.client.util.DateTime;

/**
 * Represents a event that is to be loaded in to do list window.
 */
public class ListEvent {

    private String title;
    private String location;
    private DateTime startTime;

    public ListEvent(String title, String location, DateTime startTime) {
        this.setTitle(title);
        this.setLocation(location);
        this.setStartTime(startTime);
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

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        String toReturn = "";
        if (title != null) {
            toReturn += "EVENT: " + title + "  ||  ";
        }
        if (location != null) {
            toReturn += " LOCATION: " + location + "  ||  ";
        }
        if (startTime != null) {
            toReturn += " START AT: "
                    + startTime.toString().substring(0, startTime.toString()
                    .lastIndexOf("+")).replaceAll("T", " ");
        }
        return toReturn;
    }
}
