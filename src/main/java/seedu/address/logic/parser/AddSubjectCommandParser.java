package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddSubjectCommand;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.subject.Subject;

//@@author TeyXinHui
/**
 * Parses input arguments and creates a new AddSubjectCommand object
 */
public class AddSubjectCommandParser implements Parser<AddSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSubjectCommand
     * and returns an EditSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubjectCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            parseSubjectsForEdit(argMultimap.getAllValues(PREFIX_SUBJECT)).ifPresent(editPersonDescriptor::setSubjects);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AddSubjectCommand.MESSAGE_NOT_EDITED);
        }

        return new AddSubjectCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>} if {@code subjects} is non-empty.
     * If {@code subjects} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Subject>} containing zero subjects.
     */
    private Optional<Set<Subject>> parseSubjectsForEdit(Collection<String> subjects) throws IllegalValueException {
        assert subjects != null;

        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> subjectSet = subjects.size() == 1 && subjects.contains("")
                ? Collections.emptySet() : subjects;
        return Optional.of(ParserUtil.parseSubjects(subjectSet));
    }
    //@@author
}
