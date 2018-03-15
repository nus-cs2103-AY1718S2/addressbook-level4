package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Rating;

/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser implements Parser<RateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                        PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE);

        Index index;
        Rating rating;

        if (!arePrefixesPresent(argMultimap,
                PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE)
                || argMultimap.getPreamble().isEmpty()
                || !areAllFieldsSupplied(argMultimap,
                PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            double technicalSkillsScore = ParserUtil.parseTechnicalSkillsScore(
                    argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE)).get();
            double communicationSkillsScore = ParserUtil.parseCommunicationSkillsScore(
                    argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE)).get();
            double problemSolvingSkillsScore = ParserUtil.parseProblemSolvingSkillsScore(
                    argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE)).get();
            double experienceScore = ParserUtil.parseExperienceScore(
                    argMultimap.getValue(PREFIX_EXPERIENCE_SCORE)).get();
            rating = new Rating(technicalSkillsScore, communicationSkillsScore, problemSolvingSkillsScore,
                    experienceScore);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RateCommand(index, rating);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areAllFieldsSupplied(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> !Optional.of(argumentMultimap.getValue(prefix)).equals(""));
    }
}
