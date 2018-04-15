package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.util.ListPanelController;

//@@author shookshire
/**
 * Remove the specified details of an existing client in the address book.
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "re";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the subject from the client identified "
            + "by the index number used in the last client listing. "
            + "If the specified subject exists it would be removed from the client. "
            + "Input subject should be a single word without space.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_SUBJECT + "SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CATEGORY + "s "
            + PREFIX_SUBJECT + "math";

    public static final String MESSAGE_REMOVE_CLIENT_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_VALUE_DONT_EXIST = "The inputted subject does not exist";
    public static final String MESSAGE_LAST_VALUE = "The last subject cannot be removed.\n"
            + "Recommended to delete or close client instead";

    public static final String REMOVE_VALIDATION_REGEX = "[a-zA-Z]+";

    private final Index index;
    private final Category category;
    private final Subject toRemove;

    private Client personToEdit;
    private Client editedPerson;


    /**
     * @param index of the person in the filtered person list to edit
     * @param toRemove the subject to be removed
     */
    public RemoveCommand(Index index, Subject toRemove, Category category) {
        requireNonNull(index);
        requireNonNull(toRemove);
        requireNonNull(category);

        this.category = category;
        this.index = index;
        this.toRemove = toRemove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateClient(personToEdit, editedPerson, category);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        return new CommandResult(String.format(MESSAGE_REMOVE_CLIENT_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());

        if (!isValidRemovableSubject(toRemove)) {
            throw new CommandException(MESSAGE_USAGE);
        }

        if (!containsSubject(personToEdit, toRemove)) {
            throw new CommandException(MESSAGE_VALUE_DONT_EXIST);
        }

        if (!isMoreThanOne(personToEdit.getSubject())) {
            throw new CommandException(MESSAGE_LAST_VALUE);
        }

        editedPerson = createEditedPerson(personToEdit, toRemove);
    }

    /**
     * @param personToEdit the client that we wish to remove a subject from
     * @param toRemove the subject to be removed
     * Returns true if the subject exists
     */
    private static boolean containsSubject(Client personToEdit, Subject toRemove) {
        String originalSubject = personToEdit.getSubject().toString();
        ArrayList<String> originalSubjectArrayList = new ArrayList<>(Arrays.asList(originalSubject.split(" ")));

        return originalSubjectArrayList.contains(toRemove.value);
    }

    private static boolean isValidRemovableSubject(Subject test) {
        return test.value.matches(REMOVE_VALIDATION_REGEX);
    }

    private static boolean isMoreThanOne(Subject test) {
        String[] testArray = test.value.split(" ");
        return testArray.length > 1;
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Client createEditedPerson(Client personToEdit, Subject toRemove) {
        assert personToEdit != null;

        String editSubjects = personToEdit.getSubject().value;
        ArrayList<String> editSubjectArray = new ArrayList<>(Arrays.asList(editSubjects.split(" ")));
        editSubjectArray.remove(toRemove.value);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < editSubjectArray.size(); i++) {
            sb.append(editSubjectArray.get(i));
            sb.append(" ");
        }

        return new Client(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getLocation(),
                personToEdit.getGrade(), new Subject(sb.toString()), personToEdit.getCategory());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveCommand)) {
            return false;
        }

        // state check
        RemoveCommand r = (RemoveCommand) other;
        return index.equals(r.index)
                && Objects.equals(editedPerson, r.editedPerson)
                && Objects.equals(personToEdit, r.personToEdit);
    }

}

