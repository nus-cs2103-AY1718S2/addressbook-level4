package seedu.address.model.person;

public class CalendarID {

    public final String value;

    public CalendarID(String calendarID) {
        this.value = calendarID;
    }

    @Override
    public String toString() {
        return value;
    }
}
