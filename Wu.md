# Wu
###### \main\java\seedu\address\commons\events\model\TaskBookChangedEvent.java
``` java
/** Indicates the AddressBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public TaskBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
```
###### \main\java\seedu\address\commons\events\ui\TodoPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the TodoList Panel
 */
public class TodoPanelSelectionChangedEvent extends BaseEvent {


    private final TodoCard newSelection;

    public TodoPanelSelectionChangedEvent(TodoCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TodoCard getNewSelection() {
        return this.newSelection;
    }
}
```
###### \main\java\seedu\address\logic\commands\DeleteTaskCommand.java
``` java
/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTask";
    public static final String COMMAND_ALIAS = "dt";
    public static final String COMMAND_SIGN = "-t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(taskToDelete);
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex) // state check
                && Objects.equals(this.taskToDelete, ((DeleteTaskCommand) other).taskToDelete));
    }
}

```
###### \main\java\seedu\address\logic\commands\EditTaskCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class EditTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editTask";
    public static final String COMMAND_ALIAS = "et";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values."
            + "Note: Deadline cannot be modified by editTask command.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_TASK_DESC + "TASK DESCRIPTION] "
            + "[" + PREFIX_DEADLINE + "TASK DEADLINE] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "notes "
            + PREFIX_TASK_DESC + "Send lab notes to students."
            + PREFIX_DEADLINE + "05-05-2018"
            + PREFIX_PRIORITY + "50. ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided except deadline.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    private Task taskToEdit;
    private Task editedTask;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
        editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Title updatedTitle = editTaskDescriptor.getTitle().orElse(taskToEdit.getTitle());
        TaskDescription updatedDesc = editTaskDescriptor.getDescription().orElse(taskToEdit.getTaskDesc());
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElse(taskToEdit.getDeadline());
        Priority updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());

        return new Task(updatedTitle, updatedDesc, updatedDeadline, updatedPriority);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor)
                && Objects.equals(taskToEdit, e.taskToEdit);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Title title;
        private TaskDescription desc;
        private Deadline deadline;
        private Priority priority;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.desc);
            setDeadline(toCopy.deadline);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.title, this.desc, this.deadline, this.priority);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(TaskDescription desc) {
            this.desc = desc;
        }

        public Optional<TaskDescription> getDescription() {
            return Optional.ofNullable(desc);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getDescription().equals(e.getDescription())
                    && getDeadline().equals(e.getDeadline())
                    && getPriority().equals(e.getPriority());
        }
    }
}

```
###### \main\java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 * Imports data from a xml file and overwrites the current data stored
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports data from an external xml data file with "
            + "the provided path and overwrites the current data stored.\n"
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " ~/DOWNLOADS/NewDataSet.xml";

    public static final String MESSAGE_SUCCESS = "Data imported successfully";
    public static final String MESSAGE_INVALID_PATH = "File not found";
    public static final String MESSAGE_INVALID_FILE = "Data configuration failed";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            ReadOnlyAddressBook newDataSet = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
            model.resetData(newDataSet);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_INVALID_PATH);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_INVALID_FILE);
        }
    }
}
```
###### \main\java\seedu\address\logic\commands\ListCurrentTaskCommand.java
``` java
/**
 * Lists all tasks due by the current month stored in the address book.
 */
public class ListCurrentTaskCommand extends Command {

    public static final String COMMAND_WORD = "listCurrentTask";
    public static final String COMMAND_ALIAS = "lct";

    public static final String MESSAGE_SUCCESS = "Listed all tasks due this month";

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_CURRENT_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \main\java\seedu\address\logic\commands\ListTaskCommand.java
``` java
/**
 * Lists all tasks stored in the address book.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listTask";
    public static final String COMMAND_ALIAS = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \main\java\seedu\address\logic\commands\SortTaskCommand.java
``` java
/**
 * Lists all tasks stored in the address book in date order.
 */
public class SortTaskCommand extends Command {

    public static final String COMMAND_WORD = "sortTask";
    public static final String COMMAND_ALIAS = "stt";

    public static final String MESSAGE_SUCCESS = "Todo List is sorted";


    @Override
    public CommandResult execute() {
        model.sortTasks();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \main\java\seedu\address\logic\commands\SwitchTabCommand.java
``` java
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchTab";
    public static final String COMMAND_ALIAS = "swt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches between tabs of Person List and Todo List. ";
    public static final String MESSAGE_SUCCESS = "Tab switched.";

    private static final int PERSON_TAB = 0;
    private static final int TASK_TAB = 1;

    private TabPane tabPane;

    public SwitchTabCommand(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedTab = selectionModel.getSelectedIndex();
        selectionModel.select(selectAnotherTab(selectedTab));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Alternates the tab index
     * @param currentTab index
     * @return alternated tab index
     */
    private int selectAnotherTab(int currentTab) {
        if (currentTab == PERSON_TAB) {
            return TASK_TAB;
        }
        return PERSON_TAB;
    }
}
```
###### \main\java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setTabPane(TabPane tabPane) {
        addressBookParser.setTabPane(tabPane);
    }
}
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    /**
     * Selects Person List tab before executing list command
     * @param tabPane
     */
    public void selectPersonTab(TabPane tabPane) {
        if (tabPane != null) {
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(PERSON_TAB);
        }
    }

}
```
###### \main\java\seedu\address\logic\parser\DeleteTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \main\java\seedu\address\logic\parser\EditTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    public static final String PLACE_HOLDER_HASH = "EDITED_DISPLAY";

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TASK_DESC, PREFIX_DEADLINE, PREFIX_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            ParserUtil.parseTaskTitle(argMultimap.getValue(PREFIX_TITLE)).ifPresent(editTaskDescriptor::setTitle);
            ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESC))
                    .ifPresent(editTaskDescriptor::setDescription);
            ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).ifPresent(editTaskDescriptor::setDeadline);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).ifPresent(editTaskDescriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

}
```
###### \main\java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommandParser object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an Import Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();

        String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        if (trimmedInput.isEmpty()) {
            throw new ParseException(exceptionMessage);
        }

        return new ImportCommand(userInput);
    }
}
```
###### \main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String taskTitle} into a {@code TaskTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskDescription} is invalid.
     */
    public static Title parseTaskTitle(String taskTitle) throws IllegalValueException {
        requireNonNull(taskTitle);
        String trimmedTaskTitle = taskTitle.trim();
        if (!TaskDescription.isValidDescription(trimmedTaskTitle)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTaskTitle);
    }

    /**
     * Parses a {@code Optional<String> taskDescription} into an {@code Optional<TaskDescription>}
     * if {@code TaskDscription} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTaskTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTaskTitle(title.get())) : Optional.empty();
    }

```
###### \main\java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(Task key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// tag-level operations
```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTask(Task target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateTask(Task target, Task editedTask)
            throws TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        addressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    @Override
    public void sortTasks() {
        addressBook.sortTaskList();
    }

```
###### \main\java\seedu\address\model\task\Task.java
``` java
    @Override
    public int compareTo(Task task) {
        int yearDiff = this.getDeadlineYear() - task.getDeadlineYear();
        int monthDiff = this.getDeadlineMonth() - task.getDeadlineMonth();
        int dayDiff = this.getDeadlineDay() - task.getDeadlineDay();

        return compareDate(yearDiff, monthDiff, dayDiff);
    }

    /**
     * Compares the dates
     * @param yearDiff difference in year
     * @param monthDiff difference in month
     * @param dayDiff difference in day
     */
    private int compareDate (int yearDiff, int monthDiff, int dayDiff) {
        if (yearDiff != 0) {
            return yearDiff;
        }
        if (monthDiff != 0) {
            return monthDiff;
        }
        return dayDiff;
    }
}
```
###### \main\java\seedu\address\model\task\Title.java
``` java
/**
 * Represents a task's title in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Task title can contain any characters but it should not be blank";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid name.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.value = title;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### \main\java\seedu\address\model\task\UniqueTaskList.java
``` java
    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask)
            throws TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        remove(target);
        add(editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove)
                && calendarList[toRemove.getDeadline().diff][toRemove.getDeadlineDay()].remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     * Sorts the tasks in the list in date order
     */
    public void sortList() {
        requireNonNull(internalList);

        FXCollections.sort(internalList);
    }

```
###### \main\java\seedu\address\ui\TodoCard.java
``` java
/**
 * A UI component that displays information of a {@code task} in TodoList.
 */
public class TodoCard extends UiPart<Region> {

    private static final String FXML = "TodoListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label deadline;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public TodoCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        title.setText("Title: " + task.getTitle().value);
        priority.setText("Priority: " + task.getPriority().toString());
        deadline.setText("Deadline: " + task.getDeadline().dateString);
        description.setText("Description: " + task.getTaskDesc().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoCard)) {
            return false;
        }

        // state check
        TodoCard card = (TodoCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### \main\java\seedu\address\ui\TodoListPanel.java
``` java
/**
 * Panel containing the list of tasks shown in TodoList.
 */
public class  TodoListPanel extends UiPart<Region> {
    private static final String FXML = "todoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);

    @FXML
    private ListView<TodoCard> todoListView;

    public TodoListPanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        ObservableList<TodoCard> mappedList = EasyBind.map(
                taskList, (task) -> new TodoCard(task, taskList.indexOf(task) + 1));
        todoListView.setItems(mappedList);
        todoListView.setCellFactory(listView -> new todoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TodoPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TodoCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class todoListViewCell extends ListCell<TodoCard> {

        @Override
        protected void updateItem(TodoCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }
}
```
###### \main\resources\view\DarkMainWindow.fxml
``` fxml
        <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
          <VBox fx:id="personList" minWidth="360" prefWidth="360" SplitPane.resizableWithParent="false">
            <TabPane fx:id="tabPane" VBox.vgrow="ALWAYS" tabClosingPolicy="UNAVAILABLE">
              <tabs>
                <Tab fx:id="personListTab" text="Person List">
                  <StackPane fx:id="personListPanelPlaceholder" minWidth="357" maxWidth="357" VBox.vgrow="ALWAYS"/>
                </Tab>
                <Tab fx:id="todoListTab" text="Todo List">
                  <StackPane fx:id="todoListPanelPlaceholder" minWidth="357" maxWidth="357" VBox.vgrow="ALWAYS"/>
                </Tab>
              </tabs>
            </TabPane>
          </VBox>

```
###### \main\resources\view\DarkTheme.css
``` css
.tab-pane {
    -fx-padding: 0 0 0 1;
    -fx-background-color: #232A34;
}

.tab-pane .tab-header-area {
    -fx-background-color: #232A34;
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane {
    -fx-tab-min-width:150px;
}

.tab {
    -fx-background-insets: 0 1 0 1,0,0;
}

.tab-pane .tab {
    -fx-background-color: #404040;

}

.tab-pane .tab:selected {
    -fx-border-color: transparent !important;
    -fx-background-color: #5F6A6A;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #f3f3f3;
    -fx-font-size: 12px;
    -fx-font-weight: bold;
}

.tab:selected .tab-label {
    -fx-border-color: transparent !important;
    -fx-text-fill: white;
}
```
###### \main\resources\view\TodoListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15"/>
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <Region fx:constant="USE_PREF_SIZE"/>
                    </minWidth>
                </Label>
                <Label fx:id="title" text="\$title" styleClass="cell_big_label"/>
            </HBox>
            <FlowPane fx:id="tags" />
            <Label fx:id="description" styleClass="cell_small_label" text="\$description"/>
            <Label fx:id="deadline" styleClass="cell_small_label" text="\$deadline"/>
            <Label fx:id="priority" styleClass="cell_small_label" text="\$priority"/>
        </VBox>
    </GridPane>
</HBox>
```
###### \main\resources\view\todoListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="todoListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \test\java\guitests\guihandles\TodoCardHandle.java
``` java
public class TodoCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESC_FIELD_ID = "#description";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String PRIORITY_FIELD_ID = "#priority";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label deadlineLabel;
    private final Label priorityLabel;

    public TodoCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESC_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);

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

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

}
```
###### \test\java\guitests\guihandles\TodoListPanelHandle.java
``` java
public class TodoListPanelHandle extends NodeHandle<ListView<TodoCard>> {
    public static final String TODO_LIST_VIEW_ID = "#todoListView";

    private Optional<TodoCard> lastRememberedSelectedTodoCard;

    public TodoListPanelHandle(ListView<TodoCard> todoListPanelNode) {
        super(todoListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TodoCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TodoCardHandle getHandleToSelectedCard() {
        List<TodoCard> taskList = getRootNode().getSelectionModel().getSelectedItems();

        if (taskList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new TodoCardHandle(taskList.get(0).getRoot());
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
        List<TodoCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public void navigateTodoCard(Task task) {
        List<TodoCard> cards = getRootNode().getItems();
        Optional<TodoCard> matchingCard = cards.stream().filter(card -> card.task.equals(task)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the Todocard handle of a task associated with the {@code index} in the list.
     */
    public TodoCardHandle getTodoCardHandle(int index) {
        return getTodoCardHandle(getRootNode().getItems().get(index).task);
    }

    /**
     * Returns the {@code TodoCardHandle} of the specified {@code task} in the list.
     */
    public TodoCardHandle getTodoCardHandle(Task task) {
        Optional<TodoCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.task.equals(task))
                .map(card -> new TodoCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Task does not exist."));
    }

    /**
     * Selects the {@code TodoCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code TodoCard} in the list.
     */
    public void rememberSelectedTodoCard() {
        List<TodoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTodoCard = Optional.empty();
        } else {
            lastRememberedSelectedTodoCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TodoCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTodoCard()} call.
     */
    public boolean isSelectedTodoCardChanged() {
        List<TodoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTodoCard.isPresent();
        } else {
            return !lastRememberedSelectedTodoCard.isPresent()
                    || !lastRememberedSelectedTodoCard.get().equals(selectedItems.get(0));
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
###### \test\java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addTaskSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void equals() {
        Task meeting = new TaskBuilder().withTitle("Meeting").build();
        Task assignment = new TaskBuilder().withTitle("Assignment").build();
        AddTaskCommand addMeetingCommand = new AddTaskCommand(meeting);
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddTaskCommand addMeetingCommandCopy = new AddTaskCommand(meeting);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetingCommand.equals(addAssignmentCommand));
    }

    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
        }

        @Override
        public void updateTask(Task target, Task editedTask) throws TaskNotFoundException {
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
        public void sortPersons() {
            fail("This method should not be called");
        }

        @Override
        public void sortTasks() {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public ObservableList<Task>[][] getCalendarTaskLists() {
            return new ObservableList[0][];
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public List<String> getItemList() {
            return null;
        }

        @Override
        public void clearDeleteItems() {
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \test\java\seedu\address\logic\commands\CommandTaskTestUtil.java
``` java
public class CommandTaskTestUtil {

    public static final String VALID_TITLE_EXAM = "Test Preparation";
    public static final String VALID_TITLE_MARK = "Mark Test";
    public static final String VALID_TASK_DESC_EXAM = "Giving Practical Exam tips and reviewing past year test";
    public static final String VALID_TASK_DESC_MARK = "Grade test papers for CS1020 tutorial classes";
    public static final String VALID_DEADLINE_EXAM = "01-06-2018";
    public static final String VALID_DEADLINE_MARK = "04-06-2018";
    public static final String VALID_PRIORITY_EXAM = "1";
    public static final String VALID_PRIORITY_MARK = "2";

    public static final String TITLE_DESC_EXAM = " " + PREFIX_TITLE + VALID_TITLE_EXAM;
    public static final String TITLE_DESC_MARK = " " + PREFIX_TITLE + VALID_TITLE_MARK;
    public static final String TASK_DESC_DESC_EXAM = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_EXAM;
    public static final String TASK_DESC_DESC_MARK = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_MARK;
    public static final String DEADLINE_DESC_EXAM = " " + PREFIX_DEADLINE + VALID_DEADLINE_EXAM;
    public static final String DEADLINE_DESC_MARK = " " + PREFIX_DEADLINE + VALID_DEADLINE_MARK;
    public static final String PRIORITY_DESC_EXAM = " " + PREFIX_PRIORITY + VALID_PRIORITY_EXAM;
    public static final String PRIORITY_DESC_MARK = " " + PREFIX_PRIORITY + VALID_PRIORITY_MARK;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // '&' not allowed in title
    public static final String INVALID_TASK_DESC_DESC = " " + PREFIX_TASK_DESC
            + " "; // empty string not allowed in task description
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "30-13-2018"; // invalid date
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "0"; // priority from 1 to 3

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor DESC_EXAM;
    public static final EditTaskCommand.EditTaskDescriptor DESC_MARK;
    static {
        DESC_EXAM = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_EXAM).withDesc(VALID_TASK_DESC_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).withPriority(VALID_PRIORITY_EXAM).build();
        DESC_MARK = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).withDesc(VALID_TASK_DESC_MARK)
                .withDeadline(VALID_DEADLINE_MARK).withPriority(VALID_PRIORITY_MARK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
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
     * - the address book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", tnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
```
###### \test\java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deletePerson(personToDelete);
        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
```
###### \test\java\seedu\address\logic\commands\DeleteTaskCommandTest.java
``` java
public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first task deleted
        deleteTaskCommand.execute();
        undoRedoStack.push(deleteTaskCommand);

        // undo -> reverts addressbook back to previous state and to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteTaskCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(Index index) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(index);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
```
###### \test\java\seedu\address\logic\commands\EditTaskCommandTest.java
``` java
public class EditTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTitle(VALID_TITLE_EXAM).withDesc(VALID_TASK_DESC_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_EXAM)
                .withDesc(VALID_TASK_DESC_EXAM).withDeadline(VALID_DEADLINE_EXAM).build();
        EditTaskCommand editTaskCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task editedTask = new TaskBuilder().build();
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first task edited
        editTaskCommand.execute();
        undoRedoStack.push(editTaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final EditTaskCommand standardCommand = prepareCommand(INDEX_FIRST_TASK, DESC_MARK);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_MARK);
        EditTaskCommand commandWithSameValues = prepareCommand(INDEX_FIRST_TASK, copyDescriptor);
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
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_EXAM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_EXAM)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareCommand(Index index, EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}
```
###### \test\java\seedu\address\logic\commands\ImportCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ImportCommand}.
 */
public class ImportCommandTest {

    public static final String VALID_IMPORT_FILE_PATH = "src/data/ValidImport.xml";
    public static final String INVALID_IMPORT_FILE_PATH = "src/data/InValidImport.txt";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void fileNotFound() {
        ImportCommand importCommand = pathInCommand(INVALID_FILE);
        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_INVALID_PATH);
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code filePath}
     */
    private ImportCommand pathInCommand(String filePath) {
        ImportCommand testCommand = new ImportCommand(filePath);
        testCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return testCommand;
    }
}
```
###### \test\java\seedu\address\logic\commands\ListCurrentTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCurrentTaskCommand.
 */
public class ListCurrentTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCurrentTaskCommand listCurrentTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCurrentTaskCommand = new ListCurrentTaskCommand();
        listCurrentTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCurrentTaskCommand, model, ListCurrentTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        new ListTaskCommand();
        assertCommandSuccess(listCurrentTaskCommand, model, ListCurrentTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \test\java\seedu\address\logic\commands\ListTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class ListTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private ListTaskCommand listTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listTaskCommand = new ListTaskCommand();
        listTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        new ListCurrentTaskCommand();
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \test\java\seedu\address\logic\commands\SortTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class SortTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private SortTaskCommand sortTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortTaskCommand = new SortTaskCommand();
        sortTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsEverything() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nullList_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_add_sign() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_SIGN + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_delete_sign() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_SIGN + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### \test\java\seedu\address\logic\parser\AddTaskCommandParserTest.java
``` java
public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTitle(VALID_TITLE_EXAM)
                .withDesc(VALID_TASK_DESC_EXAM).withDeadline(VALID_DEADLINE_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_MARK + TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple taskDescs - last taskDesc accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_MARK + TASK_DESC_DESC_EXAM
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_MARK
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_MARK + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                        + PRIORITY_DESC_EXAM, expectedMessage);

        // missing taskDesc prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + VALID_TASK_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + VALID_DEADLINE_EXAM
                + PRIORITY_DESC_EXAM, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + VALID_PRIORITY_EXAM, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_EXAM + VALID_TASK_DESC_EXAM + VALID_DEADLINE_EXAM
                + VALID_PRIORITY_EXAM, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid taskDesc
        assertParseFailure(parser, TITLE_DESC_EXAM + INVALID_TASK_DESC_DESC + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, TaskDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + INVALID_DEADLINE_DESC
                + PRIORITY_DESC_EXAM, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + INVALID_PRIORITY_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASK_DESC_DESC_EXAM
                        + DEADLINE_DESC_EXAM + INVALID_PRIORITY_DESC,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\logic\parser\DeleteTaskCommandParserTest.java
``` java
public class DeleteTaskCommandParserTest {
    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_noFile_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "   ", expectedMessage);
    }
}
```
###### \test\java\seedu\address\testutil\EditTaskDescriptorBuilder.java
``` java
public class EditTaskDescriptorBuilder {
    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTitle(task.getTitle());
        descriptor.setDescription(task.getTaskDesc());
        descriptor.setDeadline(task.getDeadline());
        descriptor.setPriority(task.getPriority());
    }

    /**
     * Sets the {@code Title} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDesc(String taskDesc) {
        descriptor.setDescription(new TaskDescription(taskDesc));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
```
###### \test\java\seedu\address\testutil\TaskBuilder.java
``` java
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "Dance";
    public static final String DEFAULT_DESC = "Dance till I drop";
    public static final String DEFAULT_PRIORITY = "3";

    private static LocalDate now = LocalDate.now();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String DEFAULT_DEADLINE = now.format(formatter);

    private Title title;
    private TaskDescription desc;
    private Deadline deadline;
    private Priority priority;

    public TaskBuilder() {
        title = new Title(DEFAULT_TITLE);
        desc = new TaskDescription(DEFAULT_DESC);
        deadline = new Deadline(DEFAULT_DEADLINE);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}
     */
    public TaskBuilder(Task taskToCopy) {
        title = taskToCopy.getTitle();
        desc = taskToCopy.getTaskDesc();
        deadline = taskToCopy.getDeadline();
        priority = taskToCopy.getPriority();
    }

    /**
     * Sets the {@code Title} of the {@code Task} that we are building
     */
    public TaskBuilder withTitle (String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        this.desc = new TaskDescription(desc);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public Task build() {
        return new Task(title, desc, deadline, priority);
    }
}
```
###### \test\java\seedu\address\testutil\TypicalTasks.java
``` java
public class TypicalTasks {

    private static LocalDate now = LocalDate.now();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static String tutorialDeadline = now.plusDays(5).format(formatter);
    private static String sleepDeadline = now.plusDays(6).format(formatter);
    private static String lunchDeadline = now.plusDays(10).format(formatter);
    private static String reportDeadline = now.plusDays(8).format(formatter);
    private static String biddingDeadline = now.plusMonths(1).format(formatter);

    private static final Task TUTORIAL = new TaskBuilder().withTitle("Prepare Tut")
            .withDesc("Prepare tutorial contents for friday Tutorial")
            .withDeadline(tutorialDeadline).withPriority("1").build();
    private static final Task SLEEP = new TaskBuilder().withTitle("Sleep Early")
            .withDesc("I need to sleep early before midnight today")
            .withDeadline(sleepDeadline).withPriority("2").build();
    private static final Task LUNCH = new TaskBuilder().withTitle("Group Lunch")
            .withDesc("Have lunch with the TA group")
            .withDeadline(lunchDeadline).withPriority("3").build();
    private static final Task REPORT = new TaskBuilder().withTitle("Sem Report")
            .withDesc("Prepare for end of semester report")
            .withDeadline(reportDeadline).withPriority("2").build();
    private static final Task BIDDING = new TaskBuilder().withTitle("Bid Modules")
            .withDesc("Prepare for bidding modules for the coming semester")
            .withDeadline(biddingDeadline).withPriority("2").build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TUTORIAL, SLEEP, LUNCH, REPORT, BIDDING));
    }
}
```
