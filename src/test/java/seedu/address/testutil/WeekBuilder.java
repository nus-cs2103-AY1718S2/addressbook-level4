package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.building.Week;
import seedu.address.model.building.WeekDay;

//@@author Caijun7
/**
 * A utility class to help with building {@code Week} objects
 */
public class WeekBuilder {

    public static final String DEFAULT_ROOMNAME = "COM2-0108";
    public static final int DEFAULT_WEEKDAY = 0;

    private String roomName;
    private int weekday;
    private ArrayList<WeekDay> weekSchedule;

    public WeekBuilder() {
        roomName = DEFAULT_ROOMNAME;
        weekday = DEFAULT_WEEKDAY;
        weekSchedule = createValidWeekSchedule();
    }

    /**
     * Initializes the WeekBuilder with the data of {@code weekToCopy}.
     */
    public WeekBuilder(Week weekToCopy) {
        roomName = weekToCopy.getRoomName();
        weekSchedule = weekToCopy.getWeekSchedule();
    }

    /**
     * Creates a valid {@code weekSchedule}
     */
    private ArrayList<WeekDay> createValidWeekSchedule() {
        ArrayList<WeekDay> validWeekSchedule = new ArrayList<>();
        WeekDay validWeekDay = new WeekDayBuilder().build();
        for (int i = 0; i < Week.NUMBER_OF_DAYS; i++) {
            validWeekSchedule.add(validWeekDay);
        }
        return validWeekSchedule;
    }

    /**
     * Sets the {@code weekday} into a {@code Week} that we are building.
     */
    public WeekBuilder withDay(int day) {
        this.weekday = day;
        return this;
    }

    /**
     * Sets the {@code roomName} into a {@code Week} that we are building.
     */
    public WeekBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code weekSchedule} into a {@code Week} that we are building.
     */
    public WeekBuilder withWeekSchedule(ArrayList<WeekDay> weekSchedule) {
        this.weekSchedule = weekSchedule;
        return this;
    }

    /**
     * Builds a {@code Week} object
     */
    public Week build() {
        Week week = new Week();
        week.setWeekday(weekday);
        week.setRoomName(roomName);
        week.setWeekSchedule(weekSchedule);
        return week;
    }
}
