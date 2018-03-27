package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.Student;

/**
 *
 */

public class StudentInfoDisplayEvent extends BaseEvent {

    private final Student student;

    public StudentInfoDisplayEvent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Displaying full information for " + student.getName();
    }

    public Student getStudent() {
        return student;
    }
}
