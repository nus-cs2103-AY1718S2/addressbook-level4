package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CatalogueParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;


/**
 * The main LogicManager of the app.
 */

public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;

    private final CommandHistory history;
    private final CatalogueParser catalogueParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        catalogueParser = new CatalogueParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = catalogueParser.parseCommand(commandText);
            if (!isPrivileged(command)) {
                return new CommandResult(Command.MESSAGE_UNPRIVILEGED);
            }
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    //@@author QiuHaohao
    protected boolean isPrivileged(Command command) {
        return command.getPrivilegeLevel().compareTo(model.getPrivilegeLevel()) <= 0;
    }
    //@@author
}
