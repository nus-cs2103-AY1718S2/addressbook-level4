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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;

//@@author yeggasd
/**
 * Retrieves the timetable of a person identified using it's last displayed index from the address book.
 */
public class TimeTableCommand extends Command {
    public static final String COMMAND_WORD = "timetable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds timetable of a person identified  \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "%1$s Week Timetable of selected Person: %1$s";

    private final Index targetIndex;
    private final int oddEven;
    private Person personToShow;
    /**
     * Creates a Timetable to retrieve the timetable of the given index
     */
    public TimeTableCommand(Index targetIndex, int oddEven) {
        requireNonNull(targetIndex);
        requireNonNull(oddEven);
        this.targetIndex = targetIndex;
        this.oddEven = oddEven;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        preprocess();

        Timetable timeTable = personToShow.getTimetable();
        ArrayList<ArrayList<String>> personTimeTable = timeTable.getTimetable().get(oddEven);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(personTimeTable);
        EventsCenter.getInstance().post(new TimeTableEvent(timeTableList));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, oddEven, personToShow));
    }

    /**
     * Preprocess the required data for execution.
     * @throws CommandException when index out of bound
     */
    protected void preprocess() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToShow = lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     *  Tem Po La Ly
     * @return
     */
    public ArrayList<ArrayList<String>> gd () {
        ArrayList<ArrayList<String>> d = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> e = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if ((i + j) % 2 == 0) {
                    e.add(new Integer(i + j).toString());
                } else {
                    e.add(null);
                }
            }
            d.add(e);
        }
        return  d;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTableCommand // instanceof handles nulls
                && this.targetIndex.equals(((TimeTableCommand) other).targetIndex) // state check
                && this.oddEven == ((TimeTableCommand) other).oddEven
                && Objects.equals(this.personToShow, ((TimeTableCommand) other).personToShow));

    }
}
