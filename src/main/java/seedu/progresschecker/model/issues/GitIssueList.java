package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueBuilder;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHMilestone;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.util.CollectionUtil;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.person.Person;

//@@author adityaa1998
/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class GitIssueList implements Iterable<Issue> {

    private final ObservableList<Issue> internalList = FXCollections.observableArrayList();
    private String repoName;
    private String userLogin;
    private String userAuthentication;
    private GitHub github;
    private GHRepository repository;
    private GHIssueBuilder issueBuilder;
    private GHIssue issue;
    private GHIssue toEdit;

    /**
     * Initialises github credentials
     */
    public void initialiseCredentials(GitDetails gitdetails) throws CommandException {
        repoName = gitdetails.getRepository().toString();
        userLogin = gitdetails.getUsername().toString();
        userAuthentication = gitdetails.getPasscode().toString();
        authoriseGithub();
    }

    /**
     * Authorises with github
     */
    private void authoriseGithub () throws CommandException {
        try {
            github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        } catch (IOException ie) {
            throw new CommandException("Enter correct username and password");
        }
        try {
            repository = github.getRepository(repoName);
        } catch (IOException ie) {
            throw new CommandException("Enter correct repository name");
        }
    }

    /**
     * Creates an issue on github
     *
     * @throws IOException if there is any problem creating an issue on github;
     */
    public void createIssue(Issue toAdd) throws IOException, CommandException {

        checkGitAuthentication();
        issueBuilder = repository.createIssue(toAdd.getTitle().toString());
        issueBuilder.body(toAdd.getBody().toString());

        List<Assignees> assigneesList = toAdd.getAssignees();
        List<Labels> labelsList = toAdd.getLabelsList();

        ArrayList<GHUser> listOfUsers = new ArrayList<>();
        ArrayList<String> listOfLabels = new ArrayList<>();
        MilestoneMap obj = new MilestoneMap();
        HashMap<Milestone, Integer> getMilestone = obj.getMilestoneMap();

        for (int ct = 0; ct < assigneesList.size(); ct++) {
            listOfUsers.add(github.getUser(assigneesList.get(ct).toString()));
        }

        for (int ct = 0; ct < labelsList.size(); ct++) {
            listOfLabels.add(labelsList.get(ct).toString());
        }

        GHIssue createdIssue = issueBuilder.create();
        if (toAdd.getMilestone() != null) {
            GHMilestone check = repository.getMilestone(getMilestone.get(toAdd.getMilestone()));
            createdIssue.setMilestone(check);
        }
        createdIssue.setAssignees(listOfUsers);
        createdIssue.setLabels(listOfLabels.toArray(new String[0]));
    }

    /**
     * Reopens an issue on github
     */
    public void reopenIssue(Index index) throws IOException, CommandException {

        checkGitAuthentication();
        issue = repository.getIssue(index.getOneBased());
        if (issue.getState() == GHIssueState.OPEN) {
            throw new CommandException("Issue #" + index.getOneBased() + " is already open");
        }
        issue.reopen();
    }

    /**
     * Closes an issue on github
     */
    public void closeIssue(Index index) throws IOException, CommandException {

        checkGitAuthentication();
        issue = repository.getIssue(index.getOneBased());
        if (issue.getState() == GHIssueState.CLOSED) {
            throw new CommandException("Issue #" + index.getOneBased() + " is already closed");
        }
        issue.close();
    }

    /**
     * Check if the github credentials are authorised
     */
    private void checkGitAuthentication() throws CommandException {
        if (github == null) {
            throw new CommandException("Github not authenticated. Use logn command to authenticate github first");
        }
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws IOException if the replacement is equivalent to another existing person in the list.
     */
    public void setIssue(Index index, Issue editedIssue)
            throws IOException {
        requireNonNull(editedIssue);
        toEdit = repository.getIssue(index.getOneBased());

        List<Assignees> assigneesList = editedIssue.getAssignees();
        List<Labels> labelsList = editedIssue.getLabelsList();

        ArrayList<GHUser> listOfUsers = new ArrayList<>();
        ArrayList<String> listOfLabels = new ArrayList<>();
        MilestoneMap obj = new MilestoneMap();
        HashMap<Milestone, Integer> getMilestone = obj.getMilestoneMap();

        for (Assignees assignee : assigneesList) {
            listOfUsers.add(github.getUser(assignee.toString()));
        }

        for (Labels label : labelsList) {
            listOfLabels.add(label.toString());
        }

        if (editedIssue.getMilestone() != null) {
            GHMilestone check = repository.getMilestone(getMilestone.get(editedIssue.getMilestone()));
            toEdit.setMilestone(check);
        }
        toEdit.setTitle(editedIssue.getTitle().toString());
        toEdit.setBody(editedIssue.getBody().toString());
        toEdit.setAssignees(listOfUsers);
        toEdit.setLabels(listOfLabels.toArray(new String[0]));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Issue> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Issue> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GitIssueList // instanceof handles nulls
                && this.internalList.equals(((GitIssueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

