package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA_PLATFORM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.smplatform.SocialMediaPlatform;

//@@author Nethergale
/**
 * Removes the specified social media platforms of a person identified
 * using it's last displayed index from the address book.
 */
public class RemovePlatformCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeplatform";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified social media platforms "
            + "of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SOCIAL_MEDIA_PLATFORM + "PLATFORM]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SOCIAL_MEDIA_PLATFORM + "facebook";

    public static final String MESSAGE_REMOVE_PLATFORM_SUCCESS = "Platform(s) removed from %1$s.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PLATFORM_MAP_NOT_EDITED = "No changes can be made from the given platform info.";

    private final Index targetIndex;
    private final Set<String> platformsToRemove;
    private final Map<String, SocialMediaPlatform> socialMediaPlatformMap;

    private Person personToEdit;
    private Person editedPerson;

    public RemovePlatformCommand(Index targetIndex, Set<String> platformsToRemove) {
        this.targetIndex = targetIndex;
        this.platformsToRemove = platformsToRemove;
        socialMediaPlatformMap = new HashMap<>();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());

        try {
            removeFromSocialMediaPlatformMap();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), socialMediaPlatformMap, personToEdit.getTags());
    }

    /**
     * Removes the social media platform mappings from the map if specified.
     *
     * @throws IllegalValueException if no changes are made
     */
    private void removeFromSocialMediaPlatformMap() throws IllegalValueException {
        if (!platformsToRemove.isEmpty()) {
            socialMediaPlatformMap.putAll(personToEdit.getSocialMediaPlatformMap());

            for (String platform : platformsToRemove) {
                socialMediaPlatformMap.remove(platform.toLowerCase());
            }

            if (socialMediaPlatformMap.equals(personToEdit.getSocialMediaPlatformMap())) {
                throw new IllegalValueException(MESSAGE_PLATFORM_MAP_NOT_EDITED);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePlatformCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemovePlatformCommand) other).targetIndex) // state check
                && this.platformsToRemove.equals(((RemovePlatformCommand) other).platformsToRemove));
    }
}
