package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.omg.IOP.TAG_CODE_SETS;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TagReplaceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagReplace";
    public static final String COMMAND_ALIAS = "tr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Replace specified tag from everyone in the "
            + "address book with the intended tag." + "Parameters: Tag \n"
            + "Example: " + COMMAND_WORD + " 1A" + " 2A\n"
            + "Example: " + COMMAND_ALIAS + " 1A" + " 2A\n";

    public static final String MESSAGE_REPLACE_TAG_SUCCESS = "Replace Tag: %1$s";

    private Tag tagToReplace;
    //private final EditPersonDescriptor editPersonDescriptor;
    private Set<Tag> TagSet = new HashSet<Tag>();
    private Tag[] TagArray = new Tag[2];

    public TagReplaceCommand(Set<Tag> TagSet) {
        requireNonNull(TagSet);
        this.TagSet = TagSet;
    }
    /*
    public TagReplaceCommand(Tag tagToReplace) {
        this.tagToReplace = tagToReplace;
    }
    */
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

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Nric nric;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.nric, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }


        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getNric().equals(e.getNric())
                    && getTags().equals(e.getTags());
        }
    }
}
