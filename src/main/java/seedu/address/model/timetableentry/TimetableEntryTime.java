package seedu.address.model.timetableentry;

/**
 * Container for different time fields
 */
public class TimetableEntryTime {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int seconds;

    public TimetableEntryTime(int year, int month, int date, int hour, int minute, int seconds) {
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
}
