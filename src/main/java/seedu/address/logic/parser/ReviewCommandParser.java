package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Review;

/**
 * Parses input arguments and creates a new Review object
 */
public class ReviewCommandParser implements Parser<ReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReviewCommand
     * and returns a ReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Scanner sc = new Scanner(args);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));
        }

        if (!sc.hasNext()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(sc.next());
        while (sc.hasNext()) {
            sb.append(" " + sc.next());
        }
        String review = sb.toString();

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setReview(new Review(review));
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ReviewCommand.MESSAGE_NOT_EDITED);
        }

        return new ReviewCommand(index, editPersonDescriptor);
    }

}
