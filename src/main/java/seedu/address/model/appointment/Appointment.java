package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

//@@author kengsengg
/**
 * Represents an appointment in EduBuddy.
 */
public class Appointment {

    public static final String MESSAGE_APPOINTMENT_DATE_CONSTRAINTS = "Appointment date should be in DDMMYYYY format";
    public static final String MESSAGE_APPOINTMENT_START_TIME_CONSTRAINTS = "Appointment start time should be in "
            + "24 hour format";
    public static final String MESSAGE_APPOINTMENT_END_TIME_CONSTRAINTS = "Appointment end time should be in "
            + "24 hour format";
    public static final String APPOINTMENT_DATE_VALIDATION_REGEX = "^[0-9]{8}$";
    public static final String APPOINTMENT_START_TIME_VALIDATION_REGEX = "^[0-9]{4}$";
    public static final String APPOINTMENT_END_TIME_VALIDATION_REGEX = "^[0-9]{4}$";

    public final String date;
    public final String startTime;
    public final String endTime;

    public Appointment(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    /**
     * Returns true if a given string is a valid appointment date.
     */
    public static boolean isValidAppointmentDate(String date) {
        requireNonNull(date);
        return date.matches(APPOINTMENT_DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid appointment start time.
     */
    public static boolean isValidAppointmentStartTime(String startTime) {
        requireNonNull(startTime);
        return startTime.matches(APPOINTMENT_START_TIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid appointment end time.
     */
    public static boolean isValidAppointmentEndTime(String endTime) {
        requireNonNull(endTime);
        return endTime.matches(APPOINTMENT_END_TIME_VALIDATION_REGEX);
    }
}
//@@author
