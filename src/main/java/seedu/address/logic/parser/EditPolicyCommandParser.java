//@@author ValerianRey

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Issue;
import seedu.address.model.policy.Policy;

/**
 * Parses input arguments and creates a new EditPolicyCommand object
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGINNING_DATE,
                PREFIX_EXPIRATION_DATE, PREFIX_PRICE, PREFIX_ISSUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();
        try {
            ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_BEGINNING_DATE))
                    .ifPresent(editPolicyDescriptor::setBeginning);
            ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_EXPIRATION_DATE))
                    .ifPresent(editPolicyDescriptor::setExpiration);
            ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).ifPresent(editPolicyDescriptor::setPrice);
            Optional<List<Issue>> optIssues = parseIssuesForEditPolicy(argMultimap.getAllValues(PREFIX_ISSUE));
            if (optIssues.isPresent()) {
                editPolicyDescriptor.setCoverage(new Coverage(optIssues.get()));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }

    /**
     * Parses {@code List<String> issues} into a {@code List<Issue>} if {@code issues} is non-empty.
     * If {@code issues} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Issue>} containing zero tags.
     */
    private Optional<List<Issue>> parseIssuesForEditPolicy(List<String> issues) throws IllegalValueException {
        assert issues != null;

        if (issues.isEmpty()) {
            return Optional.empty();
        }
        List<String> issueList = issues.size() == 1 && issues.contains("") ? Collections.emptyList() : issues;
        return Optional.of(ParserUtil.parseIssues(issueList));
    }
}
