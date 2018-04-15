package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.model.person.BodyMassIndex.ACCEPTABLE_CLASSIFICATION;
import static seedu.address.model.person.BodyMassIndex.OBESE_CLASSIFICATION;
import static seedu.address.model.person.BodyMassIndex.OVERWEIGHT_CLASSIFICATION;
import static seedu.address.model.person.BodyMassIndex.UNDERWEIGHT_CLASSIFICATION;

import java.util.Arrays;

import seedu.address.logic.commands.ClassificationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsClassificationPredicate;

//@@author wenhao53
/**
 * Parses input classifications and creates a new ClassificationCommand object
 */
public class ClassificationCommandParser implements Parser<ClassificationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClassificationCommand
     * and returns an ClassificationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClassificationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassificationCommand.MESSAGE_USAGE));
        }

        String[] classificationKeywords = trimmedArgs.split("\\s+");

        checkClassificationKeywordValidity(classificationKeywords);

        return new ClassificationCommand(
                new NameContainsClassificationPredicate(Arrays.asList(classificationKeywords)));
    }

    /**
     * Checks the validity of the keywords input in the context of a Classification Command
     * @throws ParseException if the keyword does not match any of the acceptable keywords
     */
    public void checkClassificationKeywordValidity(String[] classificationKeywords) throws ParseException {
        for (String keyword:classificationKeywords) {
            if (!isValidClassifcationKeyword(keyword)) {
                throw new ParseException(
                        String.format(ClassificationCommand.INVALID_KEYWORD, ClassificationCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the given keyword in the parser to see it if matches any of the allowable classification
     */
    public boolean isValidClassifcationKeyword(String keyword) {
        return keyword.compareToIgnoreCase(ACCEPTABLE_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(OBESE_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(OVERWEIGHT_CLASSIFICATION) == 0
                || keyword.compareToIgnoreCase(UNDERWEIGHT_CLASSIFICATION) == 0;
    }

}
