package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author olimhc
/**
 * Represents a sort command
 */
public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final String COMMAND_WORD_NAME = "n";
    public static final String COMMAND_WORD_LOCATION = "l";
    public static final String COMMAND_WORD_SUBJECT = "s";
    public static final String COMMAND_WORD_GRADE = "g";
    public static final String COMMAND_WORD_TUTOR = "t";
    public static final String COMMAND_WORD_STUDENT = "s";

    public static final String MESSAGE_SUCCESS_TUTOR = "Sorted tutor's list according to";
    public static final String MESSAGE_SUCCESS_STUDENT = "Sorted student's list according to";

    private static final String USAGE_MESSAGE_LIST = " "
            + COMMAND_WORD  + " " + COMMAND_WORD_NAME + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_SUBJECT + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_GRADE + " " + PREFIX_CATEGORY + COMMAND_WORD_TUTOR + ", "
            + "to sort Tutor's list base on name, location, subject and level respectively.\n"
            + "Parameters: "
            + COMMAND_WORD  + " " + COMMAND_WORD_NAME + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_SUBJECT + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + COMMAND_WORD  + " " + COMMAND_WORD_GRADE + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + ", "
            + "to sort Student's list base on name, location, subject and level respectively.\n"
            + "Example: "
            + COMMAND_WORD + " " + COMMAND_WORD_LOCATION + " " + PREFIX_CATEGORY + COMMAND_WORD_STUDENT + "\n";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort selected list according to user choice.\n"
            + "Parameters:" + USAGE_MESSAGE_LIST;

    @Override
    public abstract CommandResult execute() throws CommandException;
}
