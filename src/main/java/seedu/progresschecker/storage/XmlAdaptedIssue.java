package seedu.progresschecker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedIssue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String body;
    @XmlElement(required = true)
    private String milestone;

    @XmlElement
    private List<XmlAdaptedAssignee> assignees = new ArrayList<>();
    
    @XmlElement
    private List<XmlAdaptedLabel> labelled = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedIssue() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedIssue(
            String title, String body, String milestone, 
            List<XmlAdaptedAssignee> assignees, List<XmlAdaptedLabel> labelled) {
        this.title = title;
        this.body = body;
        this.milestone = milestone;
        
        if (assignees != null) {
            this.assignees = new ArrayList<>(assignees);
        }
        if (labelled != null) {
            this.labelled = new ArrayList<>(labelled);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedIssue(Issue source) {
        title = source.getTitle().fullMessage;
        body = source.getBody().fullBody;
        milestone = source.getMilestone().fullMilestone;
        assignees = new ArrayList<>();
        for (Assignees assignee : source.getAssignees()) {
            assignees.add(new XmlAdaptedAssignee(assignee));
        }
        for (Labels label : source.getLabelsList()) {
            labelled.add(new XmlAdaptedLabel(label));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Issue toModelType() throws IllegalValueException {
        final List<Assignees> issueAssignees = new ArrayList<>();
        final List<Labels> issueLabels = new ArrayList<>();
        for (XmlAdaptedAssignee assigneeIssue : assignees) {
            issueAssignees.add(assigneeIssue.toModelType());
        }

        for (XmlAdaptedLabel labelIssue : labelled) {
            issueLabels.add(labelIssue.toModelType());
        }
        
        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(this.title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title title = new Title(this.title);
        
        final Body body = new Body(this.body);
        
        final Milestone milestone = new Milestone(this.milestone);
        
        return new Issue(title, issueAssignees, milestone, body, issueLabels);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedIssue)) {
            return false;
        }

        XmlAdaptedIssue otherIssue = (XmlAdaptedIssue) other;
        return Objects.equals(title, otherIssue.title)
                && Objects.equals(body, otherIssue.body)
                && Objects.equals(milestone, otherIssue.milestone)
                && Objects.equals(assignees, otherIssue.assignees)
                && Objects.equals(labelled, otherIssue.labelled);
    }
}
