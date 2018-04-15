# olimhc
###### \java\seedu\address\commons\events\ui\ClientListSwitchEvent.java
``` java
/**
 * Represents an event when the user wants to switch the list
 */
public class ClientListSwitchEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\CloseCommand.java
``` java
/**
 * Deletes a person from the active list and add it to the closed list
 */
public class CloseCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "close";
    public static final String COMMAND_ALIAS = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Close an active tutor or student and store them in "
            + "a closed student or tutor list. \n"
            + "Parameters: " + COMMAND_WORD + " " + "INDEX" + " " + PREFIX_CATEGORY + "CATEGORY \n"
            + "INDEX should be non-zero and non-negative and "
            + "CATEGORY can only be either 's' or 't', where 's' represents students and 't' represents tutor).\n"
            + "Example: " + COMMAND_WORD + " " + "1" + " " + PREFIX_CATEGORY + "t\n";

    public static final String MESSAGE_CLOSE_STUDENT_SUCCESS = "Student closed: %1$s";
    public static final String MESSAGE_CLOSE_TUTOR_SUCCESS = "Tutor closed: %1$s";

    private final Index targetIndex;
    private final Category category;

    private Client clientToClose;

    public CloseCommand(Index targetIndex, Category category) {
        this.targetIndex = targetIndex;
        this.category = category;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(clientToClose);
        try {
            model.deleteClient(clientToClose, category);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target client cannot be missing");
        }

        try {
            if (category.isStudent()) {
                model.addClosedStudent(clientToClose);
            } else {
                model.addClosedTutor(clientToClose);
            }
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The client should not be duplicated");
        }

        if (category.isStudent()) {
            return new CommandResult(String.format(MESSAGE_CLOSE_STUDENT_SUCCESS, clientToClose));
        } else {
            return new CommandResult(String.format(MESSAGE_CLOSE_TUTOR_SUCCESS, clientToClose));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToClose = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloseCommand // instanceof handles nulls
                && this.targetIndex.equals(((CloseCommand) other).targetIndex) // state check
                && Objects.equals(this.clientToClose, ((CloseCommand) other).clientToClose));
    }
}
```
###### \java\seedu\address\logic\commands\exceptions\CommandNotAvailableInActiveViewException.java
``` java
/**
 * Signals that the command is not available in active list view.
 */
public class CommandNotAvailableInActiveViewException extends CommandException {
    public CommandNotAvailableInActiveViewException() {
        super("Command is not available in active list view."
                + " Please switch back to closed list view with the command word: switch\n");
    }
}
```
###### \java\seedu\address\logic\commands\exceptions\CommandNotAvailableInClosedViewException.java
``` java
/**
 * Signals that the command is not available in closed list view.
 */
public class CommandNotAvailableInClosedViewException extends CommandException {
    public CommandNotAvailableInClosedViewException() {
        super("Command is not available in closed list view."
                + " Please switch back to active list view with the command word: switch\n");
    }
}
```
###### \java\seedu\address\logic\commands\RestoreCommand.java
``` java
/**
 * Delete a person from the closed list and add it back to the active list
 */
public class RestoreCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_ALIAS = "res";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restore a closed tutor or student to the active "
            + "tutor or student list. \n"
            + "Parameters: " + COMMAND_WORD + " " + "INDEX" + " " + PREFIX_CATEGORY + "CATEGORY "
            + "(CATEGORY can only be either 's' or 't', where 's' represents students and 't' represents tutor).\n"
            + "Example: " + COMMAND_WORD + " " + "1" + " " + PREFIX_CATEGORY + "t\n";

    public static final String MESSAGE_RESTORE_STUDENT_SUCCESS = "Student restored: %1$s";
    public static final String MESSAGE_RESTORE_TUTOR_SUCCESS = "Tutor restored: %1$s";

    private final Index targetIndex;
    private final Category category;

    private Client clientToRestore;

    public RestoreCommand(Index targetIndex, Category category) {
        this.targetIndex = targetIndex;
        this.category = category;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(clientToRestore);
        try {
            model.deleteClosedClient(clientToRestore, category);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target client cannot be missing");
        }

        try {
            if (category.isStudent()) {
                model.addStudent(clientToRestore);
            } else {
                model.addTutor(clientToRestore);
            }
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The client should not be duplicated");
        }

        if (category.isStudent()) {
            return new CommandResult(String.format(MESSAGE_RESTORE_STUDENT_SUCCESS, clientToRestore));
        } else {
            return new CommandResult(String.format(MESSAGE_RESTORE_TUTOR_SUCCESS, clientToRestore));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInActiveViewException();
        }

        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredClosedStudentList();
        } else {
            lastShownList = model.getFilteredClosedTutorList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToRestore = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestoreCommand // instanceof handles nulls
                && this.targetIndex.equals(((RestoreCommand) other).targetIndex) // state check
                && Objects.equals(this.clientToRestore, ((RestoreCommand) other).clientToRestore));
    }
}
```
###### \java\seedu\address\logic\commands\SortByGradeCommand.java
``` java
/**
 *Sort the selected list according to their grade in ascending order
 */
public class SortByGradeCommand extends SortCommand {

    public static final String MESSAGE_SORT_DESC = " their grade in ascending order.";

    private Category category;

    public SortByGradeCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        if (category.isTutor()) {
            model.sortByGradeFilteredClientTutorList();
            return new CommandResult(MESSAGE_SUCCESS_TUTOR + MESSAGE_SORT_DESC);
        } else {
            model.sortByGradeFilteredClientStudentList();
            return new CommandResult(MESSAGE_SUCCESS_STUDENT + MESSAGE_SORT_DESC);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortByGradeCommand // instanceof handles nulls
                & this.category.equals(((SortByGradeCommand) other).category)); // state check // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortByLocationCommand.java
``` java
/**
 *Sort the selected list according to their location in alphabetical order
 */
public class SortByLocationCommand extends SortCommand {

    public static final String MESSAGE_SORT_DESC = " their location in alphabetical order.";

    private Category category;

    public SortByLocationCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        if (category.isTutor()) {
            model.sortByLocationFilteredClientTutorList();
            return new CommandResult(MESSAGE_SUCCESS_TUTOR + MESSAGE_SORT_DESC);
        } else {
            model.sortByLocationFilteredClientStudentList();
            return new CommandResult(MESSAGE_SUCCESS_STUDENT + MESSAGE_SORT_DESC);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortByLocationCommand // instanceof handles nulls
                & this.category.equals(((SortByLocationCommand) other).category)); // state check // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortByNameCommand.java
``` java
/**
 *Sort the selected list according to their name in alphabetical order
 */
public class SortByNameCommand extends SortCommand {

    public static final String MESSAGE_SORT_DESC = " their name in alphabetical order.";

    private Category category;

    public SortByNameCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        if (category.isTutor()) {
            model.sortByNameFilteredClientTutorList();
            return new CommandResult(MESSAGE_SUCCESS_TUTOR + MESSAGE_SORT_DESC);
        } else {
            model.sortByNameFilteredClientStudentList();
            return new CommandResult(MESSAGE_SUCCESS_STUDENT + MESSAGE_SORT_DESC);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortByNameCommand // instanceof handles nulls
                & this.category.equals(((SortByNameCommand) other).category)); // state check // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortBySubjectCommand.java
``` java
/**
 *Sort the selected list according to their subject in alphabetical order
 */
public class SortBySubjectCommand extends SortCommand {

    public static final String MESSAGE_SORT_DESC = " their subject in alphabetical order.";

    private Category category;


    public SortBySubjectCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        if (category.isTutor()) {
            model.sortBySubjectFilteredClientTutorList();
            return new CommandResult(MESSAGE_SUCCESS_TUTOR + MESSAGE_SORT_DESC);
        } else {
            model.sortBySubjectFilteredClientStudentList();
            return new CommandResult(MESSAGE_SUCCESS_STUDENT + MESSAGE_SORT_DESC);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortBySubjectCommand // instanceof handles nulls
                & this.category.equals(((SortBySubjectCommand) other).category)); // state check // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Represents a sort command
 */
public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final String COMMAND_WORD_NAME = "n";
    public static final String COMMAND_WORD_LOCATION = "l";
    public static final String COMMAND_WORD_SUBJECT = "s";
    public static final String COMMAND_WORD_GRADE = "g";
    public static final String COMMAND_WORD_TUTOR = "t";
    public static final String COMMAND_WORD_STUDENT = "s";

    public static final String MESSAGE_SUCCESS_TUTOR = "Sorted tutor's list according to";
    public static final String MESSAGE_SUCCESS_STUDENT = "Sorted student's list according to";

    private static final String USAGE_MESSAGE_LIST = " "
            + COMMAND_WORD  + " " + COMMAND_WORD_NAME + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_SUBJECT + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_GRADE + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + "to sort Tutor's list base on name, location, subject and level respectively.\n"
            + "Parameters: "
            + COMMAND_WORD  + " " + COMMAND_WORD_NAME + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_SUBJECT + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_GRADE + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + "to sort Student's list base on name, location, subject and level respectively.\n"
            + "Example: "
            + COMMAND_WORD + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + "\n";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort selected list according to user choice.\n"
            + "Parameters:" + USAGE_MESSAGE_LIST;

    @Override
    public abstract CommandResult execute() throws CommandException;
}
```
###### \java\seedu\address\logic\commands\SwitchCommand.java
``` java
/**
 * Represents a switch command to enable user to switch between closed and active client list
 * All active students and tutors or closed students and tutors will be shown after switching
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_SUCCESS = "Switched to ";
    public static final String MESSAGE_CLOSED_DISPLAY_LIST = "closed client list.\n";
    public static final String MESSAGE_ACTIVE_DISPLAY_LIST = "active client list.\n";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ClientListSwitchEvent());
        listPanelController.switchDisplay();
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            model.updateFilteredClosedTutorList(PREDICATE_SHOW_ALL_CLOSED_TUTORS);
            model.updateFilteredClosedStudentList(PREDICATE_SHOW_ALL_CLOSED_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS + MESSAGE_CLOSED_DISPLAY_LIST);
        } else {
            model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS + MESSAGE_ACTIVE_DISPLAY_LIST);
        }
    }
}
```
###### \java\seedu\address\logic\commands\util\GradeUtil.java
``` java
/**
 * Helper function for handling different format of grade
 */
public class GradeUtil {

    /**
     * Returns true if the {@code value} matches the {@code word} given that word is a valid grade
     *   <br>examples:<pre>
     *       A p3 grade should match primary3.
     *       A client with P3 P4 grades should match a p4 grade
     *       </pre>
     * @param value cannot be null, can be a string of multiple grades or just a grade
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsGradeIgnoreCase(String value, String word) {
        requireNonNull(value);
        requireNonNull(word);

        if (!isValidGrade(word)) {
            return false;
        }

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        int preppedWordValueWeightage = getGradeIndex(preppedWord);
        int[] getAllGradeWeightage = getAllGradeWeightage(value);

        for (int i : getAllGradeWeightage) {
            if (i == preppedWordValueWeightage) {
                return true;
            }
        }

        return false;
    }
}
```
###### \java\seedu\address\logic\commands\util\SortByGradeComparator.java
``` java
/**
 * Comparator to sort by int base on valueWeight
 */
public class SortByGradeComparator
        implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {
        return o1.getGrade().valueWeightage - o2.getGrade().valueWeightage;
    }
}
```
###### \java\seedu\address\logic\parser\CloseCommandParser.java
``` java
/**
 * Parses an input and create a new CloseCommand object
 */
public class CloseCommandParser implements Parser<CloseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CloseCommand
     * and returns a Close Command object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CATEGORY)
                || argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseCommand.MESSAGE_USAGE));
        }

        Index index;
        Category category;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            category = ParserUtil.parseCategory(argumentMultimap.getValue(PREFIX_CATEGORY)).get();
            return new CloseCommand(index, category);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\logic\parser\RestoreCommandParser.java
``` java
/**
 * Parse an input and create a new RestoreCommand object
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a Restore Command object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CATEGORY)
                || argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
        }

        Index index;
        Category category;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            category = ParserUtil.parseCategory(argumentMultimap.getValue(PREFIX_CATEGORY)).get();
            return new RestoreCommand(index, category);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new subclass object of SortCommand
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parse the given {@code String} of arguments in the context of SortCommand
     * @return either SortByGradeCommand, SortByNameCommand, SortByGradeCommand, SortBySubjectCommand
     * object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CATEGORY)
                || argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String sortType;
        Category category;

        try {
            sortType = argumentMultimap.getPreamble();
            category = ParserUtil.parseCategory(argumentMultimap.getValue(PREFIX_CATEGORY)).get();
            return getSortCommandType(category, sortType);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * @return the respective sort command based on the category parsed
     * @throws ParseException
     */
    private SortCommand getSortCommandType(Category category, String sortType) throws ParseException {

        switch (sortType) {
        case SortCommand.COMMAND_WORD_NAME:
            return new SortByNameCommand(category);

        case SortCommand.COMMAND_WORD_SUBJECT:
            return new SortBySubjectCommand(category);

        case SortCommand.COMMAND_WORD_LOCATION:
            return new SortByLocationCommand(category);

        case SortCommand.COMMAND_WORD_GRADE:
            return new SortByGradeCommand(category);

        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Tutor} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void sortByNameFilteredClientTutorList() {
        Comparator<Client> sortByName = (tutor1, tutor2)-> (tutor1.getName().fullName)
                .compareToIgnoreCase(tutor2.getName().fullName);
        sortedFilteredTutors.setComparator(sortByName);
    }

    @Override
    public void sortByNameFilteredClientStudentList() {
        Comparator<Client> sortByName = (student1, student2)-> (student1.getName().fullName)
                .compareToIgnoreCase(student2.getName().fullName);
        sortedFilteredStudents.setComparator(sortByName);
    }

    @Override
    public void sortByLocationFilteredClientTutorList() {
        Comparator<Client> sortByLocation = (tutor1, tutor2)-> (tutor1.getLocation().value)
                .compareToIgnoreCase(tutor2.getLocation().value);
        sortedFilteredTutors.setComparator(sortByLocation);
    }

    @Override
    public void sortByLocationFilteredClientStudentList() {
        Comparator<Client> sortByLocation = (student1, student2)-> (student1.getLocation().value)
                .compareToIgnoreCase(student2.getLocation().value);
        sortedFilteredStudents.setComparator(sortByLocation);
    }


    @Override
    public void sortByGradeFilteredClientTutorList() {
        Comparator<Client> sortByGrade = new SortByGradeComparator();
        sortedFilteredTutors.setComparator(sortByGrade);
    }

    @Override
    public void sortByGradeFilteredClientStudentList() {
        Comparator<Client> sortByGrade = new SortByGradeComparator();
        sortedFilteredStudents.setComparator(sortByGrade);
    }

    @Override
    public void sortBySubjectFilteredClientTutorList() {
        Comparator<Client> sortBySubject = (tutor1, tutor2)-> (tutor1.getSubject().value)
                .compareToIgnoreCase(tutor2.getSubject().value);
        sortedFilteredTutors.setComparator(sortBySubject);
    }

    @Override
    public void sortBySubjectFilteredClientStudentList() {
        Comparator<Client> sortBySubject = (student1, student2)-> (student1.getSubject().value)
                .compareToIgnoreCase(student2.getSubject().value);
        sortedFilteredStudents.setComparator(sortBySubject);
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @Subscribe
    private void handleClientListSwitchEvent(ClientListSwitchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (this.displayStatus.getText().equals(SYNC_STATUS_ACTIVE_LIST)) {
            setDisplayStatus(SYNC_STATUS_CLOSED_LIST);
        } else {
            setDisplayStatus(SYNC_STATUS_ACTIVE_LIST);
        }
    }
```
###### \java\seedu\address\ui\StudentListPanel.java
``` java
    /**
     * Switch the displayed student's list
     */
    private void switchListDisplay() {
        ListPanelController listPanelController = ListPanelController.getInstance();
        switch (listPanelController.getCurrentListDisplayed()) {
        case activeList:
            setConnectionsForClosedStudents();
            break;

        case closedList:
            setConnectionsForStudents();
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }
```
###### \java\seedu\address\ui\StudentListPanel.java
``` java
    @Subscribe
    private void handleClientListSwitchEvent(ClientListSwitchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchListDisplay();
    }
```
###### \java\seedu\address\ui\TutorListPanel.java
``` java
    /**
     * Switch the displayed tutor's list
     */
    private void switchListDisplay() {
        ListPanelController listPanelController = ListPanelController.getInstance();
        switch (listPanelController.getCurrentListDisplayed()) {
        case activeList:
            setConnectionsForClosedTutors();
            break;

        case closedList:
            setConnectionsForTutors();
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }
```
###### \java\seedu\address\ui\TutorListPanel.java
``` java
    @Subscribe
    private void handleClientListSwitchEvent(ClientListSwitchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchListDisplay();
    }
```
###### \java\seedu\address\ui\util\ListPanelController.java
``` java
/**
 * Stores the type of list being displayed
 */
public class ListPanelController {
    private static final Logger logger = LogsCenter.getLogger(ListPanelController.class);
    private static ListPanelController instance = null;

    /**
     * An enum to store which the type of list displayed
     */
    public enum DisplayType {
        closedList, activeList
    }

    /**
     * Ensure that the active client list is always shown first
     */
    private static DisplayType currentlyDisplayed = DisplayType.activeList;

    public DisplayType getCurrentListDisplayed() {
        return currentlyDisplayed;
    }

    /**
     * Switch the current display
     */
    public void switchDisplay() {
        switch (currentlyDisplayed) {
        case activeList:
            currentlyDisplayed = DisplayType.closedList;
            logger.fine("Switching display to closed client list.");
            break;

        case closedList:
            currentlyDisplayed = DisplayType.activeList;
            logger.fine("Switching display to active client list.");
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }

    /**
     * Reset the display to its default mode showing active client list.
     */
    public void setDefault() {
        if (!isCurrentDisplayActiveList()) {
            switchDisplay();
        }
    }

    /**
     * @return true if displayed list is active list
     */
    public static boolean isCurrentDisplayActiveList() {
        if (currentlyDisplayed == DisplayType.activeList) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ensure that only one instance of ListPanelController is created
     * @return instance
     */
    public static ListPanelController getInstance() {
        if (instance == null) {
            instance = new ListPanelController();
        }

        return instance;
    }
}
```
