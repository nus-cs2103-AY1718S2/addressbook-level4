package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.smplatform.Link;
import seedu.address.model.smplatform.SocialMediaPlatform;
import seedu.address.model.smplatform.SocialMediaPlatformBuilder;

//@@author Nethergale
/**
 * Adds social media platforms to the specified person in the address book.
 */
public class AddPlatformCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addplatform";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds social media platforms to the person "
            + "identified by the index number used in the last person listing through website links.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINK + "LINK]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "www.facebook.com/johndoe";

    public static final String MESSAGE_ADD_PLATFORM_SUCCESS = "Platform(s) successfully added to %1$s.";
    public static final String MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS = "Platform(s) successfully cleared for %1$s.";
    public static final String MESSAGE_LINK_COLLECTION_EMPTY = "At least 1 link field should be specified.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Map<String, Link> linkMap;
    private final Map<String, SocialMediaPlatform> socialMediaPlatformMap;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param linkMap as defined by the social media platform type as key and link as value
     */
    public AddPlatformCommand(Index index, Map<String, Link> linkMap) {
        requireNonNull(index);
        requireNonNull(linkMap);

        this.index = index;
        this.linkMap = linkMap;
        socialMediaPlatformMap = new HashMap<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (socialMediaPlatformMap.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS, editedPerson.getName()));
        }
        return new CommandResult(String.format(MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            addToSocialMediaPlatformMap();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        addCurrentSocialMediaPlatforms();
        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), socialMediaPlatformMap, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPlatformCommand)) {
            return false;
        }

        // state check
        AddPlatformCommand e = (AddPlatformCommand) other;
        return index.equals(e.index)
                && linkMap.equals(e.linkMap);
    }

    /**
     * Constructs social media platform objects depending on the link type and adds them to the map.
     */
    private void addToSocialMediaPlatformMap() throws IllegalValueException {
        for (String type : linkMap.keySet()) {
            socialMediaPlatformMap.put(type, SocialMediaPlatformBuilder.build(type, linkMap.get(type)));
        }
    }

    /**
     * Adds back the social media platforms not found in the edited person into the map only if it is not empty.
     */
    private void addCurrentSocialMediaPlatforms() {
        if (!socialMediaPlatformMap.isEmpty()) {
            for (String key : personToEdit.getSocialMediaPlatformMap().keySet()) {
                socialMediaPlatformMap.putIfAbsent(key, personToEdit.getSocialMediaPlatformMap().get(key));
            }
        }
    }
}
