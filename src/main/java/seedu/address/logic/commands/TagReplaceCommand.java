package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.omg.IOP.TAG_CODE_SETS;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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

    public static final String MESSAGE_REPLACE_TAG_SUCCESS = "Replaced Tag: %1$s";

    private Tag tagToReplace;
    private List<Tag> TagSet = new LinkedList<>();
    private Tag[] TagArray = new Tag[2];

    public TagReplaceCommand(List<Tag> TagSet) {
        requireNonNull(TagSet);
        this.TagSet = TagSet;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(TagSet);
        try {
            model.replaceTag(TagSet);
        } catch (TagNotFoundException error) {
            throw new AssertionError("The target tag cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_REPLACE_TAG_SUCCESS, TagArray[0]));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> lastShownList = addressBook.getTagList();
        TagSet.toArray(TagArray);
        if (!lastShownList.contains(TagArray[0])) {
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
