package seedu.address.logic.commands;

import javax.swing.JFrame;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.JournalWindow;

/**
 * Creates a new window for typing journal entries
 */
public class NewJournal extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String COMMAND_ALIAS = "n";

    @Override
    public CommandResult execute() throws CommandException {
        JFrame frame = new JournalWindow();
        frame.setTitle("Journal");
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        return null;
    }
}
