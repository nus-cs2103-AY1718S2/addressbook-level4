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
    private final String oddEven;
    private Person personToShow;
    /**
     * Creates a Timetable to retrieve the timetable of the given index
     */
    public TimeTableCommand(Index targetIndex, String oddEven) {
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
        int oddEvenIndex = StringUtil.getOddEven(oddEven);
        ArrayList<ArrayList<String>> personTimeTable = timeTable.getTimetable().get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(personTimeTable);
        EventsCenter.getInstance().post(new TimeTableEvent(timeTableList));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, StringUtil.capitalize(oddEven),
                personToShow));
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTableCommand // instanceof handles nulls
                && this.targetIndex.equals(((TimeTableCommand) other).targetIndex) // state check
                && this.oddEven.equalsIgnoreCase(((TimeTableCommand) other).oddEven)
                && Objects.equals(this.personToShow, ((TimeTableCommand) other).personToShow));

    }

    @Override
    public String toString() {
        return targetIndex.toString() + " " + oddEven + " " + personToShow;
    }
}
