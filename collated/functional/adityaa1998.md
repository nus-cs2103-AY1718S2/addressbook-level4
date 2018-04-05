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
        commandFormatList.add(CreateIssue.COMMAND_FORMAT);
        commandFormatList.add(EditIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(ReopenIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(CloseIssueCommand.COMMAND_FORMAT);

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
###### \java\seedu\progresschecker\logic\commands\CreateIssue.java
``` java
/**
 * Create an issue on github
 */
public class CreateIssue extends Command {

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
     * Creates an CreateIssue to create the specified {@code Issue}
     */
    public CreateIssue(Issue issue) {
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
 * Parses input arguments and creates a new CreateIssue object
 */
public class CreateIssueParser implements Parser<CreateIssue> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateIssue
     * and returns an createIssue object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateIssue parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES,
                        PREFIX_MILESTONE, PREFIX_BODY, PREFIX_LABEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssue.MESSAGE_USAGE));
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

            return new CreateIssue(issue);
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
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
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
        if (!Milestone.isValidMilestone(trimmedMilestone)) {
            throw new IllegalValueException(Milestone.MESSAGE_MILESTONE_CONSTRAINTS);
        }
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
     * Parses a {@code Optional<String> bodu} into an {@code Optional<Body>} if {@code body} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Body> parseBody(Optional<String> body) throws IllegalValueException {
        requireNonNull(body);
        return body.isPresent() ? Optional.of(parseBody(body.get())) : Optional.empty();
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

    public static final String MESSAGE_MILESTONE_CONSTRAINTS =
            "Milestone of the issue can be anything, but should not be blank space";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MILESTONE_VALIDATION_REGEX = "[^\\s].*";

    public final String fullMilestone;

    /**
     * Constructs a {@code Milestone}.
     *
     * @param milestone A valid milestone.
     */
    public Milestone(String milestone) {
        requireNonNull(milestone);
        checkArgument(isValidMilestone(milestone), MESSAGE_MILESTONE_CONSTRAINTS);
        this.fullMilestone = milestone;
    }

    /**
     * Returns true if a given string is a issue title.
     */
    public static boolean isValidMilestone(String test) {
        return test.matches(MILESTONE_VALIDATION_REGEX);
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

    private static HashMap<Milestone, Integer> milestoneMap;

    /* Milestone Mappings */
    private static final Milestone MILESTONE_ONE = new Milestone("v1.1");
    private static final Milestone MILESTONE_TWO = new Milestone("v1.2");
    private static final Milestone MILESTONE_THREE = new Milestone("v1.3");
    private static final Milestone MILESTONE_FOUR = new Milestone("v1.4");
    private static final Milestone MILESTONE_FIVE_RC = new Milestone("v1.5rc");
    private static final Milestone MILESTONE_FIVE = new Milestone("v1.5");

    /**
     * Returns a hashmap of milestones
     */
    public static HashMap<Milestone, Integer> getMilestoneMap() {
        milestoneMap = new HashMap<>();
        createMilestoneHashMap();
        return milestoneMap;
    }

    /**
     * creates a map with the milestone values
     */
    private static void createMilestoneHashMap() {
        //Adding values to the map
        milestoneMap.put(MILESTONE_ONE, 1);
        milestoneMap.put(MILESTONE_TWO, 2);
        milestoneMap.put(MILESTONE_THREE, 3);
        milestoneMap.put(MILESTONE_FOUR, 4);
        milestoneMap.put(MILESTONE_FIVE_RC, 5);
        milestoneMap.put(MILESTONE_FIVE, 6);
    }

}
```
###### \java\seedu\progresschecker\model\issues\Title.java
``` java
/**
 * Represents an issue's name and description
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Title of the issue can be anything, but should not be blank space";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[^\\s].*";

    public final String fullMessage;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid description.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullMessage = title;
    }

    /**
     * Returns true if a given string is a issue title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
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

    /** creates an issue on github */
    void createIssueOnGitHub(Issue issue) throws IOException;

    /** reopen issue on github */
    void reopenIssueOnGithub(Index index) throws IOException, CommandException;

    /** closes an issue issue on github */
    void closeIssueOnGithub(Index index) throws IOException, CommandException;

    /**
     * Replaces the fields in Issue {@code index} with {@code editedIssue}.
     *
     * @throws IOException if while updating the issue there is some problem in authentication
     */
    void updateIssue(Index index, Issue editedIssue) throws IOException;

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
    public void updateIssue(Index index, Issue editedIssue) throws IOException {
        requireAllNonNull(index, editedIssue);

        progressChecker.updateIssue(index, editedIssue);
        indicateProgressCheckerChanged();
    }
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java

    //issue-level operations

    /**
     * Creates issue on github
     *
     * @throws IOException if theres any fault in the input values or the authentication fails due to wrong input
     */
    public void createIssueOnGitHub(Issue i) throws IOException {
        GitHub github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        GHRepository repository = github.getRepository(repoName);
        GHIssueBuilder issueBuilder = repository.createIssue(i.getTitle().toString());
        issueBuilder.body(i.getBody().toString());

        List<Assignees> assigneesList = i.getAssignees();
        List<Labels> labelsList = i.getLabelsList();

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
        //GHMilestone check = repository.getMilestone(1);
        if (i.getMilestone() != null) {
            GHMilestone check = repository.getMilestone(getMilestone.get(i.getMilestone()));
            createdIssue.setMilestone(check);
        }
        createdIssue.setAssignees(listOfUsers);
        createdIssue.setLabels(listOfLabels.toArray(new String[0]));
    }

    /**
     * Replaces the given issue at {@code index} from github with {@code editedPerson}.
     * reopens an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void reopenIssueOnGithub(Index index) throws IOException, CommandException {
        GitHub github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        GHRepository repository = github.getRepository(repoName);
        GHIssue issue = repository.getIssue(index.getOneBased());
        if (issue.getState() == GHIssueState.OPEN) {
            throw new CommandException("Issue is already open");
        }
    }

    /**
     * closes an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void closeIssueOnGithub(Index index) throws IOException, CommandException {
        GitHub github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        GHRepository repository = github.getRepository(repoName);
        GHIssue issue = repository.getIssue(index.getOneBased());
        if (issue.getState() == GHIssueState.CLOSED) {
            throw new CommandException("This issue is already closed");
        }
        issue.close();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code ProgressChecker}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws IOException if there is any problem in git authentication or parameter
     *
     */
    public void updateIssue(Index index, Issue editedIssue) throws IOException {
        requireNonNull(editedIssue);
        GitHub github = GitHub.connectUsingPassword(userLogin, userAuthentication);
        GHRepository repository = github.getRepository(repoName);
        GHIssue toEdit = repository.getIssue(index.getOneBased());

        List<Assignees> assigneesList = editedIssue.getAssignees();
        List<Labels> labelsList = editedIssue.getLabelsList();

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

        if (editedIssue.getMilestone() != null) {
            GHMilestone check = repository.getMilestone(getMilestone.get(editedIssue.getMilestone()));
            toEdit.setMilestone(check);
        }
        toEdit.setTitle(editedIssue.getTitle().toString());
        toEdit.setBody(editedIssue.getBody().toString());
        toEdit.setAssignees(listOfUsers);
        toEdit.setLabels(listOfLabels.toArray(new String[0]));
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
