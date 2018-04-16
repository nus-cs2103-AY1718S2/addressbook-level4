package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.CalendarViewStateParser;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;
import seedu.address.ui.CalendarView;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private final Storage storage;
    private final UserPrefs userPrefs;

    private CalendarViewStateParser calendarViewStateParser;

    public LogicManager(Model model, Storage storage, UserPrefs userprefs) {
        this.model = model;
        this.userPrefs = userprefs;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            command.setStorage(storage);
            CommandResult result = command.execute();
            undoRedoStack.push(command);

            // Updates View state of Calendar UI
            if (calendarViewStateParser != null) {
                calendarViewStateParser.updateViewState(commandText);
            }

            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    //@@author x3tsunayh
    @Override
    public void setCalendarView(CalendarView calendarView) {
        this.calendarViewStateParser = new CalendarViewStateParser(this.userPrefs, this.model, calendarView);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public void setTabPane(TabPane tabPane) {
        addressBookParser.setTabPane(tabPane);
    }
}
