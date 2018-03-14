package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class ChangeTagColorCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changeTagColor";
    public static final String COMMAND_ALIAS = "color";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current color of the tag specified by name"
            + "\nParameters: TAGNAME (must be an existing tag) COLOR\n"
            + "Example: " + COMMAND_WORD + " friends red\n"
            + "Available colors are: teal, red, yellow, blue, orange, brown, green, pink, black, grey";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Tag %1$s's color changed to %2$s";
    public static final String MESSAGE_NOT_EDITED = "Wrong tag name or color provided";

    private final String tagName;
    private final String tagColor;

    private Tag tagToEdit;
    private Tag editedTag;

    /**
     * @param name of the tag to edit
     * @param color to change the tag into
     */
    public ChangeTagColorCommand(String name, String color) {
        requireNonNull(name);
        requireNonNull(color);

        this.tagName = name;
        this.tagColor = color;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (Tag.isValidTagColor(tagColor)) {
            throw new AssertionError("Tag color is not defined");
        }
        try {
            model.updateTag(tagToEdit, editedTag);
        } catch (TagNotFoundException tnfe ) {
            throw new CommandException("The tag specified is not associated with any person");
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException("Person not found");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, tagName, tagColor));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        editedTag = new Tag(tagName, tagColor);
        List<Tag> allTags = model.getAddressBook().getTagList();
        for (Tag tag: allTags) {
            if (tag.tagName.equals(tagName)) {
                tagToEdit = tag;
                return;
            }
        }
        throw new CommandException("The tag specified is not associated with any person");
    }
}
