package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.ParserUtil.parseRemark;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

//@@author chuakunhong
/**
 * Edits the details of an existing person in the address book.
 */
public class AddRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addremark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds remarks to the student that you want. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARKS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Need help" + "\n";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark added: %1$s\nPerson: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddRemarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
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
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editPersonDescriptor.getRemark().get(),
                                                personToEdit.getName()));
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
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());
        Remark updatedRemark = parseRemark(((personToEdit.getRemark()).toString() + "\n"
                                            + editPersonDescriptor.getRemark().get().toString()));
        Cca updatedCca = editPersonDescriptor.getCca().orElse(personToEdit.getCca());
        InjuriesHistory updatedInjuriesHistory = editPersonDescriptor.getInjuriesHistory()
                                                .orElse(personToEdit.getInjuriesHistory());
        NameOfKin updatedNameOfKin = editPersonDescriptor.getNameOfKin().orElse(personToEdit.getNameOfKin());

        return new Person(updatedName, updatedNric, updatedTags, updatedSubjects, updatedRemark, updatedCca,
                            updatedInjuriesHistory, updatedNameOfKin);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRemarkCommand)) {
            return false;
        }

        // state check
        AddRemarkCommand e = (AddRemarkCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }
    //@@author
}
