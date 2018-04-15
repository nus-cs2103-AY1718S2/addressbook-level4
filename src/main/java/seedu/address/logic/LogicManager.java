package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INSUFFICIENT_SECURITY_CLEARANCE;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.cell.Cell;
import seedu.address.model.person.Person;
import seedu.address.model.user.User;

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
            CommandResult result = restrictedExecute(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    //@@author zacci
    /**
     * Executes the received command if the logged in user's security level meets the MIN_SECURITY_LEVEL for the command
     */
    private CommandResult restrictedExecute (Command command) throws CommandException {
        logger.info("Command MIN_SECURITY_LEVEL: " + command.getMinSecurityLevel());
        if (command.getMinSecurityLevel() <= model.getSecurityLevel()) {
            try {
                CommandResult result = command.execute();
                undoRedoStack.push(command);
                return result;
            } finally {
            }
        } else {
            CommandResult result = new CommandResult(MESSAGE_INSUFFICIENT_SECURITY_CLEARANCE);
            return result;
        }
    }
    //@@author

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public ObservableList<Cell> getCellList() {
        return model.getAddressBook().getCellList();
    }

    //@@author zacci
    @Override
    public ObservableList<User> getUserList() {
        return model.getAddressBook().getUserList();
    }
}
