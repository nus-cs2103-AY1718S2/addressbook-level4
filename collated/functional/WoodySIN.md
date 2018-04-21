# WoodySIN
###### \java\seedu\address\commons\events\model\TaskBookChangedEvent.java
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
###### \java\seedu\address\commons\events\ui\TodoPanelSelectionChangedEvent.java
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
###### \java\seedu\address\logic\commands\DeleteTaskCommand.java
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
###### \java\seedu\address\logic\commands\EditTaskCommand.java
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
###### \java\seedu\address\logic\commands\ImportCommand.java
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
###### \java\seedu\address\logic\commands\ListCurrentTaskCommand.java
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
###### \java\seedu\address\logic\commands\ListTaskCommand.java
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
###### \java\seedu\address\logic\commands\SortTaskCommand.java
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
###### \java\seedu\address\logic\commands\SwitchTabCommand.java
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
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setTabPane(TabPane tabPane) {
        addressBookParser.setTabPane(tabPane);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
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
###### \java\seedu\address\logic\parser\DeleteTaskCommandParser.java
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
###### \java\seedu\address\logic\parser\EditTaskCommandParser.java
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
###### \java\seedu\address\logic\parser\ImportCommandParser.java
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
###### \java\seedu\address\logic\parser\ParserUtil.java
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
        if (!Title.isValidTitle(trimmedTaskTitle)) {
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
###### \java\seedu\address\model\AddressBook.java
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
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTask(Task target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\task\Task.java
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
###### \java\seedu\address\model\task\Title.java
``` java
/**
 * Represents a task's title in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Task title should contain alphanumeric characters and it should not be blank.";

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
     * Returns true if a given string is a valid task title.
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
###### \java\seedu\address\model\task\UniqueTaskList.java
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
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Contains utility methods for populating {@code AddressBook} with sample data tasks.
     */
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Title("Prepare Tut"), new TaskDescription("Prepare tutorial contents for friday Tutorial"),
                new Deadline(tutorialDeadline), new Priority("1")),
            new Task(new Title("2106 assignment"), new TaskDescription("Start doing CS2106 term assignment"),
                new Deadline(assignmentDeadline), new Priority("3")),
            new Task(new Title("Sem report"), new TaskDescription("Prepare for end of semester report"),
                new Deadline(tutorialDeadline), new Priority("3")),
            new Task(new Title("Bidding"), new TaskDescription("Prepare for bidding modules for the coming semester"),
                new Deadline(biddingDeadline), new Priority("3")),
            new Task(new Title("Revise 2010"), new TaskDescription("Revise the contents for CS2010"),
                new Deadline(reviseDeadline), new Priority("2"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### \java\seedu\address\ui\TodoCard.java
``` java
/**
 * A UI component that displays information of a {@code task} in TodoList.
 */
public class TodoCard extends UiPart<Region> {

    private static final String FXML = "todoListCard.fxml";

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
###### \java\seedu\address\ui\TodoListPanel.java
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
        todoListView.setCellFactory(listView -> new TodoListViewCell());
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
    class TodoListViewCell extends ListCell<TodoCard> {

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
###### \resources\view\DarkTheme.css
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
###### \resources\view\todoListCard.fxml
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
###### \resources\view\todoListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="todoListView" VBox.vgrow="ALWAYS" />
</VBox>
```
