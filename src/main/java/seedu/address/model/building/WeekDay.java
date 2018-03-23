package seedu.address.model.building;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents a WeekDay schedule of a Room in National University of Singapore.
 */
public class WeekDay {

    private static final int START_TIME = 600;
    private static final int END_TIME = 2330;

    private HashMap<String, String> weekDaySchedule;

    private String weekDay;
    private String roomName;

    /**
     * Uses a private {@code WeekDay} constructor for Jackson JSON API to instantiate an object
     */
    private WeekDay() {
    }

    public HashMap<String, String> getWeekDaySchedule() {
        return weekDaySchedule;
    }

    public void setWeekSchedule(HashMap<String, String> weekDaySchedule) {
        this.weekDaySchedule = weekDaySchedule;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    /**
     * Represents the {@code Room}'s weekday schedule in an ArrayList
     */
    public ArrayList<String> getWeekDayRoomSchedule() {
        requireNonNull(weekDaySchedule);
        ArrayList<String> weekDaySchedule = new ArrayList<>();
        int time = START_TIME;
        while (time <= END_TIME) {
            String timeString = "" + time;
            if (time < 1000) {
                timeString = "0" + time;
            }
            String roomStatus = this.weekDaySchedule.get(timeString);

            weekDaySchedule.add(roomStatus);
            time = incrementHalfHour(time);
        }
        return weekDaySchedule;
    }

    /**
     * Increments the time in 24 hour format by 30 minutes
     */
    private int incrementHalfHour(int time) {
        time += 30;
        if (time % 100 == 60) {
            time = time - 60 + 100;
        }
        return time;
    }

    @Override
    public String toString() {
        return roomName + " " + weekDay + " Schedule";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeekDay // instanceof handles nulls
                && weekDaySchedule.equals(((WeekDay) other).weekDaySchedule)); // state check
    }

    @Override
    public int hashCode() {
        return weekDaySchedule.hashCode();
    }

}
