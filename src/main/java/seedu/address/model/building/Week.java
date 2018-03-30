package seedu.address.model.building;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.model.building.exceptions.InvalidWeekScheduleException;

/**
 * Represents a Week schedule of a Room in National University of Singapore.
 */
public class Week {

    public static final int NUMBER_OF_DAYS = 6;
    public static final int SUNDAY = -1;

    private static final Logger logger = LogsCenter.getLogger(Week.class);

    private static final int NUMBER_OF_CLASSES = 13;

    private ArrayList<WeekDay> weekSchedule = null;
    private String roomName;
    private int weekday;

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

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        try {
            isValidWeekSchedule();
            weekday = getDayOfWeek();
            if (weekday == SUNDAY) {
                return getNoClassSchedule();
            }
            WeekDay weekDay = initializeWeekDay();
            return weekDay.retrieveWeekDayRoomSchedule();
        } catch (InvalidWeekScheduleException e) {
            throw new CorruptedVenueInformationException();
        }
    }

    /**
     * Checks for null instance in week schedule list
     *
     * @throws InvalidWeekScheduleException if the week schedule format is not as expected.
     */
    public boolean isValidWeekSchedule() throws InvalidWeekScheduleException {
        if (weekSchedule == null) {
            logger.warning("Week Schedule is null, venueinformation.json file is corrupted.");
            throw new InvalidWeekScheduleException();
        }
        if (weekSchedule.size() != NUMBER_OF_DAYS) {
            logger.warning("Week Schedule has incorrect data, venueinformation.json file is corrupted.");
            throw new InvalidWeekScheduleException();
        }
        return true;
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

    private WeekDay initializeWeekDay() {
        WeekDay weekDay = weekSchedule.get(weekday);
        weekDay.setRoomName(roomName);
        return weekDay;
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
