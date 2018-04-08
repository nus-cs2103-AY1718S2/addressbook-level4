package seedu.organizer.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.core.ComponentManager;
import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.logic.commands.Command;
import seedu.organizer.logic.commands.CommandResult;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.logic.parser.OrganizerParser;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.Model;
import seedu.organizer.model.task.Task;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final OrganizerParser organizerParser;
    private final UndoRedoStack undoRedoStack;

    private List<String> executedCommandsList;
    private ObservableList<String> executedCommandsObservableList;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        organizerParser = new OrganizerParser();
        undoRedoStack = new UndoRedoStack();
        executedCommandsObservableList = FXCollections.observableArrayList();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        executedCommandsObservableList.add(commandText.trim());

        try {
            Command command = organizerParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    //@@author guekling
    @Override
    public ObservableList<String> getExecutedCommandsList() {
        return executedCommandsObservableList;
    }
}
