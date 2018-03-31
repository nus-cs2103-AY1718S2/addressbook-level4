package seedu.progresschecker.model.issues;

import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents an Issue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Issue {

    private final Title title;
    private final List<Assignees> assigneesList;
    private final Milestone milestone;
    private final Body body;
    private final List<Labels> labelsList;

    /**
     * Every field must be present and not null.
     */
    public Issue(Title title, List<Assignees> assigneesList, Milestone milestone, Body body, List<Labels> labelsList) {
        requireAllNonNull(title, assigneesList, milestone, body);
        this.title = title;
        this.assigneesList = assigneesList;
        this.milestone = milestone;
        this.body = body;
        this.labelsList = labelsList;
    }

    public Title getTitle() {
        return title;
    }

    public List<Assignees> getAssignees() {
        return assigneesList;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public Body getBody() {
        return body;
    }

    public List<Labels> getLabelsList() {
        return labelsList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.progresschecker.model.issues.Issue)) {
            return false;
        }

        seedu.progresschecker.model.issues.Issue otherIssue = (seedu.progresschecker.model.issues.Issue) other;
        return otherIssue.getTitle().equals(this.getTitle())
                && otherIssue.getAssignees().equals(this.getAssignees())
                && otherIssue.getMilestone().equals(this.getMilestone())
                && otherIssue.getBody().equals(this.getBody())
                && otherIssue.getLabelsList().equals(this.getLabelsList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, assigneesList, milestone, body, labelsList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Assignees: ")
                .append(getAssignees())
                .append(" Milestone: ")
                .append(getMilestone())
                .append(" Body: ")
                .append(getBody())
                .append(" Labels: ")
                .append(getLabelsList());
        return builder.toString();
    }

}

