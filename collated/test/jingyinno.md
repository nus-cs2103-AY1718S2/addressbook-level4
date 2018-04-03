# jingyinno
###### /java/seedu/address/logic/parser/UnaliasCommandParserTest.java
``` java
public class UnaliasCommandParserTest {
    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_compulsoryArgumentMissing_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE);

        //missing argument
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);
    }

    @Test
    public void parse_removeAlias_success() {
        assertParseSuccess(parser, VALID_UNALIAS, new UnaliasCommand(VALID_UNALIAS));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_UNALIAS_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }
}
```
###### /java/seedu/address/logic/parser/AliasCommandParserTest.java
``` java
public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_addAlias_success() {
        Alias expectedAddAlias = new AliasBuilder().withCommand(AddCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ADD).build();

        assertParseSuccess(parser, ALIAS_DESC_ADD, new AliasCommand(expectedAddAlias));
    }

    @Test
    public void parse_aliasAlias_success() {
        Alias expectedAliasAlias = new AliasBuilder().withCommand(AliasCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ALIAS).build();

        assertParseSuccess(parser, ALIAS_DESC_ALIAS, new AliasCommand(expectedAliasAlias));
    }

    @Test
    public void parse_clearAlias_success() {
        Alias expectedClearAlias = new AliasBuilder().withCommand(ClearCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_CLEAR).build();

        assertParseSuccess(parser, ALIAS_DESC_CLEAR, new AliasCommand(expectedClearAlias));
    }

    @Test
    public void parse_deleteAlias_success() {
        Alias expectedDeleteAlias = new AliasBuilder().withCommand(DeleteCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_DELETE).build();

        assertParseSuccess(parser, ALIAS_DESC_DELETE, new AliasCommand(expectedDeleteAlias));
    }

    @Test
    public void parse_editAlias_success() {
        Alias expectedEditAlias = new AliasBuilder().withCommand(EditCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EDIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EDIT, new AliasCommand(expectedEditAlias));
    }

    @Test
    public void parse_exitAlias_success() {
        Alias expectedExitAlias = new AliasBuilder().withCommand(ExitCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EXIT).build();

        assertParseSuccess(parser, ALIAS_DESC_EXIT, new AliasCommand(expectedExitAlias));
    }

    @Test
    public void parse_findAlias_success() {
        Alias expectedFindAlias = new AliasBuilder().withCommand(FindCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_FIND).build();

        assertParseSuccess(parser, ALIAS_DESC_FIND, new AliasCommand(expectedFindAlias));
    }

    @Test
    public void parse_helpAlias_success() {
        Alias expectedHelpAlias = new AliasBuilder().withCommand(HelpCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_HELP).build();

        assertParseSuccess(parser, ALIAS_DESC_HELP, new AliasCommand(expectedHelpAlias));
    }

    @Test
    public void parse_historyAlias_success() {
        Alias expectedHistoryAlias = new AliasBuilder().withCommand(HistoryCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_HISTORY).build();

        assertParseSuccess(parser, ALIAS_DESC_HISTORY, new AliasCommand(expectedHistoryAlias));
    }

    @Test
    public void parse_importAlias_success() {
        Alias expectedImportAlias = new AliasBuilder().withCommand(ImportCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_IMPORT).build();

        assertParseSuccess(parser, ALIAS_DESC_IMPORT, new AliasCommand(expectedImportAlias));
    }

    @Test
    public void parse_listAlias_success() {
        Alias expectedListAlias = new AliasBuilder().withCommand(ListCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_LIST).build();

        assertParseSuccess(parser, ALIAS_DESC_LIST, new AliasCommand(expectedListAlias));
    }

    @Test
    public void parse_redoAlias_success() {
        Alias expectedRedoAlias = new AliasBuilder().withCommand(RedoCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_REDO).build();

        assertParseSuccess(parser, ALIAS_DESC_REDO, new AliasCommand(expectedRedoAlias));
    }

    @Test
    public void parse_selectAlias_success() {
        Alias expectedSelectAlias = new AliasBuilder().withCommand(SelectCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_SELECT).build();

        assertParseSuccess(parser, ALIAS_DESC_SELECT, new AliasCommand(expectedSelectAlias));
    }

    @Test
    public void parse_undoAlias_success() {
        Alias expectedUndoAlias = new AliasBuilder().withCommand(UndoCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNDO).build();

        assertParseSuccess(parser, ALIAS_DESC_UNDO, new AliasCommand(expectedUndoAlias));
    }

    @Test
    public void parse_invalidValue_failure() {
        //alias with symbols failure
        assertParseFailure(parser, INVALID_ALIAS_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryArgumentMissing_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        //missing command/alias word argument
        String missingArgumentCommand = AddCommand.COMMAND_WORD;
        assertParseFailure(parser, missingArgumentCommand, message);

        //missing both arguments
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias() throws Exception {
        Alias alias = new AliasBuilder().build();
        AliasCommand command = (AliasCommand) parser.parseCommand(AliasUtil.getAliasCommand(alias));
        assertEquals(new AliasCommand(alias), command);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    private static final String VALID_BUILDING = "COM1";

    private static final String INVALID_BUILDING = "COM*";
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseBuilding_validBuilding() throws Exception {
        Building building = new Building(VALID_BUILDING);
        assertEquals(building, ParserUtil.parseBuilding(VALID_BUILDING));
    }

    @Test
    public void parseBuilding_invalidBuilding_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBuilding(INVALID_BUILDING));
    }
```
###### /java/seedu/address/logic/parser/VacantCommandParserTest.java
``` java
public class VacantCommandParserTest {
    private VacantCommandParser parser = new VacantCommandParser();

    @Test
    public void parse_retrieveBuilding_success() {
        assertParseSuccess(parser, VALID_BUILDING_1, new VacantCommand(new Building(VALID_BUILDING_1)));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_BUILDING_1, Building.MESSAGE_BUILDING_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNumberOfArguments_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE);

        //more than one building name arguement
        String tooManyArgumentsCommand = INVALID_BUILDING_2;
        assertParseFailure(parser, tooManyArgumentsCommand, message);

        //missing both arguments
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);

        //missing both arguments, extra spaces
        String spacesArgumentCommand = "       ";
        assertParseFailure(parser, spacesArgumentCommand, message);
    }
}
```
###### /java/seedu/address/logic/commands/UnaliasCommandTest.java
``` java
public class UnaliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UnaliasCommand(null);
    }

    @Test
    public void execute_unaliasRemovedByModel_removeSuccessful() throws Exception {
        ModelStubAcceptingUnaliasAdded modelStub = new ModelStubAcceptingUnaliasAdded();

        String validUnalias = VALID_ALIAS_LIST;
        Alias validUnaliasAlias = new AliasBuilder().withCommand(VALID_ALIAS_LIST_COMMAND)
                .withAlias(VALID_ALIAS_LIST).build();
        ArrayList<Alias> expectedAliasesList = new ArrayList<Alias>();

        CommandResult commandResult = getUnaliasCommand(validUnalias, modelStub).execute();

        assertEquals(String.format(UnaliasCommand.MESSAGE_SUCCESS, validUnaliasAlias), commandResult.feedbackToUser);
        assertEquals(expectedAliasesList, modelStub.aliases);
    }

    @Test
    public void execute_unaliasRemovedByModel_removeFailure() throws Exception {
        ModelStubAcceptingUnaliasAdded modelStub = new ModelStubAcceptingUnaliasAdded();

        String invalidUnalias = VALID_ALIAS_FIND;

        thrown.expect(CommandException.class);
        thrown.expectMessage(UnaliasCommand.MESSAGE_UNKNOWN_UNALIAS);

        getUnaliasCommand(invalidUnalias, modelStub).execute();
    }

    @Test
    public void equals() {
        UnaliasCommand listUnaliasCommand = new UnaliasCommand(VALID_ALIAS_LIST);
        UnaliasCommand historyUnaliasCommand = new UnaliasCommand(VALID_ALIAS_HISTORY);

        // same object -> returns true
        assertTrue(listUnaliasCommand.equals(listUnaliasCommand));

        // same values -> returns true
        UnaliasCommand listUnaliasCommandCopy = new UnaliasCommand(VALID_ALIAS_LIST);
        assertTrue(listUnaliasCommand.equals(listUnaliasCommandCopy));

        // different types -> returns false
        assertFalse(listUnaliasCommand.equals(1));

        // null -> returns false
        assertFalse(listUnaliasCommand == null);

        // different unalias -> returns false
        assertFalse(listUnaliasCommand.equals(historyUnaliasCommand));
    }

    /**
     * Generates a new UnaliasCommand with the details of the given alias.
     */
    private UnaliasCommand getUnaliasCommand(String unalias, Model model) {
        UnaliasCommand command = new UnaliasCommand(unalias);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
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
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePassword(byte[] password)  {
            fail("This method should not be called.");
        }

        @Override
        public void removeAlias(String toRemove) throws AliasNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public void exportAddressBook(String filepath, Password password) {
            fail("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always accept the alias being removed.
     */
    private class ModelStubAcceptingUnaliasAdded extends ModelStub {
        private final ArrayList<Alias> aliases = new ArrayList<>();

        @Override
        public void removeAlias(String unalias) throws AliasNotFoundException {
            aliases.add(new Alias(VALID_ALIAS_LIST_COMMAND, VALID_ALIAS_LIST));
            requireNonNull(unalias);
            boolean isRemove = false;
            for (int i = 0; i < aliases.size(); i++) {
                if (aliases.get(i).getAlias().equals(unalias)) {
                    aliases.remove(aliases.get(i));
                    isRemove = true;
                    break;
                }
            }

            if (!isRemove) {
                throw new AliasNotFoundException();
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### /java/seedu/address/logic/commands/AliasCommandTest.java
``` java
public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AliasCommand(null);
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        Alias validAlias = new AliasBuilder().build();

        CommandResult commandResult = getAliasCommand(validAlias, modelStub).execute();

        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS, validAlias), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAlias), modelStub.aliasesAdded);
    }

    @Test
    public void execute_duplicateAlias_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAliasException();
        Alias validAlias = new AliasBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AliasCommand.MESSAGE_DUPLICATE_ALIAS);

        getAliasCommand(validAlias, modelStub).execute();
    }

    @Test
    public void execute_aliasWordAlias_failure() throws Exception {
        //test alias word to be a command word failure
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        List<String> commands = AliasCommand.getCommands();
        for (int i = 0; i < commands.size(); i++) {
            for (int j = 0; j < commands.size(); j++) {
                thrown.expect(CommandException.class);
                Alias invalidAlias = new Alias(commands.get(i), commands.get(j));
                getAliasCommand(invalidAlias, modelStub).execute();
            }
        }
    }

    @Test
    public void execute_commandWordAlias_failure() throws Exception {
        //test invalid command word with valid alias word failure
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        Alias invalidAlias = new Alias(INVALID_COMMAND_DESC, VALID_ALIAS_ADD);

        thrown.expect(CommandException.class);

        getAliasCommand(invalidAlias, modelStub).execute();
    }


    @Test
    public void equals() {
        Alias edit = new AliasBuilder().withCommand("Edit").build();
        Alias exit = new AliasBuilder().withCommand("Exit").build();

        AliasCommand editAliasCommand = new AliasCommand(edit);
        AliasCommand exitAliasCommand = new AliasCommand(exit);

        // same object -> returns true
        assertTrue(editAliasCommand.equals(editAliasCommand));

        // same values -> returns true
        AliasCommand editAliasCommandCopy = new AliasCommand(edit);
        assertTrue(editAliasCommand.equals(editAliasCommandCopy));

        // different types -> returns false
        assertFalse(editAliasCommand.equals(1));

        // null -> returns false
        assertFalse(editAliasCommand == null);

        // different alias -> returns false
        assertFalse(editAliasCommand.equals(exitAliasCommand));
    }

    /**
     * Generates a new AliasCommand with the details of the given alias.
     */
    private AliasCommand getAliasCommand(Alias alias, Model model) {
        AliasCommand command = new AliasCommand(alias);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
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
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePassword(byte[] password)  {
            fail("This method should not be called.");
        }

        @Override
        public void removeAlias(String toRemove) throws AliasNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public void exportAddressBook(String filepath, Password password) {
            fail("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateAliasException extends ModelStub {
        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            throw new DuplicateAliasException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }


    /**
     * A Model stub that always accept the alias being added.
     */
    private class ModelStubAcceptingAliasAdded extends ModelStub {
        private final ArrayList<Alias> aliasesAdded = new ArrayList<>();

        @Override
        public void addAlias(Alias alias) {
            requireNonNull(alias);
            aliasesAdded.add(alias);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_ALIAS_ADD = "add1";
    public static final String VALID_ALIAS_ALIAS = "alias1";
    public static final String VALID_ALIAS_CLEAR_COMMAND = ClearCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_CLEAR = "clear1";
    public static final String VALID_ALIAS_DELETE_COMMAND = DeleteCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_DELETE = "delete1";
    public static final String VALID_ALIAS_EDIT = "edit1";
    public static final String VALID_ALIAS_EXIT = "exit1";
    public static final String VALID_ALIAS_FIND = "find1";
    public static final String VALID_ALIAS_HELP_COMMAND = HelpCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_HELP = "help1";
    public static final String VALID_ALIAS_HISTORY = "history1";
    public static final String VALID_ALIAS_IMPORT = "import1";
    public static final String VALID_ALIAS_LIST_COMMAND = ListCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_LIST = "list1";
    public static final String VALID_ALIAS_REDO = "redo1";
    public static final String VALID_ALIAS_SELECT = "select1";
    public static final String VALID_ALIAS_UNDO = "undo1";
    public static final String VALID_ALIAS_NUMBER = "9";

    public static final String ALIAS_DESC_ADD = AddCommand.COMMAND_WORD + " " + VALID_ALIAS_ADD;
    public static final String ALIAS_DESC_ALIAS = AliasCommand.COMMAND_WORD + " " + VALID_ALIAS_ALIAS;
    public static final String ALIAS_DESC_CLEAR = ClearCommand.COMMAND_WORD + " " + VALID_ALIAS_CLEAR;
    public static final String ALIAS_DESC_DELETE = DeleteCommand.COMMAND_WORD + " " + VALID_ALIAS_DELETE;
    public static final String ALIAS_DESC_EDIT = EditCommand.COMMAND_WORD + " " + VALID_ALIAS_EDIT;
    public static final String ALIAS_DESC_EXIT = ExitCommand.COMMAND_WORD + " " + VALID_ALIAS_EXIT;
    public static final String ALIAS_DESC_FIND = FindCommand.COMMAND_WORD + " " + VALID_ALIAS_FIND;
    public static final String ALIAS_DESC_HELP = HelpCommand.COMMAND_WORD + " " + VALID_ALIAS_HELP;
    public static final String ALIAS_DESC_HISTORY = HistoryCommand.COMMAND_WORD + " " + VALID_ALIAS_HISTORY;
    public static final String ALIAS_DESC_IMPORT = ImportCommand.COMMAND_WORD + " " + VALID_ALIAS_IMPORT;
    public static final String ALIAS_DESC_LIST = ListCommand.COMMAND_WORD + " " + VALID_ALIAS_LIST;
    public static final String ALIAS_DESC_REDO = RedoCommand.COMMAND_WORD + " " + VALID_ALIAS_REDO;
    public static final String ALIAS_DESC_SELECT = SelectCommand.COMMAND_WORD + " " + VALID_ALIAS_SELECT;
    public static final String ALIAS_DESC_UNDO = UndoCommand.COMMAND_WORD + " " + VALID_ALIAS_UNDO;

    public static final String INVALID_ALIAS = "alias!";
    public static final String INVALID_ALIAS_DESC = INVALID_ALIAS + " " + VALID_ALIAS_ALIAS;
    public static final String INVALID_COMMAND_DESC = "invalid";

    public static final String VALID_BUILDING_1 = "COM1";
    public static final String VALID_BUILDING_2 = "S1";
    public static final String VALID_BUILDING_3 = "ERC";

    public static final String INVALID_BUILDING_1 = "ERC*";
    public static final String INVALID_BUILDING_2 = "COM1 COM2";
    public static final String INVALID_BUILDING_3 = "Building";

    public static final String VALID_UNALIAS = VALID_ALIAS_ADD;

    public static final String INVALID_UNALIAS_DESC = "alias!";
```
###### /java/seedu/address/logic/commands/VacantCommandTest.java
``` java
public class VacantCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullBuilding_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VacantCommand(null);
    }

    @Test
    public void execute_validBuildingRetrieval_success() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingBuilding();
        Building validBuilding = new Building(VALID_BUILDING_1);

        CommandResult commandResult = getVacantCommand(validBuilding, modelStub).execute();

        assertEquals(String.format(VacantCommand.MESSAGE_SUCCESS, validBuilding), commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidBuildingRetrieval_failure() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingBuilding();
        Building validBuilding = new Building(INVALID_BUILDING_3);

        thrown.expect(CommandException.class);

        getVacantCommand(validBuilding, modelStub).execute();
    }

    @Test
    public void equals() throws Exception {
        Building validBuildingOne = new Building(VALID_BUILDING_1);
        Building validBuildingTwo = new Building(VALID_BUILDING_2);

        VacantCommand oneVacantCommand = new VacantCommand(validBuildingOne);

        // same object -> returns true

        assertTrue(validBuildingOne.equals(validBuildingOne));

        // same values -> returns true
        VacantCommand validBuildingOneCopy = new VacantCommand(validBuildingOne);
        assertTrue(oneVacantCommand.equals(validBuildingOneCopy));

        // different types -> returns false
        assertFalse(validBuildingOne.equals(1));

        // null -> returns false
        assertFalse(validBuildingOne == null);

        // different vacant -> returns false
        assertFalse(validBuildingOne.equals(validBuildingTwo));
    }

    /**
     * Generates a new VacantCommand with the details of the given building
     */
    private VacantCommand getVacantCommand(Building building, Model model) {
        VacantCommand command = new VacantCommand(building);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
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
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePassword(byte[] password)  {
            fail("This method should not be called.");
        }

        @Override
        public void removeAlias(String toRemove) throws AliasNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public void exportAddressBook(String filepath, Password password) {
            fail("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always accept the building being requested.
     */
    private class ModelStubAcceptingBuilding extends ModelStub {
        private ArrayList<ArrayList<String>> roomsSchedule = new ArrayList<ArrayList<String>>();

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {

            if (!Building.isValidBuilding(building)) {
                throw new BuildingNotFoundException();
            }
            ArrayList<String> rooms = new ArrayList<>();
            rooms.add("room");
            roomsSchedule.add(rooms);
            return roomsSchedule;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### /java/seedu/address/model/alias/AliasTest.java
``` java
public class AliasTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));
    }

    @Test
    public void constructor_invalidAlias_throwsIllegalArgumentException() {
        String invalidAlias = INVALID_ALIAS;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Alias(VALID_ALIAS_LIST_COMMAND, invalidAlias));
    }

    @Test
    public void isValidAliasName() {
        // null alias
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));

        // invalid alias
        assertFalse(Alias.isValidAliasName("")); // empty string
        assertFalse(Alias.isValidAliasName(" ")); // spaces only
        assertFalse(Alias.isValidAliasName(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAliasName(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_NUMBER));
    }

    @Test
    public void isValidUnaliasName() {
        // null unalias
        Assert.assertThrows(NullPointerException.class, () -> new Alias(null, null));

        // invalid unalias
        assertFalse(Alias.isValidUnaliasName("")); // empty string
        assertFalse(Alias.isValidUnaliasName(" ")); // spaces only
        assertFalse(Alias.isValidUnaliasName(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid unalias
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidUnaliasName(VALID_ALIAS_NUMBER));
    }

    @Test
    public void equals() {
        Alias clear = new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR);
        assertEquals(clear, new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR));
    }
}
```
###### /java/seedu/address/model/UniqueAliasListTest.java
``` java
public class UniqueAliasListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasList.asObservableList().remove(0);
    }

    @Test
    public void addAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
        clearAliasList();
    }

    @Test
    public void removeAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);
        UniqueAliasList.remove(validAlias.getAlias());

        UniqueAliasList expectedList = new UniqueAliasList();
        assertEquals(uniqueAliasList.getAliasObservableList(), expectedList.asObservableList());
        clearAliasList();
    }

    @Test
    public void removeAlias_invalidAlias_failure() throws AliasNotFoundException {
        Alias validAlias = new AliasBuilder().build();
        thrown.expect(AliasNotFoundException.class);
        UniqueAliasList.remove(validAlias.getAlias());
        clearAliasList();
    }

    @Test
    public void getAliasCommand_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.add(validAlias);

        String command = uniqueAliasList.getCommandFromAlias(validAlias.getAlias());
        String expected = validAlias.getCommand();
        assertEquals(command, expected);
        clearAliasList();
    }

    @Test
    public void importAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        Alias validAlias = new AliasBuilder().build();
        uniqueAliasList.importAlias(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
        clearAliasList();
    }

    @Test
    public void setAlias_validAliasSet_success() throws DuplicateAliasException, AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        HashSet<Alias> toBeSet = new HashSet<Alias>();
        Alias help = new AliasBuilder().withCommand(VALID_ALIAS_HELP_COMMAND).withAlias(VALID_ALIAS_HELP).build();
        toBeSet.add(help);
        uniqueAliasList.add(help);

        uniqueAliasList.setAliases(toBeSet);
        ArrayList<Alias> expectedList = new ArrayList<Alias>(toBeSet);
        assertEquals(expectedList, uniqueAliasList.getAliasObservableList());
        clearAliasList();
    }

    /**
     * Removes all aliases in the list.
     *
     * @throws AliasNotFoundException if the Alias to add is not an existing Alias in the list.
     */
    private void clearAliasList() throws AliasNotFoundException {
        UniqueAliasList uniqueAliasList = new UniqueAliasList();
        for (Alias alias : uniqueAliasList.getAliasObservableList()) {
            UniqueAliasList.remove(alias.getAlias());
        }
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        private final ObservableList<Alias> aliases = FXCollections.observableArrayList();
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        @Override
        public ObservableList<Alias> getAliasList() {
            return aliases;
        }

        @Override
        public void resetAliasList() {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/testutil/AliasUtil.java
``` java
/**
 * A utility class for Alias.
 */
public class AliasUtil {

    /**
     * Returns an alias command string for adding the {@code alias}.
     */
    public static String getAliasCommand(Alias alias) {
        return AliasCommand.COMMAND_WORD + " " + getAliasDetails(alias);
    }

    /**
     * Returns the part of command string for the given {@code alias}'s details.
     */
    public static String getAliasDetails(Alias alias) {
        StringBuilder sb = new StringBuilder();
        sb.append(alias.getCommand() + " ");
        sb.append(alias.getAlias());
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TypicalAliases.java
``` java
/**
 * A utility class containing a list of {@code Alias} objects to be used in tests.
 */
public class TypicalAliases {

    public static final Alias ADD = new AliasBuilder().withCommand("add").withAlias("add1").build();
    public static final Alias ALIAS  = new AliasBuilder().withCommand("alias").withAlias("alias1").build();
    public static final Alias CLEAR = new AliasBuilder().withCommand("clear").withAlias("clear1").build();
    public static final Alias DELETE = new AliasBuilder().withCommand("delete").withAlias("delete1").build();
    public static final Alias EDIT = new AliasBuilder().withCommand("edit").withAlias("edit1").build();
    public static final Alias EXIT = new AliasBuilder().withCommand("exit").withAlias("exit1").build();
    public static final Alias FIND = new AliasBuilder().withCommand("find").withAlias("find1").build();
    public static final Alias HELP = new AliasBuilder().withCommand("help").withAlias("help1").build();
    public static final Alias HISTORY = new AliasBuilder().withCommand("history").withAlias("history1").build();
    public static final Alias IMPORT = new AliasBuilder().withCommand("import").withAlias("import1").build();
    public static final Alias LIST = new AliasBuilder().withCommand("list").withAlias("list1").build();
    public static final Alias REDO = new AliasBuilder().withCommand("redo").withAlias("redo1").build();
    public static final Alias UNDO = new AliasBuilder().withCommand("undo").withAlias("undo1").build();


    private TypicalAliases() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Alias alias : getTypicalAliases()) {
            try {
                ab.addAlias(alias);
            } catch (DuplicateAliasException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Alias> getTypicalAliases() {
        return new ArrayList<>(Arrays.asList(ADD, ALIAS, CLEAR, DELETE, EDIT, EXIT, HELP, HISTORY, LIST, REDO, UNDO));
    }
}
```
###### /java/seedu/address/testutil/AliasBuilder.java
``` java
/**
 * A utility class to help with building Alias objects.
 */
public class AliasBuilder {

    public static final String DEFAULT_COMMAND = "add";
    public static final String DEFAULT_ALIAS = "a";

    private String command;
    private String alias;

    public AliasBuilder() {
        command = DEFAULT_COMMAND;
        alias = DEFAULT_ALIAS;

    }

    /**
     * Initializes the AliasBuilder with the data of {@code aliasToCopy}.
     */
    public AliasBuilder(Alias aliasToCopy) {
        command = aliasToCopy.getCommand();
        alias = aliasToCopy.getAlias();
    }

    /**
     * Sets the {@code command} of the {@code Alias} that we are building.
     */
    public AliasBuilder withCommand(String command) {
        this.command = command;
        return this;
    }

    /**
     * Sets the {@code alias} into a {@code Alias} that we are building.
     */
    public AliasBuilder withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Alias build() {
        return new Alias(command, alias);
    }
}
```
