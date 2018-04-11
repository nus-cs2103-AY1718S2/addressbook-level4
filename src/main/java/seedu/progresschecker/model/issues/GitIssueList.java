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
import org.kohsuke.github.GHLabel;
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
    public void initialiseCredentials(GitDetails gitdetails) throws CommandException, IOException {
        repoName = gitdetails.getRepository().toString();
        userLogin = gitdetails.getUsername().toString();
        userAuthentication = gitdetails.getPasscode().toString();
        authoriseGithub();
    }

    /**
     * Authorises with github
     */
    private void authoriseGithub () throws CommandException, IOException {
        if (github != null) {
            throw new CommandException("You have already logged in as " + userLogin + ". Please logout first.");
        }
        try {
            github = GitHub.connectUsingPassword(userLogin, userAuthentication);
            if (!github.isCredentialValid()) {
                github = null;
                throw new IOException();
            }
        } catch (IOException ie) {
            throw new CommandException("Enter correct username and password");
        }
        try {
            repository = github.getRepository(repoName);
        } catch (IOException ie) {
            throw new CommandException("Enter correct repository name");
        }
        updateInternalList();
    }

    /**
     * Updates the internal list by fetching data from github
     */
    private void updateInternalList() throws IOException {
        internalList.remove(0, internalList.size());
        List<GHIssue> gitIssues = repository.getIssues(GHIssueState.OPEN);
        for (GHIssue issueOnGit : gitIssues) {
            Issue toBeAdded = convertToIssue(issueOnGit);
            internalList.add(toBeAdded);
        }
    }

    /**
     * Converts GHIssue to issue
     */
    private Issue convertToIssue(GHIssue i) throws IOException {

        List<GHUser> gitAssigneeList = i.getAssignees();
        ArrayList<GHLabel> gitLabelsList = new ArrayList<>(i.getLabels());
        List<Assignees> assigneesList = new ArrayList<>();
        List<Labels> labelsList = new ArrayList<>();
        Milestone existingMilestone = null;
        Body existingBody = new Body(i.getBody());
        Title title = new Title(i.getTitle());
        Issue issue;

        if (i.getMilestone() == null) {
            existingMilestone = new Milestone("");
        } else {
            existingMilestone = new Milestone(i.getMilestone().getTitle());
        }

        for (GHUser assignee : gitAssigneeList) {
            assigneesList.add(new Assignees(assignee.getLogin()));
        }

        for (GHLabel label : gitLabelsList) {
            labelsList.add(new Labels(label.getName()));
        }

        issue =  new Issue(title, assigneesList, existingMilestone,
                existingBody, labelsList);

        issue.setIssueIndex(i.getNumber());

        return issue;
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
        obj.setRepository(getRepository());
        HashMap<String, GHMilestone> milestoneMap = obj.getMilestoneMap();
        GHMilestone check = null;

        for (int ct = 0; ct < assigneesList.size(); ct++) {
            listOfUsers.add(github.getUser(assigneesList.get(ct).toString()));
        }

        for (int ct = 0; ct < labelsList.size(); ct++) {
            listOfLabels.add(labelsList.get(ct).toString());
        }

        if (toAdd.getMilestone() != null) {
            if (milestoneMap.get(toAdd.getMilestone().toString()) == null) {
                throw new CommandException("Milestone doesn't exist");
            } else {
                check = milestoneMap.get(toAdd.getMilestone().toString());
            }
        }
        GHIssue createdIssue = issueBuilder.create();
        if (check != null) {
            createdIssue.setMilestone(check);
        }
        createdIssue.setAssignees(listOfUsers);
        createdIssue.setLabels(listOfLabels.toArray(new String[0]));
        updateInternalList();
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
        updateInternalList();
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
        updateInternalList();
    }
    /**
     * Authorises with github
     */
    public void clearCredentials() throws CommandException {
        if (github == null) {
            throw new CommandException("No one has logged into github at the moment");
        } else {
            internalList.remove(0, internalList.size());
            github = null;
        }
    }

    /**
     * Check if the github credentials are authorised
     */
    private void checkGitAuthentication() throws CommandException {
        if (github == null) {
            throw new CommandException("Github not authenticated. "
                    + "Use 'gitlogin' command to first authenticate your github account");
        }
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws IOException if the replacement is equivalent to another existing person in the list.
     */
    public void setIssue(Index index, Issue editedIssue)
            throws IOException, CommandException {
        requireNonNull(editedIssue);
        toEdit = repository.getIssue(index.getOneBased());

        List<Assignees> assigneesList = editedIssue.getAssignees();
        List<Labels> labelsList = editedIssue.getLabelsList();

        ArrayList<GHUser> listOfUsers = new ArrayList<>();
        ArrayList<String> listOfLabels = new ArrayList<>();
        MilestoneMap obj = new MilestoneMap();
        HashMap<String, GHMilestone> milestoneMap = obj.getMilestoneMap();

        for (Assignees assignee : assigneesList) {
            listOfUsers.add(github.getUser(assignee.toString()));
        }

        for (Labels label : labelsList) {
            listOfLabels.add(label.toString());
        }

        if (editedIssue.getMilestone() != null) {
            GHMilestone check = milestoneMap.get(editedIssue.getMilestone().toString());
            toEdit.setMilestone(check);
        }
        toEdit.setTitle(editedIssue.getTitle().toString());
        toEdit.setBody(editedIssue.getBody().toString());
        toEdit.setAssignees(listOfUsers);
        toEdit.setLabels(listOfLabels.toArray(new String[0]));

    }

    /**
     * Returns github object
     */
    public GitHub getGithub() {
        return github;
    }

    /**
     * Returns github repository
     */
    public GHRepository getRepository() {
        return repository;
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

