# jasmoon
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     */
    public static void assertCommandSuccess(Command command, String expectedMessage)   {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     */
    public static void assertCommandFailure(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

```
###### \java\seedu\address\logic\commands\EventCommandTest.java
``` java
        @Override
        public ObservableList<Activity> getFilteredTaskList()    {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateActivityException when trying to add a activity.
     */
    private class ModelStubThrowingDuplicateActivityException extends ModelStub {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            throw new DuplicateActivityException();
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Activity> eventsAdded = new ArrayList<>();

        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            requireNonNull(activity);
            eventsAdded.add(activity);
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }
}
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
    @Test
    public void execute_listForTask_success() {
        ListCommand command = new ListCommand("task");
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS_TASK);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTaskOnlyRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_listForEvent_success() {
        ListCommand command = new ListCommand("event");
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS_EVENT);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowEventOnlyRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_listForActivity_success()    {
        ListCommand command = new ListCommand();
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowActivityRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_invalidArgs_throwsCommandException() throws Exception   {
        ListCommand command = new ListCommand("hello");
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_LIST_REQUEST, "hello"));
    }

}
```
###### \java\seedu\address\logic\commands\TaskCommandTest.java
``` java
        @Override
        public ObservableList<Activity> getFilteredTaskList()    {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateActivityException when trying to add a activity.
     */
    private class ModelStubThrowingDuplicateActivityException extends ModelStub {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            throw new DuplicateActivityException();
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Activity> tasksAdded = new ArrayList<>();

        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            requireNonNull(activity);
            tasksAdded.add(activity);
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

}
```
###### \java\seedu\address\logic\parser\DeskBoardParserTest.java
``` java
    @Test
    public void parseCommand_event() throws Exception {
        Event event = new EventBuilder().build();
        EventCommand command = (EventCommand) parser.parseCommand(EventUtil.getEventCommand(event));
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void parseCommandPlusAlias_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

   /* @Test
    public void parseCommand_remove() throws Exception {
        RemoveCommand command = (RemoveCommand) parser.parseCommand(
                RemoveCommand.COMMAND_WORD + " " + INDEX_FIRST_ACTIVITY.getOneBased());
        assertEquals(new RemoveCommand("task", INDEX_FIRST_ACTIVITY), command);
    }*/

//
//    public void parseCommand_edit() throws Exception {
//        Person person = new PersonBuilder().build();
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
//        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
//                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
//        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
//    }
//
//
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
//    }
//
//
//    public void parseCommand_find() throws Exception {
//        List<String> keywords = Arrays.asList("foo", "bar", "baz");
//        FindCommand command = (FindCommand) parser.parseCommand(
//                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
//        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
//    }
//
//
```
###### \java\seedu\address\logic\parser\DeskBoardParserTest.java
``` java
    @Test
    public void parseCommandPlusAlias_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " task") instanceof HelpCommand);
        try {
            parser.parseCommand(HelpCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(Messages.MESSAGE_INVALID_HELP_REQUEST, "3"), pe.getMessage());
        }
    }
//
//
//    public void parseCommand_history() throws Exception {
//        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
//        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
//
//        try {
//            parser.parseCommand("histories");
//            fail("The expected ParseException was not thrown.");
//        } catch (ParseException pe) {
//            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
//        }
//    }
//
//
    @Test
    public void parseCommandPlusAlias_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " task") instanceof ListCommand);
    }
//
//
//    public void parseCommand_select() throws Exception {
//        SelectCommand command = (SelectCommand) parser.parseCommand(
//                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
//        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
//    }
//
//
//    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
//        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
//        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
//    }
//
//
//    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
//        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
//        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
//    }
//
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand(" ");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### \java\seedu\address\logic\parser\HelpCommandParserTest.java
``` java
public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_HELP_REQUEST, "a"));
    }
}
```
###### \java\seedu\address\logic\parser\ListCommandParserTest.java
``` java
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "hello",
                String.format(Messages.MESSAGE_INVALID_LIST_REQUEST, "hello"));
    }
}
```
