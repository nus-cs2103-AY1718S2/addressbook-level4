# x3tsunayh
###### \java\guitests\guihandles\BrowserPanelHandle.java
``` java

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends StageHandle {

    public static final String GOOGLE_WINDOW_TITLE = "Google";
    private static final String GOOGLE_WINDOW_BROWSER_ID = "#browser";

    private boolean isWebViewLoaded = true;

    public BrowserPanelHandle(Stage browserPanelStage) {
        super(browserPanelStage);
        WebView webView = getChildNode(GOOGLE_WINDOW_BROWSER_ID);
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
     * Returns true if a Google window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(GOOGLE_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(GOOGLE_WINDOW_BROWSER_ID));
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     * Returns the {@code URL} of the currently loaded page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
```
###### \java\guitests\guihandles\EventCardHandle.java
``` java

/**
 * Provides a handle to an event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String LOCATION_FIELD_ID = "#eventLocation";
    private static final String DATETIME_FIELD_ID = "#datetime";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label locationLabel;
    private final Label datetimeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.datetimeLabel = getChildNode(DATETIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getDatetime() {
        return datetimeLabel.getText();
    }

}
```
###### \java\guitests\guihandles\EventListPanelHandle.java
``` java

/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code EventCard}.
 */
public class EventListPanelHandle extends NodeHandle<ListView<EventCard>> {
    public static final String EVENT_LIST_VIEW_ID = "#eventListView";

    private Optional<EventCard> lastRememberedSelectedEventCard;

    public EventListPanelHandle(ListView<EventCard> eventListPanelNode) {
        super(eventListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public EventCardHandle getHandleToSelectedCard() {
        List<EventCard> eventList = getRootNode().getSelectionModel().getSelectedItems();

        if (eventList.size() != 1) {
            throw new AssertionError("Event list size expected 1.");
        }

        return new EventCardHandle(eventList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<EventCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the event.
     */
    public void navigateToCard(ReadOnlyEvent event) {
        List<EventCard> cards = getRootNode().getItems();
        Optional<EventCard> matchingCard = cards.stream().filter(card -> card.event.equals(event)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Event does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the event card handle of an event associated with the {@code index} in the list.
     */
    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle((Event) getRootNode().getItems().get(index).event);
    }

    /**
     * Returns the {@code EventCardHandle} of the specified {@code event} in the list.
     */
    public EventCardHandle getEventCardHandle(Event event) {
        Optional<EventCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.event.equals(event))
                .map(card -> new EventCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Event does not exist."));
    }

    /**
     * Selects the {@code EventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code EventCard} in the list.
     */
    public void rememberSelectedEventCard() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEventCard = Optional.empty();
        } else {
            lastRememberedSelectedEventCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code EventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedEventCard()} call.
     */
    public boolean isSelectedEventCardChanged() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedEventCard.isPresent();
        } else {
            return !lastRememberedSelectedEventCard.isPresent()
                    || !lastRememberedSelectedEventCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\guihandles\LinkedInWindowHandle.java
``` java

/**
 * A handle to the {@code LinkedInWindow} of the application.
 */
public class LinkedInWindowHandle extends StageHandle {

    public static final String LINKEDIN_WINDOW_TITLE = "LinkedIn";

    private static final String LINKEDIN_WINDOW_BROWSER_ID = "#browser";

    public LinkedInWindowHandle(Stage linkedInWindowStage) {
        super(linkedInWindowStage);
    }

    /**
     * Returns true if a LinkedIn window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(LINKEDIN_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(LINKEDIN_WINDOW_BROWSER_ID));
    }
}
```
###### \java\guitests\guihandles\TaskCardHandle.java
``` java

/**
 * Provides a handle to an event card in the event list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DUEDATE_FIELD_ID = "#dueDate";
    private static final String PRIORITY_FIELD_ID = "#priority";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label dueDateLabel;
    private final Label priorityLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.dueDateLabel = getChildNode(DUEDATE_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTaskTitle() {
        return titleLabel.getText();
    }

    public String getTaskDescription() {
        return descriptionLabel.getText();
    }

    public String getTaskDueDate() {
        return dueDateLabel.getText();
    }

    public String getTaskPriority() {
        return priorityLabel.getText();
    }

}
```
###### \java\guitests\guihandles\TaskListPanelHandle.java
``` java

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code TaskCard}.
 */
public class TaskListPanelHandle extends NodeHandle<ListView<TaskCard>> {
    public static final String TASK_LIST_VIEW_ID = "#taskListView";

    private Optional<TaskCard> lastRememberedSelectedTaskCard;

    public TaskListPanelHandle(ListView<TaskCard> taskListPanelNode) {
        super(taskListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TaskCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TaskCardHandle getHandleToSelectedCard() {
        List<TaskCard> taskList = getRootNode().getSelectionModel().getSelectedItems();

        if (taskList.size() != 1) {
            throw new AssertionError("Task list size expected 1.");
        }

        return new TaskCardHandle(taskList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<TaskCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public void navigateToCard(Task task) {
        List<TaskCard> cards = getRootNode().getItems();
        Optional<TaskCard> matchingCard = cards.stream().filter(card -> card.task.equals(task)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the task card handle of a task associated with the {@code index} in the list.
     */
    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle((Task) getRootNode().getItems().get(index).task);
    }

    /**
     * Returns the {@code TaskCardHandle} of the specified {@code task} in the list.
     */
    public TaskCardHandle getTaskCardHandle(Task task) {
        Optional<TaskCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.task.equals(task))
                .map(card -> new TaskCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Task does not exist."));
    }

    /**
     * Selects the {@code TaskCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code TaskCard} in the list.
     */
    public void rememberSelectedTaskCard() {
        List<TaskCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTaskCard = Optional.empty();
        } else {
            lastRememberedSelectedTaskCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TaskCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTaskCard()} call.
     */
    public boolean isSelectedEventCardChanged() {
        List<TaskCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTaskCard.isPresent();
        } else {
            return !lastRememberedSelectedTaskCard.isPresent()
                    || !lastRememberedSelectedTaskCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\seedu\address\commons\util\XmlUtilTest.java
``` java
    @Test
    public void saveDataToFile_nullEventFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new EventBook());
    }

    @Test
    public void saveDataToFile_nullEventClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_TASKBOOK_FILE, null);
    }

    @Test
    public void saveDataToFile_missingEventFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new EventBook());
    }

```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_addEventSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void equals() {
        Event test1 = new EventBuilder().withTitle("Test 1").build();
        Event test2 = new EventBuilder().withTitle("Test 2").build();
        AddEventCommand addCommandTest1 = new AddEventCommand(test1);
        AddEventCommand addCommandTest2 = new AddEventCommand(test2);

        // same object -> returns true
        assertTrue(addCommandTest1.equals(addCommandTest1));
        assertTrue(addCommandTest2.equals(addCommandTest2));

        // different types -> returns false
        assertFalse(addCommandTest1.equals(1));

        // null -> returns false
        assertFalse(addCommandTest1.equals(null));

        // different event -> returns false
        assertFalse(addCommandTest1.equals(addCommandTest2));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
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
        public void resetData(ReadOnlyEventBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTaskBook newDate) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return null;
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
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
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersons() {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent toAdd) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEvent(ReadOnlyEvent eventToDelete) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getFilteredEventList() {
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void sortEventList(String parameter) throws CommandException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub with working addEvent method but fails irrelevant methods.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<ReadOnlyEvent> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent toAdd) {
            requireNonNull(toAdd);
            eventsAdded.add(toAdd);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return new TaskBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_XML_FILEPATH = "validXmlFile.xml";
    public static final String VALID_CSV_FILEPATH = "validCsvFile.csv";
    public static final String INVALID_XML_FILEPATH = "invalidXmlFile.xmll";
    public static final String INVALID_CSV_FILEPATH = "invalidCsvFile.csvv";
    public static final String EXISTING_XML_FILEPATH = "existingXmlFile.xml";
    public static final String EXISTING_CSV_FILEPATH = "existingCsvFile.csv";
    public static final String VALID_TITLE_CNY = "CNY Celebration 2018";
    public static final String VALID_TITLE_CHRISTMAS = "Christmas Celebration 2018";
    public static final String VALID_DESCRIPTION_CNY = "CNY Celebration at FOS";
    public static final String VALID_DESCRIPTION_CHRISTMAS = "Christmas Party at SOC";
    public static final String VALID_LOCATION_CNY = "NUS S16 Level 3";
    public static final String VALID_LOCATION_CHRISTMAS = "NUS COM1";
    public static final String VALID_DATETIME_CNY = "2018-02-15 1000";
    public static final String VALID_DATETIME_CHRISTMAS = "2018-12-24 1830";

    public static final String TITLE_DESC_CNY = " " + PREFIX_EVENT_TITLE + VALID_TITLE_CNY;
    public static final String TITLE_DESC_CHRISTMAS = " " + PREFIX_EVENT_TITLE + VALID_TITLE_CHRISTMAS;
    public static final String DESCRIPTION_DESC_CNY = " " + PREFIX_EVENT_DESCRIPTION + VALID_DESCRIPTION_CNY;
    public static final String DESCRIPTION_DESC_CHRISTMAS = " "
            + PREFIX_EVENT_DESCRIPTION + VALID_DESCRIPTION_CHRISTMAS;
    public static final String LOCATION_DESC_CNY = " " + PREFIX_EVENT_LOCATION + VALID_LOCATION_CNY;
    public static final String LOCATION_DESC_CHRISTMAS = " " + PREFIX_EVENT_LOCATION + VALID_LOCATION_CHRISTMAS;
    public static final String DATETIME_DESC_CNY = " " + PREFIX_EVENT_DATETIME + VALID_DATETIME_CNY;
    public static final String DATETIME_DESC_CHRISTMAS = " " + PREFIX_EVENT_DATETIME + VALID_DATETIME_CHRISTMAS;

    public static final String VALID_DATETIME_01 = "2018-12-01 2359";
    public static final String VALID_DATETIME_29 = "2018-12-29 2359";
    public static final String VALID_DATETIME_30 = "2018-12-30 2359";
    public static final String VALID_DATETIME_31 = "2018-12-31 2359";
    public static final String INVALID_DATETIME_00 = "2018-12-00 2359"; //There is no 00 in the date
    public static final String INVALID_DATETIME_32 = "2018-12-32 2359"; //There is no 32 in the date

    public static final String VALID_DATETIME_DESC_01 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_01;
    public static final String VALID_DATETIME_DESC_29 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_29;
    public static final String VALID_DATETIME_DESC_30 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_30;
    public static final String VALID_DATETIME_DESC_31 = " "
            + PREFIX_EVENT_DATETIME + VALID_DATETIME_31;
    public static final String INVALID_DATETIME_DESC_00 = " "
            + PREFIX_EVENT_DATETIME + INVALID_DATETIME_00;
    public static final String INVALID_DATETIME_DESC_32 = " "
            + PREFIX_EVENT_DATETIME + INVALID_DATETIME_32;

```
###### \java\seedu\address\logic\commands\DeleteEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel =
                new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(event.getTitle())));
        // checks if filtered event list only has one event here
        assert model.getFilteredEventList().size() == 1;

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel =
                new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(event.getTitle())));
        // checks if filtered event list only has one event here
        assert model.getFilteredEventList().size() == 1;

        Index outOfBoundIndex = Index.fromOneBased(2);
        // ensures outOfBoundIndex is still within size of Event Book
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondEventCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstEventCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteEventCommand prepareCommand(Index index) {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(index);
        deleteEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteEventCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assert model.getFilteredEventList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java

public class ExportCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Storage storage;
    private Model model;

    @Before
    public void setUp() {
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(getFilePath("addressbook.xml"));
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getFilePath("eb.xml"));
        TaskBookStorage taskBookStorage = new XmlTaskBookStorage(getFilePath("tb.xml"));
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getFilePath("preferences.json"));

        storage = new StorageManager(addressBookStorage, eventBookStorage, taskBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    }

    @Test
    public void execute_validXmlFilePath_success() {
        String filePath = getFilePath(VALID_XML_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_validCsvFilePath_success() {
        String filePath = getFilePath(VALID_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidXmlFileExtension_throwsCommandException() {
        String filePath = getFilePath(INVALID_XML_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_NOT_XML_CSV_FILE);

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidCsvFileExtension_throwsCommandException() {
        String filePath = getFilePath(INVALID_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_NOT_XML_CSV_FILE);

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_existingXmlName_throwsCommandException() {
        ExportCommand command = prepareCommand(EXISTING_XML_FILEPATH);
        try {
            storage.exportAddressBook(model.getAddressBook(), EXISTING_XML_FILEPATH);
        } catch (ExistingFileException e) {
            //do nothing if correct exception is thrown
        } catch (IOException | InvalidFileException e) {
            throw new AssertionError("The expected CommandException was not thrown.", e);
        }
    }

    @Test
    public void execute_existingCsvName_throwsCommandException() {
        String filePath = getFilePath(EXISTING_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        try {
            storage.exportAddressBook(model.getAddressBook(), EXISTING_CSV_FILEPATH);
        } catch (ExistingFileException e) {
            //do nothing if correct exception is thrown
        } catch (IOException | InvalidFileException e) {
            throw new AssertionError("The expected CommandException was not thrown.", e);
        }
    }


    private String getFilePath(String fileName) {

        return testFolder.getRoot().getPath() + fileName;
    }

    /**
     * Returns an {@code ExportCommand} with parameter {@String filePath}
     */
    private ExportCommand prepareCommand(String filePath) {
        ExportCommand exportCommand = new ExportCommand(filePath);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exportCommand.setStorage(storage);
        return exportCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ExportCommand command, String expectedMessage, String filePath) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            // Storage does not support CSV readability for addressbook data.
            if (!FileUtil.isValidCsvFile(filePath)) {
                assertEquals(model.getAddressBook(), new AddressBook(storage.readAddressBook(filePath).get()));
            }
        } catch (CommandException | DataConversionException | IOException | JAXBException e) {
            throw new AssertionError("Export Command is not working as expected.", e);
        }
    }

    /**
     * Asserts that {@code command} is executed, but<br>
     *     - the correct CommandException is thrown<br>
     */
    public void assertCommandFailure(ExportCommand command, String expectedMessage, String filePath) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertFalse((new File(filePath)).exists());
        }
    }

}
```
###### \java\seedu\address\logic\commands\FindEventCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("Event One"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("Event Two"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        FindEventCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        FindEventCommand command = prepareCommand("CNY Christmas Movie");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CHRISTMAS, CNY, MOVIE));
    }

    /**
     * Parses {@code userInput} into a {@code FindEventCommand}.
     */
    private FindEventCommand prepareCommand(String userInput) {
        FindEventCommand command =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Event>} is equal to {@code expectedList}<br>
     *     - the {@code EventBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEventCommand command, String expectedMessage,
                                      List<ReadOnlyEvent> expectedList) {
        EventBook expectedEventBook = new EventBook(model.getEventBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
        assertEquals(expectedEventBook.toString(), model.getEventBook().toString());
    }
}
```
###### \java\seedu\address\logic\commands\ListAllEventsCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAllEventsCommand.
 */
public class ListAllEventsCommandTest {

    private Model model;
    private Model expectedModel;
    private ListAllEventsCommand listEventCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel =
                new ModelManager(model.getAddressBook(), model.getEventBook(), model.getTaskBook(), new UserPrefs());

        listEventCommand = new ListAllEventsCommand();
        listEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listNotFiltered_showsSameList() {
        String expectedMessage = String.format("All "
                + getMessageForEventListShownSummary(expectedModel.getFilteredEventList().size()));
        assertCommandSuccess(listEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(event.getTitle())));
        String expectedMessage = String.format("All "
                + getMessageForEventListShownSummary(expectedModel.getFilteredEventList().size()));

        // ensures only one event in the filtered event list
        assert model.getFilteredEventList().size() == 1;

        assertCommandSuccess(listEventCommand, model, expectedMessage, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\SortEventCommandTest.java
``` java

public class SortEventCommandTest {
    private Model model;
    private Model expectedModel;

    private String firstParameter;
    private String secondParameter;
    private String thirdParameter;

    @Before
    public void setUp() {
        // all possible parameters declared here
        firstParameter = "TITLE";
        secondParameter = "LOCATION";
        thirdParameter = "DATETIME";

        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel =
                new ModelManager(model.getAddressBook(), model.getEventBook(), model.getTaskBook(), new UserPrefs());

    }

    @Test
    public void execute_listIsOrdered_showsEverything() {
        SortEventCommand command = prepareCommand(firstParameter);
        assertCommandSuccess(command, model, SortEventCommand.MESSAGE_SORT_SUCCESS
                + firstParameter, expectedModel);
    }

    @Test
    public void execute_emptyParameter_listNotSorted() throws CommandException {
        SortEventCommand command = prepareCommand("");
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_whitespaceParameter_listNotSorted() throws CommandException {
        SortEventCommand command = prepareCommand(" ");
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_titleParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(firstParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + firstParameter,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }


    @Test
    public void execute_locationParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(secondParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + secondParameter,
                Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }

    @Test
    public void execute_datetimeParameter_listSorted() throws CommandException {
        SortEventCommand command = prepareCommand(thirdParameter);
        assertSortSuccess(command, SortEventCommand.MESSAGE_SORT_SUCCESS + thirdParameter,
                // list order is backwards, from latest event to oldest event
                Arrays.asList(REUNION, MOVIE, CNY, CHRISTMAS));
    }

    @Test
    public void equals() {
        final SortEventCommand standardCommand = new SortEventCommand(firstParameter);

        // same values -> returns true
        SortEventCommand commandWithSameValues = new SortEventCommand("TITLE");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different parameter -> returns false
        assertFalse(standardCommand.equals(new SortEventCommand(secondParameter)));
        assertFalse(standardCommand.equals(new SortEventCommand(thirdParameter)));

    }

    /**
     * Generates a new OrderCommand
     */
    private SortEventCommand prepareCommand(String parameter) {
        SortEventCommand sortEventCommand = new SortEventCommand(parameter);
        sortEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortEventCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     */
    private void assertSortSuccess(SortEventCommand command, String expectedMessage,
                                   List<ReadOnlyEvent> expectedList) throws CommandException {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(AddEventCommand.COMMAND_WORD + " "
                + event.getEventDetails());
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        List<String> keywords = Arrays.asList("movie", "date", "party");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " et/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_sortEvent() throws Exception {
        assertTrue(parser.parseCommand(SortEventCommand.COMMAND_WORD + " TITLE") instanceof SortEventCommand);
    }

    @Test
    public void parseCommand_clearEvents() throws Exception {
        assertTrue(parser.parseCommand(ClearEventsCommand.COMMAND_WORD) instanceof ClearEventsCommand);
        assertTrue(parser.parseCommand(ClearEventsCommand.COMMAND_WORD + " 3") instanceof ClearEventsCommand);
    }

    @Test
    public void parseCommand_listAllEvents() throws Exception {
        assertTrue(parser.parseCommand(ListAllEventsCommand.COMMAND_WORD) instanceof ListAllEventsCommand);
        assertTrue(parser.parseCommand(ListAllEventsCommand.COMMAND_WORD + " 3") instanceof ListAllEventsCommand);
    }

    @Test
    public void parseCommand_jumpto() throws Exception {
        assertTrue(parser.parseCommand(JumpToCommand.COMMAND_WORD + " 2018-05") instanceof JumpToCommand);
    }

    @Test
    public void parseCommand_switchtab() throws Exception {
        assertTrue(parser.parseCommand(SwitchTabCommand.COMMAND_WORD) instanceof SwitchTabCommand);
        assertTrue(parser.parseCommand(SwitchTabCommand.COMMAND_WORD + " 3") instanceof SwitchTabCommand);
    }

```
###### \java\seedu\address\logic\parser\DeleteEventCommandParserTest.java
``` java

public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "one", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindEventCommandParserTest.java
``` java

public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty input not allowed
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no prefixes given
        assertParseFailure(parser, "CNY Christmas", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindEventCommand expectedFindCommand =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList("CNY", "Christmas")));
        // multiple keywords allowed
        assertParseSuccess(parser, "et/CNY Christmas", expectedFindCommand);

        // multiple whitespaces allowed
        assertParseSuccess(parser, "et/ \n CNY \n \t Christmas  \t", expectedFindCommand);
    }
}
```
###### \java\seedu\address\logic\parser\GoogleCommandParserTest.java
``` java

public class GoogleCommandParserTest {

    private GoogleCommandParser parser = new GoogleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty input not allowed
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GoogleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_returnsGoogleCommand() {
        //invalid args that are not indexes
        assertParseFailure(parser, "one", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GoogleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        GoogleCommand expectedGoogleCommand =
                new GoogleCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedGoogleCommand);
    }

}
```
###### \java\seedu\address\logic\parser\JumpToCommandParserTest.java
``` java

public class JumpToCommandParserTest {

    private JumpToCommandParser parser = new JumpToCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty input not allowed
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // incorrect yyyy input
        assertParseFailure(parser, "18999-12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // incorrect mm input
        assertParseFailure(parser, "18999-112", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // incorrect format
        assertParseFailure(parser, "18999#12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // yyyy out of bounds
        assertParseFailure(parser, "1899-12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // yyyy out of bounds
        assertParseFailure(parser, "2301-01", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // mm out of bounds
        assertParseFailure(parser, "2105-00", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // mm out of bounds
        assertParseFailure(parser, "2109-13", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validArgs_returnsJumpToCommand() {
        JumpToCommand expectedJumpToCommand =
                new JumpToCommand(YearMonth.of(2018, 5));

        assertParseSuccess(parser, " 2018-05", expectedJumpToCommand);
    }

}
```
###### \java\seedu\address\logic\parser\SortEventCommandParserTest.java
``` java

public class SortEventCommandParserTest {

    private SortEventCommandParser parser = new SortEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArg_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        SortEventCommand expectedSortTitleCommand = new SortEventCommand("TITLE");
        SortEventCommand expectedSortLocationCommand = new SortEventCommand("LOCATION");
        SortEventCommand expectedSortDatetimeCommand = new SortEventCommand("DATETIME");

        //same value
        assertParseSuccess(parser, "TITLE", expectedSortTitleCommand);
        assertParseSuccess(parser, "LOCATION", expectedSortLocationCommand);
        assertParseSuccess(parser, "DATETIME", expectedSortDatetimeCommand);

        //case insensitive
        assertParseSuccess(parser, "tITLe", expectedSortTitleCommand);
        assertParseSuccess(parser, "LOCAtion", expectedSortLocationCommand);
        assertParseSuccess(parser, "DATetimE", expectedSortDatetimeCommand);
    }
}
```
###### \java\seedu\address\model\event\DatetimeTest.java
``` java

public class DatetimeTest {

    @Test
    public void isValidDate() {
        // invalid datetimes
        assertFalse(Datetime.isValidDatetime("")); // empty string
        assertFalse(Datetime.isValidDatetime(" ")); // whitespace only
        assertFalse(Datetime.isValidDatetime("20180401")); // numbers only
        assertFalse(Datetime.isValidDatetime("1st April 2018")); // characters only
        assertFalse(Datetime.isValidDatetime("test123")); // numbers and characters
        assertFalse(Datetime.isValidDatetime("2018-09-1 1845")); // invalid date format
        assertFalse(Datetime.isValidDatetime("2018-02-29 1915")); // invalid day
        assertFalse(Datetime.isValidDatetime("2018-13-02 2030")); // invalid month
        assertFalse(Datetime.isValidDatetime("18-09-02 2030")); // invalid year
        assertFalse(Datetime.isValidDatetime("2018-09-02 2410")); // invalid hour
        assertFalse(Datetime.isValidDatetime("2018-09-02 2060")); // invalid minute

        // valid datetimes
        assertTrue(Datetime.isValidDatetime("2018-02-01 0800"));
        assertTrue(Datetime.isValidDatetime("2018-05-29 1030"));
        assertTrue(Datetime.isValidDatetime("2018-10-30 1345"));
        assertTrue(Datetime.isValidDatetime("2018-12-31 2015"));
    }
}
```
###### \java\seedu\address\model\event\TitleContainsKeywordsPredicateTest.java
``` java

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("firstEvent");
        List<String> secondPredicateKeywordList = Arrays.asList("firstEvent", "firstEvent");

        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordsPredicate firstPredicateCopy =
                new TitleContainsKeywordsPredicate(firstPredicateKeywordList);

        // different types -> returns false
        assertFalse(firstPredicate.equals(5));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventNameContainsKeywords_returnsTrue() {
        // One keyword
        TitleContainsKeywordsPredicate predicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("EventOne"));
        assertTrue(predicate.test((new EventBuilder().withTitle("EventOne").build())));

        // Multiple keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EventOne", "Project"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EventOne", "NA"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne is over").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EvenTONe", "pRojEct"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Uppercase keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("RANDOMEVENT", "PROJECT"));
        assertTrue(predicate.test(new EventBuilder().withTitle("TaskRandom Project").build()));

        // Different keywords order
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Project", "EventOne"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Partial keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("ject", "Event"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));
    }

    @Test
    public void test_taskNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withTitle("Event").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("unrelated"));
        assertFalse(predicate.test(new EventBuilder().withTitle("Event Project").build()));

    }

}
```
###### \java\seedu\address\model\event\UniqueEventListTest.java
``` java

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\EventBookTest.java
``` java

public class EventBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventBook eventBook = new EventBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventBook_replacesData() {
        EventBook newData = getTypicalEventBook();
        eventBook.resetData(newData);
        assertEquals(newData, eventBook);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeated events should throw AssertionError
        List<Event> newEvents = Arrays.asList(new Event(CNY), new Event(CNY));
        EventBookStub newData = new EventBookStub(newEvents);

        thrown.expect(AssertionError.class);
        eventBook.resetData(newData);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        // Event List should not be altered unknowingly by unsupported operations
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventBook whose events lists can violate interface constraints.
     */
    private static class EventBookStub implements ReadOnlyEventBook {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventBookStub(Collection<? extends ReadOnlyEvent> events) {
            this.events.setAll(events);
        }

        @Override
        public ObjectProperty<String> titleProperty() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public ObjectProperty<String> descriptionProperty() {
            return null;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public ObjectProperty<String> locationProperty() {
            return null;
        }

        @Override
        public String getLocation() {
            return null;
        }

        @Override
        public ObjectProperty<String> datetimeProperty() {
            return null;
        }

        @Override
        public String getDatetime() {
            return null;
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
    }
}
```
###### \java\seedu\address\storage\XmlEventBookStorageTest.java
``` java

public class XmlEventBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("./src/test/data/XmlEventBookStorageTest/");

    private static final String HEADER = "Title,Description,Location,Datetime";
    private static final String EXPORT_DATA = "TempEventBook.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventBook(null);
    }

    private java.util.Optional<ReadOnlyEventBook> readEventBook(String filePath) throws Exception {
        return new XmlEventBookStorage(filePath)
                .readEventBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readAndSaveEventBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventBook.xml";
        EventBook original = getTypicalEventBook();
        XmlEventBookStorage xmlEventBookStorage = new XmlEventBookStorage(filePath);

        // saves in new file and read again; ensures data integrity
        xmlEventBookStorage.saveEventBook(original, filePath);
        ReadOnlyEventBook readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original, new EventBook(readBack));


    }

    @Test
    public void saveEventBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventBook(null, "SomeFile.xml");
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException()
            throws IllegalValueException, CommandException {
        XmlSerializableEventBook eventBook = new XmlSerializableEventBook();
        thrown.expect(UnsupportedOperationException.class);
        eventBook.toModelType().getEventList().remove(0);
    }

    /**
     * Saves {@code eventBook} at the specified {@code filePath}.
     */
    private void saveEventBook(ReadOnlyEventBook eventBook, String filePath) {
        try {
            new XmlEventBookStorage(filePath)
                    .saveEventBook(eventBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should be no error writing to this file.", ioe);
        }
    }

    @Test
    public void saveEventBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventBook(new EventBook(), null);
    }
}
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Halloween Horror Night";
    public static final String DEFAULT_DESCRIPTION = "Terrifying Night";
    public static final String DEFAULT_LOCATION = "Universal Studio";
    public static final String DEFAULT_DATETIME = "2017-10-13 2359";

    private Event event;

    public EventBuilder() {
        try {
            String defaultTitle = new String(DEFAULT_TITLE);
            String defaultDescription = new String(DEFAULT_DESCRIPTION);
            String defaultLocation = new String(DEFAULT_LOCATION);
            Datetime defaultDatetime = new Datetime(DEFAULT_DATETIME);
            this.event = new Event(defaultTitle, defaultDescription, defaultLocation, defaultDatetime);
        } catch (IllegalValueException e) {
            throw new AssertionError("Incorrect input given!");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        this.event.setTitle(new String(title));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.event.setDescription(new String(description));
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.event.setLocation(new String(location));
        return this;
    }

    /**
     * Sets the {@code datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String datetime) {
        try {
            this.event.setDatetime(new Datetime(datetime));
        } catch (IllegalValueException e) {
            throw new AssertionError("Incorrect input given!");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }
}
```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent CHRISTMAS = new EventBuilder().withTitle("Christmas")
            .withDescription("Christmas Party at SOC").withLocation("NUS COM1")
            .withDatetime("2017-12-24 1830").build();
    public static final ReadOnlyEvent CNY = new EventBuilder().withTitle("CNY")
            .withDescription("CNY Celebration at FOS").withLocation("NUS S16 Level 3")
            .withDatetime("2018-02-15 1000").build();
    public static final ReadOnlyEvent MOVIE = new EventBuilder().withTitle("Movie Outing")
            .withDescription("Black Panther Movie").withLocation("Suntec GV")
            .withDatetime("2018-04-21 1500").build();
    public static final ReadOnlyEvent REUNION = new EventBuilder().withTitle("Secondary School Class Reunion")
            .withDescription("With Secondary School Classmates").withLocation("Vivian's House")
            .withDatetime("2018-05-06 1730").build();

    private TypicalEvents() {
        // prevents instantiation
    }

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook eb = new EventBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                eb.addEvent(event);
            } catch (CommandException e) {
                assert false : "Invalid Command";
            } catch (DuplicateEventException e) {
                assert false : "Duplicate Event";
            }
        }
        return eb;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(CHRISTMAS, CNY, MOVIE, REUNION));
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java

public class BrowserPanelTest extends GuiUnitTest {

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(null));
        Stage browserPanelStage = FxToolkit.setupStage((stage) -> stage.setScene(browserPanel.getRoot().getScene()));
        FxToolkit.showStage();
        browserPanelHandle = new BrowserPanelHandle(browserPanelStage);
    }

    @Test
    public void displayGoogleChromeBrowser() {
        URL expectedGooglePage = null;
        try {
            expectedGooglePage = new URL(GOOGLE_URL);
        } catch (MalformedURLException e) {
            throw new AssertionError("Google URL broken");
        }
        assertEquals(expectedGooglePage, browserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\EventCardTest.java
``` java

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {

        // past event
        Event pastEvent = new EventBuilder().withTitle("Event Title").build();
        EventCard pastEventCard = new EventCard(pastEvent, 1);
        uiPartRule.setUiPart(pastEventCard);
        assertCardDisplay(pastEventCard, pastEvent, 1);

        // present event
        String presentDatetime = LocalDateTime.now().toString().substring(0, 10) + " 2000";
        Event presentEvent = new EventBuilder().withDatetime(presentDatetime).build();
        EventCard presentEventCard = new EventCard(presentEvent, 1);
        uiPartRule.setUiPart(presentEventCard);
        assertCardDisplay(presentEventCard, presentEvent, 1);

        // incoming event (within 3 days)
        String incomingDatetime = LocalDateTime.now().plusDays(1).toString().substring(0, 10) + " 2000";
        Event incomingEvent = new EventBuilder().withDatetime(incomingDatetime).build();
        EventCard incomingEventCard = new EventCard(incomingEvent, 1);
        uiPartRule.setUiPart(incomingEventCard);
        assertCardDisplay(incomingEventCard, incomingEvent, 1);

        // future event
        Event futureEvent = new EventBuilder().withDatetime("2020-05-21 1800").build();
        EventCard futureEventCard = new EventCard(futureEvent, 1);
        uiPartRule.setUiPart(futureEventCard);
        assertCardDisplay(futureEventCard, futureEvent, 1);

    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withTitle("differentEvent").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, Event expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
```
###### \java\seedu\address\ui\EventListPanelTest.java
``` java

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyEvent> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_EVENT);

    private EventListPanelHandle eventListPanelHandle;

    @Before
    public void setUp() {
        EventListPanel eventListPanel = new EventListPanel(TYPICAL_EVENTS);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            ReadOnlyEvent expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedCard = eventListPanelHandle.getEventCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedCard = eventListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
```
###### \java\seedu\address\ui\LinkedInWindowTest.java
``` java

public class LinkedInWindowTest extends GuiUnitTest {

    private LinkedInWindow linkedInWindow;
    private LinkedInWindowHandle linkedInWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> linkedInWindow = new LinkedInWindow());
        Stage linkedInWindowStage = FxToolkit.setupStage((stage)
            -> stage.setScene(linkedInWindow.getRoot().getScene()));
        FxToolkit.showStage();
        linkedInWindowHandle = new LinkedInWindowHandle(linkedInWindowStage);
    }

    @Test
    public void display() {
        URL expectedLinkedInPage = null;
        try {
            expectedLinkedInPage = new URL(LINKEDIN_URL);
        } catch (MalformedURLException e) {
            throw new AssertionError("LinkedIn URL broken");
        }
        assertEquals(expectedLinkedInPage, linkedInWindowHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\TaskCardTest.java
``` java

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {

        // overdue event
        Task pastTask = new TaskBuilder().withTaskDueDate("2017-05-21").build();
        TaskCard pastTaskCard = new TaskCard(pastTask, 1);
        uiPartRule.setUiPart(pastTaskCard);
        assertCardDisplay(pastTaskCard, pastTask, 1);

        // task duedate in 3 days
        String threeDaysToDatetime = LocalDateTime.now().plusDays(2).toString().substring(0, 10);
        Task threeDaysTask = new TaskBuilder().withTaskDueDate(threeDaysToDatetime).build();
        TaskCard threeDaysTaskCard = new TaskCard(threeDaysTask, 1);
        uiPartRule.setUiPart(threeDaysTaskCard);
        assertCardDisplay(threeDaysTaskCard, threeDaysTask, 1);

        // task duedate in 5 days
        String fiveDaysToDatetime = LocalDateTime.now().plusDays(4).toString().substring(0, 10);
        Task fiveDaysTask = new TaskBuilder().withTaskDueDate(fiveDaysToDatetime).build();
        TaskCard fiveDaysTaskCard = new TaskCard(fiveDaysTask, 1);
        uiPartRule.setUiPart(fiveDaysTaskCard);
        assertCardDisplay(fiveDaysTaskCard, fiveDaysTask, 1);

        // high priority task
        Task highPriorityTask = new TaskBuilder().withTaskPriority("high").build();
        TaskCard highPriorityTaskCard = new TaskCard(highPriorityTask, 1);
        uiPartRule.setUiPart(highPriorityTaskCard);
        assertCardDisplay(highPriorityTaskCard, highPriorityTask, 1);

        // low priority task
        Task lowPriorityTask = new TaskBuilder().withTaskPriority("low").build();
        TaskCard lowPriorityTaskCard = new TaskCard(lowPriorityTask, 1);
        uiPartRule.setUiPart(lowPriorityTaskCard);
        assertCardDisplay(lowPriorityTaskCard, lowPriorityTask, 1);

        // low priority task
        Task finishedTask = new TaskBuilder().withTaskStatus("done").build();
        TaskCard finishedTaskCard = new TaskCard(finishedTask, 1);
        uiPartRule.setUiPart(finishedTaskCard);
        assertCardDisplay(finishedTaskCard, finishedTask, 1);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withTaskName("DifferentTask").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
```
###### \java\seedu\address\ui\TaskListPanelTest.java
``` java

public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TASK);

    private TaskListPanelHandle taskListPanelHandle;

    @Before
    public void setUp() {
        TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            Task expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TaskCardHandle expectedCard = taskListPanelHandle.getTaskCardHandle(INDEX_SECOND_TASK.getZeroBased());
        TaskCardHandle selectedCard = taskListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
```
