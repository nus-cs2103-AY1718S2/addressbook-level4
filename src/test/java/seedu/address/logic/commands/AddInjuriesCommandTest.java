package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INJURIES_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author chuakunhong

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class AddInjuriesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        AddInjuriesHistoryCommand addInjuriesHistoryCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(addInjuriesHistoryCommand.MESSAGE_REMARK_PERSON_SUCCESS,
                                                editedPerson.getInjuriesHistory(), editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addInjuriesHistoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(GEORGE.getName().toString()).withNric(VALID_NRIC_BOB)
                .withTags(VALID_TAG_HUSBAND).withInjuriesHistory(VALID_INJURIES_HISTORY)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(GEORGE.getName().toString())
                .withNric(VALID_NRIC_BOB).withTags(VALID_TAG_HUSBAND).withInjuriesHistory("").build();
        AddInjuriesHistoryCommand addInjuriesHistoryCommand = prepareCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(AddInjuriesHistoryCommand.MESSAGE_REMARK_PERSON_SUCCESS,
                descriptor.getInjuriesHistory().get(), editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);

        assertCommandSuccess(addInjuriesHistoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() throws IOException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        AddInjuriesHistoryCommand addInjuriesHistoryCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(addInjuriesHistoryCommand, model, addInjuriesHistoryCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IOException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        AddInjuriesHistoryCommand addInjuriesHistoryCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addInjuriesHistoryCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final AddInjuriesHistoryCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        AddInjuriesHistoryCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddInjuriesHistoryCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddInjuriesHistoryCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Returns an {@code AddInjuriesHistoryCommand} with parameters {@code index} and {@code descriptor}
     */
    private AddInjuriesHistoryCommand prepareCommand(Index index, EditPersonDescriptor descriptor) {
        AddInjuriesHistoryCommand addInjuriesHistoryCommand = new AddInjuriesHistoryCommand(index, descriptor);
        addInjuriesHistoryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addInjuriesHistoryCommand;
    }

    /**
     * A utility class to help with building EditPersonDescriptor objects.
     */
    public class EditPersonDescriptorBuilder {

        private EditPersonDescriptor descriptor;

        public EditPersonDescriptorBuilder() {
            descriptor = new EditPersonDescriptor();
        }

        public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
            this.descriptor = new EditPersonDescriptor();
        }

        /**
         * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
         */
        public EditPersonDescriptorBuilder(Person person) {
            descriptor = new EditPersonDescriptor();
            descriptor.setName(person.getName());
            descriptor.setNric(person.getNric());
            descriptor.setTags(person.getTags());
            descriptor.setSubjects(person.getSubjects());
            descriptor.setRemark(person.getRemark());
            descriptor.setInjuriesHistory(person.getInjuriesHistory());
        }

        /**
         * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
         */
        public EditPersonDescriptorBuilder withName(String name) {
            descriptor.setName(new Name(name));
            return this;
        }

        /**
         * Sets the {@code Nric} of the {@code EditPersonDescriptor} that we are building.
         */
        public EditPersonDescriptorBuilder withNric(String nric) {
            descriptor.setNric(new Nric(nric));
            return this;
        }

        /**
         * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
         * that we are building.
         */
        public EditPersonDescriptorBuilder withTags(String... tags) {
            Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
            descriptor.setTags(tagSet);
            return this;
        }

        /**
         * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
         * that we are building.
         */
        public EditPersonDescriptorBuilder withSubjects(String... subjects) {
            Set<Subject> subjectSet = Stream.of(subjects).map(Subject::new).collect(Collectors.toSet());
            descriptor.setSubjects(subjectSet);
            return this;
        }

        /**
         * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
         */
        public EditPersonDescriptorBuilder withRemark(String remark) {
            descriptor.setRemark(new Remark(remark));
            return this;
        }

        /**
         * Sets the {@code InjuriesHistory} of the {@code EditPersonDescriptor} that we are building.
         */
        public EditPersonDescriptorBuilder withInjuriesHistory(String injuriesHistory) {
            descriptor.setInjuriesHistory(new InjuriesHistory(injuriesHistory));
            return this;
        }

        public EditPersonDescriptor build() {
            return descriptor;
        }
    }
    //@@author
}
