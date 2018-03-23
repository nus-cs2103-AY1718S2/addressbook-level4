package seedu.address.model.building;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Represents a Week schedule of a Room in National University of Singapore.
 */
public class Week {

    private static final int SUNDAY = -1;
    private static final int NUMBER_OF_CLASSES = 36;

    private ArrayList<WeekDay> weekSchedule;
    private String roomName;

    /**
     * Uses private {@code Week} constructor for Jackson JSON API to instantiate an object
     */
    private Week() {
    }

    public ArrayList<WeekDay> getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(ArrayList<WeekDay> weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     */
    public ArrayList<String> getWeekDaySchedule() {
        requireNonNull(weekSchedule);
        int day = getDayOfWeek();
        if (day == SUNDAY) {
            return getNoClassSchedule();
        }
        WeekDay weekDay =  weekSchedule.get(day);
        return weekDay.getWeekDayRoomSchedule();
    }

    /**
     * Gets the current weekday, weekday starts from Monday which is 0, which is offset by 2 from calendar API
     */
    private int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day - 2;
    }

    private ArrayList<String> getNoClassSchedule() {
        ArrayList<String> noClassSchedule = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CLASSES; i++) {
            noClassSchedule.add("vacant");
        }
        return noClassSchedule;
    }

    @Override
    public String toString() {
        return roomName + "Week Schedule";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && weekSchedule.equals(((Week) other).weekSchedule)); // state check
    }

    @Override
    public int hashCode() {
        return weekSchedule.hashCode();
    }

}
