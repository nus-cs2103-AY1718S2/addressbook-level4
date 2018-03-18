package seedu.address.model.student.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Student's Dashboard
 * Guarantees: details are present and not null, immutable.
 */
public class Dashboard {

    private final List<Milestone> milestoneList;
    private final List<Homework> homeworkList;

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard() {
        milestoneList = new ArrayList<>();
        homeworkList = new ArrayList<>();
    }

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard(List<Milestone> milestoneList, List<Homework> homeworkList) {
        this.milestoneList = milestoneList;
        this.homeworkList = homeworkList;
    }

    public List<Milestone> getMilestoneList() {
        return Collections.unmodifiableList(milestoneList);
    }

    public List<Homework> getHomeworkList() {
        return Collections.unmodifiableList(homeworkList);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Dashboard // instanceof handles null
                && this.milestoneList.equals(((Dashboard) obj).getMilestoneList())
                && this.homeworkList.equals(((Dashboard) obj).getHomeworkList()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index;

        builder.append("Milestones:\n");
        index = 1;
        for (Milestone milestone : milestoneList) {
            builder.append(index++)
                    .append(". ")
                    .append(milestone)
                    .append("\n");
        }
        builder.append("Homework List:\n");
        index = 1;
        for (Homework homework : homeworkList) {
            builder.append(index++)
                    .append(". ")
                    .append(homework)
                    .append("\n");
        }

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(milestoneList, homeworkList);
    }
}
