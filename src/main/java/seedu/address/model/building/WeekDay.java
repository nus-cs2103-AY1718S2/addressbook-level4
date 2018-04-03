package seedu.address.model.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.model.building.exceptions.InvalidWeekDayScheduleException;

//@@author Caijun7
/**
 * Represents a WeekDay schedule of a Room in National University of Singapore.
 */
public class WeekDay {

    public static final int NUMBER_OF_CLASSES = 13;

    private static final Logger logger = LogsCenter.getLogger(WeekDay.class);

    private static final int START_TIME = 800;
    private static final int END_TIME = 2000;
    private static final int FOUR_DIGIT_24_HOUR_FORMAT = 1000;
    private static final int ONE_HOUR_IN_24_HOUR_FORMAT = 100;

    private HashMap<String, String> weekDaySchedule = null;
    private String weekday;
    private String roomName;

    public HashMap<String, String> getWeekDaySchedule() {
        return weekDaySchedule;
    }

    public void setWeekDaySchedule(HashMap<String, String> weekDaySchedule) {
        this.weekDaySchedule = weekDaySchedule;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the weekday schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        try {
            isValidWeekDaySchedule();
            ArrayList<String> weekDayScheduleList = new ArrayList<>();
            int time = START_TIME;
            while (time <= END_TIME) {
                String timeString = "" + time;
                if (time < FOUR_DIGIT_24_HOUR_FORMAT) {
                    timeString = "0" + time;
                }
                String roomStatus = this.weekDaySchedule.get(timeString);

                weekDayScheduleList.add(roomStatus);
                time = incrementOneHour(time);
            }
            return weekDayScheduleList;
        } catch (InvalidWeekDayScheduleException e) {
            throw new CorruptedVenueInformationException();
        }
    }

    /**
     * Checks for null instance in week day schedule hash map
     *
     * @throws InvalidWeekDayScheduleException if the weekday schedule format is not as expected.
     */
    public boolean isValidWeekDaySchedule() throws InvalidWeekDayScheduleException {
        if (weekDaySchedule == null) {
            logger.warning("Weekday Schedule is null, venueinformation.json file is corrupted.");
            throw new InvalidWeekDayScheduleException();
        }
        int time = START_TIME;
        while (time <= END_TIME) {
            String timeString = "" + time;
            if (time < FOUR_DIGIT_24_HOUR_FORMAT) {
                timeString = "0" + time;
            }
            String roomStatus = this.weekDaySchedule.get(timeString);
            if (roomStatus == null) {
                logger.warning("Weekday Schedule contains some null data, venueinformation.json file is corrupted.");
                throw new InvalidWeekDayScheduleException();
            }
            if (!"vacant".equals(roomStatus) && !"occupied".equals(roomStatus)) {
                logger.warning("Weekday Schedule contains some incorrect data, "
                        + "venueinformation.json file is corrupted.");
                throw new InvalidWeekDayScheduleException();
            }
            time = incrementOneHour(time);
        }
        return true;
    }

    /**
     * Increments the time in 24 hour format by one hour
     */
    private int incrementOneHour(int time) {
        int timeAfterHalfHour = time + ONE_HOUR_IN_24_HOUR_FORMAT;
        return timeAfterHalfHour;
    }

    @Override
    public String toString() {
        return roomName + " " + weekday + " Schedule";
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
