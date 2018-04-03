package seedu.address.model.listEvent;

import com.google.api.client.util.DateTime;

public class ListEvent {

    private String title;
    private String location;
    private DateTime endTime;

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

    public ListEvent(String title, String location, DateTime endTime) {
        this.setTitle(title);
        this.setLocation(location);
        this.setEndTime(endTime);
    }

    @Override
    public String toString() {
        return "Event: " + title + "  Location: " + location + "   End at: " + endTime.toString();
    }
}
