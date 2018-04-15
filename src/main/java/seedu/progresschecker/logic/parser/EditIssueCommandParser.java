package seedu.progresschecker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.EditIssueCommand;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Labels;

//@@author adityaa1998
/**
 * Parses input arguments and creates a new EditIssueCommand object
 */
public class EditIssueCommandParser implements Parser<EditIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIssueCommand
     * and returns an EditIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIssueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES, PREFIX_MILESTONE, PREFIX_BODY,
                        PREFIX_LABEL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIssueCommand.MESSAGE_USAGE));
        }

        EditIssueCommand.EditIssueDescriptor editIssueDescriptor = new EditIssueCommand.EditIssueDescriptor();
        try {
            ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).ifPresent(editIssueDescriptor::setTitle);
            parseAssigneesForEdit(argMultimap.getAllValues(PREFIX_ASSIGNEES))
                    .ifPresent(editIssueDescriptor::setAssignees);
            ParserUtil.parseMilestone(argMultimap.getValue(PREFIX_MILESTONE))
                    .ifPresent(editIssueDescriptor::setMilestone);
            ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY))
                    .ifPresent(editIssueDescriptor::setBody);
            parseLabelsForEdit(argMultimap.getAllValues(PREFIX_LABEL)).ifPresent(editIssueDescriptor::setLabels);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editIssueDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        }

        try {
            return new EditIssueCommand(index, editIssueDescriptor);
        } catch (CommandException ce) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        } catch (IOException ie) {
            throw new ParseException(EditIssueCommand.MESSAGE_NOT_EDITED);
        }
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Labels>} if {@code labels} is non-empty.
     * If {@code labels} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Labels>} containing zero labels.
     */
    private Optional<Set<Labels>> parseLabelsForEdit(Collection<String> labels) throws IllegalValueException {
        assert labels != null;

        if (labels.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> labelSet = labels.size() == 1 && labels.contains("") ? Collections.emptySet() : labels;
        return Optional.of(ParserUtil.parseLabels(labels));
    }

    /**
     * Parses {@code Collection<String> assignees} into a {@code Set<Assignees>} if {@code assignees} is non-empty.
     * If {@code assignees} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Assignees>} containing zero assignees.
     */
    private Optional<Set<Assignees>> parseAssigneesForEdit(Collection<String> assignees) throws IllegalValueException {
        assert assignees != null;

        if (assignees.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> assigneesSet = assignees.size() == 1
                && assignees.contains("") ? Collections.emptySet() : assignees;
        return Optional.of(ParserUtil.parseAssignees(assignees));
    }
}
