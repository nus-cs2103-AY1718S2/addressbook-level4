//@@author IzHoBX
package seedu.address.model.notification;

import java.util.Calendar;

/**
 * Container for different time fields
 */
public class NotificationTime {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int seconds;

    public NotificationTime(int year, int month, int date, int hour, int minute, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;

    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * Checks if the date contained refers to today's date
     */
    public boolean isToday() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return this.year == year && this.month == month && this.date == day;
    }
}
