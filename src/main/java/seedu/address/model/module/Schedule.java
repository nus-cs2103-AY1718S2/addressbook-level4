package seedu.address.model.module;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Represents a schedule for a class in a module
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Schedule {
    private String classNo;

    private String lessonType;
    private String weekText;
    private String dayText;
    private String startTime;
    private String endTime;
    private String venue;

    public Schedule() {
        this.classNo = "";
        this.lessonType = "";
        this.weekText = "";
        this.dayText = "";
        this.startTime = "";
        this.endTime = "";
        this.venue = "";
    }

    public Schedule(String classNo, String lessonType, String weekText, String dayText,
             String startTime, String endTime, String venue) {
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekText = weekText;
        this.dayText = dayText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekText() {
        return weekText;
    }

    public String getDayText() {
        return dayText;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getVenue() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getClassNo().equals(this.getClassNo())
                && otherSchedule.getLessonType().equals(this.getLessonType())
                && otherSchedule.getWeekText().equals(this.getWeekText())
                && otherSchedule.getDayText().equals(this.getDayText())
                && otherSchedule.getStartTime().equals(this.getStartTime())
                && otherSchedule.getEndTime().equals(this.getEndTime())
                && otherSchedule.getVenue().equals(this.getVenue());
    }

    @Override
    public String toString() {
        return "ClassNo: " + classNo
                + "\nLessonType: " + lessonType
                + "\nWeekText: " + weekText
                + "\nDayText: " + dayText
                + "\nStartTime: " + startTime
                + "\nEndTime: " + endTime
                + "\nVenue: " + venue + "\n";
    }
}
