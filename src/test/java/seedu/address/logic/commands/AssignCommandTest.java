package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.runner.Runner;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * AssignCommand.
 */
public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //refer to TypicalPersons.java for the list of default Persons initialized in the model

    @Test
    public void execute_assignOneValidRunnerAndOneValidCustomer_success() throws Exception {
        int runnerIndex = 5;
        int customerIndex = 2;

        //produce AssignCommand(runner index, customer index...)
        AssignCommand assignCommand = prepareCommand(Index.fromZeroBased(runnerIndex),
                Index.fromZeroBased(customerIndex));

        //get runner
        Person runner = model.getFilteredPersonList().get(runnerIndex);
        //get customer
        Person customer = model.getFilteredPersonList().get(customerIndex);

        List<Person> customers = new ArrayList<>();
        customers.add(customer);

        //build editedRunner (assigned with customer)
        Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();
        //build editedCustomer (assigned with runner)
        Person editedCustomer = new PersonBuilder(customer).withRunner((Runner) runner).buildCustomer();

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(runnerIndex), editedRunner);
        expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex), editedCustomer);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignOneValidRunnerAndTwoValidCustomers_success() throws Exception {
        int runnerIndex = 5;
        int customerIndex1 = 2;
        int customerIndex2 = 3;

        //produce AssignCommand(runner index, customer index...)
        AssignCommand assignCommand = prepareCommand(Index.fromZeroBased(runnerIndex),
                Index.fromZeroBased(customerIndex1), Index.fromZeroBased(customerIndex2));

        //get runner
        Person runner = model.getFilteredPersonList().get(runnerIndex);
        //get customer
        Person customer1 = model.getFilteredPersonList().get(customerIndex1);
        Person customer2 = model.getFilteredPersonList().get(customerIndex2);

        List<Person> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        //build editedRunner (assigned with customers)
        Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();
        //build editedCustomer (assigned with runner)
        Person editedCustomer1 = new PersonBuilder(customer1).withRunner((Runner) runner).buildCustomer();
        Person editedCustomer2 = new PersonBuilder(customer2).withRunner((Runner) runner).buildCustomer();

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(runnerIndex), editedRunner);
        expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex1), editedCustomer1);
        expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex2), editedCustomer2);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }


    //Test_assertFailure: Assign from empty filtered list
    @Test
    public void execute_assignInvalidRunnerIndexAndTwoValidCustomers_failure() throws Exception {
        int runnerIndex = 20;
        int customerIndex1 = 2;
        int customerIndex2 = 3;

        //produce AssignCommand(runner index, customer index...)
        AssignCommand assignCommand = prepareCommand(Index.fromZeroBased(runnerIndex),
                Index.fromZeroBased(customerIndex1), Index.fromZeroBased(customerIndex2));

        try {
            //get runner
            Person runner = model.getFilteredPersonList().get(runnerIndex);
            //get customer
            Person customer1 = model.getFilteredPersonList().get(customerIndex1);
            Person customer2 = model.getFilteredPersonList().get(customerIndex2);

            List<Person> customers = new ArrayList<>();
            customers.add(customer1);
            customers.add(customer2);

            //build editedRunner (assigned with customers)
            Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();
            //build editedCustomer (assigned with runner)
            Person editedCustomer1 = new PersonBuilder(customer1).withRunner((Runner) runner).buildCustomer();
            Person editedCustomer2 = new PersonBuilder(customer2).withRunner((Runner) runner).buildCustomer();

            String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(runnerIndex), editedRunner);
            expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex1), editedCustomer1);
            expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex2), editedCustomer2);

        } catch (Exception e) {
            assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    //Test_assertFailure: Assign runner to runner
    @Test
    public void execute_assignRunnerToRunner_failure() throws Exception {
        int runnerIndex = 5;
        int customerIndex1 = 6;

        //produce AssignCommand(runner index, customer index...)
        AssignCommand assignCommand = prepareCommand(Index.fromZeroBased(runnerIndex));

        try {
            //get runner
            Person runner = model.getFilteredPersonList().get(runnerIndex);
            //get customer
            Person customer1 = model.getFilteredPersonList().get(customerIndex1);

            List<Person> customers = new ArrayList<>();
            customers.add(customer1);

            //build editedRunner (assigned with customers)
            Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();
            //build editedCustomer (assigned with runner)
            Person editedCustomer1 = new PersonBuilder(customer1).withRunner((Runner) runner).buildCustomer();

            String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(runnerIndex), editedRunner);
            expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex1), editedCustomer1);

        } catch (Exception e) {
            assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    //Test_assertFailure: Assign runner to customer
    @Test
    public void execute_assignRunnerToCustomer_failure() throws Exception {
        int runnerIndex = 2;
        int customerIndex1 = 5;

        //produce AssignCommand(runner index, customer index...)
        AssignCommand assignCommand = prepareCommand(Index.fromZeroBased(runnerIndex));

        try {
            //get runner
            Person runner = model.getFilteredPersonList().get(runnerIndex);
            //get customer
            Person customer1 = model.getFilteredPersonList().get(customerIndex1);

            List<Person> customers = new ArrayList<>();
            customers.add(customer1);

            //build editedRunner (assigned with customers)
            Person editedRunner = new PersonBuilder(runner).withCustomers(customers).buildRunner();
            //build editedCustomer (assigned with runner)
            Person editedCustomer1 = new PersonBuilder(customer1).withRunner((Runner) runner).buildCustomer();

            String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, editedRunner);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(runnerIndex), editedRunner);
            expectedModel.updatePerson(model.getFilteredPersonList().get(customerIndex1), editedCustomer1);

        } catch (Exception e) {
            assertCommandFailure(assignCommand, model, String.format(AssignCommand.MESSAGE_NOT_A_RUNNER,
                    runnerIndex + 1));
        }
    }

    //TODO: implement the test cases below!
    //Test_assertSuccess: Undo
    //Test_assertSuccess: Redo

    /*
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    */

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    /*
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first person edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    /*
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
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
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Sets up AssignCommand object with the correct model, CommandHistory and UndoRedoStack
     * Returns an {@code AssignCommand} with parameters {@code runnerIndex} and {@code customerIndex...}
     */
    private AssignCommand prepareCommand(Index runnerIndex, Index... customerIndex) {
        AssignCommand assignCommand = new AssignCommand(runnerIndex, customerIndex);
        assignCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return assignCommand;
    }
}
