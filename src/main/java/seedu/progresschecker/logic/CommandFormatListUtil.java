package seedu.progresschecker.logic;

import java.util.ArrayList;
import java.util.Collections;

import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.logic.commands.ClearCommand;
import seedu.progresschecker.logic.commands.CloseIssueCommand;
import seedu.progresschecker.logic.commands.CreateIssueCommand;
import seedu.progresschecker.logic.commands.DeleteCommand;
import seedu.progresschecker.logic.commands.EditCommand;
import seedu.progresschecker.logic.commands.EditIssueCommand;
import seedu.progresschecker.logic.commands.ExitCommand;
import seedu.progresschecker.logic.commands.FindCommand;
import seedu.progresschecker.logic.commands.HelpCommand;
import seedu.progresschecker.logic.commands.ListCommand;
import seedu.progresschecker.logic.commands.RedoCommand;
import seedu.progresschecker.logic.commands.ReopenIssueCommand;
import seedu.progresschecker.logic.commands.SelectCommand;
import seedu.progresschecker.logic.commands.SortCommand;
import seedu.progresschecker.logic.commands.UndoCommand;
import seedu.progresschecker.logic.commands.UploadCommand;
import seedu.progresschecker.logic.commands.ViewCommand;
import seedu.progresschecker.logic.commands.ViewTaskListCommand;

//@@author adityaa1998
/**
 * Initialises and returns a list which contains different command formats
 */
public final class CommandFormatListUtil {
    private static ArrayList<String> commandFormatList;

    public static ArrayList<String> getCommandFormatList () {
        commandFormatList = new ArrayList<>();
        createCommandFormatList();
        return commandFormatList;
    }

    /**
     * Creates commandFormatList for existing commands
     */
    private static void createCommandFormatList() {
        commandFormatList.add(AddCommand.COMMAND_FORMAT);
        commandFormatList.add(ClearCommand.COMMAND_WORD);
        commandFormatList.add(DeleteCommand.COMMAND_FORMAT);
        commandFormatList.add(EditCommand.COMMAND_FORMAT);
        commandFormatList.add(ExitCommand.COMMAND_WORD);
        commandFormatList.add(FindCommand.COMMAND_FORMAT);
        commandFormatList.add(HelpCommand.COMMAND_WORD);
        commandFormatList.add(ListCommand.COMMAND_WORD);
        commandFormatList.add(RedoCommand.COMMAND_WORD);
        commandFormatList.add(SelectCommand.COMMAND_FORMAT);
        commandFormatList.add(SortCommand.COMMAND_WORD);
        commandFormatList.add(UndoCommand.COMMAND_WORD);
        commandFormatList.add(UploadCommand.COMMAND_FORMAT);
        commandFormatList.add(ViewCommand.COMMAND_FORMAT);
        commandFormatList.add(ViewTaskListCommand.COMMAND_FORMAT);
        commandFormatList.add(CreateIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(EditIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(ReopenIssueCommand.COMMAND_FORMAT);
        commandFormatList.add(CloseIssueCommand.COMMAND_FORMAT);

        //sorting the commandFormatList
        Collections.sort(commandFormatList);
    }
}
