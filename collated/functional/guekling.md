# guekling
###### \java\seedu\organizer\logic\commands\CurrentMonthCommand.java
``` java
/**
 * Shows the view of the current month.
 */
public class CurrentMonthCommand extends Command {

    public static final String COMMAND_WORD = "cmonth";
    public static final String COMMAND_ALIAS = "cm";

    public static final String MESSAGE_SUCCESS = "Shows current month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\organizer\logic\commands\FindDeadlineCommand.java
``` java
/**
 * Finds and lists all tasks in PrioriTask whose deadline contains any of the argument keywords.
 * Keyword should be in the format of YYYY-MM-DD.
 */
public class FindDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "findd";
    public static final String COMMAND_ALIAS = "fd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose deadline contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers. Keywords "
            + "should be in the format of YYYY-MM-DD. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 2018-03-17 2018-05-03";

    private final DeadlineContainsKeywordsPredicate predicate;

    public FindDeadlineCommand(DeadlineContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDeadlineCommand // instanceof handles nulls
                && this.predicate.equals(((FindDeadlineCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\organizer\logic\commands\FindMultipleFieldsCommand.java
``` java
/**
 * Finds and lists all tasks in organizer whose name, descriptions or deadline contains any of the argument
 * keywords.
 * Keyword matching is not case sensitive.
 */
public class FindMultipleFieldsCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names, descriptions or "
            + "deadlines contain any of the specified keywords (not case-sensitive) and displays them as a list with "
            + "index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " cs2103 2018-03-17 assignment";

    private final MultipleFieldsContainsKeywordsPredicate predicate;

    public FindMultipleFieldsCommand(MultipleFieldsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMultipleFieldsCommand // instanceof handles nulls
                && this.predicate.equals(((FindMultipleFieldsCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\organizer\logic\commands\NextMonthCommand.java
``` java
/**
 * Shows the view of the month after the currently viewed month.
 */
public class NextMonthCommand extends Command {

    public static final String COMMAND_WORD = "nmonth";
    public static final String COMMAND_ALIAS = "nm";

    public static final String MESSAGE_SUCCESS = "Shows next month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\organizer\logic\commands\PreviousMonthCommand.java
``` java
/**
 * Shows the view of the month before the currently viewed month.
 */
public class PreviousMonthCommand extends Command {

    public static final String COMMAND_WORD = "pmonth";
    public static final String COMMAND_ALIAS = "pm";

    public static final String MESSAGE_SUCCESS = "Shows previous month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\organizer\logic\LogicManager.java
``` java
    @Override
    public ObservableList<String> getExecutedCommandsList() {
        return executedCommandsObservableList;
    }
}
```
###### \java\seedu\organizer\logic\parser\AddQuestionAnswerCommandParser.java
``` java
    /**
     * Returns true if any of the prefixes contains multiple values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesRepeated(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> (argumentMultimap.getSize(prefix) > 1));
    }
```
###### \java\seedu\organizer\logic\parser\AnswerCommandParser.java
``` java
    /**
     * Returns true if any of the prefixes contains multiple values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesRepeated(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> (argumentMultimap.getSize(prefix) > 1));
    }
```
###### \java\seedu\organizer\logic\parser\ArgumentMultimap.java
``` java
    /**
     * Returns the size of list holding all values of the {@code prefix}.
     */
    public int getSize(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? 0 : values.size();
    }
```
###### \java\seedu\organizer\logic\parser\FindDeadlineCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindDeadlineCommand object
 */
public class FindDeadlineCommandParser implements Parser<FindDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeadlineCommand
     * and returns an FindDeadlineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDeadlineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeadlineCommand.MESSAGE_USAGE));
        }

        String[] deadlineKeywords = trimmedArgs.split("\\s+");

        return new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(Arrays.asList(deadlineKeywords)));
    }
}
```
###### \java\seedu\organizer\logic\parser\FindMultipleFieldsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindMultipleFieldsCommand object
 */
public class FindMultipleFieldsCommandParser implements Parser<FindMultipleFieldsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMultipleFieldsCommand
     * and returns an FindMultipleFieldsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMultipleFieldsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMultipleFieldsCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(Arrays.asList(keywords)));
    }
}
```
###### \java\seedu\organizer\logic\parser\LoginCommandParser.java
``` java
    /**
     * Returns true if any of the prefixes contains multiple values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesRepeated(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> (argumentMultimap.getSize(prefix) > 1));
    }
```
###### \java\seedu\organizer\logic\parser\SignUpCommandParser.java
``` java
    /**
     * Returns true if any of the prefixes contains multiple values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesRepeated(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> (argumentMultimap.getSize(prefix) > 1));
    }
```
###### \java\seedu\organizer\model\task\Deadline.java
``` java
/**
 * Represents a Task's deadline in the organizer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadlines should be in the format YYYY-MM-DD, and it should not be blank. Dates should also not be "
                + "invalid (e.g. 2018-02-31 is invalid as there is no such date).";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DEADLINE_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";

    public final LocalDate date;

    /**
     * Constructs an {@code Deadline}.
     *
     * @param deadline A valid deadline.
     * @throws IllegalValueException if the {@code LocalDate} class is unable to parse {@code deadline}.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_DEADLINE_CONSTRAINTS);
        this.date = LocalDate.parse(deadline);
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX) && isValidDate(test);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        Logger logger = LogsCenter.getLogger(Deadline.class);

        try {
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(test);

            return true;

        } catch (ParseException e) {
            logger.warning("Invalid date. Deadline cannot be parsed.");

            return false;
        }
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
```
###### \java\seedu\organizer\model\task\Description.java
``` java
/**
 * Represents a Task's description in the organizer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task descriptions can take any values, and can be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid organizer.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\organizer\model\task\predicates\DeadlineContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Task}'s {@code Deadline} matches any of the keywords given. Keywords given should in the
 * format of YYYY-MM-DD.
 */
public class DeadlineContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public DeadlineContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDeadline().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DeadlineContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\organizer\model\task\predicates\DescriptionContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Task}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\organizer\model\task\predicates\MultipleFieldsContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Task}'s {@code Name}, {@code Description} and {@code Deadline} matches any of the keywords given.
 */
public class MultipleFieldsContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> nameKeywords;
    private final List<String> descriptionKeywords;
    private final List<String> deadlineKeywords;
    private final List<String> keywords;


    public MultipleFieldsContainsKeywordsPredicate(List<String> keywords) {
        this.nameKeywords = keywords;
        this.descriptionKeywords = keywords;
        this.deadlineKeywords = keywords;

        this.keywords = concatKeywords();
    }

    /**
     * Concatenate the list of keywords from {@code Name}, {@code Description} and {@code Deadline}.
     *
     * @return A list of concatenated String containing the keywords from {@code Name}, {@code Description} and
     * {@code Deadline}.
     */
    private List<String> concatKeywords() {
        Stream<String> nameDescriptionStreams = Stream.concat(nameKeywords.stream(), descriptionKeywords.stream());
        List<String> concatenatedLists = Stream.concat(nameDescriptionStreams, deadlineKeywords.stream()).collect
                (Collectors.toList());
        return concatenatedLists;
    }

    @Override
    public boolean test(Task task) {
        return nameKeywords.stream().anyMatch(nameKeyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName,
                nameKeyword))
                || descriptionKeywords.stream().anyMatch(descriptionKeyword -> StringUtil.containsWordIgnoreCase(
                    task.getDescription().value, descriptionKeyword))
                || deadlineKeywords.stream().anyMatch(deadlineKeyword -> StringUtil.containsWordIgnoreCase(
                task.getDeadline().toString(), deadlineKeyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultipleFieldsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MultipleFieldsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\organizer\ui\calendar\EntryCard.java
``` java
/**
 * An UI component that displays the name of a {@code Task}.
 */
public class EntryCard extends UiPart<Region> {

    private static final String FXML = "EntryCard.fxml";

    private Task task;

    @FXML
    private Label entryCard;

    public EntryCard(Task task) {
        super(FXML);

        this.task = task;
        entryCard.setText(task.getName().fullName);
    }

    public Task getTask() {
        return task;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntryCard)) {
            return false;
        }

        // state check
        EntryCard card = (EntryCard) other;
        return task.equals(card.task);
    }
}
```
###### \java\seedu\organizer\ui\calendar\MonthView.java
``` java
/**
 * Supports the display of the month view of the calendar.
 */
public class MonthView extends UiPart<Region> {

    private static final String FXML = "MonthView.fxml";

    private static final int MAX_NUM_OF_DAYS = 35;
    private static final int NO_REMAINDER = 0;
    private static final int SUNDAY = 7;
    private static final int MAX_COLUMN = 6;
    private static final int MAX_ROW = 4;

    private int dateCount;
    private YearMonth currentYearMonth;
    private YearMonth viewYearMonth;
    private String[] datesToBePrinted;
    private ObservableList<Task> taskList;
    private ObservableList<String> executedCommandsList;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane taskCalendar;

    public MonthView(ObservableList<Task> taskList, ObservableList<String> executedCommandsList) {
        super(FXML);

        currentYearMonth = YearMonth.now();
        viewYearMonth = currentYearMonth;

        this.taskList = taskList;
        this.executedCommandsList = executedCommandsList;
        addListenerToExecutedCommandsList();
        addListenerToTaskList();

    }

    /**
     * Displays the month view.
     *
     * @param yearMonth Year and month in the YearMonth format.
     */
    public void getMonthView(YearMonth yearMonth) {
        viewYearMonth = yearMonth;
        int year = yearMonth.getYear();

        setMonthCalendarTitle(year, yearMonth.getMonth().toString());
        setMonthCalendarDatesAndEntries(year, yearMonth.getMonthValue());
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     *
     * @param month Full month name.
     * @param year Year represented as a 4-digit integer.
     */
    public void setMonthCalendarTitle(int year, String month) {
        calendarTitle.setText(month + " " + year);
    }

    /**
     * Sets the dates and entries of a month-view calendar according to the specific month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     */
    private void setMonthCalendarDatesAndEntries(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        int lengthOfMonth = startDate.lengthOfMonth();
        int startDay = getMonthStartDay(startDate);

        setMonthCalendarEntries(year, month, startDay);

        datesToBePrinted = new String[36];
        storeMonthDatesToBePrinted(lengthOfMonth);

        setFiveWeeksMonthCalendar(startDay);

        // If month has more than 5 weeks
        if (dateCount != lengthOfMonth) {
            setSixWeeksMonthCalendar(lengthOfMonth);
        }
    }

    /**
     * Sets the entries of a month-view calendar according to the specific month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setMonthCalendarEntries(int year, int month, int startDay) {
        ObservableList<EntryCard> entryCardsList = getEntryCardsList(year, month);
        setMonthEntries(startDay, entryCardsList);
    }

    /**
     * Clears the calendar of all dates and entries, while retaining the {@code gridLines}.
     */
    private void clearCalendar() {
        Node gridLines = taskCalendar.getChildren().get(0);

        // To update the JavaFX component from a non-JavaFX thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskCalendar.getChildren().retainAll(gridLines);
            }
        });
    }

    //====================================== Interacting with Command ==============================================

    /**
     * Shows the view of the current month.
     */
    private void goToCurrentMonth() {
        clearCalendar();
        getMonthView(currentYearMonth);
    }

    /**
     * Shows the view of the month before the currently viewed month.
     */
    private void goToPreviousMonth() {
        viewYearMonth = viewYearMonth.minusMonths(1);

        clearCalendar();
        getMonthView(viewYearMonth);
    }

    /**
     * Shows the view of the month after the currently viewed month.
     */
    private void goToNextMonth() {
        viewYearMonth = viewYearMonth.plusMonths(1);

        clearCalendar();
        getMonthView(viewYearMonth);
    }

    /**
     * Tracks the commands executed by the user in the {@code executedCommandsList}. Calendar view may change depending
     * on the commands executed by the user.
     */
    private void addListenerToExecutedCommandsList() {
        executedCommandsList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change change) {

                while (change.next()) {
                    int size = executedCommandsList.size();
                    String executedCommand = executedCommandsList.get(size - 1);

                    if ((executedCommand.equals(CurrentMonthCommand.COMMAND_WORD)) || (
                            executedCommand.equals(CurrentMonthCommand.COMMAND_ALIAS))) {
                        goToCurrentMonth();
                    }

                    if ((executedCommand.equals(PreviousMonthCommand.COMMAND_WORD)) || (
                        executedCommand.equals(PreviousMonthCommand.COMMAND_ALIAS))) {
                        goToPreviousMonth();
                    }

                    if ((executedCommand.equals(NextMonthCommand.COMMAND_WORD)) || (
                        executedCommand.equals(NextMonthCommand.COMMAND_ALIAS))) {
                        goToNextMonth();
                    }
                }
            }
        });
    }

    //============================= Populating the Month Calendar Dates ===========================================

    /**
     * Sets the dates of a five-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setFiveWeeksMonthCalendar(int startDay) {
        dateCount = 1;
        for (int row = 0; row <= MAX_ROW; row++) {
            if (row == 0) {
                for (int column = startDay; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            } else {
                for (int column = 0; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            }
        }
    }

    /**
     * Sets the dates of the sixth week in a six-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void setSixWeeksMonthCalendar(int lengthOfMonth) {
        int remainingDays = lengthOfMonth - dateCount;

        for (int column = 0; column <= remainingDays; column++) {
            Text dateToPrint = new Text(datesToBePrinted[dateCount]);
            addMonthDate(dateToPrint, column, 0);
            dateCount++;
        }
    }

    /**
     * Gets the day of week of the start date of a particular month and year.
     *
     * @param startDate A LocalDate variable that represents the date, viewed as year-month-day. The day will always
     *                  be set as 1.
     * @return Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *         representing the different days of the week.
     */
    private int getMonthStartDay(LocalDate startDate) {
        int startDay = startDate.getDayOfWeek().getValue();

        // Sunday is the first column in the calendar
        if (startDay == SUNDAY) {
            startDay = 0;
        }

        return startDay;
    }

    /**
     * Adds a particular date to the correct {@code column} and {@code row} in the {@code taskCalendar}.
     *
     * @param dateToPrint The formatted date text to be printed on the {@code taskCalendar}.
     * @param column The column number in {@code taskCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code taskCalendar}. Row number should range from 0 to 4.
     */
    private void addMonthDate(Text dateToPrint, int column, int row) {
        // To update the JavaFX component from a non-JavaFX thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskCalendar.add(dateToPrint, column, row);
            }
        });

        taskCalendar.setHalignment(dateToPrint, HPos.LEFT);
        taskCalendar.setValignment(dateToPrint, VPos.TOP);
        dateToPrint.setId("date" + String.valueOf(dateCount));
        dateToPrint.setFill(Color.WHITE);
    }

    /**
     * Stores the formatted date String to be printed on the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void storeMonthDatesToBePrinted(int lengthOfMonth) {
        for (int date = 1; date <= 35; date++) {
            if (date <= lengthOfMonth) {
                datesToBePrinted[date] = "  " + String.valueOf(date);
            }
        }
    }

    //============================= Populating the Month Calendar Entries =========================================

    /**
     * Sets the entries, in the form of {@code EntryCard}, into the {@code taskCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     * @param entryCardsList An {@code ObservableList} of {@code EntryCard}.
     */
    private void setMonthEntries(int startDay, ObservableList<EntryCard> entryCardsList) {
        int numOfEntries = entryCardsList.size();
        for (int size = 0; size < numOfEntries; size++) {
            List<EntryCard> toAddList = new ArrayList<>();
            EntryCard currentEntry = entryCardsList.get(size);
            toAddList.add(currentEntry);
            ObservableList<EntryCard> toAddObservableList = FXCollections.observableList(toAddList);

            int deadline = currentEntry.getTask().getDeadline().date.getDayOfMonth();
            int countDate = deadline + startDay;

            size = checkSameDayEntries(entryCardsList, size, toAddList, deadline);

            int remainder = countDate % 7;
            int divisor = countDate / 7;

            addEntries(toAddObservableList, countDate, remainder, divisor);
        }
    }

    /**
     * Calculating the position of the entries to be added into the {@code taskCalendar}.
     *
     * @param toAddObservableList An {@code ObservableList} to be added to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     * @param countDate The addition of {@code deadline} and {@code startDay}.
     */
    private void addEntries(ObservableList<EntryCard> toAddObservableList, int countDate, int remainder, int divisor) {
        if (countDate <= MAX_NUM_OF_DAYS) {
            if (remainder == NO_REMAINDER) { // entry on a Sunday
                int row = divisor - 1;
                int column = MAX_COLUMN;

                addEntryListView(toAddObservableList, row, column);
            } else {
                int row = divisor;
                int column = remainder - 1;

                addEntryListView(toAddObservableList, row, column);
            }
        } else {
            int row = 0;
            int column = remainder - 1;

            addEntryListView(toAddObservableList, row, column);
        }
    }

    /**
     * Adds a {@code ListView} that contains a list of {@code EntryCard} to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     *
     * @param toAddObservableList An {@code ObservableList} to be added to a particular {@code column} and {@code
     * row} in the {@code taskCalendar}.
     * @param column The column number in {@code taskCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code taskCalendar}. Row number should range from 0 to 4.
     */
    private void addEntryListView(ObservableList<EntryCard> toAddObservableList, int row, int column) {
        ListView<EntryCard> entries = new ListView<>();
        entries.getStyleClass().add("entryListView");
        entries.setId("entry" + String.valueOf(row) + String.valueOf(column));
        entries.setItems(toAddObservableList);
        entries.setCellFactory(listView -> new EntryListViewCell());
        entries.setMaxHeight(60);

        // To update the JavaFX component from a non-JavaFX thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskCalendar.add(entries, column, row);
            }
        });

        taskCalendar.setValignment(entries, VPos.BOTTOM);
    }

    /**
     * Checks if the {@code entryCardsList} contains other {@code EntryCard} with the same {@code deadline} as the
     * previous {@code EntryCard}. If there exists one, the {@code EntryCard} will be added to the {@code toAddList}.
     *
     * @param entryCardsList An {@code ObservableList} of {@code EntryCard}.
     * @param size The variable used as the condition for the for loop in {@code setMonthEntries}.
     * @param toAddList A list of {@EntryCard} to be added to a particular {@code column} and {@code row} in the
     * {@code taskCalendar}.
     * @param deadline The deadline of the {@code Task} in the previous {@code EntryCard}.
     *
     * @return An increment in {@code size} if there exists an {@code EntryCard} with the same {@code deadline} as
     * the previous {@code EntryCard}
     */
    private int checkSameDayEntries(ObservableList<EntryCard> entryCardsList, int size, List<EntryCard> toAddList,
                                    int deadline) {
        int numOfEntries = entryCardsList.size();
        if (size != numOfEntries) {
            for (int nextSize = size + 1; nextSize < numOfEntries; nextSize++) {
                EntryCard nextEntry = entryCardsList.get(nextSize);
                int nextDeadline = nextEntry.getTask().getDeadline().date.getDayOfMonth();

                if (deadline == nextDeadline) {
                    toAddList.add(nextEntry);
                    size++;
                } else {
                    break;
                }
            }
        }

        return size;
    }

    /**
     * Maps each {@code Task} in the {@code SortedList} to an {@code EntryCard}.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @return An {@code ObservableList} of {@code EntryCard}.
     */
    private ObservableList<EntryCard> getEntryCardsList(int year, int month) {
        FilteredList<Task> filteredList = getFilteredTaskList(year, month);
        SortedList<Task> taskSortedList = getSortedTaskList(filteredList);

        return EasyBind.map(taskSortedList, (task) -> new EntryCard(task));
    }

    /**
     * Sorts the {@code filteredList} according to the {@code deadlineComparator}.
     *
     * @param filteredList Filtered {@code taskList} that contains tasks whose deadlines are of a particular month
     *                     and year.
     * @return A sorted {@code filteredList} that contains tasks arranged according to their deadlines.
     */
    private SortedList<Task> getSortedTaskList(FilteredList<Task> filteredList) {
        return filteredList.sorted(deadlineComparator());
    }

    /**
     * Filters the {@code taskList} so that it contains tasks whose deadlines are of a particular month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     * @return A filtered {@code taskList} that contains tasks whose deadlines are of a particular month and year.
     */
    private FilteredList<Task> getFilteredTaskList(int year, int month) {
        FilteredList<Task> filteredList = new FilteredList<>(taskList, task -> true);

        filteredList.setPredicate(task -> {
            LocalDate date = task.getDeadline().date;

            if ((date.getMonthValue() == month) && (date.getYear() == year)) {
                return true;
            } else {
                return false;
            }
        });
        return filteredList;
    }

    /**
     * Updates the calendar entries when a change in {@code taskList} is detected.
     */
    private void addListenerToTaskList() {
        taskList.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change change) {

                while (change.next()) {
                    clearCalendar();
                    setMonthCalendarDatesAndEntries(viewYearMonth.getYear(), viewYearMonth.getMonthValue());
                }
            }
        });
    }

    /**
     * @return A {@code Task} comparator based on deadline.
     */
    private static Comparator<Task> deadlineComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (task1.getDeadline().date).compareTo(task2.getDeadline().date);
            }
        };
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EntryCard}.
     */
    class EntryListViewCell extends ListCell<EntryCard> {

        @Override
        protected void updateItem(EntryCard entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(entry.getRoot());
            }
        }
    }

    //================================================= isEqual ======================================================

    /**
     * Checks if the entries are the same.
     */
    public boolean entriesIsEqual(Object other) {
        MonthView monthView = (MonthView) other;

        for (int size = 0; size < taskList.size(); size++) {
            return taskList.get(size).equals(monthView.taskList.get(size));
        }

        return false;
    }

    /**
     * Checks if the dates are printed in the same row and column.
     */
    public boolean dateIsEqual(Object other) {
        MonthView monthView = (MonthView) other;

        for (int date = 1; date <= viewYearMonth.lengthOfMonth(); date++) {
            Node expectedText = taskCalendar.lookup("#date" + String.valueOf(date));
            int expectedRow = taskCalendar.getRowIndex(expectedText);
            int expectedColumn = taskCalendar.getColumnIndex(expectedText);

            Node actualText = monthView.taskCalendar.lookup("#date" + String.valueOf(date));
            int actualRow = monthView.taskCalendar.getRowIndex(actualText);
            int actualColumn = monthView.taskCalendar.getColumnIndex(actualText);

            return (expectedRow == actualRow) && (expectedColumn == actualColumn);
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthView)) {
            return false;
        }

        // state check
        MonthView monthView = (MonthView) other;
        return calendarTitle.getText().equals(monthView.calendarTitle.getText())
                && dateIsEqual(monthView)
                && entriesIsEqual(monthView);
    }
}
```
###### \java\seedu\organizer\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private MonthView monthView;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendarPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableList<String> executedCommandsList) {
        super(FXML);

        monthView = new MonthView(taskList, executedCommandsList);
        currentYearMonth = currentYearMonth.now();

        createMainView();
    }

    /**
     * Creates the main view of the calendar, which by default, is the current month view.*
     */
    private void createMainView() {
        monthView.getMonthView(currentYearMonth);
        calendarPane.getChildren().add(monthView.getRoot());
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
```
###### \resources\view\BlueTheme.css
``` css

.background {
    -fx-background-color: derive(#0B0C10, 20%);
    background-color: #1F2833; /* Used in the default.html file */
}

.label {
    -fx-font-size: 10pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 10pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 10pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #0B0C10;
    -fx-control-inner-background: #0B0C10;
    -fx-background-color: #0B0C10;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
            transparent
            transparent
            derive(-fx-base, 80%)
            transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#0B0C10, 20%);
    -fx-border-color: transparent transparent transparent #66FCF1;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#0B0C10, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#0B0C10, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: #1F2833;
}

.list-cell:filled:even {
    -fx-background-color: #4578a2;
}

.list-cell:filled:odd {
    -fx-background-color: #45A29E;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
    -fx-background-color: derive(#0B0C10, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#0B0C10, 20%);
    -fx-border-color: derive(#0B0C10, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#0B0C10, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 11pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#0B0C10, 30%);
    -fx-border-color: derive(#0B0C10, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#0B0C10, 30%);
    -fx-border-color: derive(#0B0C10, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#0B0C10, 30%);
}

.context-menu {
    -fx-background-color: derive(#0B0C10, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#0B0C10, 20%);
}

.menu-bar .label {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #0B0C10;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #0B0C10;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #0B0C10;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #0B0C10;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #0B0C10;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#0B0C10, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#0B0C10, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#0B0C10, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: #1F2833 #383838 #1F2833 #383838;
    -fx-background-insets: 0;
    -fx-border-color: #1F2833 #1F2833 #1F2833 #1F2833;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #taskListPanel, #taskWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #1F2833, transparent, #1F2833;
    -fx-background-radius: 0;
}

#resultDisplayPlaceholder {
    -fx-border-color: #66FCF1 #383838 #66FCF1 #383838;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: #000000;
    -fx-background-color: #FFFFFF;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#tags .blue {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #0000FF;
}

#tags .brown {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #A52A2A;
}

#tags .gray {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #808080;
}

#tags .green {
    -fx-text-fill: #000000;
    -fx-background-color: #008000;
}

#tags .maroon {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #800000;
}

#tags .orange {
    -fx-text-fill: #000000;
    -fx-background-color: #FFA500;
}

#tags .pink {
    -fx-text-fill: #000000;
    -fx-background-color: #FFC0CB;
}

#tags .purple {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #800080;
}

#tags .red {
    -fx-text-fill: #FFFFFF;
    -fx-background-color: #FF0000;
}

#tags .yellow {
    -fx-text-fill: #000000;
    -fx-background-color: #FFFF00;
}

#calendarPane {
    -fx-background-color: #1F2833;
}

.calendarPaneStyle Line {
    -fx-stroke: white;
}

.taskListViewStyle .list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#0B0C10, 20%);
}

.taskListViewStyle .list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-color: #1F2833;
    -fx-padding: 5;
}

.taskListViewStyle .list-cell:filled:even {
    -fx-background-color: #4578a2;
    -fx-border-insets: 5;
    -fx-background-insets: 5;
    -fx-background-radius: 15;
    -fx-border-radius: 10;
    -fx-border-width: 2;
    -fx-border-style: solid;
    -fx-border-color: #0B0C10;
}

.taskListViewStyle .list-cell:filled:odd {
    -fx-background-color: #45A29E;
    -fx-border-insets: 5;
    -fx-background-insets: 5;
    -fx-background-radius: 15;
    -fx-border-radius: 10;
    -fx-border-width: 2;
    -fx-border-style: solid;
    -fx-border-color: #0B0C10;
}

.taskListViewStyle .list-cell .label {
    -fx-text-fill: white;
}

.taskListViewStyle .cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.taskListViewStyle .cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.subtaskListViewStyle .list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 3 !important;
    -fx-border-insets: 3 !important;
    -fx-background-insets: 3 !important;
    -fx-background-radius: 0 !important;
    -fx-border-radius: 0 !important;
    -fx-border-width: 0 !important;
    -fx-background-color: #1F2833;
}

.subtaskListViewStyle .scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 5 1 5;
}

.subtaskListViewStyle .scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 5 1 5 1;
}

.entryListView .scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 4 1 4;
}

.entryListView .scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 4 1 4 1;
}

#calendarTitle {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-weight: bold;
}


```
###### \resources\view\CalendarPanel.fxml
``` fxml


<StackPane fx:id="calendarPane" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
</StackPane>
```
