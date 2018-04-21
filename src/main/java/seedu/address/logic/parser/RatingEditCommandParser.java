package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RatingEditCommand;
import seedu.address.logic.commands.RatingEditCommand.EditRatingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kexiaowen
/**
 * Parses input arguments and creates a new RatingEditCommand object
 */
public class RatingEditCommandParser implements Parser<RatingEditCommand> {
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    /**
     * Parses the given {@code String} of arguments in the context of the RatingEditCommand
     * and returns a RatingEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingEditCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                        PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_MISSING_INDEX));
        }

        EditRatingDescriptor editRatingDescriptor = new EditRatingDescriptor();
        try {
            if (argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setTechnicalSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setCommunicationSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setProblemSolvingSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_EXPERIENCE_SCORE).isPresent()) {
                editRatingDescriptor.setExperienceScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_EXPERIENCE_SCORE)).get());
            }
            logger.info("Parsed user arguments of rating-edit command");
            logger.info("t/" + editRatingDescriptor.getTechnicalSkillsScore().get());
            logger.info("c/" + editRatingDescriptor.getCommunicationSkillsScore().get());
            logger.info("p/" + editRatingDescriptor.getProblemSolvingSkillsScore().get());
            logger.info("e/" + editRatingDescriptor.getExperienceScore().get());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editRatingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(RatingEditCommand.MESSAGE_NOT_EDITED);
        }

        return new RatingEditCommand(index, editRatingDescriptor);
    }
}
