package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.timetable.Lesson;

//@@author AzuraAiR
/**
 * Represents a slot in the timetable
 */
public class TimetableSlot {

    private ArrayList<Lesson> lessons = new ArrayList<>();

    /**
     * Fills up the slot with the given lesson
     * @param lesson
     */
    public void addLessonToSlot(Lesson lesson) {
        requireNonNull(lesson);

        this.lessons.add(lesson);
    }

    public Lesson getLesson() {
        return concat(lessons);
    }

    @Override
    public String toString() {
        if (lessons.size() == 0) {
            return "";
        }
        String resultString = new String();
        for (Lesson lesson: lessons) {
            resultString += lesson + " \n";
        }
        return resultString;
    }

    /**
     * Merges all the lessons in this time slot.
     * @return a merged Lesson
     */
    private Lesson concat(ArrayList<Lesson> lessons) {
        String concatModCode = new String();
        String concatClassNo = new String();
        String concatLessonType = new String();

        for (Lesson lesson: lessons) {
            concatModCode += lesson.getModuleCode() + " ";
            concatClassNo += lesson.getClassNo() + " ";
            concatLessonType += lesson.getLessonType() + " ";
        }
        Lesson lesson = lessons.get(0);
        Lesson concatLesson = new Lesson(concatModCode, concatClassNo, concatLessonType, lesson.getWeekType(),
                lesson.getDay(), lesson.getStartTime(), lesson.getEndTime());
        return concatLesson;
    }
}
