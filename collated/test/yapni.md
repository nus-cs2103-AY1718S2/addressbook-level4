# yapni
###### \java\seedu\address\logic\commands\AddMilestoneCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddMilestoneCommand}.
 */
public class AddMilestoneCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_newMilestoneUnfilteredList_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(Index.fromOneBased(expectedModel.getFilteredStudentList().size()),
                validMilestone, model), model, String.format(AddMilestoneCommand.MESSAGE_SUCCESS, validMilestone),
                expectedModel);
    }

    @Test
    public void execute_newMilestoneFilteredList_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        Index unfilteredTargetStudentIndex = INDEX_EIGHTH;

        model.addStudent(targetStudent);
        showStudentAtIndex(model, unfilteredTargetStudentIndex);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        showStudentAtIndex(expectedModel, unfilteredTargetStudentIndex);
        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_FIRST, validMilestone, model), model,
                String.format(AddMilestoneCommand.MESSAGE_SUCCESS, validMilestone), expectedModel);
    }

    @Test
    public void execute_duplicateMilestone_throwsCommandException() throws Exception {
        Milestone duplicateMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(duplicateMilestone).build();
        model.addStudent(targetStudent);

        assertCommandFailure(prepareCommand(Index.fromOneBased(model.getFilteredStudentList().size()),
                duplicateMilestone, model), model, AddMilestoneCommand.MESSAGE_DUPLICATE_MILESTONE);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Milestone validMilestone = new MilestoneBuilder().build();

        assertCommandFailure(prepareCommand(outOfBoundIndex, validMilestone, model), model,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        Milestone validMilestone = new MilestoneBuilder().build();

        assertCommandFailure(prepareCommand(outOfBoundIndex, validMilestone, model), model,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Generates a new {@code AddMilestoneCommand} which upon execution, adds {@code milestone} in the {@code dashboard}
     * of the {@code student} at {@code studentIndex} in the {@code model}.
     */
    private AddMilestoneCommand prepareCommand(Index studentIndex, Milestone milestone, Model model) {
        AddMilestoneCommand command = new AddMilestoneCommand(studentIndex, milestone);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_newTaskUnfilteredList_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(Index.fromOneBased(expectedModel.getFilteredStudentList().size()),
                INDEX_FIRST, validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_newTaskFilteredList_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        Index unfilteredTargetStudentIndex = INDEX_EIGHTH;

        model.addStudent(targetStudent);
        showStudentAtIndex(model, unfilteredTargetStudentIndex);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        showStudentAtIndex(expectedModel, unfilteredTargetStudentIndex);
        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_FIRST, INDEX_FIRST, validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, validTask, model), model,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        assertCommandFailure(prepareCommand(outOfBoundIndex, validTargetMilestoneIndex, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void  execute_invalidStudentIndexFilteredList_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        showStudentAtIndex(model, INDEX_EIGHTH);
        Index outOfBoundStudentIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundStudentIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        assertCommandFailure(prepareCommand(outOfBoundStudentIndex, validTargetMilestoneIndex, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() {
        Task validTask = TypicalTasks.TASK_4;

        // INDEX_FIRST student has 0 milestones
        assertCommandFailure(prepareCommand(INDEX_FIRST, INDEX_FIRST, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code AddTaskCommand} which upon execution, adds {@code task} to the {@code milestone}
     * in the {@code dashboard} of the {@code student} in the {@code model}.
     */
    private AddTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Task newTask, Model model) {
        AddTaskCommand command = new AddTaskCommand(studentIndex, milestoneIndex, newTask);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\CheckTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code CheckTaskCommand}.
 */
public class CheckTaskCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_checkTaskNotCompleted_success() throws Exception {
        Task validTaskNotCompleted = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted).build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted)
                .withTaskCompleted(INDEX_FOURTH).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                String.format(CheckTaskCommand.MESSAGE_SUCCESS, INDEX_FOURTH.getOneBased(), INDEX_FIRST.getOneBased()),
                expectedModel);
    }

    @Test
    public void execute_checkTaskAlreadyCompleted_messageTaskAlreadyCompleted() throws Exception {
        Task validTaskAlreadyCompleted = new TaskBuilder(TypicalTasks.TASK_4).asCompleted().build();
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskAlreadyCompleted)
                .withProgress(new Progress("1/4")).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                CheckTaskCommand.MESSAGE_TASK_ALREADY_COMPLETED, expectedModel);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Task validTaskNotCompleted = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        // targetStudent only has 1 milestone (which contains 4 tasks)
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Milestone validTargetMilestone = new MilestoneBuilder().build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        // targetStudent only has 3 tasks in the INDEX_FIRST milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code CheckTaskCommand} which upon execution marks {@code task}, which is from the
     * {@code milestone} in the {@code dashboard} of the {@code student} in the {@code model}, as completed.
     */
    private CheckTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Index taskIndex, Model model) {
        CheckTaskCommand command = new CheckTaskCommand(studentIndex, milestoneIndex, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteMilestoneCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DeleteMilestoneCommand}.
 */
public class DeleteMilestoneCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_validMilestoneIndex_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, model), model,
                String.format(DeleteMilestoneCommand.MESSAGE_DELETE_MILESTONE_SUCCESS, validMilestone), expectedModel);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent has only 1 milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, model), model, MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code DeleteMilestoneCommand} which upon execution, deletes {@code milestone} from the
     * {@code dashboard} of the {@code student} in the {@code model}.
     */
    private DeleteMilestoneCommand prepareCommand(Index studentIndex, Index milestoneIndex, Model model) {
        DeleteMilestoneCommand command = new DeleteMilestoneCommand(studentIndex, milestoneIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}
```
###### \java\seedu\address\logic\commands\DeleteTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_validTaskAndMilestoneIndex_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Milestone validUpdatedTargetedMilestone = new MilestoneBuilder().build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetedMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent only have 3 tasks in the INDEX_FIRST milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent only has 1 milestone (that contains 4 tasks)
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, INDEX_FIRST, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code DeleteTaskCommand} which upon execution, deletes {@code task} from the {@code milestone}
     * in the {@code dashboard} of the {@code student} in the {@code model}.
     */
    private DeleteTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Index taskIndex, Model model) {
        DeleteTaskCommand command = new DeleteTaskCommand(studentIndex, milestoneIndex, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FavouriteCommand}.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Student targetStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Student favouritedTargetStudent = new StudentBuilder(targetStudent).withFavourite(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_SUCCESS, favouritedTargetStudent.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(targetStudent, favouritedTargetStudent);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UnfavouriteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code UnfavouriteCommand}.
 */
public class UnfavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Student targetStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Student unfavouriteTargetStudent = new StudentBuilder(targetStudent).withFavourite(false).build();
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_SUCCESS, unfavouriteTargetStudent.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(targetStudent, unfavouriteTargetStudent);

        assertCommandSuccess(unfavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
```
###### \java\seedu\address\model\student\dashboard\DateTest.java
``` java
public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // missing parts
        assertFalse(Date.isValidDate("01/02/2018")); // missing time
        assertFalse(Date.isValidDate("22:11")); // missing date
        assertFalse(Date.isValidDate("01/02 22:11")); // missing year
        assertFalse(Date.isValidDate("01/2018 22:11")); // missing day

        // incorrect number of digits
        assertFalse(Date.isValidDate("1/02/2018 22:11")); // day with 1 digit
        assertFalse(Date.isValidDate("111/02/2018 22:11")); // day with 3 digits
        assertFalse(Date.isValidDate("01/2/2018 22:11")); // month with 1 digit
        assertFalse(Date.isValidDate("01/222/2018 22:11")); // month with 3 digit
        assertFalse(Date.isValidDate("01/02/18 22:11")); // year with 2 digits
        assertFalse(Date.isValidDate("01/02/18181 22:11")); // year with 5 digits
        assertFalse(Date.isValidDate("01/02/2018 2:11")); // hour with 1 digit
        assertFalse(Date.isValidDate("01/02/2018 2222:11")); // hour with 3 digits
        assertFalse(Date.isValidDate("01/02/2018 22:1")); // minute with 1 digit
        assertFalse(Date.isValidDate("01/02/2018 22:111")); // minute with 3 digits

        // invalid calendar date
        assertFalse(Date.isValidDate("32/02/2018 22:11")); // invalid day
        assertFalse(Date.isValidDate("01/13/2018 22:11")); // invalid month
        assertFalse(Date.isValidDate("29/02/2018 22:11")); // non-existent day

        // invalid time
        assertFalse(Date.isValidDate("01/02/2018 24:11")); // invalid hour
        assertFalse(Date.isValidDate("01/02/2018 22:61")); // invalid minutes

        // valid dates
        assertTrue(Date.isValidDate("01/01/2018 22:11"));
        assertTrue(Date.isValidDate("31/12/2020 23:59"));
        assertTrue(Date.isValidDate("31/12/2020 00:00"));
    }
}
```
###### \java\seedu\address\model\student\dashboard\ProgressTest.java
``` java
public class ProgressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Progress(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidProgress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Progress(invalidProgress));
    }

    @Test
    public void isValidProgress() {
        // null progress
        Assert.assertThrows(NullPointerException.class, () -> Progress.isValidProgress(null));

        // invalid format
        assertFalse(Progress.isValidProgress("1/"));
        assertFalse(Progress.isValidProgress("/1"));
        assertFalse(Progress.isValidProgress("1"));

        // invalid values
        assertFalse(Progress.isValidProgress("2/1")); // number completed more than total completed
        assertFalse(Progress.isValidProgress("2/0")); // number completed more than total completed
        assertFalse(Progress.isValidProgress("-2/2")); // negative number completed
        assertFalse(Progress.isValidProgress("-2/-1")); // negative number completed and total completed

        // valid progress
        assertTrue(Progress.isValidProgress("1/2"));
        assertTrue(Progress.isValidProgress("0/2"));
        assertTrue(Progress.isValidProgress("0/0"));
    }


}
```
###### \java\seedu\address\model\student\dashboard\UniqueMilestoneListTest.java
``` java
public class UniqueMilestoneListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMilestoneList uniqueMilestonelist = new UniqueMilestoneList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueMilestonelist.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\student\dashboard\UniqueTaskListTest.java
``` java
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\MilestoneBuilder.java
``` java
/**
 * A utility class to help with building Milestone objects.
 */
public class MilestoneBuilder {

    private final Date defaultDate = new Date("23/05/2018 23:59");
    private final UniqueTaskList defaultTaskList = setDefaultTaskList();
    private final Progress defaultProgress = new ProgressBuilder().build();
    private final String defaultDescription = "Arrays";

    private Date dueDate;
    private UniqueTaskList taskList;
    private Progress progress;
    private String description;

    public MilestoneBuilder() {
        dueDate = defaultDate;
        taskList = defaultTaskList;
        progress = defaultProgress;
        description = defaultDescription;
    }

    /**
     * Initializes the MilestoneBuilder with the data of {@code milestoneToCopy}.
     */
    public MilestoneBuilder(Milestone milestoneToCopy) {
        dueDate = milestoneToCopy.getDueDate();
        taskList = milestoneToCopy.getTaskList();
        progress = milestoneToCopy.getProgress();
        description = milestoneToCopy.getDescription();
    }

    /**
     * Sets the {@code dueDate} of the milestone we are building.
     */
    public MilestoneBuilder withDueDate(Date date) {
        this.dueDate = date;
        return this;
    }

    /**
     * Sets the {@code progress} of the milestone we are building.
     */
    public MilestoneBuilder withProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    /**
     * Sets the {@code tasksList} of the milestone we are building.
     */
    public MilestoneBuilder withTaskList(List<Task> taskList) throws DuplicateTaskException {
        UniqueTaskList modelTaskList = new UniqueTaskList();
        for (Task task : taskList) {
            modelTaskList.add(task);
        }
        this.taskList = modelTaskList;

        return this;
    }

    /**
     * Sets the {@code description} of the milestone we are building.
     */
    public MilestoneBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Adds a new {@code Task} to the {@code Milestone} we are building.
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task in the milestone.
     */
    public MilestoneBuilder withNewTask(Task newTask) throws DuplicateTaskException {
        taskList.add(newTask);
        progress = new ProgressBuilder(progress).withOneNewIncompletedTaskToTotal().build();

        return this;
    }

    /**
     * Removes the {@code Task} from the {@code Milestone} we are building
     *
     * @throws TaskNotFoundException if the task is not found in the milestone.
     */
    public MilestoneBuilder withoutTask(Task task) throws TaskNotFoundException {
        taskList.remove(task);
        if (task.isCompleted()) {
            progress = new ProgressBuilder().withOneLessCompletedTaskFromTotal().build();
        } else {
            progress = new ProgressBuilder(progress).withOneLessIncompletedTaskFromTotal().build();
        }

        return this;
    }

    /**
     * Marks the specified {@code Task} in the {@code taskList} of the {@code Milestone} we are building as completed.
     */
    public MilestoneBuilder withTaskCompleted(Index taskIndex) throws DuplicateTaskException, TaskNotFoundException {
        Task targetTask = taskList.get(taskIndex);

        if (!targetTask.isCompleted()) {
            Task completedTargetTask = new TaskBuilder(targetTask).asCompleted().build();
            taskList.setTask(targetTask, completedTargetTask);
            progress = new ProgressBuilder(progress).withOneMoreCompletedTask().build();
        }

        return this;
    }

    /**
     * Creates and returns the Milestone object with the current attributes.
     */
    public Milestone build() {
        return new Milestone(dueDate, taskList, progress, description);
    }

    /**
     * Sets the default {@code UniqueTasklist}
     */
    private static UniqueTaskList setDefaultTaskList() {
        UniqueTaskList taskList = new UniqueTaskList();

        try {
            taskList.add(TypicalTasks.TASK_1);
            taskList.add(TypicalTasks.TASK_2);
            taskList.add(TypicalTasks.TASK_3);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("Cannot have duplicate task in test");
        }

        return taskList;
    }
}
```
###### \java\seedu\address\testutil\ProgressBuilder.java
``` java
/**
 * A utility class to help with building Progress objects.
 */
public class ProgressBuilder {

    public static final int DEFAULT_TOTAL_TASKS = 3;
    public static final int DEFAULT_NUM_COMPLETED_TASKS = 0;

    private int totalTasks;
    private int numCompletedTasks;
    private int progressInPercent;
    private String value;

    public ProgressBuilder() {
        totalTasks = DEFAULT_TOTAL_TASKS;
        numCompletedTasks = DEFAULT_NUM_COMPLETED_TASKS;
        setProgressPercentAndValue();
    }

    /**
     * Initializes the ProgressBuilder with the data of {@code progressToCopy}.
     */
    public ProgressBuilder(Progress progressToCopy) {
        totalTasks = progressToCopy.getTotalTasks();
        numCompletedTasks = progressToCopy.getNumCompletedTasks();
        progressInPercent = progressToCopy.getProgressInPercent();
        value = progressToCopy.getValue();
    }

    /**
     * Sets the {@code totalTasks} of the {@code Progress} we are building
     */
    public ProgressBuilder withTotalTask(int totalTasks) {
        this.totalTasks = totalTasks;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Sets the {@code numCompletedTask} of the {@code Progress} we are building
     */
    public ProgressBuilder withNumCompletedTasks(int numCompletedTasks) {
        this.numCompletedTasks = numCompletedTasks;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Adds 1 to the {@code totalTask} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneNewIncompletedTaskToTotal() {
        this.totalTasks += 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Subtracts 1 from the {@code totalTask} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneLessIncompletedTaskFromTotal() {
        this.totalTasks -= 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Subtracts 1 from the {@code totalTask} and {@code numCompletedTask } of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneLessCompletedTaskFromTotal() {
        this.totalTasks -= 1;
        this.numCompletedTasks -= 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Adds 1 to the {@code numCompletedTasks} of the {@code Progress} we are building.
     */
    public ProgressBuilder withOneMoreCompletedTask() {
        this.numCompletedTasks += 1;
        setProgressPercentAndValue();

        return this;
    }

    /**
     * Creates and returns the Progress object with the current attributes.
     */
    public Progress build() {
        return new Progress(value);
    }

    /**
     * Sets the {@code progressInPercent} and {@code value} of the Progress we are building with the current attributes.
     */
    private void setProgressPercentAndValue() {
        this.progressInPercent = (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = this.numCompletedTasks + "/" + this.totalTasks;
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
/**
 * A utility class to help with building Milestone objects.
 */
public class TaskBuilder {

    private final String defaultName = "Learn syntax";
    private final String defaultDescription = "Refer to coding website";
    private final boolean defaultIsCompleted = false;

    private String name;
    private String description;
    private boolean isCompleted;

    public TaskBuilder() {
        name = defaultName;
        description = defaultDescription;
        isCompleted = defaultIsCompleted;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        description = taskToCopy.getDescription();
        isCompleted = taskToCopy.isCompleted();
    }

    /**
     * Sets the {@code name} of the {@code Task} we are building
     */
    public TaskBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} we are building
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code isCompleted} of the {@code Task} we are building as true
     */
    public TaskBuilder asCompleted() {
        this.isCompleted = true;
        return this;
    }

    /**
     * Creates and returns the Task object with the current attributes.
     */
    public Task build() {
        return new Task(name, description, isCompleted);
    }
}
```
###### \java\seedu\address\testutil\TypicalMilestones.java
``` java
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalMilestones {

    public static final Milestone MILESTONE_1 = new MilestoneBuilder().withDescription("Arrays")
            .withDueDate(new Date("31/12/2018 23:59")).build();
    public static final Milestone MILESTONE_2 = new MilestoneBuilder().withDescription("Recursion")
            .withDueDate(new Date("01/12/2018 10:00")).build();
    public static final Milestone MILESTONE_3 = new MilestoneBuilder().withDescription("Strings")
            .withDueDate(new Date("22/05/2018 11:59")).build();

    public static List<Milestone> getTypicalMilestones() {
        return new ArrayList<>(Arrays.asList(MILESTONE_1, MILESTONE_2, MILESTONE_3));
    }
}
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task TASK_1 = new TaskBuilder().withName("Learn syntax").withDescription("Refer to website")
            .build();
    public static final Task TASK_2 = new TaskBuilder().withName("Practice ex22").withDescription("From problem set 4")
            .build();
    public static final Task TASK_3 = new TaskBuilder().withName("Learn framework").withDescription("Can be any one")
            .build();
    public static final Task TASK_4 = new TaskBuilder().withName("Hands on practice").withDescription("Go to lab")
            .build(); // Extra task not added to any default task list of a milestone

    public List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_1, TASK_2, TASK_3));
    }
}
```
