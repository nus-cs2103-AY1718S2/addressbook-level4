package seedu.address.logic.commands;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

//@@author x3tsunayh

/**
 * switch between the Events and Tasks list
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchtab";
    public static final String COMMAND_WORD_TWO = "switch";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches between Event and Task UI List Tab";
    public static final String MESSAGE_SUCCESS = "Switched Tabs!";

    private static final int EVENTS_TAB = 0;
    private static final int TASKS_TAB = 1;

    private final TabPane tabPane;

    public SwitchTabCommand(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        if (selectedIndex == EVENTS_TAB) {
            selectedIndex = TASKS_TAB;
        } else {
            selectedIndex = EVENTS_TAB;
        }
        selectionModel.select(selectedIndex);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
