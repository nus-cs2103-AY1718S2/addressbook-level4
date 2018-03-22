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

    Schedule() {
        this.classNo = "";
        this.lessonType = "";
        this.weekText = "";
        this.dayText = "";
        this.startTime = "";
        this.endTime = "";
    }

    Schedule(String classNo, String lessonType, String weekText, String dayText, String startTime, String endTime) {
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekText = weekText;
        this.dayText = dayText;
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    public String toString() {
        String toRet = "ClassNo: " + classNo
                + "\nLessonType: " + lessonType
                + "\nWeekText: " + weekText
                + "\nDayText: " + dayText
                + "\nStartTime: " + startTime
                + "\nEndTime: " + endTime + "\n";
        return toRet;
    }
}
