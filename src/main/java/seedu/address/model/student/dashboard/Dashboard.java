package seedu.address.model.student.dashboard;

import static java.util.Objects.requireNonNull;

//@@author yapni
/**
 * Represents a Student's Dashboard
 */
public class Dashboard {

    private final UniqueMilestoneList milestoneList;

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard() {
        milestoneList = new UniqueMilestoneList();
    }

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard(UniqueMilestoneList milestoneList) {
        requireNonNull(milestoneList);

        this.milestoneList = milestoneList;
    }

    public UniqueMilestoneList getMilestoneList() {
        return milestoneList;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Dashboard // instanceof handles null
                && this.milestoneList.equals(((Dashboard) obj).getMilestoneList()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;

        builder.append("Milestones:\n");
        for (Milestone milestone : milestoneList) {
            builder.append(index++)
                    .append(". ")
                    .append(milestone)
                    .append("\n");
        }

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return milestoneList.hashCode();
    }
}
