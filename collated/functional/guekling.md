# guekling
###### /java/seedu/organizer/logic/commands/CurrentMonthCommand.java
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
###### /java/seedu/organizer/logic/commands/FindDeadlineCommand.java
``` java
/**
 * Finds and lists all persons in PrioriTask whose deadline contains any of the argument keywords.
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
###### /java/seedu/organizer/logic/commands/FindMultipleFieldsCommand.java
``` java
/**
 * Finds and lists all persons in organizer book whose name, descriptions or deadline contains any of the argument
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
###### /java/seedu/organizer/logic/commands/NextMonthCommand.java
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
###### /java/seedu/organizer/logic/commands/PreviousMonthCommand.java
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
###### /java/seedu/organizer/logic/LogicManager.java
``` java
    @Override
    public ObservableList<String> getExecutedCommandsList() {
        return executedCommandsObservableList;
    }
}
```
###### /java/seedu/organizer/logic/parser/FindDeadlineCommandParser.java
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
###### /java/seedu/organizer/logic/parser/FindMultipleFieldsCommandParser.java
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
###### /java/seedu/organizer/model/task/Deadline.java
``` java
/**
 * Represents a Task's deadline in the organizer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadlines should be in the format YYYY-MM-DD, and it should not be blank";

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
        return test.matches("") || test.matches(DEADLINE_VALIDATION_REGEX);
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
###### /java/seedu/organizer/model/task/DeadlineContainsKeywordsPredicate.java
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
###### /java/seedu/organizer/model/task/Description.java
``` java
/**
 * Represents a Task's description in the organizer book.
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
###### /java/seedu/organizer/model/task/DescriptionContainsKeywordsPredicate.java
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
###### /java/seedu/organizer/model/task/MultipleFieldsContainsKeywordsPredicate.java
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
###### /java/seedu/organizer/ui/calendar/EntryCard.java
``` java
/**
 * !!! ADD COMMENTS !!!
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
###### /java/seedu/organizer/ui/calendar/MonthView.java
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
###### /java/seedu/organizer/ui/CalendarPanel.java
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
###### /resources/view/CalendarPanel.fxml
``` fxml

<StackPane fx:id="calendarPane" xmlns="http://javafx.com/javafx/8.0.121"
           xmlns:fx="http://javafx.com/fxml/1">
</StackPane>
```
###### /resources/view/DarkTheme.css
``` css

#calendarPane {
    -fx-background-color: #FFFFFF;
}

#calendarTitle {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-weight: bold;
}

```
