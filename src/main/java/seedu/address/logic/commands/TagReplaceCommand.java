package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TagReplaceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagReplace";
    public static final String COMMAND_ALIAS = "tr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Replace specified tag from everyone in the "
            + "address book with the intended tag." + "Parameters: Tag \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "1A " + PREFIX_TAG + "2A\n"
            + "Example: " + COMMAND_ALIAS + " " + PREFIX_TAG + "1A " + PREFIX_TAG + "2A";

    public static final String MESSAGE_REPLACE_TAG_SUCCESS = "Replaced Tag: From %1$s to %2$s";

    private Tag tagToReplace;
    private List<Tag> tagSet = new LinkedList<>();
    private Tag[] tagArray = new Tag[2];

    public TagReplaceCommand(List<Tag> tagSet) {
        requireNonNull(tagSet);
        this.tagSet = tagSet;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagSet);
        try {
            model.replaceTag(tagSet);
        } catch (TagNotFoundException error) {
            throw new AssertionError("The target tag cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_REPLACE_TAG_SUCCESS, tagArray[0], tagArray[1]));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> lastShownList = addressBook.getTagList();
        tagSet.toArray(tagArray);
        if (tagSet.isEmpty() || tagSet.size() == 1) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagReplaceCommand.MESSAGE_USAGE));
        }else if (!lastShownList.contains(tagArray[0])) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_ENTERED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagReplaceCommand // instanceof handles nulls
                && this.tagToReplace.equals(((TagReplaceCommand) other).tagToReplace)); // state check
    }
}
