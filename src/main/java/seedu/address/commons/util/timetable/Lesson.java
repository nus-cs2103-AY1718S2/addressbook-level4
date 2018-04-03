package seedu.address.commons.util.timetable;

/**
 * Represents a lesson that a module has
 */
public class Lesson {

    private final String moduleCode;
    private final String classNo;
    private final String lessonType;
    private final String weekType;
    private final String day;
    private final String startTime;
    private final String endTime;

    public Lesson(String moduleCode, String classNo, String lessonType, String weekType,
                  String day, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekType = weekType;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekType() {
        return weekType;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return moduleCode + "\n" + lessonType.substring(0, 3).toUpperCase() + "[" + classNo + "]\n";
    }
}
