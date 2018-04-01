package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.RuntimeEntity;
import com.ibm.watson.developer_cloud.conversation.v1.model.RuntimeIntent;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.CheckTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
//import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ConversationCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MoreInfoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowDashboardCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.ui.ResultDisplay;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case AddMilestoneCommand.COMMAND_WORD:
            return new AddMilestoneCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FavouriteCommand.COMMAND_WORD:
            return new FavouriteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommand();

        case UnfavouriteCommand.COMMAND_WORD:
            return new UnfavouriteCommandParser().parse(arguments);

        case MoreInfoCommand.COMMAND_WORD:
            return new MoreInfoCommandParser().parse(arguments);

        case ShowDashboardCommand.COMMAND_WORD:
            return new ShowDashboardCommandParser().parse(arguments);

        case CheckTaskCommand.COMMAND_WORD:
            return new CheckTaskCommandParser().parse(arguments);

        default:
            //@@chweeee
            /**
             * aims to decipher user intention and returns the command required
             */
            //initialises the agent
            ConversationCommand.setUpAgent();
            MessageResponse response = null;
            List<RuntimeIntent> intents; //stores user intents
            List<RuntimeEntity> entities; //stores entities identified in the user's input
            String intention = "";
            String entity = "";

            //processes the userInput
            response = ConversationCommand.getMessageResponse(userInput);
            intents = response.getIntents();
            entities = response.getEntities();
            //System.out.println("list of entities: " + entities);

            for (int i = 0; i < intents.size(); i++) {
                intention = intents.get(i).getIntent();
                //entity = entities.get(i).getValue();
            }
            System.out.println("this is the intention of the user: " + intention);
            //System.out.println("this is the value of the entity " + entity);

            switch (intention) {
            case "Clear":
                return new ClearCommand();

            case "Undo":
                return new UndoCommand();

            case "Redo":
                return new RedoCommand();

            case "Help":
                return new HelpCommand();

            case "Exit":
                return new ExitCommand();

            case "History":
                return new HistoryCommand();

            case "List":
                return new ListCommandParser().parse("");

            case "Schedule":
                return new ScheduleCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
            //@@
        }
    }
}
