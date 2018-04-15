package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.person.Person;

//@@author alexawangzi
/**
 * Match a tutor and a student in STUtor
 */
public class MatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_WORD_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Match a student and a tutor for lesson.\n"
            + "Parameters: INDEX_A, INDEX_B (must be non-zero positive integers, one student and one tutor.) "
            + "Example: " + COMMAND_WORD + " 4 7 ";

    public static final String MESSAGE_MATCH_SUCCESS = "Created new match %1$s\n";
    public static final String MESSAGE_MATCH_FAILED = "Matching failed.\n %1$s";
    public static final String MESSAGE_MISMATCH_WRONG_ROLE = "Incompatible role.";
    public static final String MESSAGE_MISMATCH_WRONG_SUBJECT = "Incompatible subject. ";
    public static final String MESSAGE_MISMATCH_WRONG_LEVEL = "Incompatible level. ";
    public static final String MESSAGE_MISMATCH_WRONG_PRICE = "Incompatible price.";
    public static final String MESSAGE_MISMATCH_ALREADY_MATCHED = "The two persons are already matched. ";
    public static final String MESSAGE_MISSING_FIELDS = "The person has missing fields. ";

    private final Index indexA;
    private final Index indexB;
    private Person student;
    private Person tutor;


    /**
     * @param indexA,of the person in the filtered person list to match
     */
    public MatchCommand(Index indexA, Index indexB) {
        requireAllNonNull(indexA, indexB);
        this.indexA = indexA;
        this.indexB = indexB;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addPair(student, tutor);
        } catch (DuplicatePairException dpe) {
            throw new CommandException(MESSAGE_MISMATCH_ALREADY_MATCHED);
        }
        return new CommandResult(String.format(MESSAGE_MATCH_SUCCESS, student.getName().fullName
                + " and " + tutor.getName().fullName));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (indexA.getZeroBased() >= lastShownList.size() || indexB.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        student = lastShownList.get(indexA.getZeroBased());
        tutor = lastShownList.get(indexB.getZeroBased());

        if (student.hasMissingFieldForMatch() || tutor.hasMissingFieldForMatch()) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISSING_FIELDS));
        }

        //filter invalid matchings
        if (student.getRole().equals(tutor.getRole())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
        }

        //standardize input order : person A is student, person B is tutor
        if (!student.getRole().value.equals("Student")) {
            Person temp = student;
            student = tutor;
            tutor = temp;
        }

        if (!student.getSubject().equals(tutor.getSubject())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
        }
        if (!student.getLevel().equals(tutor.getLevel())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
        }

        if (Integer.parseInt(student.getPrice().value) < Integer.parseInt(tutor.getPrice().value)) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
        }

    }

    @Override
    public boolean equals(Object other) {
        return this == other //short circuit for same object
            || (other instanceof MatchCommand
            && indexA.equals(((MatchCommand) other).indexA) && indexB.equals(((MatchCommand) other).indexB));    }
}
