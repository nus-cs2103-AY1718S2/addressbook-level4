package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

//@@author TeyXinHui
/**
 * Edits the subject details of the student at a specified index.
 */
public class AddSubjectCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addsub";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds on the new subjects to the student's "
            + "subjects identified by the index number used in the last student listing. Duplicate subject input "
            + "will not alter the original subject in the subject list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT SUBJECT_GRADE...]...\n"
            + "Example: " + COMMAND_WORD + " 1 sub/Jap A1";

    public static final String MESSAGE_ADD_SUBJECT_SUCCESS = "Edited Person: ";
    public static final String MESSAGE_NEW_SUBJECTS = ". Updated Subjects: ";
    public static final String MESSAGE_NOT_EDITED = "At least one field to add must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddSubjectCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
        StringBuilder result = new StringBuilder();
        try {
            model.updatePerson(personToEdit, editedPerson);
            model.deletePage(personToEdit);
            model.addPage(editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(result.append(MESSAGE_ADD_SUBJECT_SUCCESS).append(editedPerson.getName())
                .append(MESSAGE_NEW_SUBJECTS).append(editedPerson.getSubjects()).toString());
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Subject> updatedSubjects = new HashSet<>(personToEdit.getSubjects());
        Set<Subject> newSubjects = new HashSet<>(editPersonDescriptor.getSubjectsAsSet());
        checkIfSubjectExists(newSubjects, updatedSubjects);
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
        Cca updatedCca = editPersonDescriptor.getCca().orElse(personToEdit.getCca());
        InjuriesHistory updatedInjuriesHistory = editPersonDescriptor.getInjuriesHistory()
                .orElse(personToEdit.getInjuriesHistory());
        NextOfKin updatedNextOfKin = editPersonDescriptor.getNextOfKin().orElse(personToEdit.getNextOfKin());
        return new Person(updatedName, updatedNric, updatedTags, updatedSubjects, updatedRemark, updatedCca,
                updatedInjuriesHistory, updatedNextOfKin);
    }

    /**
     * Checks if the new subjects to be added exist in original subject list.
     * If the subject exists, the subject will not be added to the list. Else, it will be added.
     */
    public static void checkIfSubjectExists(Set<Subject> newSubjects, Set<Subject> subjectList) {
        boolean isPresent = false;
        for (Subject subToAdd : newSubjects) {
            for (Subject sub : subjectList) {
                if (subToAdd.subjectName.equals(sub.subjectName)) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                subjectList.add(subToAdd);
            }
            isPresent = false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubjectCommand)) {
            return false;
        }

        // state check
        AddSubjectCommand e = (AddSubjectCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }
    //@@author
}
