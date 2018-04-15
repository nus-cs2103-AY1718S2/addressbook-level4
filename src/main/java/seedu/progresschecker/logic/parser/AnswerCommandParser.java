package seedu.progresschecker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.AnswerCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.StudentAnswer;

//@@author iNekox3
/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    private static final int QUESTION_INDEX_INDEX = 0;
    private static final int ANSWER_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns an AnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String[] content = args.trim().split(" ", 2);

            QuestionIndex questionIndex;
            questionIndex = ParserUtil.parseQuestionIndex(content[QUESTION_INDEX_INDEX]);
            StudentAnswer studentAnswer = ParserUtil.parseStudentAnswer(content[ANSWER_INDEX]);

            return new AnswerCommand(questionIndex, studentAnswer);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }
    }

}
