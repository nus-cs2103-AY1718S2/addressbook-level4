package seedu.progresschecker.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;
import seedu.progresschecker.model.util.SampleDataUtil;

//@@author adityaa1998
/**
 * A utility class to help with building Issue objects.
 */
public class IssueBuilder {

    public static final String DEFAULT_TITLE = "CS2103 software engneering";
    public static final String DEFAULT_ASSIGNEE = "anminkang";
    public static final String DEFAULT_BODY = "This an issue created for testing purposes";
    public static final String DEFAULT_MIILESTONE = "v1.1";
    public static final String DEFAULT_LABELS = "testing";

    private Title title;
    private List<Assignees> assignees;
    private Body body;
    private Milestone milestone;
    private List<Labels> labels;

    public IssueBuilder() {
        title = new Title(DEFAULT_TITLE);
        assignees = SampleDataUtil.getAssigneeList(DEFAULT_ASSIGNEE);
        body = new Body(DEFAULT_BODY);
        milestone = new Milestone(DEFAULT_MIILESTONE);
        labels = SampleDataUtil.getLabelsList(DEFAULT_LABELS);
    }

    /**
     * Initializes the IssueBuilder with the data of {@code issueToCopy}.
     */
    public IssueBuilder (Issue issueToCopy) {
        title = issueToCopy.getTitle();
        assignees = new ArrayList<>(issueToCopy.getAssignees());
        body = issueToCopy.getBody();
        milestone = issueToCopy.getMilestone();
        labels = new ArrayList<>(issueToCopy.getLabelsList());
    }

    /**
     * Sets the {@code Title} of the {@code Issue} that we are building.
     */
    public IssueBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code assignees} into a {@code List<Assignee>} and set it to the {@code Issues} that we are building.
     */
    public IssueBuilder withAssignees(String... assignees) {
        this.assignees = SampleDataUtil.getAssigneeList(assignees);
        return this;
    }

    /**
     * Sets the {@code Body} of the {@code Issue} that we are building.
     */
    public IssueBuilder withBody(String body) {
        this.body = new Body(body);
        return this;
    }

    /**
     * Sets the {@code Milestone} of the {@code Issue} that we are building.
     */
    public IssueBuilder withMilestone(String milestone) {
        this.milestone = new Milestone(milestone);
        return this;
    }

    /**
     * Parses the {@code labels} into a {@code List<Labels>} and set it to the {@code Issues} that we are building.
     */
    public IssueBuilder withLabels(String... labels) {
        this.labels = SampleDataUtil.getLabelsList(labels);
        return this;
    }

    public Issue build() {
        return new Issue(title, assignees, milestone, body, labels);
    }
}
