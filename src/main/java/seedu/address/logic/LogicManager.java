package seedu.address.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = execute(command);
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    //@@author Jason1im
    /**
     * Executes the command if it can used before logging in
     * @throws CommandException if the command is restricted.
     */
    private CommandResult execute(Command command) throws CommandException {
        if (model.isLoggedIn() || isUnrestrictedCommand(command)) {
            return command.execute();
        } else {
            throw new CommandException(Messages.MESSAGE_RESTRICTED_COMMMAND);
        }
    }

    /**
     * Checks if the command can be executed before logging in.
     */
    private boolean isUnrestrictedCommand(Command command) {
        return command instanceof LoginCommand || command instanceof HelpCommand
                || command instanceof ExitCommand;
    }

    //@@author
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@@author kush1509
    @Override
    public List<Appointment> getAppointmentList() {
        return model.getAppointmentList();
    }

    @Override
    public ObservableList<Job> getFilteredJobList() {
        return model.getFilteredJobList();
    }

    //@@author
    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
