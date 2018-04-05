package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.util.CollectionUtil;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;

//@@author adityaa1998
/**
 * Edits the details of an existing issue on Github.
 */
public class EditIssueCommand extends Command {
    public static final String COMMAND_WORD = "editissue";
    public static final String COMMAND_ALIAS = "edI";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + "INDEX "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_ASSIGNEES + "ASSIGNEES] "
            + "[" + PREFIX_MILESTONE + "MILESTONE] "
            + "[" + PREFIX_BODY + "BODY] "
            + "[" + PREFIX_LABEL + "LABEL]...";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of an existing issue "
            + "by the index number used in the issue listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_ASSIGNEES + "ASSIGNEES] "
            + "[" + PREFIX_MILESTONE + "MILESTONE] "
            + "[" + PREFIX_BODY + "BODY] "
            + "[" + PREFIX_LABEL + "LABEL]..."
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 5 "
            + PREFIX_TITLE + "Make a new attribute "
            + PREFIX_MILESTONE + "v1.3";

    public static final String MESSAGE_EDIT_ISSUE_SUCCESS = "Issue #%d was successfully edited.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_ISSUE_INVALID = "Issue doesn't exist, enter correct index";

    private final String repoName = new String("AdityaA1998/samplerepo-pr-practice");
    private final String userLogin = new String("anminkang");
    private final String userAuthentication = new String("aditya2018");

    private final Index index;
    private final EditIssueCommand.EditIssueDescriptor editIssueDescriptor;

    private Issue issueToEdit;
    private Issue editedIssue;

    /**
     * @param index of the issue on github that is to be edited
     * @param editIssueDescriptor details to edit the issue with
     */
    public EditIssueCommand(Index index, EditIssueCommand.EditIssueDescriptor editIssueDescriptor)
            throws CommandException, IOException {
        requireNonNull(index);
        requireNonNull(editIssueDescriptor);

        this.index = index;
        this.editIssueDescriptor = new EditIssueCommand.EditIssueDescriptor(editIssueDescriptor);
        preprocess();
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.updateIssue(index, editedIssue);
        } catch (IOException io) {
            throw  new CommandException(io.getLocalizedMessage());
        }
        return new CommandResult(String.format(MESSAGE_EDIT_ISSUE_SUCCESS, index.getOneBased()));
    }

    /**
     * Preprocess data for existing issue
     * @throws CommandException is thrown when invalid issue index is used
     * @throws IOException when the authentication fails
     */
    private void preprocess() throws CommandException, IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        GHRepository repository = github.getRepository(repoName);
        GHIssue issue;
        try {
            issue = repository.getIssue(index.getOneBased());
        } catch (IOException ie) {
            throw new CommandException(MESSAGE_ISSUE_INVALID);
        }
        List<GHUser> gitAssigneeList = issue.getAssignees();
        ArrayList<GHLabel> gitLabelsList = new ArrayList<>(issue.getLabels());
        List<Assignees> assigneesList = new ArrayList<>();
        List<Labels> labelsList = new ArrayList<>();
        Milestone existingMilestone = null;
        Body existingBody = new Body("");

        if (issue.getMilestone() == null) {
            existingMilestone = null;
        } else {
            existingMilestone = new Milestone(issue.getMilestone().getTitle());
        }

        for (int i = 0; i < gitAssigneeList.size(); i++) {
            assigneesList.add(new Assignees(gitAssigneeList.get(i).getLogin()));
        }

        for (int i = 0; i < labelsList.size(); i++) {
            labelsList.add(new Labels(gitLabelsList.get(i).getName()));
        }

        issueToEdit = new Issue(new Title(issue.getTitle()), assigneesList, existingMilestone,
                existingBody, labelsList);
        editedIssue = createEditedIssue(issueToEdit, editIssueDescriptor);

    }
    /**
     * Creates and returns a {@code Issue} with the details of {@code issueToEdit}
     * edited with {@code editIssueDescriptor}.
     */
    private static Issue createEditedIssue(Issue issueToEdit,
                                           EditIssueCommand.EditIssueDescriptor editIssueDescriptor) {
        assert issueToEdit != null;

        Title updatedTitle = editIssueDescriptor.getTitle().orElse(issueToEdit.getTitle());
        Set<Assignees> updatedAssignees = editIssueDescriptor.getAssignees()
                .orElse(new HashSet<>(issueToEdit.getAssignees()));
        Milestone updatedMilestone = editIssueDescriptor.getMilestone().orElse(issueToEdit.getMilestone());
        Body updatedBody = editIssueDescriptor.getBody().orElse(issueToEdit.getBody());
        Set<Labels> updatedLabels = editIssueDescriptor.getLabels().orElse(new HashSet<>(issueToEdit.getLabelsList()));

        List<Assignees> updatedAssigneesList = new ArrayList<>(updatedAssignees);
        List<Labels> updatedLabelsList = new ArrayList<>(updatedLabels);

        return new Issue(updatedTitle, updatedAssigneesList, updatedMilestone, updatedBody, updatedLabelsList);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIssueCommand)) {
            return false;
        }

        // state check
        EditIssueCommand e = (EditIssueCommand) other;
        return index.equals(e.index)
                && editIssueDescriptor.equals(e.editIssueDescriptor)
                && Objects.equals(issueToEdit, e.issueToEdit);
    }

    /**
     * Stores the details to edit the issue with. Each non-empty field value will replace the
     * corresponding field value of the Issue.
     */
    public static class EditIssueDescriptor {
        private Title title;
        private Set<Assignees> assignees;
        private Milestone milestone;
        private Body body;
        private Set<Labels> labels;

        public EditIssueDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code labels} is used internally.
         */
        public EditIssueDescriptor(EditIssueCommand.EditIssueDescriptor toCopy) {
            setTitle(toCopy.title);
            setAssignees(toCopy.assignees);
            setMilestone(toCopy.milestone);
            setBody(toCopy.body);
            setLabels(toCopy.labels);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.title, this.assignees, this.milestone, this.body,
                    this.labels);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        /**
         * Sets {@code assignees} to this object's {@code assignees}.
         * A defensive copy of {@code assignees} is used internally.
         */
        public void setAssignees(Set<Assignees> assignees) {
            this.assignees = (assignees != null) ? new HashSet<>(assignees) : null;
        }

        /**
         * Returns an unmodifiable assignees set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code labels} is null.
         */
        public Optional<Set<Assignees>> getAssignees() {
            return (assignees != null) ? Optional.of(Collections.unmodifiableSet(assignees)) : Optional.empty();
        }

        public void setMilestone(Milestone milestone) {
            this.milestone = milestone;
        }

        public Optional<Milestone> getMilestone() {
            return Optional.ofNullable(milestone);
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public Optional<Body> getBody() {
            return Optional.ofNullable(body);
        }

        /**
         * Sets {@code labels} to this object's {@code labels}.
         * A defensive copy of {@code labels} is used internally.
         */
        public void setLabels(Set<Labels> labels) {
            this.labels = (labels != null) ? new HashSet<>(labels) : null;
        }

        /**
         * Returns an unmodifiable labels set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code labels} is null.
         */
        public Optional<Set<Labels>> getLabels() {
            return (labels != null) ? Optional.of(Collections.unmodifiableSet(labels)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditIssueCommand.EditIssueDescriptor e = (EditIssueCommand.EditIssueDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getAssignees().equals(e.getAssignees())
                    && getMilestone().equals(e.getMilestone())
                    && getBody().equals(e.getBody())
                    && getLabels().equals(e.getLabels());
        }
    }
}
