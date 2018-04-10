// @@author kush1509
package seedu.address.logic.parser.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.job.JobEditCommand;
import seedu.address.logic.commands.job.JobEditCommand.EditJobDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new JobEditCommand object
 */
public class JobEditCommandParser implements Parser<JobEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JobEditCommand
     * and returns an JobEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JobEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_POSITION, PREFIX_TEAM, PREFIX_LOCATION, PREFIX_NUMBER_OF_POSITIONS, PREFIX_SKILL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobEditCommand.MESSAGE_USAGE));
        }

        EditJobDescriptor editJobDescriptor = new EditJobDescriptor();
        try {
            ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION)).ifPresent(editJobDescriptor::setPosition);
            ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM)).ifPresent(editJobDescriptor::setTeam);
            ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).ifPresent(editJobDescriptor::setLocation);
            ParserUtil.parseNumberOfPositions(argMultimap.getValue(PREFIX_NUMBER_OF_POSITIONS))
                    .ifPresent(editJobDescriptor::setNumberOfPositions);
            parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(editJobDescriptor::setSkills);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editJobDescriptor.isAnyFieldEdited()) {
            throw new ParseException(JobEditCommand.MESSAGE_NOT_EDITED);
        }

        return new JobEditCommand(index, editJobDescriptor);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>} if {@code skills} is non-empty.
     * If {@code skills} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Skill>} containing zero skills.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws IllegalValueException {
        assert skills != null;

        if (skills.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = skills.size() == 1 && skills.contains("") ? Collections.emptySet() : skills;
        return Optional.of(ParserUtil.parseSkills(tagSet));
    }

}
