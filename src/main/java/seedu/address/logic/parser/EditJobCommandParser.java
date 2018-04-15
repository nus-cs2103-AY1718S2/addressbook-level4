package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments in the context of the EditJobCommand
 * and creates a new EditJobCommand object
 */
public class EditJobCommandParser implements Parser<EditJobCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditJobCommand
     * and returns an EditJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditJobCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentWithOption argWithOption = new ArgumentWithOption(args);
        args = argWithOption.getArgs();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBTITLE, PREFIX_LOCATION, PREFIX_SKILLS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditJobCommand.MESSAGE_USAGE));
        }

        EditJobCommand.EditJobDescriptor editJobDescriptor = new EditJobCommand.EditJobDescriptor();
        try {
            ParserUtil.parseJobTitle(argMultimap.getValue(PREFIX_JOBTITLE)).ifPresent(editJobDescriptor::setJobTitle);
            ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).ifPresent(editJobDescriptor::setLocation);
            ParserUtil.parseSkill(argMultimap.getValue(PREFIX_SKILLS)).ifPresent(editJobDescriptor::setSkill);

            if (argWithOption.isOption(EditJobCommand.COMMAND_OPTION_ADD_TAG)) {
                parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editJobDescriptor::setNewTags);
            } else if (argWithOption.isOption(EditCommand.COMMAND_OPTION_DELETE_TAG)) {
                parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editJobDescriptor::setDeletedTags);
            } else {
                parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editJobDescriptor::setTags);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editJobDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditJobCommand.MESSAGE_NOT_EDITED);
        }

        return new EditJobCommand(index, editJobDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

