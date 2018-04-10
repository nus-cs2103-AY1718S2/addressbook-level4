package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.TimeTableEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;

//@@author AzuraAiR
/**
 * Retrieves the unified timetable of the persons identified using it's last displayed index from StardyTogether
 */
public class TimetableUnionCommand extends Command {
    public static final String COMMAND_WORD = "union";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the unified timetable of the persons entered\n"
            + "Parameters: INDEX ODD/EVEN INDEX1 INDEX2 INDEX3..."
            + "(indexes must be unique positive integers and separated by one space)\n"
            + "Example: " + COMMAND_WORD + "Odd " + "1 " + "2 " + "3";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "%1$s Combined Timetable: %2$s";

    private final ArrayList<Index> targetIndexes;
    private final String oddEven;
    private ArrayList<Person> personsToShow;
    private ArrayList<Timetable> timetablesToShow;

    public TimetableUnionCommand(ArrayList<Index> targetIndexes, String oddEven) {
        requireNonNull(targetIndexes);
        requireNonNull(oddEven);
        this.targetIndexes = targetIndexes;
        this.oddEven = oddEven;
        personsToShow = new ArrayList<Person>();
        timetablesToShow = new ArrayList<Timetable>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        preprocess();

        int oddEvenIndex = StringUtil.getOddEven(oddEven);
        ArrayList<ArrayList<String>> commonTimeTable = Timetable.unionTimetable(timetablesToShow).get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(commonTimeTable);
        EventsCenter.getInstance().post(new TimeTableEvent(timeTableList));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, StringUtil.capitalize(oddEven),
                printNames()));
    }

    /**
     * Preprocess the required data for execution.
     * @throws CommandException when index out of bound
     */
    protected void preprocess() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index targetIndex : targetIndexes) {
            personsToShow.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        for (Person person : personsToShow) {
            timetablesToShow.add(person.getTimetable());
        }

    }

    /**
     * Prints the names in personsToShow
     */
    protected String printNames() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < personsToShow.size(); i++) {
            sb.append(personsToShow.get(i).getName());
            if (i != personsToShow.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimetableUnionCommand // instanceof handles nulls
                && this.targetIndexes.equals(((TimetableUnionCommand) other).targetIndexes) // state check
                && this.oddEven.equals(((TimetableUnionCommand) other).oddEven)
                && Objects.equals(this.personsToShow, ((TimetableUnionCommand) other).personsToShow));

    }

    @Override
    public String toString() {
        return targetIndexes.toString() + " " + oddEven + " " + personsToShow;
    }
}
