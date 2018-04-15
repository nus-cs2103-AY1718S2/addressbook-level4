# pkaijun
###### \main\java\seedu\investigapptor\commons\events\ui\FilteredCrimeCaseListChangedEvent.java
``` java
/**
 * Indicates a request to change the cases displayed on the calendar panel
 */
public class FilteredCrimeCaseListChangedEvent extends BaseEvent {

    public final ObservableList<CrimeCase> crimecases;

    public FilteredCrimeCaseListChangedEvent(ObservableList<CrimeCase> crimecases) {
        this.crimecases = crimecases;
    }

    public ObservableList<CrimeCase> getFilteredCrimeCaseList() {
        return crimecases;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\CloseCaseCommand.java
``` java
/**
 * Update the status of a case from open to close and update the EndDate field
 */
public class CloseCaseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "close";
    public static final String COMMAND_ALIAS = "cl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status from open to close "
            + "and updates the end date to today's date.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CLOSE_CASE_SUCCESS = "Case status updated: %1$s";
    public static final String MESSAGE_DUPLICATE_CASE = "This case already exists in investigapptor.";
    public static final String MESSAGE_CASE_ALREADY_CLOSE = "Case is already closed.";

    private final Index index;

    private CrimeCase caseToClose;
    private CrimeCase closedCase;

    /**
     * @param index of the crimecase in the filtered crimecase list to close
     */
    public CloseCaseCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCrimeCase(caseToClose, closedCase);
        } catch (DuplicateCrimeCaseException dce) {
            throw new CommandException(MESSAGE_DUPLICATE_CASE);
        } catch (CrimeCaseNotFoundException cnfe) {
            throw new AssertionError("The target case cannot be missing");
        }
        model.updateFilteredCrimeCaseList(PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(String.format(MESSAGE_CLOSE_CASE_SUCCESS, closedCase.getStatus()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        EventsCenter.getInstance().post(new SwapTabEvent(1));   // List results toggles to case tab

        List<CrimeCase> lastShownList = model.getFilteredCrimeCaseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        caseToClose = lastShownList.get(index.getZeroBased());

        if (caseToClose.getStatus().toString().equals(CASE_CLOSE)) {
            throw new CommandException(MESSAGE_CASE_ALREADY_CLOSE);
        }

        closedCase = createClosedCase(caseToClose);
    }

    /**
     * Creates and returns a {@code CrimeCase} with the details of {@code caseToEdit}
     * Updates status to "close" with the other fields remaining the same
     */
    private static CrimeCase createClosedCase(CrimeCase caseToClose) {
        assert caseToClose != null;

        CaseName name = caseToClose.getCaseName();
        Description desc = caseToClose.getDescription();
        StartDate startDate = caseToClose.getStartDate();
        EndDate endDate = new EndDate(EndDate.getTodayDate());
        Set<Tag> tags = caseToClose.getTags();
        Investigator investigator = caseToClose.getCurrentInvestigator();
        Status status = new Status(CASE_CLOSE);

        return new CrimeCase(name, desc, investigator, startDate, endDate, status, tags);
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\FindByStatusCommand.java
``` java
/**
 * Finds and lists all cases in investigapptor according to the status predicate specified
 */
public class FindByStatusCommand extends Command {
    private final StatusContainsKeywordsPredicate predicate;

    public FindByStatusCommand(String caseStatus) {
        List<String> keywords = Arrays.asList(caseStatus);
        this.predicate = new StatusContainsKeywordsPredicate(keywords);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredCrimeCaseList(predicate);
        EventsCenter.getInstance().post(new SwapTabEvent(1));   // List results toggles to case tab
        return new CommandResult(getMessageForCrimeListShownSummary(model.getFilteredCrimeCaseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByStatusCommand // instanceof handles nulls
                && this.predicate.equals(((FindByStatusCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\FindCaseTagsCommand.java
``` java
/**
 * Finds and lists all cases in investigapptor whose tags contains any of the argument keywords.
 * Keyword matching is not case-sensitive.
 */
public class FindCaseTagsCommand extends Command {

    public static final String COMMAND_WORD = "findcasetags";
    public static final String COMMAND_ALIAS = "fct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds cases whose tags contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Homicide";

    private final TagContainsKeywordsPredicate predicate;

    public FindCaseTagsCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredCrimeCaseList(predicate);
        EventsCenter.getInstance().post(new SwapTabEvent(1));   // List results toggles to case tab
        return new CommandResult(getMessageForCrimeListShownSummary(model.getFilteredCrimeCaseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCaseTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindCaseTagsCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\FindCloseCaseCommand.java
``` java
/**
 * Finds and lists all cases in investigapptor whose status are closed
 */
public class FindCloseCaseCommand extends FindByStatusCommand {
    public static final String COMMAND_WORD = "findclosecases";
    public static final String COMMAND_ALIAS = "fcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds cases whose status is "
            + CASE_CLOSE + ".\n"
            + "Example: " + COMMAND_WORD;

    public FindCloseCaseCommand() {
        super(CASE_CLOSE);
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\FindInvestTagsCommand.java
``` java
/**
 * Finds and lists all investigators in investigapptor whose tags contains any of the argument keywords.
 * Keyword matching is not case-sensitive.
 */
public class FindInvestTagsCommand extends Command {

    public static final String COMMAND_WORD = "findinvestigatortags";
    public static final String COMMAND_ALIAS = "fit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds investigators whose tags contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " teamA";

    private final TagContainsKeywordsPredicate predicate;

    public FindInvestTagsCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new SwapTabEvent(0));   // List results toggles to investigators tab
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindInvestTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindInvestTagsCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\logic\commands\FindOpenCaseCommand.java
``` java
/**
 * Finds and lists all cases in investigapptor whose status are opened
 */
public class FindOpenCaseCommand extends FindByStatusCommand {
    public static final String COMMAND_WORD = "findopencases";
    public static final String COMMAND_ALIAS = "foc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds cases whose status is "
            + CASE_OPEN + ".\n"
            + "Example: " + COMMAND_WORD;

    public FindOpenCaseCommand() {
        super(CASE_OPEN);
    }
}
```
###### \main\java\seedu\investigapptor\logic\parser\CloseCaseCommandParser.java
``` java
package seedu.investigapptor.logic.parser;

import static seedu.investigapptor.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.commons.exceptions.IllegalValueException;
import seedu.investigapptor.logic.commands.CloseCaseCommand;
import seedu.investigapptor.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CloseCaseCommandParser object
 */
public class CloseCaseCommandParser implements Parser<CloseCaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CloseCaseCommand
     * and returns an CloseCaseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseCaseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CloseCaseCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CloseCaseCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \main\java\seedu\investigapptor\logic\parser\FindCaseTagsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCaseTagsCommand object
 */
public class FindCaseTagsCommandParser implements Parser<FindCaseTagsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindInvestTagsCommandParser
     * and returns an FindInvestTagsCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCaseTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCaseTagsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.toLowerCase().split("\\s+");

        return new FindCaseTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \main\java\seedu\investigapptor\logic\parser\FindInvestTagsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindInvestTagsCommand object
 */
public class FindInvestTagsCommandParser implements Parser<FindInvestTagsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindInvestTagsCommandParser
     * and returns an FindInvestTagsCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindInvestTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInvestTagsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.toLowerCase().split("\\s+");

        return new FindInvestTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \main\java\seedu\investigapptor\model\crimecase\CrimeCase.java
``` java
    /**
     * Returns an immutable tag set of type String
     */
    public Set<String> getTagsRaw() {
        Set<String> rawTags = new HashSet<>();
        for (Tag s : tags) {
            rawTags.add(s.getRawString().toLowerCase());
        }

        return rawTags;
    }
```
###### \main\java\seedu\investigapptor\model\crimecase\StatusContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code CrimeCase}'s {@code Status} matches any of the keywords given.
 */
public class StatusContainsKeywordsPredicate implements Predicate<CrimeCase> {
    private final List<String> keywords;

    public StatusContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /* Returns true if keywords matches with any element in the set of status of a crimecase
     */
    @Override
    public boolean test(CrimeCase crimecase) {
        return keywords.stream()
                .anyMatch(crimecase.getStatus().toString()::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatusContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((StatusContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\model\crimecase\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<CrimeCase> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /* Returns true if keywords matches with any element in the set of tags of a person
     */
    @Override
    public boolean test(CrimeCase crimecase) {
        return keywords.stream()
                .anyMatch(crimecase.getTagsRaw()::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\model\Investigapptor.java
``` java
    /**
     * Replaces the given case {@code target} in the list with {@code editedCase}.
     * {@code Investigapptor}'s tag list will be updated with the tags of {@code editedCase}.
     *
     * @throws DuplicateCrimeCaseException if updating the crimeCase's details causes the crimeCase to be equivalent to
     *                                  another existing crimeCase in the list.
     * @throws CrimeCaseNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(CrimeCase)
     */
    public void updateCrimeCase(CrimeCase target, CrimeCase editedCase)
            throws DuplicateCrimeCaseException, CrimeCaseNotFoundException {
        requireNonNull(editedCase);

        CrimeCase syncedEditedCrimeCase = syncWithMasterTagList(editedCase);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the crimeCase list.
        removeCrimeCaseFromInvestigator(target);
        cases.setCrimeCase(target, syncedEditedCrimeCase);
        addCrimeCaseToInvestigator(syncedEditedCrimeCase);
    }
```
###### \main\java\seedu\investigapptor\model\ModelManager.java
``` java
    /** Raises an event to indicate the filtered crime cases list has changed */
    private void indicateFilteredCrimeCaseListChanged() {
        raise(new FilteredCrimeCaseListChangedEvent(filteredCrimeCases));
    }
```
###### \main\java\seedu\investigapptor\model\person\investigator\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /* Returns true if keywords matches with any element in the set of tags of a person
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(person.getTagsRaw()::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \main\java\seedu\investigapptor\ui\CalendarPanel.java
``` java
/**
 * The CalendarPanel of the Application which displays an overview of the duration of all the cass
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private static final Style[] ALL_STYLES = { Style.STYLE1, Style.STYLE2, Style.STYLE3, Style.STYLE4,
        Style.STYLE5, Style.STYLE6, Style.STYLE7 };
    private static final String CLOSED_CASE_CALENDAR = "Closed Cases";
    private static final String OPENED_CASE_CALENDAR = "Opened Cases";
    private static final String CALENDAR_SOURCE = "All Cases";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    private Calendar caseCloseCalendar;
    private Calendar caseOpenCalendar;
    private CalendarSource caseCalendarSource;

    private ObservableList<CrimeCase> crimeList;

    @FXML
    private CalendarView calendarPanel;

    public CalendarPanel(ObservableList<CrimeCase> crimeList) {
        super(FXML);

        this.crimeList = crimeList;

        /** Constructing the new various calendar objects **/
        calendarPanel = new CalendarView();
        caseCalendarSource = new CalendarSource(CALENDAR_SOURCE);  // Contains calendars
        caseCloseCalendar = new Calendar(CLOSED_CASE_CALENDAR);  // Contains entries of cases that are closed
        caseOpenCalendar = new Calendar(OPENED_CASE_CALENDAR);  // Contains entries of cases that are opened

        /** Setting the defaults **/
        setCalendarView();
        setCalendar();

        /** Creating the calendar entries **/
        createCalendarEntries();

        /** Creating the calendar entries and adding it to the calendar **/
        addToCalendar();

        registerAsAnEventHandler(this);
    }

    public CalendarView getViewPanel() {
        return this.calendarPanel;
    }

    /**
     * Create canlendar entries for all the cases in the crime list
     */
    private void createCalendarEntries() {
        StartDate startDate;
        EndDate endDate;
        String caseName;
        String status;

        for (CrimeCase crimecase : crimeList) {
            status = crimecase.getStatus().toString();
            startDate = crimecase.getStartDate();
            endDate = crimecase.getEndDate();
            caseName = crimecase.getCaseName().toString();

            setEntry(startDate, endDate, status, caseName);
        }
    }

    /**
     * Creates an entry and adds it to the respective calendar according to its status
     * @param startDate
     * @param endDate
     * @param status
     * @param caseName
     */
    private void setEntry(StartDate startDate, EndDate endDate, String status, String caseName) {
        Entry<String> caseEntry = new Entry<>(caseName);
        caseEntry.changeStartDate(LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDay()));
        caseEntry.setFullDay(true);

        if (status.equals(CASE_OPEN)) {
            caseEntry.changeEndDate(caseEntry.getStartDate());
            caseOpenCalendar.addEntry(caseEntry);
        } else if (status.equals(CASE_CLOSE)) {
            caseEntry.changeEndDate(LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDay()));
            caseCloseCalendar.addEntry(caseEntry);
        } else {
            assert(false);  // Should not reach here
        }
    }

    /**
     * Configure the view of the calendar
     */
    private void setCalendarView() {
        calendarPanel.setShowAddCalendarButton(false);
        calendarPanel.setShowSearchField(false);
        calendarPanel.setShowSearchResultsTray(false);
        calendarPanel.setShowPrintButton(false);
        calendarPanel.setShowToolBar(true);
        calendarPanel.setShowAddCalendarButton(false);
        calendarPanel.setShowToday(true);
        calendarPanel.showMonthPage();
    }

    /**
     * Configure the settings of the calendars
     */
    private void setCalendar() {
        caseCloseCalendar.setReadOnly(true);
        caseCloseCalendar.setStyle(ALL_STYLES[0]);

        caseOpenCalendar.setReadOnly(true);
        caseOpenCalendar.setStyle(ALL_STYLES[4]);
    }

    /**
     * Add the entries to the calendar view and source
     */
    private void addToCalendar() {
        caseCalendarSource.getCalendars().add(caseCloseCalendar);
        caseCalendarSource.getCalendars().add(caseOpenCalendar);
        calendarPanel.getCalendarSources().addAll(caseCalendarSource);
        calendarPanel.getCalendarSources().remove(0);   // Remove the default calendar
    }

    @Subscribe
    private void handleInvestigapptorChangedEvent(InvestigapptorChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        crimeList = event.data.getCrimeCaseList();
        Platform.runLater(this::updateCalendar);
    }

    @Subscribe
    private void handleFilteredCrimeCaseListChangedEvent(FilteredCrimeCaseListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        crimeList = event.getFilteredCrimeCaseList();
        Platform.runLater(this::updateCalendar);
    }

    /**
     * Updates the calendar whenever there is a change made to the crimecaselist
     */
    private void updateCalendar() {
        caseCloseCalendar.clear();
        caseOpenCalendar.clear();
        createCalendarEntries();
    }
}
```
###### \main\java\seedu\investigapptor\ui\PersonCard.java
``` java
    /**
     *
     * Creates tag labels for person
     */
    private void colorTag(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyle(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     *
     * @param tagName
     * @return Colour in the array
     */
    private String getTagColorStyle(String tagName) {
        // Hash the tag name to get the corresponding colour
        return LABEL_COLOR[Math.abs(tagName.hashCode()) % LABEL_COLOR.length];
    }
```
###### \main\resources\view\DarkTheme.css
``` css
#tags .red {
    -fx-text-fill: black;
    -fx-background-color: #FF0000;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: #FFFF00;
}

#tags .blue {
    -fx-text-fill: black;
    -fx-background-color: #76D3E2;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: #F9C815;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: #F915EE;
}

#tags .olive {
    -fx-text-fill: black;
    -fx-background-color: #72B07C;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: #000000;
}

#tags .brown {
    -fx-text-fill: black;
    -fx-background-color: #CD853F;
}

#tags .gray {
    -fx-text-fill: black;
    -fx-background-color: #b6b6b6;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: #00ff06;
}

#tags .beige {
    -fx-text-fill: black;
    -fx-background-color: #FFE4C4;
}

#tags .lightblue {
    -fx-text-fill: black;
    -fx-background-color: #27fffa;
}

#tags .golden {
    -fx-text-fill: black;
    -fx-background-color: #DAA520;
}

#tags .purple {
    -fx-text-fill: black;
    -fx-background-color: #bc99da;
}


```
###### \main\resources\view\LightTheme.css
``` css
#tags .red {
    -fx-text-fill: black;
    -fx-background-color: #FF0000;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: #FFFF00;
}

#tags .blue {
    -fx-text-fill: black;
    -fx-background-color: #76D3E2;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: #F9C815;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: #F915EE;
}

#tags .olive {
    -fx-text-fill: black;
    -fx-background-color: #72B07C;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: #000000;
}

#tags .brown {
    -fx-text-fill: black;
    -fx-background-color: #CD853F;
}

#tags .gray {
    -fx-text-fill: black;
    -fx-background-color: #b6b6b6;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: #00ff06;
}

#tags .beige {
    -fx-text-fill: black;
    -fx-background-color: #FFE4C4;
}

#tags .lightblue {
    -fx-text-fill: black;
    -fx-background-color: #27fffa;
}

#tags .golden {
    -fx-text-fill: black;
    -fx-background-color: #DAA520;
}

#tags .purple {
    -fx-text-fill: black;
    -fx-background-color: #bc99da;
}

```
###### \main\resources\view\MainWindow.fxml
``` fxml
          <StackPane fx:id="calendarPanelPlaceholder" prefWidth="340" >
            <padding>
              <Insets top="10" right="10" bottom="10" left="10" />
            </padding>
          </StackPane>
```
###### \test\java\seedu\investigapptor\logic\commands\CloseCaseCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand)
 * and unit tests for CloseCaseCommand.
 */
public class CloseCaseCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() throws Exception {
        final CloseCaseCommand standardCommand = prepareCommand(INDEX_SECOND_CASE);

        // same values -> returns true
        CloseCaseCommand commandWithSameValues = prepareCommand(INDEX_SECOND_CASE);
        //assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CloseCaseCommand(INDEX_FIRST_CASE)));
    }

    @Test
    public void execute_validCrimeCaseIndexFilteredList_success() throws Exception {
        Index indexLastCrimeCase = Index.fromOneBased(model.getFilteredCrimeCaseList().size());
        CrimeCase lastCrimeCase = model.getFilteredCrimeCaseList().get(indexLastCrimeCase.getZeroBased());

        CrimeCaseBuilder crimeCaseInList = new CrimeCaseBuilder(lastCrimeCase);
        CrimeCase closedCrimeCase = crimeCaseInList.withStatus(CASE_CLOSE).build();

        CloseCaseCommand closeCaseCommand = prepareCommand(indexLastCrimeCase);

        String expectedMessage = String.format(MESSAGE_CLOSE_CASE_SUCCESS, closedCrimeCase.getStatus().toString());

        Model expectedModel = new ModelManager(new Investigapptor(model.getInvestigapptor()), new UserPrefs());
        expectedModel.updateCrimeCase(lastCrimeCase, closedCrimeCase);

        assertCommandSuccess(closeCaseCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of investigapptor book
     */
    @Test
    public void execute_invalidCrimeCaseIndexFilteredList_failure() {
        showCrimeCaseAtIndex(model, INDEX_FIRST_CASE);
        Index outOfBoundIndex = INDEX_SECOND_CASE;

        // ensures that outOfBoundIndex is still in bounds of investigapptor book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInvestigapptor().getCrimeCaseList().size());

        CloseCaseCommand closeCaseCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(closeCaseCommand, model, Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
    }

    /**
     * Command applied on a case whose status is already closed - test should fail
     */
    @Test
    public void execute_invalidCaseStatusClosed_failure() {
        final CloseCaseCommand closeCaseCommand = prepareCommand(INDEX_FIRST_CASE); // case status already closed
        assertCommandFailure(closeCaseCommand, model, MESSAGE_CASE_ALREADY_CLOSE);
    }

    /**
     * Invalid index as the input - test should fail
     */
    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCrimeCaseList().size() + 1);

        CloseCaseCommand closeCaseCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> editCaseCommand not pushed into undoRedoStack
        assertCommandFailure(closeCaseCommand, model, Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Closes a {@code CrimeCase} from a filtered list.
     * 2. Undo the close.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited crimeCase in the
     * unfiltered list is different from the index at the filtered list.
     */
    @Test
    public void executeUndo_validIndexFilteredList_sameCrimeCaseClosed() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);

        CrimeCase closedCrimeCase = new CrimeCaseBuilder().withStatus(CASE_CLOSE).build();
        CloseCaseCommand closeCaseCommand = prepareCommand(INDEX_FIRST_CASE);

        Model expectedModel = new ModelManager(new Investigapptor(model.getInvestigapptor()), new UserPrefs());

        showCrimeCaseAtIndex(model, INDEX_SECOND_CASE);
        CrimeCase crimeCaseToClose = model.getFilteredCrimeCaseList().get(INDEX_FIRST_CASE.getZeroBased());

        // close -> closes second crimeCase in unfiltered crimeCase list / first crimeCase in filtered crimeCase list
        closeCaseCommand.execute();
        undoRedoStack.push(closeCaseCommand);

        // undo -> reverts investigapptor back to previous state and filtered crimeCase list to show all crimeCases
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateCrimeCase(crimeCaseToClose, closedCrimeCase);
        assertNotEquals(model.getFilteredCrimeCaseList().get(INDEX_FIRST_CASE.getZeroBased()), crimeCaseToClose);
    }

    /**
     * Returns an {@code CloseCaseCommand} with parameters {@code index}
     */
    private CloseCaseCommand prepareCommand(Index index) {
        CloseCaseCommand closeCaseCommand = new CloseCaseCommand(index);
        closeCaseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return closeCaseCommand;
    }
}
```
###### \test\java\seedu\investigapptor\logic\commands\FindCaseTagsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCaseTagsCommand}.
 */
public class FindCaseTagsCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCaseTagsCommand findFirstCommand = new FindCaseTagsCommand(firstPredicate);
        FindCaseTagsCommand findSecondCommand = new FindCaseTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCaseTagsCommand findFirstCommandCopy = new FindCaseTagsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCrimeCaseFound() {
        String expectedMessage = String.format(MESSAGE_CASES_LISTED_OVERVIEW, 0);
        FindCaseTagsCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleCrimeCaseFound() {
        String expectedMessage = String.format(MESSAGE_CASES_LISTED_OVERVIEW, 5);
        String userInput = "Murder Kidnap".toLowerCase();  // Tags are converted to lowercase during comparison
        FindCaseTagsCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALFA, BRAVO, ONE, TWO, THREE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCaseTagsCommand}.
     */
    private FindCaseTagsCommand prepareCommand(String userInput) {
        FindCaseTagsCommand command =
                new FindCaseTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<CrimeCase>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCaseTagsCommand command, String expectedMessage,
                                      List<CrimeCase> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCrimeCaseList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
```
###### \test\java\seedu\investigapptor\logic\commands\FindCloseCaseCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCloseCaseCommand}.
 */
public class FindCloseCaseCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        FindCloseCaseCommand findCloseCaseFirstCommand = new FindCloseCaseCommand();
        FindCloseCaseCommand findCloseCaseSecondCommand = new FindCloseCaseCommand();

        // same object -> returns true
        assertTrue(findCloseCaseFirstCommand.equals(findCloseCaseFirstCommand));

        // same values -> returns true
        FindCloseCaseCommand findFirstCommandCopy = new FindCloseCaseCommand();
        assertTrue(findCloseCaseFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findCloseCaseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findCloseCaseFirstCommand.equals(null));

        // same object -> returns true
        assertTrue(findCloseCaseFirstCommand.equals(findCloseCaseSecondCommand));
    }

    @Test
    public void execute_command_multipleCrimeCaseFound() {
        String expectedMessage = String.format(MESSAGE_CASES_LISTED_OVERVIEW, 7);
        FindCloseCaseCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALFA, CHARLIE, FOXTROT, ONE, TWO, THREE, FOUR));
    }

    /**
     * Prepare the FindCloseCaseCommand {@code FindCloseCaseCommand}.
     */
    private FindCloseCaseCommand prepareCommand() {
        FindCloseCaseCommand command = new FindCloseCaseCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<CrimeCase>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCloseCaseCommand command, String expectedMessage,
                                      List<CrimeCase> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCrimeCaseList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
```
###### \test\java\seedu\investigapptor\logic\commands\FindInvestTagsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindInvestTagsCommand}.
 */
public class FindInvestTagsCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindInvestTagsCommand findFirstCommand = new FindInvestTagsCommand(firstPredicate);
        FindInvestTagsCommand findSecondCommand = new FindInvestTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindInvestTagsCommand findFirstCommandCopy = new FindInvestTagsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindInvestTagsCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        String userInput = "teamB new".toLowerCase();  // Tags are converted to lowercase during comparison
        FindInvestTagsCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(SIR_LIM, MDM_ONG, SIR_CHONG));
    }

    /**
     * Parses {@code userInput} into a {@code FindInvestTagsCommand}.
     */
    private FindInvestTagsCommand prepareCommand(String userInput) {
        FindInvestTagsCommand command =
                new FindInvestTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindInvestTagsCommand command, String expectedMessage,
                                      List<Person> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
```
###### \test\java\seedu\investigapptor\logic\commands\FindOpenCaseCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindOpenCaseCommand}.
 */
public class FindOpenCaseCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        FindOpenCaseCommand findOpenCaseFirstCommand = new FindOpenCaseCommand();
        FindOpenCaseCommand findOpenCaseSecondCommand = new FindOpenCaseCommand();

        // same object -> returns true
        assertTrue(findOpenCaseFirstCommand.equals(findOpenCaseFirstCommand));

        // same values -> returns true
        FindOpenCaseCommand findFirstCommandCopy = new FindOpenCaseCommand();
        assertTrue(findOpenCaseFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findOpenCaseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findOpenCaseFirstCommand.equals(null));

        // same object -> returns true
        assertTrue(findOpenCaseFirstCommand.equals(findOpenCaseSecondCommand));
    }

    @Test
    public void execute_command_multipleCrimeCaseFound() {
        String expectedMessage = String.format(MESSAGE_CASES_LISTED_OVERVIEW, 5);
        FindOpenCaseCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BRAVO, DELTA, ECHO, GOLF, FIVE));
    }

    /**
     * Prepare the FindOpenCaseCommand {@code FindOpenCaseCommand}.
     */
    private FindOpenCaseCommand prepareCommand() {
        FindOpenCaseCommand command = new FindOpenCaseCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<CrimeCase>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindOpenCaseCommand command, String expectedMessage,
                                      List<CrimeCase> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCrimeCaseList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
```
###### \test\java\seedu\investigapptor\logic\parser\FindCaseTagsCommandParserTest.java
``` java
public class FindCaseTagsCommandParserTest {

    private FindCaseTagsCommandParser parser = new FindCaseTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCaseTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindInvestTagsCommand() {
        // no leading and trailing whitespaces. arguments are lowercase as comparison is lowercase based
        FindCaseTagsCommand expectedFindCommand =
                new FindCaseTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList("murder", "robbery")));
        assertParseSuccess(parser, "murder robbery", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n murder \n \t robbery  \t", expectedFindCommand);
    }

}
```
###### \test\java\seedu\investigapptor\logic\parser\FindInvestTagsCommandParserTest.java
``` java
public class FindInvestTagsCommandParserTest {

    private FindInvestTagsCommandParser parser = new FindInvestTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindInvestTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindInvestTagsCommand() {
        // no leading and trailing whitespaces. arguments are lowercase as comparison is lowercase based
        FindInvestTagsCommand expectedFindCommand =
                new FindInvestTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList("teama", "new")));
        assertParseSuccess(parser, "teama new", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n teama \n \t new  \t", expectedFindCommand);
    }

}
```
