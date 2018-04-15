# jingyinno
###### \java\guitests\guihandles\AliasListHandle.java
``` java
/**
 * A handler for the {@code AliasList} of the UI
 */
public class AliasListHandle extends NodeHandle<TableView> {

    public static final String ALIAS_LIST_ID = "#aliasList";

    public AliasListHandle(TableView aliasListNode) {
        super(aliasListNode);
    }

    /**
     * Returns the tables in the alias list
     */
    public ObservableList<TableColumn> getTables() {
        return getRootNode().getColumns();
    }

    public boolean getFront() {
        return getRootNode().getChildrenUnmodifiable().get(0).equals(this);
    }
}
```
###### \java\guitests\guihandles\GoogleMapsDisplayHandle.java
``` java
/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class GoogleMapsDisplayHandle extends NodeHandle<Node> {

    public static final String MAP_ID = "#maps";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    public GoogleMapsDisplayHandle(Node mapPanelNode) {
        super(mapPanelNode);

        WebView webView = getChildNode(MAP_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(MAP_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return !lastRememberedUrl.equals(getLoadedUrl());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
```
###### \java\guitests\guihandles\WebViewUtil.java
``` java
    /**
     * If the {@code googleMapsDisplayHandle}'s {@code WebView} is loading, sleeps the thread till it is
     * successfully loaded.
     */
    public static void waitUntilBrowserLoaded(GoogleMapsDisplayHandle googleMapsDisplayHandle) {
        new GuiRobot().waitForEvent(googleMapsDisplayHandle::isLoaded);
    }
```
###### \java\seedu\address\logic\commands\AliasCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for AliasCommand.
 */
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
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_ALIAS_ADD = "add1";
    public static final String VALID_ALIAS_ALIAS = "alias1";
    public static final String VALID_ALIAS_BIRTHDAYS = "birthdays1";
    public static final String VALID_ALIAS_CLEAR_COMMAND = ClearCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_CLEAR = "clear1";
    public static final String VALID_ALIAS_DELETE_COMMAND = DeleteCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_DELETE = "delete1";
    public static final String VALID_ALIAS_EDIT = "edit1";
    public static final String VALID_ALIAS_EXIT = "exit1";
    public static final String VALID_ALIAS_EXPORT = "export1";
    public static final String VALID_ALIAS_FIND = "find1";
    public static final String VALID_ALIAS_HELP_COMMAND = HelpCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_HELP = "help1";
    public static final String VALID_ALIAS_HISTORY = "history1";
    public static final String VALID_ALIAS_IMPORT = "import1";
    public static final String VALID_ALIAS_LIST_COMMAND = ListCommand.COMMAND_WORD;
    public static final String VALID_ALIAS_LIST = "list1";
    public static final String VALID_ALIAS_MAP1 = "map1";
    public static final String VALID_ALIAS_MAP2 = "map2";
    public static final String VALID_ALIAS_ENCRYPT = "encrypt1";
    public static final String VALID_ALIAS_REDO = "redo1";
    public static final String VALID_ALIAS_DECRYPT = "decrypt1";
    public static final String VALID_ALIAS_SELECT = "select1";
    public static final String VALID_ALIAS_UNALIAS = "unalias1";
    public static final String VALID_ALIAS_UNDO = "undo1";
    public static final String VALID_ALIAS_VACANT = "vacant1";
    public static final String VALID_ALIAS_UNION = "union1";
    public static final String VALID_ALIAS_UPLOAD = "upload1";
    public static final String VALID_ALIAS_NUMBER = "911";

    public static final String ALIAS_DESC_ADD = AddCommand.COMMAND_WORD + " " + VALID_ALIAS_ADD;
    public static final String ALIAS_DESC_ALIAS = AliasCommand.COMMAND_WORD + " " + VALID_ALIAS_ALIAS;
    public static final String ALIAS_DESC_BIRTHDAYS = BirthdaysCommand.COMMAND_WORD + " " + VALID_ALIAS_BIRTHDAYS;
    public static final String ALIAS_DESC_CLEAR = ClearCommand.COMMAND_WORD + " " + VALID_ALIAS_CLEAR;
    public static final String ALIAS_DESC_DELETE = DeleteCommand.COMMAND_WORD + " " + VALID_ALIAS_DELETE;
    public static final String ALIAS_DESC_DECRYPT = RemovePasswordCommand.COMMAND_WORD + " " + VALID_ALIAS_DECRYPT;
    public static final String ALIAS_DESC_EDIT = EditCommand.COMMAND_WORD + " " + VALID_ALIAS_EDIT;
    public static final String ALIAS_DESC_EXIT = ExitCommand.COMMAND_WORD + " " + VALID_ALIAS_EXIT;
    public static final String ALIAS_DESC_ENCRYPT = PasswordCommand.COMMAND_WORD + " " + VALID_ALIAS_ENCRYPT;
    public static final String ALIAS_DESC_EXPORT = ExportCommand.COMMAND_WORD + " " + VALID_ALIAS_EXPORT;
    public static final String ALIAS_DESC_FIND = FindCommand.COMMAND_WORD + " " + VALID_ALIAS_FIND;
    public static final String ALIAS_DESC_HELP = HelpCommand.COMMAND_WORD + " " + VALID_ALIAS_HELP;
    public static final String ALIAS_DESC_HISTORY = HistoryCommand.COMMAND_WORD + " " + VALID_ALIAS_HISTORY;
    public static final String ALIAS_DESC_IMPORT = ImportCommand.COMMAND_WORD + " " + VALID_ALIAS_IMPORT;
    public static final String ALIAS_DESC_LIST = ListCommand.COMMAND_WORD + " " + VALID_ALIAS_LIST;
    public static final String ALIAS_DESC_MAP1 = MapCommand.COMMAND_WORD + " " + VALID_ALIAS_MAP1;
    public static final String ALIAS_DESC_MAP2 = MapCommand.COMMAND_WORD + " " + VALID_ALIAS_MAP2;
    public static final String ALIAS_DESC_REDO = RedoCommand.COMMAND_WORD + " " + VALID_ALIAS_REDO;
    public static final String ALIAS_DESC_SELECT = SelectCommand.COMMAND_WORD + " " + VALID_ALIAS_SELECT;
    public static final String ALIAS_DESC_UNALIAS = UnaliasCommand.COMMAND_WORD + " " + VALID_ALIAS_UNALIAS;
    public static final String ALIAS_DESC_UNDO = UndoCommand.COMMAND_WORD + " " + VALID_ALIAS_UNDO;
    public static final String ALIAS_DESC_VACANT = VacantCommand.COMMAND_WORD + " " + VALID_ALIAS_VACANT;
    public static final String ALIAS_DESC_UNION = TimetableUnionCommand.COMMAND_WORD + " " + VALID_ALIAS_UNION;
    public static final String ALIAS_DESC_UPLOAD = UploadCommand.COMMAND_WORD + " " + VALID_ALIAS_UPLOAD;
    public static final String ALIAS_DESC_NUMBER = UploadCommand.COMMAND_WORD + " " + VALID_ALIAS_NUMBER;

    public static final String INVALID_COMMAND_SYNTAX = "command!";
    public static final String INVALID_ALIAS = "alias!";
    public static final String INVALID_COMMAND_DESC = "invalid";

    public static final String INVALID_COMMAND_SYNTAX_DESC = INVALID_COMMAND_SYNTAX + " " + VALID_ALIAS_ALIAS;
    public static final String INVALID_COMMAND_WORD_DESC = INVALID_COMMAND_DESC + " " + VALID_ALIAS_ALIAS;
    public static final String INVALID_ALIAS_SYNTAX_DESC = SelectCommand.COMMAND_WORD + " " + INVALID_ALIAS;
    public static final String INVALID_ALIAS_WORD_DESC = SelectCommand.COMMAND_WORD + " " + VacantCommand.COMMAND_WORD;

    public static final String VALID_BUILDING_1 = "COM1";
    public static final String VALID_BUILDING_2 = "S1";
    public static final String VALID_BUILDING_3 = "ERC";

    public static final String INVALID_BUILDING_1 = "ERC*";
    public static final String INVALID_BUILDING_2 = "COM1 COM2";
    public static final String INVALID_BUILDING_3 = "Building";
    public static final String MIXED_CASE_VACANT_COMMAND_WORD = "VaCaNt";

    public static final String VALID_UNALIAS = VALID_ALIAS_ADD;

    public static final String INVALID_UNALIAS = "nonexistent";

    public static final String INVALID_UNALIAS_DESC = "alias!";

    public static final String VALID_LOCATION_BUILDING_UPPERCASE_1 = "EA";
    public static final String VALID_LOCATION_BUILDING_UPPERCASE_2 = "COM1";
    public static final String VALID_LOCATION_BUILDING_LOWERCASE = "ea";
    public static final String VALID_LOCATION_POSTAL_1 = "677743";
    public static final String VALID_LOCATION_POSTAL_2 = "819643";
    public static final String VALID_LOCATION_ADDRESS_1 = "Changi Airport Singapore";
    public static final String VALID_LOCATION_ADDRESS_2 = "Serangoon block 413";

    //EA to COM1
    public static final String VALID_TWO_LOCATIONS_BUILDING = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_BUILDING_UPPERCASE_2;
    //677743 to 819643
    public static final String VALID_TWO_LOCATIONS_POSTAL = VALID_LOCATION_POSTAL_1 + "/"
            + VALID_LOCATION_POSTAL_2;
    //Changi Airport Terminal to Serangoon block 413
    public static final String VALID_TWO_LOCATIONS_ADDRESS = VALID_LOCATION_ADDRESS_1 + "/"
            + VALID_LOCATION_ADDRESS_2;
    //building, postal
    public static final String VALID_TWO_LOCATIONS_1 = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_POSTAL_1;
    //building, address
    public static final String VALID_TWO_LOCATIONS_2 = VALID_LOCATION_BUILDING_UPPERCASE_1 + "/"
            + VALID_LOCATION_ADDRESS_1;
    //postal, address
    public static final String VALID_TWO_LOCATIONS_3 = VALID_LOCATION_POSTAL_1 + "/"
            + VALID_LOCATION_ADDRESS_1;

    public static final String VALID_THREE_LOCATIONS = VALID_LOCATION_ADDRESS_1 + "/"
            + VALID_LOCATION_BUILDING_LOWERCASE + "/" + VALID_LOCATION_POSTAL_2;
```
###### \java\seedu\address\logic\commands\MapCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for MapCommand.
 */
public class MapCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MapCommand(null);
    }

    @Test
    public void execute_inputOneLocation_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_LOCATION_BUILDING_UPPERCASE_1, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_inputTwoLocations_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_TWO_LOCATIONS_ADDRESS, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_inputThreeLocations_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_THREE_LOCATIONS, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void equals() {
        MapCommand nus = new MapCommand("nus");
        MapCommand soc = new MapCommand("soc");
        MapCommand nusCopy = new MapCommand("nus");

        // same object & values -> returns true
        assertTrue(nus.equals(nusCopy));

        // different types -> returns false
        assertFalse(nus.equals(1));

        // null -> returns false
        assertFalse(nus == null);

        // different person -> returns false
        assertFalse(nus.equals(soc));
    }

    /**
     * Generates a new MapCommand given the locations specified
     */
    private MapCommand prepareCommand(String locations, Model model) {
        MapCommand command = new MapCommand(locations);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\UnaliasCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for UnaliasCommand.
 */
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
###### \java\seedu\address\logic\commands\VacantCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for VacantCommand.
 */
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias() throws Exception {
        Alias alias = new AliasBuilder().build();
        String[] input = parser.extractCommandArgs(AliasUtil.getAliasCommand(alias));
        AliasCommand command = (AliasCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new AliasCommand(alias), command);
    }

    @Test
    public void parseCommand_unalias() throws Exception {
        Alias toUnalias = new AliasBuilder().build();
        String unalias = toUnalias.getAlias();
        String[] input = parser.extractCommandArgs(AliasUtil.getUnliasCommand(unalias));
        UnaliasCommand command = (UnaliasCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new UnaliasCommand(unalias), command);
    }

    @Test
    public void parseCommand_vacant() throws Exception {
        Building building = new BuildingBuilder().build();
        String vacant = VacantCommand.COMMAND_WORD + " " + building.getBuildingName();
        String[] input = parser.extractCommandArgs(vacant);
        VacantCommand command = (VacantCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new VacantCommand(building), command);
    }

    @Test
    public void parseCommand_map() throws Exception {
        String locations = "com1";
        String map = MapCommand.COMMAND_WORD + " " + locations;
        String[] input = parser.extractCommandArgs(map);
        MapCommand command = (MapCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new MapCommand(locations), command);
    }
```
###### \java\seedu\address\logic\parser\AliasCommandParserTest.java
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
    public void parse_encryptAlias_success() {
        Alias expectedEncryptAlias = new AliasBuilder().withCommand(PasswordCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_ENCRYPT).build();

        assertParseSuccess(parser, ALIAS_DESC_ENCRYPT, new AliasCommand(expectedEncryptAlias));
    }

    @Test
    public void parse_decryptAlias_success() {
        Alias expectedDecryptAlias = new AliasBuilder().withCommand(RemovePasswordCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_DECRYPT).build();

        assertParseSuccess(parser, ALIAS_DESC_DECRYPT, new AliasCommand(expectedDecryptAlias));
    }

    @Test
    public void parse_mapAlias_success() {
        Alias expectedMapAlias = new AliasBuilder().withCommand(MapCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_MAP1).build();

        assertParseSuccess(parser, ALIAS_DESC_MAP1, new AliasCommand(expectedMapAlias));
    }

    @Test
    public void parse_unionAlias_success() {
        Alias expectedUnionAlias = new AliasBuilder().withCommand(TimetableUnionCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNION).build();

        assertParseSuccess(parser, ALIAS_DESC_UNION, new AliasCommand(expectedUnionAlias));
    }

    @Test
    public void parse_unaliasAlias_success() {
        Alias expectedUnaliasAlias = new AliasBuilder().withCommand(UnaliasCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UNALIAS).build();

        assertParseSuccess(parser, ALIAS_DESC_UNALIAS, new AliasCommand(expectedUnaliasAlias));
    }

    @Test
    public void parse_uploadAlias_success() {
        Alias expectedUploadAlias = new AliasBuilder().withCommand(UploadCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_UPLOAD).build();

        assertParseSuccess(parser, ALIAS_DESC_UPLOAD, new AliasCommand(expectedUploadAlias));
    }

    @Test
    public void parse_exportAlias_success() {
        Alias expectedExportAlias = new AliasBuilder().withCommand(ExportCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_EXPORT).build();

        assertParseSuccess(parser, ALIAS_DESC_EXPORT, new AliasCommand(expectedExportAlias));
    }

    @Test
    public void parse_vacantAlias_success() {
        Alias expectedVacantAlias = new AliasBuilder().withCommand(VacantCommand.COMMAND_WORD)
                .withAlias(VALID_ALIAS_VACANT).build();

        assertParseSuccess(parser, ALIAS_DESC_VACANT, new AliasCommand(expectedVacantAlias));
    }

    @Test
    public void parse_invalidAliasSyntax_failure() {
        //alias with symbols failure
        assertParseFailure(parser, INVALID_ALIAS_SYNTAX_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCommandSyntax_failure() {
        //command with symbols failure
        assertParseFailure(parser, INVALID_COMMAND_SYNTAX_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
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
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_BUILDING = "COM1";
    private static final String VALID_BUILDING_2 = "COM2";
    private static final String INVALID_BUILDING = "COM*";
    private static final String VALID_LOCATION = "com1";
    private static final String VALID_POSTAL_CODE = "117417";
    private static final String VALID_POSTAL_CODE_2 = "138527";
    private static final String VALID_ALIAS = "add1";
    private static final String INVALID_ALIAS = "add*";
    private static final Alias ADD_ALIAS = new Alias(AddCommand.COMMAND_WORD, VALID_ALIAS);
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
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

    @Test
    public void parseBuilding_invalidBuilding_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBuilding(null));
    }

    @Test
    public void parseUnalias_validUnAlias() throws Exception {
        assertEquals(VALID_ALIAS, ParserUtil.parseUnalias(VALID_ALIAS));
    }

    @Test
    public void parseUnalias_invalidUnAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUnalias(INVALID_ALIAS));
    }

    @Test
    public void parseUnalias_invalidUnAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUnalias(null));
    }

    @Test
    public void parseAlias_validAliasCommandAndAlias() throws Exception {
        assertEquals(ADD_ALIAS, ParserUtil.parseAlias(AddCommand.COMMAND_WORD, VALID_ALIAS));
    }

    @Test
    public void parseAlias_validAliasCommandAndinvalidAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAlias(AddCommand.COMMAND_WORD,
                INVALID_ALIAS));
    }

    @Test
    public void parseAlias_invalidAliasCommandAndvalidAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAlias(INVALID_ALIAS, VALID_ALIAS));
    }

    @Test
    public void parseAlias_invalidAliasCommandAndvalidAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(null, VALID_ALIAS));
    }

    @Test
    public void parseAlias_validAliasCommandAndinvalidAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(AddCommand.COMMAND_WORD, null));
    }

    @Test
    public void parseLocations_validLocation_success() {
        assertEquals(VALID_POSTAL_CODE, ParserUtil.parseLocations(VALID_POSTAL_CODE));
    }

    @Test
    public void parseLocations_multipleLocations_success() {
        String[] locations = new String[] {VALID_POSTAL_CODE, VALID_POSTAL_CODE_2};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(joinedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_validNusLocations_success() {
        assertEquals(VALID_POSTAL_CODE , ParserUtil.parseLocations(VALID_BUILDING));
    }

    @Test
    public void parseLocations_validNusLocationsMixedCase_success() {
        assertEquals(VALID_POSTAL_CODE , ParserUtil.parseLocations(VALID_LOCATION));
    }

    @Test
    public void parseLocations_validMultipleNusLocations_success() {
        String[] locationsPostalCode = new String[] {VALID_POSTAL_CODE, VALID_POSTAL_CODE};
        String expectedLocations = String.join(MapCommand.SPLIT_TOKEN, locationsPostalCode);
        String[] locations = new String[] {VALID_BUILDING, VALID_BUILDING_2};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(expectedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_validMixedLocations_success() {
        String[] locationsPostalCode = new String[] {VALID_POSTAL_CODE_2, VALID_POSTAL_CODE};
        String expectedLocations = String.join(MapCommand.SPLIT_TOKEN, locationsPostalCode);
        String[] locations = new String[] {VALID_POSTAL_CODE_2, VALID_BUILDING};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(expectedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_invalidLocation_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLocations(null));
    }
```
###### \java\seedu\address\logic\parser\UnaliasCommandParserTest.java
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
###### \java\seedu\address\logic\parser\VacantCommandParserTest.java
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
###### \java\seedu\address\model\AddressBookTest.java
``` java
        private final ObservableList<Alias> aliases = FXCollections.observableArrayList();
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        @Override
        public ObservableList<Alias> getAliasList() {
            return aliases;
        }

        @Override
        public HashMap<String, String> getAliasMapping() {
            return new HashMap<>();
        }

        @Override
        public ArrayList<ArrayList<String>> getUiFormattedAliasList() {
            return null;
        }

        @Override
        public void resetAliasList() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\model\alias\AliasTest.java
``` java
public class AliasTest {

    private Alias clear;
    private Alias add;
    private Alias addClone;
    private Alias map;

    @Before
    public void setUp() {
        AliasBuilder builder = new AliasBuilder();
        clear = builder.withCommand(ClearCommand.COMMAND_WORD).withAlias(VALID_ALIAS_CLEAR).build();
        add = builder.withCommand(AddCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ADD).build();
        addClone = builder.withCommand(AddCommand.COMMAND_WORD).withAlias(VALID_ALIAS_ADD).build();
        map = builder.withCommand(MapCommand.COMMAND_WORD).withAlias(VALID_ALIAS_MAP1).build();
    }

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
        assertFalse(Alias.isValidAliasParameter("")); // empty string
        assertFalse(Alias.isValidAliasParameter(" ")); // spaces only
        assertFalse(Alias.isValidAliasParameter(INVALID_ALIAS)); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAliasParameter(VALID_ALIAS_ADD));
        assertTrue(Alias.isValidAliasParameter(VALID_ALIAS_NUMBER));
    }

    @Test
    public void equals() {
        assertEquals(clear, new Alias(VALID_ALIAS_CLEAR_COMMAND, VALID_ALIAS_CLEAR));
    }

    @Test
    public void getCommand_validAliases_success() {
        assertEquals(add.getCommand(), AddCommand.COMMAND_WORD);
        assertEquals(map.getCommand(), MapCommand.COMMAND_WORD);
    }

    @Test
    public void getAlias_validAliases_success() {
        assertEquals(add.getAlias(), VALID_ALIAS_ADD);
        assertEquals(map.getAlias(), VALID_ALIAS_MAP1);
    }

    @Test
    public void hashCode_equals() {
        assertEquals(add.hashCode(), addClone.hashCode());
        assertNotEquals(add.hashCode(), clear.hashCode());
    }

    @Test
    public void toString_equals() {
        String expectedString = String.format("[%s]", add.getAlias());
        assertEquals(add.toString(), expectedString);
    }
}
```
###### \java\seedu\address\model\ModelStub.java
``` java
/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(Person person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newAliasList) {
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
    public void updateAliasesMapping(HashMap<String, String> aliases) {
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
    public void uploadAddressBook(String filepath, Password password) {
        fail("This method should not be called.");
    }

    @Override
    public void addAlias(Alias alias) throws DuplicateAliasException {
        fail("This method should not be called.");
    }

    @Override
    public HashMap<String, String> getAliasList() {
        return new HashMap<String, String>();
    }

    @Override
    public String getCommandFromAlias(String aliasKey) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> getUiFormattedAliasList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
            throws BuildingNotFoundException {
        fail("This method should not be called.");
        return null;
    }
}
```
###### \java\seedu\address\model\UniqueAliasListTest.java
``` java
public class UniqueAliasListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueAliasList uniqueAliasList;

    @Before
    public void setUp() {
        uniqueAliasList = new UniqueAliasList();
    }

    @After
    public void clean() throws AliasNotFoundException {
        clearAliasList();
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueAliasList = new UniqueAliasList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasList.asObservableList().remove(0);
    }

    @Test
    public void addAlias_validAlias_success() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void addAlias_validAlias_throwsDuplicateAliasException() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        thrown.expect(DuplicateAliasException.class);
        uniqueAliasList.add(validAlias);
    }

    @Test
    public void removeAlias_validAlias_success() throws DuplicateAliasException, AliasNotFoundException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);
        uniqueAliasList.remove(validAlias.getAlias());

        UniqueAliasList expectedList = new UniqueAliasList();
        assertEquals(uniqueAliasList.getAliasObservableList(), expectedList.asObservableList());
    }

    @Test
    public void removeAlias_invalidAlias_failure() throws AliasNotFoundException {
        Alias validAlias = ADD;
        thrown.expect(AliasNotFoundException.class);
        uniqueAliasList.remove(validAlias.getAlias());
    }

    @Test
    public void getAliasCommand_validAlias_success() throws DuplicateAliasException {
        Alias validAlias = ADD;
        uniqueAliasList.add(validAlias);

        String command = uniqueAliasList.getCommandFromAlias(validAlias.getAlias());
        String expected = validAlias.getCommand();
        assertEquals(command, expected);
    }

    @Test
    public void importAlias_validAlias_success() {
        Alias validAlias = ADD;
        uniqueAliasList.importAlias(validAlias);
        assertEquals(Arrays.asList(validAlias), uniqueAliasList.getAliasObservableList());
    }

    @Test
    public void extractAliasMapping_noAliasAdded_success() {
        ArrayList<String> expectedList = new ArrayList<String>();
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_validAliasAdded_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_differentCommandAliases_success() throws DuplicateAliasException,
            AliasNotFoundException {
        uniqueAliasList.add(ADD);
        uniqueAliasList.add(EDIT);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD, EDIT}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_sameCommandAliases_success() throws DuplicateAliasException {
        uniqueAliasList.add(MAP_1);
        uniqueAliasList.add(MAP_2);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{MAP_1}, {MAP_2}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void extractAliasMapping_mixedCommandAliases_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);
        uniqueAliasList.add(MAP_1);
        uniqueAliasList.add(MAP_2);

        ArrayList<ArrayList<String>> expectedList = generateExpectedList(new Alias[][]{{ADD, MAP_1}, {MAP_2}});
        assertEquals(expectedList, uniqueAliasList.extractAliasMapping());
    }

    @Test
    public void resetHashMap_success() throws DuplicateAliasException {
        uniqueAliasList.add(ADD);
        assertEquals(Arrays.asList(ADD), uniqueAliasList.getAliasObservableList());
        uniqueAliasList.resetHashmap();
        assertEquals(new ArrayList<Alias>(), uniqueAliasList.getAliasObservableList());
    }

    /**
     * Generates an expected list with the aliases in the testAliasList inserted at their correct positions.
     */
    private ArrayList<ArrayList<String>> generateExpectedList(Alias[][] testAliasList) {
        ArrayList<ArrayList<String>> expectedList = new ArrayList<ArrayList<String>>();
        for (Alias[] row : testAliasList) {
            ArrayList<String> innerList = populateEmptyAlias();
            insertAliasAtPositions(row, innerList);
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Inserts the alias command at their respective positions in the array.
     */
    private void insertAliasAtPositions(Alias[] testInnerAliasList, ArrayList<String> innerList) {
        for (Alias alias : testInnerAliasList) {
            innerList.set(AliasCommand.getCommands().indexOf(alias.getCommand()), alias.getAlias());
        }
    }

    /**
     * Removes all aliases in the list.
     *
     * @throws AliasNotFoundException if the Alias to add is not an existing Alias in the list.
     */
    private void clearAliasList() throws AliasNotFoundException {
        for (Alias alias : uniqueAliasList.getAliasObservableList()) {
            uniqueAliasList.remove(alias.getAlias());
        }
    }

    /**
     * Creates an empty arraylist of size number of commands.
     */
    private ArrayList<String> populateEmptyAlias() {
        ArrayList<String> emptyList = new ArrayList<String>();
        int size = AliasCommand.getCommands().size();
        for (int i = 0; i < size; i++) {
            emptyList.add("");
        }
        return emptyList;
    }
}
```
###### \java\seedu\address\testutil\AliasBuilder.java
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
###### \java\seedu\address\testutil\AliasUtil.java
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
     * Returns an unalias command string for removing the {@code alias}.
     */
    public static String getUnliasCommand(String unalias) {
        return UnaliasCommand.COMMAND_WORD + " " + unalias;
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
###### \java\seedu\address\testutil\TypicalAliases.java
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
    public static final Alias NONEXISTENT = new AliasBuilder().withCommand("list").withAlias("nonexistent").build();
    public static final Alias REDO = new AliasBuilder().withCommand("redo").withAlias("redo1").build();
    public static final Alias UNDO = new AliasBuilder().withCommand("undo").withAlias("undo1").build();
    public static final Alias UPLOAD = new AliasBuilder().withCommand("upload").withAlias("911").build();
    public static final Alias UNION = new AliasBuilder().withCommand("union").withAlias("union1").build();
    public static final Alias UNKNOWN = new AliasBuilder().withCommand("unknownCommand").withAlias("add1").build();
    public static final Alias MAP_1 = new AliasBuilder().withCommand("map").withAlias("map1").build();
    public static final Alias MAP_2 = new AliasBuilder().withCommand("map").withAlias("map2").build();


    private TypicalAliases() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical aliases.
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
        return new ArrayList<>(Arrays.asList(ADD, ALIAS, CLEAR, DELETE, EDIT, EXIT, HELP, HISTORY, LIST, REDO,
                UNDO, UNION, MAP_2, MAP_1, UPLOAD));
    }
}
```
###### \java\seedu\address\ui\AliasListTest.java
``` java
public class AliasListTest extends GuiUnitTest {
    private AliasList aliasList;
    private AliasListHandle aliasListHandle;

    @Before
    public void setUp() {
        aliasList = new AliasList();
        uiPartRule.setUiPart(aliasList);
        aliasListHandle = new AliasListHandle(getChildNode(aliasList.getRoot(), aliasListHandle.ALIAS_LIST_ID));
    }

    @Test
    public void checkTable() {

        /* Case: add two aliases in the first row and one alias in the second row */
        String[][] expected = new String[][] {{"add1", "alias1"}, {"add2"}};

        // Init alias list and post event
        ArrayList<ArrayList<String>> expectedList = GuiTestAssert.populateExpectedAliases(expected);
        ObservableList<ArrayList<String>> obsExpected = FXCollections.observableArrayList(expectedList);
        AliasListEvent aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        aliasList.setStyle();
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);

        /* Case: add one alias in the first row */
        expected = new String[][] {{"add1"}};
        expectedList = GuiTestAssert.populateExpectedAliases(expected);

        // Init alias list and post event
        obsExpected = FXCollections.observableArrayList(expectedList);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);

        /* Case: add three aliases in the first row */
        expected = new String[][] {{"add1", "alias1", "birthday1"}};
        expectedList = GuiTestAssert.populateExpectedAliases(expected);

        // Init alias list and post event
        obsExpected = FXCollections.observableArrayList(expectedList);
        aliasListEventStub = new AliasListEvent(obsExpected);

        aliasList.init(obsExpected);
        postNow(aliasListEventStub);
        guiRobot.pauseForHuman();

        // Assert content of the table
        GuiTestAssert.assertTableContent(aliasListHandle.getTables(), expected);
    }
}
```
###### \java\seedu\address\ui\GoogleMapsDisplayTest.java
``` java
public class GoogleMapsDisplayTest extends GuiUnitTest {

    private GoogleMapsDisplay googleMapsDisplay;
    private GoogleMapsDisplayHandle googleMapsDisplayHandle;
    private GoogleMapsEvent googleMapsChangedStub;

    @Before
    public void setUp() throws IOException {
        guiRobot.interact(() -> googleMapsDisplay = new GoogleMapsDisplay());
        uiPartRule.setUiPart(googleMapsDisplay);
        googleMapsChangedStub = new GoogleMapsEvent(VALID_LOCATION_ADDRESS_1, true);

        googleMapsDisplayHandle = new GoogleMapsDisplayHandle(googleMapsDisplay.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, googleMapsDisplayHandle.getLoadedUrl());

        postNow(googleMapsChangedStub);
        URL expectedMapsUrl = new URL(GoogleMapsDisplay.MAP_SEARCH_URL_PREFIX
                + VALID_LOCATION_ADDRESS_1.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(googleMapsDisplayHandle);
        assertNotEquals(expectedMapsUrl, googleMapsDisplayHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that the content in {@code aliasListTable} matches all the string in {@code expected}
     */
    public static void assertTableContent(ObservableList<TableColumn> aliasListTable, String[][] expected) {
        ArrayList<ArrayList<String>> expectedList = populateExpectedAliases(expected);
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = 0; j < expectedList.get(i).size(); j++) {
                TableColumn column = aliasListTable.get(j);

                // Current Row value at column
                assertEquals(expectedList.get(i).get(j), column.getCellObservableValue(i).getValue());
            }
        }
    }

    /**
     * Asserts that the content in {@code aliasListTable} matches all the string in {@code expected}
     */
    public static void assertTableContent(ObservableList<TableColumn> aliasListTable, Alias[][] expected) {
        ArrayList<ArrayList<String>> expectedList = populateExpectedAliases(expected);
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = 0; j < expectedList.get(i).size(); j++) {
                TableColumn column = aliasListTable.get(j);

                // Current Row value at column
                assertEquals(expectedList.get(i).get(j), column.getCellObservableValue(i).getValue());
            }
        }
    }

    /**
     * Helper method to populate expectedTable with unused empty Alias
     */
    public static ArrayList<ArrayList<String>> populateExpectedAliases(String[][] expected) {
        String emptyAlias = "";
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        for (String[] inner : expected) {
            ArrayList<String> innerList = new ArrayList<>();
            // Add expected alias for command
            for (String alias : inner) {
                innerList.add(alias);
            }
            // Generate empty alias ("") for no alias command
            while (innerList.size() < AliasCommand.getCommands().size()) {
                innerList.add(emptyAlias);
            }
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Helper method to populate expectedTable with unused empty Alias
     */
    public static ArrayList<ArrayList<String>> populateExpectedAliases(Alias[][] expected) {
        String emptyAlias = "";
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        for (Alias[] inner : expected) {
            ArrayList<String> innerList = setExpectedAliases(emptyAlias, inner);
            expectedList.add(innerList);
        }
        return expectedList;
    }

    /**
     * Helper method to set inner expected aliases
     */
    private static ArrayList<String> setExpectedAliases(String emptyAlias, Alias[] inner) {
        ArrayList<String> innerList = createNewInnerList(emptyAlias);

        // reset expected alias for command
        for (Alias alias : inner) {
            int index = AliasCommand.getCommands().indexOf(alias.getCommand());
            innerList.set(index, alias.getAlias());
        }
        return innerList;
    }

    /**
     * Helper method to generate new inner list
     */
    private static ArrayList<String> createNewInnerList(String emptyAlias) {
        // Generate empty alias ("") for inner list
        ArrayList<String> innerList = new ArrayList<>();
        for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
            innerList.add(emptyAlias);
        }
        return innerList;
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    public GoogleMapsDisplayHandle getGoogleMapsDisplay() {
        return mainWindowHandle.getMapPanel();
    }

    public AliasListHandle getAliasList() {
        return mainWindowHandle.getAliasList();
    }

```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    protected void assertTableDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                               Alias[][] table) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        GuiTestAssert.assertTableContent(getAliasList().getTables(), table);
    }

    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertMapDisplayChanged(boolean isOneLocation, String query) {
        URL expectedUrl;
        try {
            if (isOneLocation) {
                expectedUrl = new URL(GoogleMapsDisplay.MAP_SEARCH_URL_PREFIX + query);
            } else {
                expectedUrl = new URL(GoogleMapsDisplay.MAP_DIRECTIONS_URL_PREFIX + query);
            }
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getGoogleMapsDisplay().getLoadedUrl());
    }

```
###### \java\systemtests\MapCommandSystemTest.java
``` java
/**
 * A system test class for the Google Maps Display panel, which contains interaction with other UI components.
 */
public class MapCommandSystemTest extends AddressBookSystemTest {

    private static final boolean isOneLocation = true;

    @Test
    public void map() {
        String bufferOneLocation = MapCommand.COMMAND_WORD + " " + VALID_LOCATION_BUILDING_LOWERCASE;
        executeCommand(bufferOneLocation);
        String bufferTwoLocations = MapCommand.COMMAND_WORD + " " + VALID_TWO_LOCATIONS_1;
        executeCommand(bufferTwoLocations);
        /* ----------------------------------- Perform valid map operations ----------------------------------- */

        /* Case: NUS building specified, command with no leading and trailing spaces
        * -> retrieved
        */
        String expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_LOWERCASE);
        String command = MapCommand.COMMAND_WORD + " " + VALID_LOCATION_BUILDING_LOWERCASE;
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: NUS building specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_2);
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_BUILDING_UPPERCASE_2 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Postal code specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_POSTAL_1 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_2).replaceAll(" ", "%20");
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_ADDRESS_2 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Two NUS buildings specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedSecondBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_2);
        expectedQuery = expectedFirstBuilding + "/" + expectedSecondBuilding;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_BUILDING + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Two postal codes specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        String expectedSecondPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_2);
        expectedQuery = expectedFirstPostal + "/" + expectedSecondPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_POSTAL + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Two addresses specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        String expectedSecondAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_2).replaceAll(" ", "%20");
        expectedQuery = expectedFirstAddress + "/" + expectedSecondAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_ADDRESS + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: NUS building and postal code specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        expectedQuery = expectedBuilding + "/" + expectedPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_1 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: NUS building and address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedBuilding + "/" + expectedAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_2 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: postal and address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedPostal + "/" + expectedAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_3 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Three locations specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_LOWERCASE);
        expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_2);
        expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedAddress + "/" + expectedBuilding + "/" + expectedPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_THREE_LOCATIONS + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid map operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String query, boolean isOneLocation) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertMapDisplayChanged(isOneLocation, query);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\ModelHelper.java
``` java
    /**
     * Updates {@code model}'s aliases list to display only {@code aliases}.
     */
    public static void setAliases(Model model, HashMap<String, String> aliases) {
        model.updateAliasesMapping(aliases);
    }
```
###### \java\systemtests\UnaliasCommandSystemTest.java
``` java
public class UnaliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void unalias() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform remove operations from the alias list ----------------------------- */

        /* Case: remove an alias from a non-empty address book, command with leading spaces and trailing spaces
        * -> removed
        * */
        String toDeleteAdd = VALID_ALIAS_ADD;
        generateAliases(model);
        String command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_ADD + "   ";
        Alias[] expectedAliasArray = getRemainingAliases(new Alias[] {ADD});
        Alias[][] expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteAdd, expectedAliases);

        /* Case: undo removing ADD from the list -> ADD added */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo removing ADD from the list -> ADD removed again */
        command = RedoCommand.COMMAND_WORD;
        model.removeAlias(VALID_ALIAS_ADD);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, model, expectedResultMessage, expectedAliases);

        /* Case: remove another alias from a non-empty address book, command with leading and trailing spaces
         * -> removed
         * */
        String toDeleteMap = VALID_ALIAS_MAP2;
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_MAP2 + "  ";
        expectedAliasArray = getRemainingAliases(new Alias[] {MAP_2, ADD});
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteMap, expectedAliases);

        /* Case: remove another alias from a non-empty address book, command with leading and trailing spaces
         * -> removed
         * */
        String toDeleteUpload = VALID_ALIAS_NUMBER;
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_NUMBER + "  ";
        expectedAliasArray = getRemainingAliases(new Alias[] {MAP_2, ADD, UPLOAD});
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteUpload, expectedAliases);

        /* --------------------------------- Perform invalid unalias operations ------------------------------------- */

        /* Case: remove nonexistent alias from a non-empty address book, command with leading and trailing spaces
         * -> rejected
         * */
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + INVALID_UNALIAS + "  ";
        assertCommandFailure(command, UnaliasCommand.MESSAGE_UNKNOWN_UNALIAS);

        /* Case: remove alias with symbols from a non-empty address book, command with leading and trailing spaces
         * -> rejected
         * */
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + INVALID_UNALIAS_DESC + "  ";
        assertCommandFailure(command, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    /**
     * Executes the {@code UnaliasCommand} that removes {@code toDelete} from the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code unaliasCommand} <br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * the current model without {@code toDelete}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String toDelete, Alias[][] expectedTable) {
        Model expectedModel = getModel();
        try {
            expectedModel.removeAlias(toDelete);
        } catch (AliasNotFoundException e) {
            throw new AssertionError("toDelete does not exists in the model.");
        }
        String expectedResultMessage = UnaliasCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see UnaliasCommandSystemTest#assertCommandSuccess(String, String, Alias[][])
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      String expectedResultMessage, Alias[][] expectedTable) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
        assertAliasTable(expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see UnaliasCommandSystemTest#assertCommandSuccess(String, String, Alias[][])
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    private void assertAliasTable(Alias[][] expectedTable) {
        executeCommand(ListCommand.COMMAND_WORD);
        assertTableDisplaysExpected("", ListCommand.MESSAGE_SUCCESS, expectedTable);
    }

    /**
     * To add new alias into {@code model} with typicalAliases
     * @param model the current model of the testing stage
     */
    private void generateAliases(Model model) {
        List<Alias> aliases = TypicalAliases.getTypicalAliases();
        for (Alias alias : aliases) {
            try {
                model.addAlias(alias);
            } catch (DuplicateAliasException dpe) {
                throw new IllegalArgumentException("toAdd already exists in the model.");
            }
            executeCommand(AliasUtil.getAliasCommand(alias));
        }
    }

    /**
     * To generate a new expected aliases list with typicalAliases excluding {@code aliases}
     * @param aliases the aliases to be excluded from typicalAlias list
     * @return the expected aliases excuding {@code aliases}
     */
    private Alias[] getRemainingAliases(Alias[] aliases) {
        List<Alias> expectedAliases = TypicalAliases.getTypicalAliases();
        for (Alias alias : aliases) {
            expectedAliases.remove(alias);
        }
        return expectedAliases.toArray(new Alias[expectedAliases.size()]);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\VacantCommandSystemTest.java
``` java
/**
 * A system test class for the Venue Table table view, which contains interaction with other UI components.
 */
public class VacantCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void vacant() {
        /* ----------------------------------- Perform valid vacant operations  ----------------------------------- */

        /* Case: find building COM2, command with no leading and trailing spaces -> retrieved */
        String command = VacantCommand.COMMAND_WORD + " " + VALID_BUILDING_1;
        assertCommandSuccess(command);

        /* Case: find building COM2, command with leading spaces and trailing spaces
         * -> retrieved
         */
        command = "   " + VacantCommand.COMMAND_WORD + "   " + VALID_BUILDING_1 + "   ";
        assertCommandSuccess(command);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid vacant operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));

        /* Case: invalid number of parameters -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));

        /* Case: invalid building regex -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_1,
                String.format(MESSAGE_BUILDING_CONSTRAINTS));

        /* Case: invalid building name -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_3,
                String.format(MESSAGE_INVALID_BUILDING));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_VACANT_COMMAND_WORD + " " + VALID_BUILDING_1, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
