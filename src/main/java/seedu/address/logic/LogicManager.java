package seedu.address.logic;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.storage.PasswordManger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private boolean isLocked;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        this.isLocked = PasswordManger.passwordExists();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        if (isLocked) {
            tryUnlock(commandText);
            if (isLocked) {
                throw new CommandException(PasswordCommand.MESSAGE_WRONG_PASSWORD);
            } else {
                decryptFile();
                return new CommandResult("Welcome to reInsurance");
            }
        }

        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    /**
     * Method to decrypt file
     */
    private void decryptFile() {
        try {
            UserPrefs userPrefs = new UserPrefs();
            File file = new File(userPrefs.getAddressBookFilePath());
            EncryptionUtil.decrypt(file);
        } catch (IOException ioe) {
            logger.warning("File not found" + ioe.getMessage());
        }
    }

    /**
     * Checks with the PasswordManger on whether to unlock the program
     * @param commandText password provided
     * @throws CommandException if password file is corrupted
     */
    private void tryUnlock(String commandText) throws  CommandException {

        try {
            isLocked = !PasswordManger.verifyPassword(commandText);
        } catch (IOException ioe) {
            throw new CommandException("Unable to open password file");
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
