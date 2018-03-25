package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.Student;

/**
 * Indicates a request to show the student's dashboard
 */
public class ShowStudentDashboardEvent extends BaseEvent {

    private final Student targetStudent;

    public ShowStudentDashboardEvent(Student targetStudent) {
        this.targetStudent = targetStudent;
    }

    public Student getTargetStudent() {
        return targetStudent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
