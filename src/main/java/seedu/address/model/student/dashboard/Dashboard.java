package seedu.address.model.student.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * Represents a Student's Dashboard
 * Guarantees: details are present and not null, immutable.
 */
public class Dashboard {

    private final List<Milestone> milestoneList;
    private final List<Homework> homeworkList;
    private final Student student;

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard(Student student) {
        this.student = student;
        milestoneList = new ArrayList<>();
        homeworkList = new ArrayList<>();
    }

    public List<Milestone> getMilestoneList() {
        return milestoneList;
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Dashboard // instanceof handles null
                && this.milestoneList.equals(((Dashboard) obj).getMilestoneList())
                && this.homeworkList.equals(((Dashboard) obj).getHomeworkList())
                && this.student.equals(((Dashboard) obj).getStudent()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student: ")
                .append(student)
                .append("Milestones: ");
        milestoneList.forEach(builder::append);
        builder.append("Homework List: ");
        homeworkList.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(milestoneList, homeworkList, student);
    }
}
