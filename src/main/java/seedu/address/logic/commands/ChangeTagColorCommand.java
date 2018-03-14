package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

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

    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Tag %1$s's color changed to %2$s";
    public static final String MESSAGE_TAG_NOT_IN_LIST = "The tag specified is not associated with any person";

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
        if (!Tag.isValidTagColor(tagColor)) {
            throw new AssertionError("Tag color is not defined");
        }
        try {
            model.updateTag(tagToEdit, editedTag);
        } catch (TagNotFoundException tnfe ) {
            throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException("Person not found");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, tagName, tagColor));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        editedTag = new Tag(tagName, tagColor);
        List<Tag> allTags = model.getAddressBook().getTagList();
        for (Tag tag: allTags) {
            if (tag.name.equals(tagName)) {
                tagToEdit = tag;
                return;
            }
        }
        throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeTagColorCommand)) {
            return false;
        }

        // state check
        ChangeTagColorCommand e = (ChangeTagColorCommand) other;
        return tagName.equals(e.tagName)
                && tagColor.equals(e.tagColor);
    }
}
