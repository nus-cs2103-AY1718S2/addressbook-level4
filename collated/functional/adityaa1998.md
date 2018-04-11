# adityaa1998
###### \java\seedu\progresschecker\logic\CommandFormatListUtil.java
``` java
/**
 * Initialises and returns a list which contains different command formats
 */
public final class CommandFormatListUtil {
    private static ArrayList<String> commandFormatList;

    public static ArrayList<String> getCommandFormatList () {
        commandFormatList = new ArrayList<>();
        createCommandFormatList();
        return commandFormatList;
    }

    /**
     * Creates commandFormatList for existing commands
     */
    private static void createCommandFormatList() {
        commandFormatList.add(AddCommand.COMMAND_FORMAT);
        commandFormatList.add(ClearCommand.COMMAND_WORD);
        commandFormatList.add(DeleteCommand.COMMAND_FORMAT);
        commandFormatList.add(EditCommand.COMMAND_FORMAT);
        commandFormatList.add(ExitCommand.COMMAND_WORD);
        commandFormatList.add(FindCommand.COMMAND_FORMAT);
        commandFormatList.add(HelpCommand.COMMAND_WORD);
        commandFormatList.add(ListCommand.COMMAND_WORD);
        commandFormatList.add(RedoCommand.COMMAND_WORD);
        commandFormatList.add(SelectCommand.COMMAND_FORMAT);
        commandFormatList.add(SortCommand.COMMAND_WORD);
        commandFormatList.add(UndoCommand.COMMAND_WORD);
        commandFormatList.add(UploadCommand.COMMAND_FORMAT);
        commandFormatList.add(ViewCommand.COMMAND_FORMAT);
        commandFormatList.add(ViewTaskListCommand.COMMAND_FORMAT);
        commandFormatList.add(CreateIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(EditIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(ReopenIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(CloseIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(GitLoginCommand.COMMAND_FORMAT);
        commandFormatList.add(ThemeCommand.COMMAND_FORMAT);
        commandFormatList.add(GitLogoutCommand.COMMAND_WORD);

        //sorting the commandFormatList
        Collections.sort(commandFormatList);
    }
}
```
###### \java\seedu\progresschecker\logic\commands\CloseIssueCommand.java
``` java
/**
 * Close an issue on github
 */
public class CloseIssueCommand extends Command {

    public static final String COMMAND_WORD = "-issue";
    public static final String COMMAND_ALIAS = "cli";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " ISSUE-INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\nParameters: ISSUE_INDEX (must be a positive valid index number)"
            + "Example: \n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Issue #%1$s closed successfully";
    public static final String MESSAGE_FAILURE = "Issue wasn't closed. Enter correct index number.";

    private final Index targetIndex;

    public CloseIssueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.closeIssueOnGithub(targetIndex);
        } catch (IOException ie) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloseIssueCommand // instanceof handles nulls
                && this.targetIndex.equals(((CloseIssueCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\progresschecker\logic\commands\CreateIssueCommand.java
``` java
/**
 * Create an issue on github
 */
public class CreateIssueCommand extends Command {

    public static final String COMMAND_WORD = "+issue";
    public static final String COMMAND_ALIAS = "ci";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_BODY + "BODY "
            + PREFIX_LABEL + "LABELS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create an issue in your team organisation. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_BODY + "BODY "
            + PREFIX_LABEL + "LABELS/n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Add new create issue command "
            + PREFIX_ASSIGNEES + "johndoe "
            + PREFIX_MILESTONE + "v1.1 "
            + PREFIX_BODY + "This is a test issue "
            + PREFIX_LABEL + "bug";
    public static final String MESSAGE_SUCCESS = "Issue successfully created on Github";
    public static final String MESSAGE_FAILURE = "There is some error in the parameter or authentication";

    private final Issue toCreate;

    /**
     * Creates an CreateIssueCommand to create the specified {@code Issue}
     */
    public CreateIssueCommand(Issue issue) {
        requireNonNull(issue);
        toCreate = issue;
    }
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.createIssueOnGitHub(toCreate);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
```
###### \java\seedu\progresschecker\logic\commands\EditIssueCommand.java
``` java
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
```
###### \java\seedu\progresschecker\logic\commands\GitLoginCommand.java
``` java
/**
 * Logins into github from app for issue creation
 */
public class GitLoginCommand extends Command {

    public static final String COMMAND_WORD = "gitlogin";
    public static final String COMMAND_ALIAS = "gl";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_GIT_USERNAME + "USERNAME "
            + PREFIX_GIT_PASSCODE + "PASSCODE "
            + PREFIX_GIT_REPO + "REPOSITORY ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs into github \n"
            + "Parameters: "
            + PREFIX_GIT_USERNAME + "USERNAME "
            + PREFIX_GIT_PASSCODE + "PASSCODE "
            + PREFIX_GIT_REPO + "REPOSITORY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GIT_USERNAME + "johndoe "
            + PREFIX_GIT_PASSCODE + "dummy123 "
            + PREFIX_GIT_REPO + "CS2103/main ";
    public static final String MESSAGE_SUCCESS = "You have successfully authenticated github!";
    public static final String MESSAGE_FAILURE = "Oops? Maybe the password or the username is incorrect";

    private final GitDetails toAuthenticate;

    /**
     * Creates an GitDetails object to authenticate with github {@code GitDetails}
     */
    public GitLoginCommand(GitDetails gitDetails) {
        requireNonNull(gitDetails);
        toAuthenticate = gitDetails;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.loginGithub(toAuthenticate);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
```
###### \java\seedu\progresschecker\logic\commands\GitLogoutCommand.java
``` java
/**
 * Logs out of github
 */
public class GitLogoutCommand extends Command {

    public static final String COMMAND_WORD = "gitlogout";
    public static final String COMMAND_ALIAS = "glo";
    public static final String COMMAND_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "You have successfully logged out of github!";
    public static final String MESSAGE_FAILURE = "You are currently not logged in";

    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.logoutGithub();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
```
###### \java\seedu\progresschecker\logic\commands\ReopenIssueCommand.java
``` java
/**
 * Reopens an issue on github
 */
public class ReopenIssueCommand extends Command {

    public static final String COMMAND_WORD = "reopenissue";
    public static final String COMMAND_ALIAS = "ri";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " ISSUE-INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\nParameters: ISSUE_INDEX (must be a positive valid index number)"
            + "Example: \n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Issue #%1$s was reopened successfully";
    public static final String MESSAGE_FAILURE = "Issue wasn't reopened. Enter correct index number.";

    private final Index targetIndex;

    public ReopenIssueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.reopenIssueOnGithub(targetIndex);
        } catch (IOException ie) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReopenIssueCommand // instanceof handles nulls
                && this.targetIndex.equals(((ReopenIssueCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\progresschecker\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Issue> getFilteredIssueList() {
        return model.getFilteredIssueList();
    }

```
###### \java\seedu\progresschecker\logic\parser\CloseIssueCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CloseIssueCommand object
 */
public class CloseIssueCommandParser implements Parser<CloseIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CloseIssueCommand
     * and returns an CloseIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseIssueCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CloseIssueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));
        }
    }
}

```
###### \java\seedu\progresschecker\logic\parser\CreateIssueParser.java
``` java
/**
 * Parses input arguments and creates a new CreateIssueCommand object
 */
public class CreateIssueParser implements Parser<CreateIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateIssueCommand
     * and returns an createIssue object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateIssueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES,
                        PREFIX_MILESTONE, PREFIX_BODY, PREFIX_LABEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssueCommand.MESSAGE_USAGE));
        }

        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            Set<Assignees> assigneeSet = ParserUtil.parseAssignees(argMultimap.getAllValues(PREFIX_ASSIGNEES));
            Milestone milestone = ParserUtil.parseMilestone(argMultimap.getValue(PREFIX_MILESTONE)).orElse(null);
            Body body = ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY).orElse(""));
            Set<Labels> labelSet = ParserUtil.parseLabels(argMultimap.getAllValues(PREFIX_LABEL));

            List<Assignees> assigneesList = new ArrayList<>(assigneeSet);
            List<Labels> labelsList = new ArrayList<>(labelSet);

            Issue issue = new Issue(title, assigneesList, milestone, body, labelsList);

            return new CreateIssueCommand(issue);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\progresschecker\logic\parser\EditIssueCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditIssueCommand object
 */
public class EditIssueCommandParser implements Parser<EditIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIssueCommand
     * and returns an EditIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIssueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES, PREFIX_MILESTONE, PREFIX_BODY,
                        PREFIX_LABEL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIssueCommand.MESSAGE_USAGE));
        }

        EditIssueCommand.EditIssueDescriptor editIssueDescriptor = new EditIssueCommand.EditIssueDescriptor();
        try {
            ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).ifPresent(editIssueDescriptor::setTitle);
            parseAssigneesForEdit(argMultimap.getAllValues(PREFIX_ASSIGNEES))
                    .ifPresent(editIssueDescriptor::setAssignees);
            ParserUtil.parseMilestone(argMultimap.getValue(PREFIX_MILESTONE))
                    .ifPresent(editIssueDescriptor::setMilestone);
            ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY))
                    .ifPresent(editIssueDescriptor::setBody);
            parseLabelsForEdit(argMultimap.getAllValues(PREFIX_LABEL)).ifPresent(editIssueDescriptor::setLabels);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editIssueDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        }

        try {
            return new EditIssueCommand(index, editIssueDescriptor);
        } catch (CommandException ce) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        } catch (IOException ie) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        }
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Labels>} if {@code labels} is non-empty.
     * If {@code labels} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Labels>} containing zero labels.
     */
    private Optional<Set<Labels>> parseLabelsForEdit(Collection<String> labels) throws IllegalValueException {
        assert labels != null;

        if (labels.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> labelSet = labels.size() == 1 && labels.contains("") ? Collections.emptySet() : labels;
        return Optional.of(ParserUtil.parseLabels(labels));
    }

    /**
     * Parses {@code Collection<String> assignees} into a {@code Set<Assignees>} if {@code assignees} is non-empty.
     * If {@code assignees} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Assignees>} containing zero assignees.
     */
    private Optional<Set<Assignees>> parseAssigneesForEdit(Collection<String> assignees) throws IllegalValueException {
        assert assignees != null;

        if (assignees.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> assigneesSet = assignees.size() == 1
                && assignees.contains("") ? Collections.emptySet() : assignees;
        return Optional.of(ParserUtil.parseAssignees(assignees));
    }
}
```
###### \java\seedu\progresschecker\logic\parser\GitLoginCommandParser.java
``` java
/**
 * Parses input arguments and creates a new GitDetails object
 */
public class GitLoginCommandParser implements Parser<GitLoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GitLoginCommand
     * and returns an GitLoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GitLoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GIT_USERNAME, PREFIX_GIT_PASSCODE, PREFIX_GIT_REPO);

        if (!arePrefixesPresent(argMultimap, PREFIX_GIT_USERNAME, PREFIX_GIT_PASSCODE, PREFIX_GIT_REPO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitLoginCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseGitUsername(argMultimap.getValue(PREFIX_GIT_USERNAME)).get();
            Passcode passcode = ParserUtil.parsePasscode(argMultimap.getValue(PREFIX_GIT_PASSCODE)).get();
            Repository repository = ParserUtil.parseRepository(argMultimap.getValue(PREFIX_GIT_REPO)).get();

            GitDetails details = new GitDetails(username, passcode, repository);

            return new GitLoginCommand(details);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code title} is invalid.
     */

    public static Title parseTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Name>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String assignees} into a {@code Assignees}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Assignees parseAssignees(String assignees) {
        requireNonNull(assignees);
        String trimmedAssignees = assignees.trim();
        return new Assignees(trimmedAssignees);
    }

    /**
     * Parses {@code Collection<String> assignees} into a {@code Set<Assignees>}.
     */
    public static Set<Assignees> parseAssignees(Collection<String> assignees) throws IllegalValueException {
        requireNonNull(assignees);
        final Set<Assignees> assigneesSet = new HashSet<>();
        for (String assigneeName : assignees) {
            assigneesSet.add(parseAssignees(assigneeName));
        }
        return assigneesSet;
    }

    /**
     * Parses a {@code String labels} into a {@code Labels}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Labels parseLabels(String labels) {
        requireNonNull(labels);
        String trimmedLabels = labels.trim();
        return new Labels(trimmedLabels);
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Labels>}.
     */
    public static Set<Labels> parseLabels(Collection<String> labels) throws IllegalValueException {
        requireNonNull(labels);
        final Set<Labels> labelsSet = new HashSet<>();
        for (String labelName : labels) {
            labelsSet.add(parseLabels(labelName));
        }
        return labelsSet;
    }


    /**
     * Parses a {@code String milestone} into a {@code Milestone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code milestone} is invalid.
     */

    public static Milestone parseMilestone(String milestone) throws IllegalValueException {
        requireNonNull(milestone);
        String trimmedMilestone = milestone.trim();
        return new Milestone(trimmedMilestone);
    }

    /**
     * Parses a {@code Optional<String> milestone} into an {@code Optional<Milestone>} if {@code milestone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Milestone> parseMilestone(Optional<String> milestone) throws IllegalValueException {
        requireNonNull(milestone);
        return milestone.isPresent() ? Optional.of(parseMilestone(milestone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String body} into a {@code Body}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Body parseBody(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();
        return new Body(trimmedBody);
    }

    /**
     * Parses a {@code Optional<String> body} into an {@code Optional<Body>} if {@code body} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Body> parseBody(Optional<String> body) throws IllegalValueException {
        requireNonNull(body);
        return body.isPresent() ? Optional.of(parseBody(body.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Username parseGitUsername(String username) {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        return new Username(trimmedUsername);
    }

    /**
     Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseGitUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseGitUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String passcode} into a {@code Passcode}.
     */
    public static Passcode parsePasscode(String passcode) {
        requireNonNull(passcode);
        return new Passcode(passcode);
    }

    /**
     Parses a {@code Optional<String> Passcode} into an {@code Optional<Passcode>} if {@code passcpde} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Passcode> parsePasscode(Optional<String> passcode) throws IllegalValueException {
        requireNonNull(passcode);
        return passcode.isPresent() ? Optional.of(parsePasscode(passcode.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String repositroy} into a {@code Repository}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Repository parseRepository(String repository) {
        requireNonNull(repository);
        String trimmedRepository = repository.trim();
        return new Repository(trimmedRepository);
    }

    /**
     Parses a {@code Optional<String> Repository} into an {@code Optional<Repository>} if {@code repository} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Repository> parseRepository(Optional<String> repository) throws IllegalValueException {
        requireNonNull(repository);
        return repository.isPresent() ? Optional.of(parseRepository(repository.get())) : Optional.empty();
    }
```
###### \java\seedu\progresschecker\logic\parser\ReopenIssueCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CloseIssueCommand object
 */
public class ReopenIssueCommandParser implements Parser<ReopenIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReopenIssueCommand
     * and returns an ReopenIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReopenIssueCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReopenIssueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReopenIssueCommand.MESSAGE_USAGE));
        }
    }
}

```
###### \java\seedu\progresschecker\model\credentials\GitDetails.java
``` java
/**
 * Represents an Issue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class GitDetails {

    private final Username username;
    private final Repository repository;
    private final Passcode passcode;

    /**
     * Every field must be present and not null.
     */
    public GitDetails(Username username, Passcode passcode, Repository repository) {
        requireAllNonNull(username, repository, passcode);
        this.username = username;
        this.repository = repository;
        this.passcode = passcode;
    }

    public Username getUsername() {
        return username;
    }

    public Repository getRepository() {
        return repository;
    }

    public Passcode getPasscode() {
        return passcode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.progresschecker.model.credentials.GitDetails)) {
            return false;
        }

        seedu.progresschecker.model.credentials.GitDetails otherGitDetails =
                (seedu.progresschecker.model.credentials.GitDetails) other;
        return otherGitDetails.getUsername().equals(this.getUsername())
                && otherGitDetails.getRepository().equals(this.getRepository())
                && otherGitDetails.getPasscode().equals(this.getPasscode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, repository, passcode);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Username: ")
                .append(getUsername())
                .append(" Repository: ")
                .append(getRepository());
        return builder.toString();
    }

}

```
###### \java\seedu\progresschecker\model\credentials\Passcode.java
``` java
/**
 * Represents a github passcode
 */
public class Passcode {

    public final String passcode;

    /**
     * Constructs a {@code Passcode}.
     *
     * @param passcode A valid assignees.
     */
    public Passcode(String passcode) {
        requireNonNull(passcode);
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return passcode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.credentials.Passcode // instanceof handles nulls
                && this.passcode.equals(((Passcode) other).passcode)); // state check
    }

    @Override
    public int hashCode() {
        return passcode.hashCode();
    }

}

```
###### \java\seedu\progresschecker\model\credentials\Username.java
``` java
/**
 * Represents the username of a user on github
 */
public class Username {

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.credentials.Username // instanceof handles nulls
                && this.username.equals(((Username) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\issues\Assignees.java
``` java
/**
 * Represents all the assignees to an issue
 */
public class Assignees {

    public static final String MESSAGE_ASSIGNEES_CONSTRAINTS =
            "Assignees of the issue can be anything, but should not be blank space";

    /*
     * The first character of the Assignee must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public final String fullAssignees;

    /**
     * Constructs a {@code Assignees}.
     *
     * @param assignees A valid assignees.
     */
    public Assignees(String assignees) {
        requireNonNull(assignees);
        this.fullAssignees = assignees;
    }

    @Override
    public String toString() {
        return fullAssignees;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Assignees // instanceof handles nulls
                && this.fullAssignees.equals(((Assignees) other).fullAssignees)); // state check
    }

    @Override
    public int hashCode() {
        return fullAssignees.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\issues\Body.java
``` java
/**
 * Represents an issue's name and description
 */
public class Body {

    public final String fullBody;

    /**
     * Constructs a {@code Body}.
     *
     * @param body A valid issue description.
     */
    public Body(String body) {
        requireNonNull(body);
        this.fullBody = body;
    }

    @Override
    public String toString() {
        return fullBody;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Body // instanceof handles nulls
                && this.fullBody.equals(((Body) other).fullBody)); // state check
    }

    @Override
    public int hashCode() {
        return fullBody.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\issues\GitIssueList.java
``` java
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

```
###### \java\seedu\progresschecker\model\issues\Issue.java
``` java
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
    private int issueIndex;

    /**
     * Every field must be present and not null.
     */
    public Issue(Title title, List<Assignees> assigneesList, Milestone milestone, Body body, List<Labels> labelsList) {
        requireAllNonNull(title);
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

    public int getIssueIndex() {
        return issueIndex;
    }

    public void setIssueIndex(int issueIndex) {
        this.issueIndex = issueIndex;
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

```
###### \java\seedu\progresschecker\model\issues\Labels.java
``` java
/**
 * Represents all the Labels of an issue
 */
public class Labels {

    public final String fullLabels;

    /**
     * Constructs a {@code Labels}.
     *
     * @param labels valid labels.
     */
    public Labels(String labels) {
        requireNonNull(labels);
        this.fullLabels = labels;
    }

    @Override
    public String toString() {
        return fullLabels;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Labels // instanceof handles nulls
                && this.fullLabels.equals(((Labels) other).fullLabels)); // state check
    }

    @Override
    public int hashCode() {
        return fullLabels.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\issues\Milestone.java
``` java
/**
 * Represents a milestone for an issue
 */
public class Milestone {

    public final String fullMilestone;

    /**
     * Constructs a {@code Milestone}.
     *
     * @param milestone A valid milestone.
     */
    public Milestone(String milestone) {
        //requireNonNull(milestone);
        this.fullMilestone = milestone;
    }

    @Override
    public String toString() {
        return fullMilestone;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Milestone // instanceof handles nulls
                && this.fullMilestone.equals(((Milestone) other).fullMilestone)); // state check
    }

    @Override
    public int hashCode() {
        return fullMilestone.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\issues\MilestoneMap.java
``` java
/**
 * Initialises and returns a Hashmap of milestones
 */
public final class MilestoneMap {

    private static HashMap<String, GHMilestone> milestoneMap;

    private GHRepository repository;

    /**
     * Returns a hashmap of milestones
     */
    public HashMap<String, GHMilestone> getMilestoneMap() throws CommandException {
        milestoneMap = new HashMap<>();
        createMilestoneHashMap();
        return milestoneMap;
    }

    /**
     * creates a map with the milestone values
     */
    private void createMilestoneHashMap() {
        List<GHMilestone> milestones = repository.listMilestones(GHIssueState.ALL).asList();
        for (int i = 0; i < milestones.size(); i++) {
            milestoneMap.put(milestones.get(i).getTitle(), milestones.get(i));
        }
    }

    public void setRepository(GHRepository repo) {
        repository = repo;
    }
}
```
###### \java\seedu\progresschecker\model\issues\Title.java
``` java
/**
 * Represents an issue's name and description
 */
public class Title {

    public final String fullMessage;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid description.
     */
    public Title(String title) {
        this.fullMessage = title;
    }

    @Override
    public String toString() {
        return fullMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Title // instanceof handles nulls
                && this.fullMessage.equals(((Title) other).fullMessage)); // state check
    }

    @Override
    public int hashCode() {
        return fullMessage.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\Model.java
``` java
    /** authenticates git using password */
    void loginGithub(GitDetails gitdetails) throws IOException, CommandException;

    /** authenticates git using password */
    void logoutGithub() throws CommandException;

    /** creates an issue on github */
    void createIssueOnGitHub(Issue issue) throws IOException, CommandException;

    /** reopen issue on github */
    void reopenIssueOnGithub(Index index) throws IOException, CommandException;

    /** closes an issue issue on github */
    void closeIssueOnGithub(Index index) throws IOException, CommandException;

    /**
     * Replaces the fields in Issue {@code index} with {@code editedIssue}.
     *
     * @throws IOException if while updating the issue there is some problem in authentication
     */
    void updateIssue(Index index, Issue editedIssue) throws IOException, CommandException;

    /** Returns unmodifiable view of the filtered issue list */
    ObservableList<Issue> getFilteredIssueList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIssueList(Predicate<Issue> predicate);
```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    @Override
    public synchronized void reopenIssueOnGithub(Index index) throws IOException, CommandException {
        progressChecker.reopenIssueOnGithub(index);
        indicateProgressCheckerChanged();
    }
```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    @Override
    public void updateIssue(Index index, Issue editedIssue) throws IOException, CommandException {
        requireAllNonNull(index, editedIssue);

        progressChecker.updateIssue(index, editedIssue);
        indicateProgressCheckerChanged();
    }
```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    //=========== Filtered Issue List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Issue} backed by the internal list of
     * {@code progressChecker}
     */
    @Override
    public ObservableList<Issue> getFilteredIssueList() {
        return FXCollections.unmodifiableObservableList(filteredIssues);
    }

    @Override
    public void updateFilteredIssueList(Predicate<Issue> predicate) {
        requireNonNull(predicate);
        filteredIssues.setPredicate(predicate);
    }
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java

    //issue-level operations

    /**
     * Login to github
     *
     * @throws IOException is there is any problem in authentication
     *
     */
    public void loginGithub(GitDetails gitdetails) throws IOException, CommandException {
        issues.initialiseCredentials(gitdetails);
    }

    /**
     * Logout of github
     */
    public void logoutGithub() throws CommandException {
        issues.clearCredentials();
    }

    /**
     * Creates issue on github
     *
     * @throws IOException if theres any fault in the input values or the authentication fails due to wrong input
     */
    public void createIssueOnGitHub(Issue i) throws IOException, CommandException {
        issues.createIssue(i);
    }

    /**
     * Replaces the given issue at {@code index} from github with {@code editedPerson}.
     * reopens an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void reopenIssueOnGithub(Index index) throws IOException, CommandException {
        issues.reopenIssue(index);
    }

    /**
     * closes an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void closeIssueOnGithub(Index index) throws IOException, CommandException {
        issues.closeIssue(index);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code ProgressChecker}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws IOException if there is any problem in git authentication or parameter
     *
     */
    public void updateIssue(Index index, Issue editedIssue) throws IOException, CommandException {
        requireNonNull(editedIssue);
        issues.setIssue(index, editedIssue);
    }

```
###### \java\seedu\progresschecker\storage\XmlAdaptedIssue.java
``` java
/**
 * JAXB-friendly version of the Issue.
 */
public class XmlAdaptedIssue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = false)
    private String body;
    @XmlElement(required = false)
    private String milestone;

    @XmlElement
    private List<XmlAdaptedAssignee> assignees = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedLabel> labelled = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedIssue.
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
     * Converts a given Issue into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedIssue
     */
    public XmlAdaptedIssue(Issue source) {
        title = source.getTitle().fullMessage;
        body = source.getBody().fullBody;
        if (source.getMilestone() == null) {
            milestone = "";
        } else {
            milestone = source.getMilestone().fullMilestone;
        }
        assignees = new ArrayList<>();
        for (Assignees assignee : source.getAssignees()) {
            assignees.add(new XmlAdaptedAssignee(assignee));
        }
        for (Labels label : source.getLabelsList()) {
            labelled.add(new XmlAdaptedLabel(label));
        }
    }

    /**
     * Converts this jaxb-friendly adapted issue object into the model's Issue object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted issue
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
```
###### \java\seedu\progresschecker\ui\CommandBox.java
``` java
        //TAB case is used to auto-complete commands
        case TAB:
            keyEvent.consume();
            autocompleteCommad(commandTextField.getText());
            break;
        default:
            //dynamic search implementation
            try {
                if ((commandTextField.getText().trim().equalsIgnoreCase(CORRECT_COMMAND_WORD)
                        || isCorrectCommandWord)) {
                    isCorrectCommandWord = !commandTextField.getText().trim().isEmpty();
                    CommandResult commandResult;
                    if (keyEvent.getCode() != KeyCode.BACK_SPACE && keyEvent.getCode() != KeyCode.DELETE) {
                        commandResult = logic.execute(commandTextField.getText() + keyEvent.getText());
                    } else {
                        commandResult = logic.execute(commandTextField.getText().substring(0,
                                commandTextField.getText().length() - 1));
                    }
                    // process result of the command
                    logger.info("Result: " + commandResult.feedbackToUser);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
                }

            } catch (CommandException | ParseException e) {
                // handle command failure
                setStyleToIndicateCommandFailure();
                logger.info("Invalid command: " + commandTextField.getText());
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
```
###### \java\seedu\progresschecker\ui\IssueCard.java
``` java
/**
 * An UI component that displays information of a {@code Issue}.
 */
public class IssueCard extends UiPart<Region> {

    private static final String FXML = "IssueListCard.fxml";
    private static final String[] LABEL_COLORS = { "red", "orange", "yellow", "green", "blue", "purple" };
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Issue issue;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label body;
    @FXML
    private Label milestone;
    @FXML
    private FlowPane labelled;
    @FXML
    private FlowPane assignees;

    public IssueCard(Issue issue, int displayedIndex) {
        super(FXML);
        this.issue = issue;
        id.setText("#" + displayedIndex + " ");
        title.setText(issue.getTitle().toString());
        body.setText(issue.getBody().fullBody);
        milestone.setText(issue.getMilestone().fullMilestone);
        issue.getLabelsList().forEach(labels -> {
            Label label = new Label(labels.fullLabels);
            label.getStyleClass().add(getLabelColor(labels.fullLabels));
            labelled.getChildren().add(label);
        });
        issue.getAssignees().forEach(assignee -> {
            Label label = new Label(assignee.fullAssignees);
            label.getStyleClass().add(getLabelColor(assignee.fullAssignees));
            assignees.getChildren().add(label);
        });
    }

    /**
     * Get a deterministic label color based off label's name value.
     */
    private String getLabelColor(String labelName) {
        int index = getValueOfString(labelName) % LABEL_COLORS.length;
        return LABEL_COLORS[index];
    }

    /**
     * Adds each letter of given string into an integer.
     */
    private int getValueOfString(String labelName) {
        int sum = 0;
        for (char c : labelName.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IssueCard)) {
            return false;
        }

        // state check
        IssueCard card = (IssueCard) other;
        return id.getText().equals(card.id.getText())
                && issue.equals(card.issue);
    }
}
```
###### \java\seedu\progresschecker\ui\IssueListPanel.java
``` java
/**
 * Panel containing the issues on github.
 */
public class IssueListPanel extends UiPart<Region> {
    private static final String FXML = "IssueListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IssueListPanel.class);

    @javafx.fxml.FXML
    private ListView<IssueCard> issueListView;

    public IssueListPanel(ObservableList<Issue> issueList) {
        super(FXML);
        setConnections(issueList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Issue> issueList) {
        ObservableList<IssueCard> mappedList = EasyBind.map(
                issueList, (issue) -> new IssueCard(issue, issue.getIssueIndex()));
        issueListView.setItems(mappedList);
        issueListView.setCellFactory(listView -> new IssueListViewCell());
    }

    /**
     * Scrolls to the {@code IssueCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            issueListView.scrollTo(index);
            issueListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code IssueCard}.
     */
    class IssueListViewCell extends ListCell<IssueCard> {

        @Override
        protected void updateItem(IssueCard issue, boolean empty) {
            super.updateItem(issue, empty);

            if (empty || issue == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(issue.getRoot());
            }
        }
    }

}
```
###### \resources\view\IssueListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" prefHeight="140.0" prefWidth="380.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane prefHeight="140.0" prefWidth="380.0" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <HBox prefHeight="140.0" prefWidth="216.0">
            <children>
                <VBox alignment="CENTER_LEFT" minHeight="-Infinity" prefHeight="140.0" prefWidth="216.0">
                    <padding>
                        <Insets bottom="5" left="15" right="5" top="5" />
                    </padding>
                    <HBox alignment="CENTER_LEFT" spacing="5">
                        <Label fx:id="id" styleClass="cell_big_label">
                            <minWidth>
                                <!-- Ensures that the label text is never truncated -->
                                <Region fx:constant="USE_PREF_SIZE" />
                            </minWidth>
                        </Label>
                        <Label fx:id="title" styleClass="cell_big_label" text="\$first" />
                    </HBox>
                    <FlowPane fx:id="labelled" />
                    <GridPane prefHeight="80.0" prefWidth="332.0">
                        <columnConstraints>
                            <ColumnConstraints hgrow="SOMETIMES" maxWidth="92.0" minWidth="10.0" prefWidth="74.0" />
                            <ColumnConstraints hgrow="ALWAYS" maxWidth="300.0" minWidth="10.0" prefWidth="200.0" />
                        </columnConstraints>
                        <rowConstraints>
                            <RowConstraints maxHeight="25.0" minHeight="10.0" prefHeight="20.0" vgrow="SOMETIMES" />
                            <RowConstraints maxHeight="21.0" minHeight="0.0" prefHeight="20.0" vgrow="SOMETIMES" />
                            <RowConstraints maxHeight="23.0" minHeight="7.0" prefHeight="20.0" vgrow="SOMETIMES" />
                        </rowConstraints>
                        <children>
                            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Body: " />
                            <Label fx:id="body" styleClass="cell_small_label" text="\$body" GridPane.columnIndex="1" />
                            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Assignees: " GridPane.rowIndex="2" />
                            <FlowPane fx:id="assignees" prefHeight="25.0" prefWidth="282.0" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Milestone: " GridPane.rowIndex="1" />
                            <Label fx:id="milestone" styleClass="cell_small_label" text="\$milestone" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        </children>
                    </GridPane>
                </VBox>
            </children>
        </HBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\IssueListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="issueListView" VBox.vgrow="ALWAYS" />
</VBox>
```
