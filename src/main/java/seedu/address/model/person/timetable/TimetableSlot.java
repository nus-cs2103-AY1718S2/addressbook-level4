package seedu.address.model.person.timetable;

import seedu.address.commons.util.timetable.Lesson;

public class TimetableSlot {

    private Lesson lesson;
    private boolean isEmpty;

    public TimetableSlot(){
        isEmpty = true;
    }

    public void addLessonToSlot(Lesson lesson){
        this.lesson = lesson;
        isEmpty = false;
    }
}
