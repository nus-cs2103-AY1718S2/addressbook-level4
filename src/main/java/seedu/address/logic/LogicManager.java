package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DeleteUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private String username;

    public LogicManager(Model model, String username) {
        this.model = model;
        history = new CommandHistory();
        this.username = username;
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        clearRedundantImages();
    }

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        clearRedundantImages();
    }

    //@@author Alaru
    /**
     * Clears the data folder of redundant images
     */
    public void clearRedundantImages() {
        logger.info("Deleting any unused display pictures");
        DeleteUtil.clearImageFiles(model.getItemList(), model.getFilteredPersonList());
        model.clearDeleteItems();
    }
    //@@author

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
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

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@@author JoonKai1995
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task>[][] getCalendarTaskLists() {
        return model.getCalendarTaskLists();
    }
    //@@author
    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    //@@author WoodySIN
    @Override
    public void setTabPane(TabPane tabPane) {
        addressBookParser.setTabPane(tabPane);
    }
}
