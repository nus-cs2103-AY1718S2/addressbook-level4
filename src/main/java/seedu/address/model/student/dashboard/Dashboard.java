package seedu.address.model.student.dashboard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Student's Dashboard
 */
public class Dashboard {

    private final UniqueMilestoneList milestoneList;
    private final UniqueHomeworkList homeworkList;

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard() {
        milestoneList = new UniqueMilestoneList();
        homeworkList = new UniqueHomeworkList();
    }

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard(UniqueMilestoneList milestoneList, UniqueHomeworkList homeworkList) {
        requireAllNonNull(milestoneList, homeworkList);

        this.milestoneList = milestoneList;
        this.homeworkList = homeworkList;
    }

    public UniqueMilestoneList getMilestoneList() {
        return milestoneList;
    }

    public UniqueHomeworkList getHomeworkList() {
        return homeworkList;
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
