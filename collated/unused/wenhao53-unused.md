# wenhao53-unused
###### /WeightLogCommandParser.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Parses input arguments and creates a new WeightLogCommand object
 */
public class WeightLogCommandParser implements Parser<WeightLogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeightLogCommand
     * and returns a WeightLogCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public WeightLogCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeightLogCommand.MESSAGE_USAGE));
        }

        return new WeightLogCommand(index);
    }
}
```
###### /WeightLogCommandTest.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
public class WeightLogCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WeightLogCommand weightLogCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(weightLogCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final WeightLogCommand standardCommand = new WeightLogCommand(INDEX_FIRST_PERSON);
        final WeightLogCommand commandWithSameIndex = new WeightLogCommand(INDEX_FIRST_PERSON);
        final WeightLogCommand commandWithDifferentIndex = new WeightLogCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        WeightLogCommand weightLogCommand = prepareCommand(INDEX_FIRST_PERSON);
        weightLogCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowWeightLogEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Executes a {@code WeightLogCommand} with the given {@code index}, and checks that {@code ShowWeightLogEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        WeightLogCommand weightLogCommand = prepareCommand(index);

        try {
            CommandResult commandResult = weightLogCommand.execute();
            assertEquals(String.format(WeightLogCommand.MESSAGE_WEIGHT_LOG_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowWeightLogEvent lastEvent = (ShowWeightLogEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code WeightLogCommand} with parameters {@code index}}
     */
    private WeightLogCommand prepareCommand(Index index) {
        WeightLogCommand weightLogCommand = new WeightLogCommand(index);
        weightLogCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return weightLogCommand;
    }
}
```
###### /WeightLog.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Represents a Person's weight history (in Kg) since the startup of the Personal Trainer Pro app.
 * Guarantees: contains weight data that is valid as declared in {@link #isValidWeight(String)}
 */
public class WeightLog {

    private ArrayList<Pair<Date, Double>> list;

    /**
     * Constructs a new {@code WeightLog}.
     */
    public WeightLog(Weight weight) {
        this.list = new ArrayList<>();
        addNewEntry(new Date(), weight);
    }

    /**
     * Returns the ArrayList that contains the information in the WeightLog.
     */
    public ArrayList<Pair<Date, Double>> getList() {
        return this.list;
    }

    /**
     * Adds a new weight entry into the log.
     * @param date A date representing the moment where the Person was created.
     * @param weight A valid weight (in Kg).
     */
    public void addNewEntry(Date date, Weight weight) {
        list.add(new Pair(date, getDoubleValueFromWeight(weight)));
    }

    /*
     * Returns the double value of the input weight from the Weight string.
     */
    private Double getDoubleValueFromWeight(Weight weight) {
        return Double.parseDouble(weight.toString());
    }

    @Override
    public String toString() {
        String toDisplayAsString = "";
        for (Pair entry:list) {
            toDisplayAsString += entry.getValue().toString() + " ";
        }
        return toDisplayAsString;
    }
}
```
###### /WeightLogHtmlFormatter.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
/**
 * HTML formatter for Person class. HTML referenced from Google Charts API
 * (https://developers.google.com/chart/interactive/docs/gallery/linechart)
 */
public class WeightLogHtmlFormatter {

    public static String getWeightLogHtmlFormat(Person person) {

        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>"
                + "<script type='text/javascript'>"
                + "google.charts.load('current', {'packages':['corechart']});"
                + "google.charts.setOnLoadCallback(drawChart);"
                + "function drawChart() {"
                + "var data = google.visualization.arrayToDataTable(["
                + "['Instance', 'Weight'],"
                + getWeightData(person)
                + "]);"
                + "var options = {"
                + "title: 'Weight Log for " + person.getName().toString() + "',"
                + "curveType: 'function',"
                + "legend: { position: 'bottom' }"
                + "};"
                + "var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));"
                + "chart.draw(data, options);"
                + "}"
                + "</script>"
                + "</head>"
                + "<body>"
                + "<div id='curve_chart' style='width: 900px; height: 500px'></div>"
                + getWeightDataDetails(person)
                + "</body>"
                + "</html>";
    }

    /*
     * Returns the HTML String containing the weight data to be input as text below the chart
     */
    private static String getWeightDataDetails(Person person) {
        final String twoTabSpaces = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String result = "<p><u> Past Weight Changes for " + person.getName().toString() + "</u></p>";
        for (int entry = 0; entry < person.getWeightLog().getList().size(); entry++) {
            result = String.format("%s %s %s %s %s", result, Integer.toString(entry) + ". ",
                    "[" + person.getWeightLog().getList().get(entry).getKey().toString() + "]", twoTabSpaces,
                    person.getWeightLog().getList().get(entry).getValue().toString() + "kg <br>");
        }
        return result;
    }

    /*
     * Returns the HTML String containing the weight data to be plotted in the chart
     */
    private static String getWeightData(Person person) {
        String result = "";
        for (int entry = 0; entry < person.getWeightLog().getList().size(); entry++) {
            result = String.format("%s %s %s", result,
                    "[" + Integer.toString(entry) + ",",
                    person.getWeightLog().getList().get(entry).getValue().toString() + "]");
            if (entry != person.getWeightLog().getList().size() - 1) {
                result += ",";
            }
        }
        return result;
    }
}
```
###### /WeightLogCommand.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Opens up the Weight Log of a specified person in the Personal Trainer Pro
 */
public class WeightLogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays weight log of a person.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WEIGHT_LOG_SUCCESS = "Weight log for %1$s displayed! Displaying all weight "
            + "changes since application startup.";

    private final Index index;

    public WeightLogCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShowWeightLog = lastShownList.get(index.getZeroBased());

        try {
            model.showWeightLog(personToShowWeightLog);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_WEIGHT_LOG_SUCCESS,
                personToShowWeightLog.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightLogCommand // instanceof handles nulls
                && this.index.equals(((WeightLogCommand) other).index)); // state check
    }
}
```
###### /ShowWeightLogEvent.java
``` java
// Feature is working but was unable to solve the many tests that were failing.
/**
 * An event requesting to show the weight log of a given person
 */
public class ShowWeightLogEvent extends BaseEvent {

    public final Person person;

    public ShowWeightLogEvent(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
