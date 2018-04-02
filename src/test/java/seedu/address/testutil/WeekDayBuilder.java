package seedu.address.testutil;

import java.util.HashMap;

import seedu.address.model.building.WeekDay;

//@@author Caijun7
/**
 * A utility class to help with building {@code WeekDay} objects
 */
public class WeekDayBuilder {

    public static final String DEFAULT_WEEKDAY = "Monday";
    public static final String DEFAULT_ROOMNAME = "COM2-0108";

    private HashMap<String, String> weekDaySchedule;
    private String weekday;
    private String roomName;

    public WeekDayBuilder() {
        weekday = DEFAULT_WEEKDAY;
        roomName = DEFAULT_ROOMNAME;
        weekDaySchedule = createValidWeekDaySchedule();
    }

    /**
     * Initializes the WeekDayBuilder with the data of {@code weekDayToCopy}.
     */
    public WeekDayBuilder(WeekDay weekDayToCopy) {
        weekday = weekDayToCopy.getWeekday();
        roomName = weekDayToCopy.getRoomName();
        weekDaySchedule = weekDayToCopy.getWeekDaySchedule();
    }

    /**
     * Creates a valid {@code weekDaySchedule}
     */
    private HashMap<String, String> createValidWeekDaySchedule() {
        HashMap<String, String> validWeekDaySchedule = new HashMap<>();
        validWeekDaySchedule.put("0800", "vacant");
        validWeekDaySchedule.put("0900", "vacant");
        validWeekDaySchedule.put("1000", "vacant");
        validWeekDaySchedule.put("1100", "vacant");
        validWeekDaySchedule.put("1200", "vacant");
        validWeekDaySchedule.put("1300", "vacant");
        validWeekDaySchedule.put("1400", "vacant");
        validWeekDaySchedule.put("1500", "vacant");
        validWeekDaySchedule.put("1600", "vacant");
        validWeekDaySchedule.put("1700", "vacant");
        validWeekDaySchedule.put("1800", "vacant");
        validWeekDaySchedule.put("1900", "vacant");
        validWeekDaySchedule.put("2000", "vacant");
        return validWeekDaySchedule;
    }

    /**
     * Sets the {@code weekday} of the {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withWeekDay(String weekday) {
        this.weekday = weekday;
        return this;
    }

    /**
     * Sets the {@code roomName} into a {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code weekDaySchedule} into a {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withWeekDaySchedule(HashMap<String, String> weekDaySchedule) {
        this.weekDaySchedule = weekDaySchedule;
        return this;
    }

    /**
     * Builds a {@code WeekDay} object
     */
    public WeekDay build() {
        WeekDay weekDay = new WeekDay();
        weekDay.setWeekday(weekday);
        weekDay.setRoomName(roomName);
        weekDay.setWeekDaySchedule(weekDaySchedule);
        return weekDay;
    }
}
