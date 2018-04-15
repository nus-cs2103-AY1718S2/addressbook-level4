package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Set;

import seedu.organizer.model.tag.Tag;

//@@author natania
/**
 * Removes a specified tag from all tasks in the organizer.
 */
public class RemoveTagsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tags from all tasks in the organizer.\n"
            + "Parameters: " + PREFIX_TAG + "TAG1 " + PREFIX_TAG + "TAG2\n"
            + "Example: " + COMMAND_WORD + " t/friends t/homework";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tags: %1$s";

    private final Set<Tag> tagList;

    public RemoveTagsCommand(Set<Tag> tagList) {
        this.tagList = tagList;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagList);

        for (Tag tag : tagList) {
            model.deleteTag(tag);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagsCommand // instanceof handles nulls
                && this.tagList.equals(((RemoveTagsCommand) other).tagList)); // state check
    }
}
