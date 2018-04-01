package seedu.address.logic.commands;

import javafx.stage.Stage;
import seedu.address.ui.MyCalendarView;

/**
 * Show my own calendar
 * */
public class MyCalendarCommand extends Command {

    public static final String COMMAND_WORD = "myCalendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": show my own calendar. ";

    public static final String MESSAGE_SUCCESS = "Your calendar is loaded.";

    @Override
    public CommandResult execute() {

        MyCalendarView webViewSample = new MyCalendarView();
        webViewSample.start(new Stage());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
