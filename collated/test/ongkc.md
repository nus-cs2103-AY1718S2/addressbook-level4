# ongkc
###### /java/seedu/address/ui/DebtorCardTest.java
``` java
public class DebtorCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Debtor debtor = new DebtorBuilder().build();
        DebtorCard debtorCard = new DebtorCard(debtor, 1);
        uiPartRule.setUiPart(debtorCard);
        assertDebtorCardDisplay(debtorCard, debtor, 1);
    }

    @Test
    public void equals() {
        Debtor debtor = new DebtorBuilder().build();
        DebtorCard debtorCard = new DebtorCard(debtor, 0);

        // same person, same index -> returns true
        DebtorCard copy = new DebtorCard(debtor, 0);
        assertTrue(debtorCard.equals(copy));

        // same object -> returns true
        assertTrue(debtorCard.equals(debtorCard));

        // null -> returns false
        assertFalse(debtorCard.equals(null));

        // different types -> returns false
        assertFalse(debtorCard.equals(0));

    }


    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertDebtorCardDisplay(DebtorCard debtorCard, Debtor expectedDebtor, int expectedId) {
        guiRobot.pauseForHuman();

        DebtorCardHandle debtorCardHandle = new DebtorCardHandle(debtorCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", debtorCardHandle.getId());

        // verify person details are displayed correctly
        assertDebtorCardDisplays(expectedDebtor, debtorCardHandle);
    }
}
```
###### /java/seedu/address/ui/CreditorCardTest.java
``` java
public class CreditorCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Creditor creditor = new CreditorBuilder().build();
        CreditorCard creditorCard = new CreditorCard(creditor, 1);
        uiPartRule.setUiPart(creditorCard);
        assertCreditorCardDisplay(creditorCard, creditor, 1);
    }

    @Test
    public void equals() {
        Creditor creditor = new CreditorBuilder().build();
        CreditorCard creditorCard = new CreditorCard(creditor, 0);

        // same person, same index -> returns true
        CreditorCard copy = new CreditorCard(creditor, 0);
        assertTrue(creditorCard.equals(copy));

        // same object -> returns true
        assertTrue(creditorCard.equals(creditorCard));

        // null -> returns false
        assertFalse(creditorCard.equals(null));

        // different types -> returns false
        assertFalse(creditorCard.equals(0));

    }


    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCreditorCardDisplay(CreditorCard creditorCard, Creditor expectedCreditor, int expectedId) {
        guiRobot.pauseForHuman();

        CreditorCardHandle creditorCardHandle = new CreditorCardHandle(creditorCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", creditorCardHandle.getId());

        // verify person details are displayed correctly
        assertCreditorCardDisplays(expectedCreditor, creditorCardHandle);
    }
}
```
###### /java/seedu/address/logic/parser/AddTransactionCommandParserTest.java
``` java
public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() throws CommandException, DuplicatePersonException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing Transaction type prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + VALID_TRANSACTION_TYPE_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // missing payer prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + VALID_TRANSACTION_PAYEE_ONE
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // missing amount prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + VALID_TRANSACTION_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);
        // missing description prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + VALID_TRANSACTION_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);
        // missing payee prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + VALID_TRANSACTION_PAYEE_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // all prefix missing
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + VALID_TRANSACTION_SPLIT_METHOD_ONE
                        + VALID_TRANSACTION_PAYEE_ONE
                        + VALID_TRANSACTION_AMOUNT_ONE + VALID_TRANSACTION_DESCRIPTION_ONE
                        + VALID_TRANSACTION_PAYEE_ONE + VALID_TRANSACTION_SPLIT_METHOD_ONE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException, DuplicatePersonException {
        // invalid transaction type
        assertParseTransactionFailure(parser, INVALID_TRANSACTION_TYPE_DESC
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        // Payer or payee does not exist
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC_TWO
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_NONEXISTENT_PERSON);
        // Payer is payee
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC_THREE
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_PAYEE_IS_PAYER);
        // invalid amount
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                Amount.MESSAGE_AMOUNT_CONSTRAINTS);

        // invalid description
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + INVALID_DESCRIPTION_DESC
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid split method evenly
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + INVALID_SPLIT_METHOD_DESC_ONE,
                SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_ONE,
                "List of percentages can only take comma-separated integers");
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_THREE,
                "The number of percentage values does not match the number of persons involved."
                        + " Remember to include the payer in the count.");
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_TWO,
                "The sum of the percentages does not equal 100.");
        // invalid split method units
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_THREE
                        + INVALID_SPLIT_METHOD_DESC_UNITS_ONE,
                "List of units can only take comma-separated integers");
        // invalid split method units
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_THREE
                        + INVALID_SPLIT_METHOD_DESC_UNITS_TWO,
                "The number of units values does not match the number of persons involved."
                        + " Remember to include the payer in the count.");
        // two invalid values, only first invalid value reported
        assertParseTransactionFailure(parser, INVALID_TRANSACTION_TYPE_DESC
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        // too many prefit for paydebt command
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_THREE
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_TOO_MANY_PREFIXES_FOR_PAYDEBT);
        // non-empty preamble
        assertParseTransactionFailure(parser, PREAMBLE_NON_EMPTY + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));

    }

}
```
###### /java/seedu/address/logic/parser/DeleteTransactionCommandParserTest.java
``` java
public class DeleteTransactionCommandParserTest {

    private DeleteTransactionCommandParser parser = new DeleteTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() throws CommandException {
        assertParseSuccess(parser, "1", new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTransactionCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/AddTransactionCommandTest.java
``` java
public class AddTransactionCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTransactionCommand(null);
    }

    @Test
    public void execute_paymentTransactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub =
                new ModelStubAcceptingTransactionAdded();
        Transaction validTransaction = new TransactionBuilder().build();

        CommandResult commandResult = getAddTransactionCommand(validTransaction, modelStub).execute();
        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS, validTransaction),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
    }
    @Test
    public void execute_paymentTransactionRoundedAmountAcceptedByModel_addSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Transaction validTransaction = new TransactionBuilder().withAmount("12345").withSplitMethod("percentage")
                .withPercentages("50, 50").build();
        AddTransactionCommand addTransactionCommand = prepareCommand(validTransaction);
        String expectedMessage = String.format(addTransactionCommand.MESSAGE_SUCCESS,
                validTransaction);
        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_payDebtTransactionRoundedAmountAcceptedByModel_addSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Transaction validTransaction = new TransactionBuilder().withPayer(SampleDataUtil.getSamplePersons()[1])
                .withTransactionType("paydebt").withPayees("Alice Pauline").build();
        AddTransactionCommand addTransactionCommand = prepareCommand(validTransaction);
        String expectedMessage = String.format(addTransactionCommand.MESSAGE_SUCCESS,
                validTransaction);
        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_paymentTransactionUnitsAcceptedByModel_addSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Transaction validTransaction = model.getFilteredTransactionList().get(INDEX_SIXTH_TRANSACTION.getZeroBased());
        AddTransactionCommand addTransactionCommand = prepareCommand(validTransaction);
        String expectedMessage = String.format(addTransactionCommand.MESSAGE_SUCCESS,
                validTransaction);
        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paymentTransactionPercentageAcceptedByModel_addSuccessful() throws Exception {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Transaction validTransaction = model.getFilteredTransactionList().get(INDEX_SEVENTH_TRANSACTION.getZeroBased());

        AddTransactionCommand addTransactionCommand = prepareCommand(validTransaction);
        String expectedMessage = String.format(addTransactionCommand.MESSAGE_SUCCESS,
                validTransaction);
        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_paydebtTransactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub =
                new ModelStubAcceptingTransactionAdded();
        Transaction validTransaction = new TransactionBuilder().withAmount("12345")
                .withTransactionType("paydebt").build();

        CommandResult commandResult = getAddTransactionCommand(validTransaction, modelStub).execute();
        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS, validTransaction),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
    }
    @Test
    public void execute_personNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub =
                new ModelStubThrowingPersonNotFoundException();
        Transaction validTransaction = new TransactionBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTransactionCommand.MESSAGE_NONEXISTENT_PERSON);

        getAddTransactionCommand(validTransaction, modelStub).execute();
    }

    @Test
    public void equals() throws DuplicatePersonException {
        Transaction one = new TransactionBuilder().build();
        Transaction two = new TransactionBuilder().withTransactionType("paydebt").build();
        Transaction three = new TransactionBuilder().withPayees(
                VALID_TRANSACTION_PAYEE_ONE, VALID_TRANSACTION_PAYEE_TWO).build();
        AddTransactionCommand addOneCommand = new AddTransactionCommand(one);
        AddTransactionCommand addThreeCommand = new AddTransactionCommand(three);

        // same object -> returns true
        assertTrue(addOneCommand.equals(addOneCommand));

        // same values -> returns true
        AddTransactionCommand addOneCommandCopy = new AddTransactionCommand(one);
        assertTrue(addOneCommand.equals(addOneCommandCopy));

        // different types -> returns false
        assertFalse(addOneCommand.equals(1));

        // null -> returns false
        assertFalse(addOneCommand.equals(null));

        // different Transaction -> returns false
        assertFalse(addOneCommand.equals(addThreeCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given transaction.
     */
    private AddTransactionCommand getAddTransactionCommand(Transaction transaction, Model model) {
        AddTransactionCommand command = new AddTransactionCommand(transaction);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            fail("This method should not be called.");
        }

        @Override
        public Person findPersonByName(Name name) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public UniquePersonList getPayeesList(ArgumentMultimap argMultimap, Model model) {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public boolean personNotFoundInTransaction(Person person) {
            fail("This method should not be called.");
            return true;
        }
        @Override
        public List<Transaction> findTransactionsWithPerson(Person person) {
            return null;
        }
        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {

        }

        @Override
        public void addTransaction(Transaction transaction) throws PersonNotFoundException {}

        @Override
        public void deleteTransaction(Transaction transaction) {
        }

        @Override
        public ObservableList<Debtor> getFilteredDebtors() {
            return null;
        }

        @Override
        public ObservableList<Creditor> getFilteredCreditors() {
            return null;
        }

        @Override
        public void updateDebtorList(Predicate<Debtor> predicateShowNoDebtors) {

        }

        @Override
        public void updateCreditorList(Predicate<Creditor> predicateShowAllCreditors) {

        }
    }
    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();

        @Override
        public void addTransaction(Transaction transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that throw PersonNotFound except the transaction being added.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public void addTransaction(Transaction transaction) throws PersonNotFoundException {
            throw new PersonNotFoundException();

        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

    }

    private AddTransactionCommand prepareCommand(Transaction validTransaction) {
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(validTransaction);
        addTransactionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTransactionCommand;
    }
}
```
###### /java/seedu/address/logic/commands/AddTransactionCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddTransactionCommand}.
 */
public class AddTransactionCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTransaction_success() throws Exception {
        Transaction validTransaction = new TransactionBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(prepareCommand(validTransaction, model), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS, validTransaction), expectedModel);
    }

    /**
     * Generates a new {@code AddTransactionCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddTransactionCommand prepareCommand(Transaction transaction, Model model) {
        AddTransactionCommand command = new AddTransactionCommand(transaction);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTransactionCommandTest.java
``` java
public class DeleteTransactionCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        transactionToDelete.setTransactionType("paydebt");
        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
        transactionToDelete.setTransactionType("payment");
    }
    @Test
    public void execute_validIndexUnfilteredListPaydebt_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_validIndexUnfilteredListUnits_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_SIXTH_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_SIXTH_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_validIndexUnfilteredListPercentage_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_SEVENTH_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_SEVENTH_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);
        showNoTransaction(expectedModel);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws PersonNotFoundException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteTransactionCommand.execute();
        undoRedoStack.push(deleteTransactionCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteTransaction(transactionToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws PersonNotFoundException {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Transaction} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted transaction in the
     * unfiltered list is same from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the transaction object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTransactionDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        deleteTransactionCommand.execute();
        undoRedoStack.push(deleteTransactionCommand);

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteTransaction(transactionToDelete);
        assertEquals(transactionToDelete, model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased()));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_payerOrPayeesDoNotExist_throwsCommandException() throws CommandException,
            PersonNotFoundException {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Transaction transactionToDelete = model.getAddressBook().getTransactionList().get(1);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteTransactionCommand.MESSAGE_NONEXISTENT_PAYER_PAYEES);

        getDeleteTransactionCommand(transactionToDelete, expectedModel).execute();
    }

    @Test
    public void execute_emptyTransactionList_throwsCommandException() throws CommandException,
            PersonNotFoundException {
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_EMPTY_TRANSACTION_LIST);

        getDeleteTransactionCommand(expectedModel).execute();
    }

    @Test
    public void equals() throws Exception {
        DeleteTransactionCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        DeleteTransactionCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTransactionCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteTransactionCommand prepareCommand(Index index) {
        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(index);
        deleteTransactionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTransactionCommand;
    }
    /**
     * Generates a new AddPersonCommand with the details of the given person.
     */
    private DeleteTransactionCommand getDeleteTransactionCommand(Transaction transaction, Model model) throws
            PersonNotFoundException, CommandException {
        DeleteTransactionCommand command = new DeleteTransactionCommand(INDEX_SECOND_TRANSACTION);
        model.deletePerson(transaction.getPayer());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddPersonCommand with the details of the given person.
     */
    private DeleteTransactionCommand getDeleteTransactionCommand(Model model) {
        DeleteTransactionCommand command = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedTransactionTest.java
``` java
public class XmlAdaptedTransactionTest extends XmlAdaptedPersonTest {

    private static final String INVALID_AMOUNT = " ";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TRANSACTION_TYPE = " ";
    private static final String INVALID_SPLIT_METHOD = " ";

    private static final Person VALID_PAYER = TypicalTransactions.getTypicalTransactions().get(0).getPayer();
    private static final String VALID_AMOUNT = TypicalTransactions.getTypicalTransactions().get(0)
            .getAmount().toString();
    private static final String VALID_DESCRIPTION = TypicalTransactions.getTypicalTransactions().get(0)
            .getDescription().toString();
    private static final Person VALID_PAYEE = TypicalTransactions.getTypicalTransactions().get(0)
            .getPayees().iterator().next();
    private static UniquePersonList validPayees = new UniquePersonList();
    private static final String VALID_TRANSACTION_TYPE = TypicalTransactions.getTypicalTransactions().get(0)
            .getTransactionType().toString();
    private static final String VALID_SPLIT_METHOD_EVENLY = TypicalTransactions.getTypicalTransactions().get(0)
            .getSplitMethod().toString();
    private static final List<Integer> VALID_SPLIT_BY_UNITS_LIST = TypicalTransactions.getTypicalTransactions().get(1)
            .getUnits();
    private static final List<Integer> VALID_SPLIT_BY_PERCENTAGES_LIST = TypicalTransactions
            .getTypicalTransactions().get(2)
            .getPercentages();
    private static final List<Integer> VALID_EMPTY_LIST = new ArrayList<>();

    @Before
    public void setUp() {
        try {
            validPayees.add(VALID_PAYEE);
        } catch (DuplicatePersonException dpe) {
            dpe.printStackTrace();
        }
    }

```
###### /java/seedu/address/storage/XmlSerializableAddressBookTest.java
``` java
    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TRANSACTIONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalTransactionsAddressBook = TypicalTransactions.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalTransactionsAddressBook);
    }
    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TRANSACTION_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
        original.addTransaction(TypicalTransactions.getTypicalTransactions().get(3));
        original.removeTransaction(TypicalTransactions.getTypicalTransactions().get(3));
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));
```
###### /java/seedu/address/model/transaction/AmountTest.java
``` java
public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("^")); // only non-alphanumeric characters
        assertFalse(Amount.isValidAmount("123*")); // contains non-alphanumeric characters
        assertFalse(Amount.isValidAmount("1.2.3")); // contains irregular numeric format
        assertFalse(Amount.isValidAmount("one dollars")); // contains alphabet characters
        assertFalse(Amount.isValidAmount("1 2 3")); // contains spacing between numbers
        assertFalse(Amount.isValidAmount("12345.123")); // numbers & more than 2 decimals
        assertFalse(Amount.isValidAmount("12345.")); // numbers & decimal point only

        // valid name
        assertTrue(Amount.isValidAmount("12345")); // numbers only
        assertTrue(Amount.isValidAmount("12345.1")); // numbers & 1 decimal only
        assertTrue(Amount.isValidAmount("12345.78")); // numbers & 2 decimals only
    }
}
```
###### /java/seedu/address/model/transaction/DescriptionTest.java
``` java
public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("peter jack")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("lunch meal")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Dinner Meal")); // with capital letters
        assertTrue(Description.isValidDescription("expense for meal and travel")); // long description
        assertTrue(Description.isValidDescription("*food*")); // contain non-alphanumeric characters
    }
}
```
###### /java/seedu/address/model/TransactionListTest.java
``` java
public class TransactionListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        TransactionList transactionList = new TransactionList();
        thrown.expect(UnsupportedOperationException.class);
        transactionList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/DebtorBuilder.java
``` java
/**
 * A utility class to help with building Debtor objects.
 */
public class DebtorBuilder {
    public static final Person DEFAULT_PERSON = SampleDataUtil.getSamplePersons()[0];
    public static final String DEFAULT_DEBT = "0.00";

    private Person person;
    private Balance debt;

    public DebtorBuilder() {
        person = DEFAULT_PERSON;
        debt = new Balance(DEFAULT_DEBT);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public DebtorBuilder(Creditor creditorToCopy) {
        person = creditorToCopy.getCreditor();
        debt = creditorToCopy.getDebt();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public DebtorBuilder withDebtor(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public DebtorBuilder withDebt(String debt) {
        this.debt = new Balance(debt);
        return this;
    }


    public Debtor build() {
        return new Debtor(person, debt);
    }

}
```
###### /java/seedu/address/testutil/TypicalTransactions.java
``` java
/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    private static Transaction t1 = null;
    private static Transaction t2 = null;
    private static Transaction t3 = null;
    private static Transaction t4 = null;
    private static Transaction t5 = null;
    private static Transaction t6 = null;
    private static Transaction t7 = null;

    private static Person person1 = SampleDataUtil.getSamplePersons()[0];
    private static Person person2 = SampleDataUtil.getSamplePersons()[1];
    private static Person person3 = SampleDataUtil.getSamplePersons()[2];
    private static Person person4 = SampleDataUtil.getSamplePersons()[3];
    private static Person person5 = SampleDataUtil.getSamplePersons()[4];
    private static Person person6 = SampleDataUtil.getSamplePersons()[5];

    private static UniquePersonList payee2 = new UniquePersonList();
    private static UniquePersonList payee4 = new UniquePersonList();
    private static UniquePersonList payee6 = new UniquePersonList();
    private static UniquePersonList payeeGeorge = new UniquePersonList();
    private static UniquePersonList payeeFiona = new UniquePersonList();

    private static Date date = new Date();

    static {
        try {
            payee2.add(person2);
            payee4.add(person4);
            payee6.add(person6);
            payeeGeorge.add(TypicalPersons.GEORGE);
            payeeFiona.add(TypicalPersons.FIONA);


            t1 = new TransactionBuilder().withPayer(person1).withAmount("12345.00")
                    .withDescription("Boat trip").withDate(date)
                    .withSplitMethod("evenly").build();
            t2 = new TransactionBuilder().withPayer(person3).withAmount("0")
                    .withDescription("Food for barbecue")
                    .withSplitMethod("units").withUnits("1, 2, 3").withDate(date).build();
            t3 = new TransactionBuilder().withPayer(person5).withAmount("0.00")
                    .withDescription("Open air concert")
                    .withSplitMethod("percentage").withPercentages("20, 20, 60").withDate(date).build();
            t4 = new TransactionBuilder().withPayer(TypicalPersons.GEORGE).withAmount("0.00")
                    .withDescription("Transport")
                    .withSplitMethod("evenly").withDate(date).build();
            t5 = new TransactionBuilder().withPayer(TypicalPersons.FIONA)
                    .withAmount("0.00").withDescription("Dinner")
                    .withDate(date)
                    .withSplitMethod("evenly").withDate(date).build();
            t6 = new TransactionBuilder().withPayer(person5).withAmount("1234.00")
                    .withDescription("Food for barbecue")
                    .withSplitMethod("units").withUnits("1, 2, 3").withDate(date).build();
            t7 = new TransactionBuilder().withPayer(person5).withAmount("1234.00")
                    .withDescription("Open air concert")
                    .withSplitMethod("percentage").withPercentages("20, 20, 60").withDate(date).build();
        } catch (DuplicatePersonException dpe) {
            dpe.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    private TypicalTransactions() { } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Transaction transaction : getTypicalTransactions()) {
            try {
                ab.addTransaction(transaction);
            } catch (CommandException ce) {
                System.out.println(ce.getMessage());
            }
        }
        return ab;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(t1, t2, t3, t4, t5, t6, t7));
    }

}
```
###### /java/seedu/address/testutil/CreditorBuilder.java
``` java
/**
 * A utility class to help with building Creditor objects.
 */
public class CreditorBuilder {

    public static final Person DEFAULT_PERSON = SampleDataUtil.getSamplePersons()[0];
    public static final String DEFAULT_DEBT = "0.00";

    private Person person;
    private Balance debt;

    public CreditorBuilder() {
        person = DEFAULT_PERSON;
        debt = new Balance(DEFAULT_DEBT);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public CreditorBuilder(Creditor creditorToCopy) {
        person = creditorToCopy.getCreditor();
        debt = creditorToCopy.getDebt();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CreditorBuilder withCreditor(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CreditorBuilder withDebt(String debt) {
        this.debt = new Balance(debt);
        return this;
    }


    public Creditor build() {
        return new Creditor(person, debt);
    }

}
```
###### /java/guitests/guihandles/CreditorCardHandle.java
``` java
/**
 * Provides a handle to a creditor card in the creditor list panel.
 */
public class CreditorCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String DEBT_FIELD_ID = "#debt";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final Label debtLabel;

    public CreditorCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDebt() {
        return debtLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
###### /java/guitests/guihandles/DebtorCardHandle.java
``` java
/**
 * Provides a handle to a debtor card in the debtor list panel.
 */
public class DebtorCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String DEBT_FIELD_ID = "#debt";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final Label debtLabel;

    public DebtorCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDebt() {
        return debtLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
