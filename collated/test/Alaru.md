# Alaru
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        assertEmailSuccess(expectedMessage, emailCommand);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        assertEmailSuccess(expectedMessage, emailCommand);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        EmailCommand emailCommand = new EmailCommand(index);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedResult} <br>
     * - the {@code expectedResult} matches {@code testCommand}
     */
    private static void assertEmailSuccess(String expectedResult, EmailCommand testCommand) {
        try {
            CommandResult result = testCommand.execute();
            assertEquals(expectedResult, result.feedbackToUser);
        } catch (UnsupportDesktopException de) {
            // Code is running on unsupported OS
            assertEquals(de.getMessage(), Messages.MESSAGE_UNSUPPORTED_DESKTOP);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
