package seedu.address.logic.parser;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CalendarViewCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {
    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_NRIC = new Prefix("nr/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_SPECIES = new Prefix("s/");
    public static final Prefix PREFIX_BREED = new Prefix("b/");
    public static final Prefix PREFIX_COLOUR = new Prefix("c/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("bt/");

    //@@author aquarinte
    /* Option definitions */
    public static final String OPTION_OWNER = "-o";
    public static final String OPTION_PETPATIENT = "-p";
    public static final String OPTION_APPOINTMENT = "-a";
    public static final String OPTIONFORCE_OWNER = "-fo";
    public static final String OPTIONFORCE_PETPATIENT = "-fp";
    public static final String OPTIONFORCE_APPOINTMENT = "-fa";

    private static CliSyntax instance;

    private static final Set<String> prefixes = Stream.of(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_NRIC, PREFIX_BREED, PREFIX_SPECIES,
            PREFIX_COLOUR, PREFIX_BLOODTYPE, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG)
            .map(p -> p.toString())
            .collect(Collectors.toSet());

    private static final Set<String> commandWords = Stream.of(
            AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD, CalendarViewCommand.COMMAND_WORD).collect(Collectors.toSet());

    private static final Set<String> options = Stream.of(OPTION_OWNER, OPTION_PETPATIENT, OPTION_APPOINTMENT,
            OPTIONFORCE_OWNER, OPTIONFORCE_PETPATIENT, OPTIONFORCE_APPOINTMENT)
            .collect(Collectors.toSet());

    public static CliSyntax getInstance() {
        if (instance == null) {
            instance = new CliSyntax();
        }
        return instance;
    }

    public Set<String> getCommandWords() {
        return CliSyntax.commandWords;
    }

    public Set<String> getPrefixes() {
        return CliSyntax.prefixes;
    }

    public Set<String> getOptions() {
        return CliSyntax.options;
    }

}
