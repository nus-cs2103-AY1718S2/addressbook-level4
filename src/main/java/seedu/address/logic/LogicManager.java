package seedu.address.logic;

import java.io.File;
import java.util.HashSet;
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

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    /**
     * Deletes unreferenced resume files for disk space optimisation
     */
    public void cleanUnusedResume() {
        HashSet<String> usedResume = new HashSet<String>();
        for (Person p: model.getFilteredPersonList()) {
            if (p.getResume().value != null) {
                usedResume.add(p.getResume().value.substring(5)); //"data\..."
            }
        }
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir + File.separator + "data");
        File[] dataDirList = dataDir.listFiles();
        if (dataDirList == null) {
            return;
        }
        for (File child : dataDirList) {
            if (child.getName().equals("addressbook.xml")) {
                break;
            }
            if (!usedResume.contains(child.getName())) {
                child.delete();
            }
        }
    }
}
