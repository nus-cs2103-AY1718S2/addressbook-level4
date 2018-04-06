# yapni
###### \java\seedu\address\commons\events\ui\ShowMilestonesEvent.java
``` java
/**
 * Indicates a request to show the milestones in a student's dashboard
 */
public class ShowMilestonesEvent extends BaseEvent {

    private final UniqueMilestoneList milestoneList;

    public ShowMilestonesEvent(UniqueMilestoneList milestoneList) {
        this.milestoneList = milestoneList;
    }

    public UniqueMilestoneList getMilestoneList() {
        return milestoneList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowStudentDashboardEvent.java
``` java
/**
 * Indicates a request to show the student's dashboard
 */
public class ShowStudentDashboardEvent extends BaseEvent {

    private final Student targetStudent;

    public ShowStudentDashboardEvent(Student targetStudent) {
        this.targetStudent = targetStudent;
    }

    public Student getTargetStudent() {
        return targetStudent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowStudentNameInDashboardEvent.java
``` java
/**
 * Indicates a request to show the student's name in his dashboard
 */
public class ShowStudentNameInDashboardEvent extends BaseEvent {

    private final Name name;

    public ShowStudentNameInDashboardEvent(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddMilestoneCommand.java
``` java
/**
 * Adds a Milestone to a Student's Dashboard
 */
public class AddMilestoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a milestone to a student's dashboard."
            + " Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_DATE + "DATE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DATE + "17/05/2018 23:59 "
            + PREFIX_DESCRIPTION + "Learn Arrays";

    public static final String MESSAGE_DUPLICATE_MILESTONE = "Milestone already exists in the student's Dashboard";
    public static final String MESSAGE_SUCCESS = "Milestone added to Student's Dashboard: %1$s";

    private final Index studentIndex;
    private final Milestone newMilestone;

    private Student targetStudent;
    private Student editedStudent;

    public AddMilestoneCommand(Index index, Milestone newMilestone) {
        requireAllNonNull(index, newMilestone);

        this.newMilestone = newMilestone;
        this.studentIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the milestone list is the same as before */
            throw new AssertionError("New milestone cannot be missing");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, newMilestone));
    }

    @Override
    public void preprocessUndoableCommand() throws CommandException {

        try {
            setTargetStudent();
            editedStudent = createEditedStudent(targetStudent, newMilestone);
        } catch (DuplicateMilestoneException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MILESTONE);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the new Milestone added to its Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Milestone newMilestone)
            throws DuplicateMilestoneException {
        requireAllNonNull(studentToEdit, newMilestone);

        return new StudentBuilder(studentToEdit).withNewMilestone(newMilestone).build();
    }


    /**
     * Sets the {@code targetStudent} object
     * @throws IllegalValueException if the studentIndex is invalid
     */
    private void setTargetStudent() throws IllegalValueException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }


        targetStudent = lastShownList.get(studentIndex.getZeroBased());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMilestoneCommand // instanceof handles null
                && ((AddMilestoneCommand) other).studentIndex == this.studentIndex
                && ((AddMilestoneCommand) other).newMilestone == this.newMilestone);
    }
}
```
###### \java\seedu\address\logic\commands\AddTaskCommand.java
``` java
/**
 * Adds a Task to a Milestone
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to a Milestone in a Student's Dashboard.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX "
            + PREFIX_NAME + "NAME OF TASK "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2 "
            + PREFIX_NAME + "Learn syntax of arrays "
            + PREFIX_DESCRIPTION + "Learn declaration and initialisation of arrays";

    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists in the milestone";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Index studentIndex;
    private final Index milestoneIndex;
    private final Task newTask;

    private Student targetStudent;
    private Student editedStudent;

    public AddTaskCommand(Index studentIndex, Index milestoneIndex, Task newTask) {
        requireAllNonNull(studentIndex, milestoneIndex, newTask);

        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the task list is the same as before */
            throw new AssertionError("New task cannot be missing");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(studentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = targetStudent.getDashboard().getMilestoneList();

        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        try {
            editedStudent = createEditedStudent(targetStudent, newTask, milestoneIndex);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("Milestone cannot be duplicated");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the new task added to its targeted milestone in the Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Task newTask, Index targetMilestoneIndex)
            throws DuplicateTaskException, DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(studentToEdit, newTask);

        return new StudentBuilder(studentToEdit).withNewTask(targetMilestoneIndex, newTask).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles null
                && ((AddTaskCommand) other).studentIndex == this.studentIndex
                && ((AddTaskCommand) other).milestoneIndex  == this.milestoneIndex
                && ((AddTaskCommand) other).newTask == this.newTask);
    }
}
```
###### \java\seedu\address\logic\commands\CheckTaskCommand.java
``` java
/**
 * Mark a task as completed
 */
public class CheckTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "checkTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task in a Milestone as completed.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX "
            + PREFIX_TASK_INDEX + "TASK'S INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2"
            + PREFIX_TASK_INDEX + "3";

    public static final String MESSAGE_SUCCESS = "Task %1$s marked as completed in milestone %2$s";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "Task is already marked as completed";

    private Student targetStudent;
    private Student editedStudent;

    private final Index targetStudentIndex;
    private final Index targetMilestoneIndex;
    private final Index targetTaskIndex;

    public CheckTaskCommand(Index targetStudentIndex, Index targetMilestoneIndex, Index targetTaskIndex) {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, targetTaskIndex);

        this.targetStudentIndex = targetStudentIndex;
        this.targetMilestoneIndex = targetMilestoneIndex;
        this.targetTaskIndex = targetTaskIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        if (targetStudent != editedStudent) {
            try {
                model.updateStudent(targetStudent, editedStudent);
            } catch (DuplicateStudentException e) {
                /* DuplicateStudentException caught will mean that the task list is the same as before */
                throw new AssertionError("New task cannot be missing");
            } catch (StudentNotFoundException e) {
                throw new AssertionError("The target student cannot be missing");
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetTaskIndex.getOneBased(), targetMilestoneIndex.getOneBased()));
        } else {
            return new CommandResult(MESSAGE_TASK_ALREADY_COMPLETED);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            checkIfIndexesAreValid();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        List<Student> lastShownList = model.getFilteredStudentList();
        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());

        /* Only create new edited student if the task has not been marked as completed */
        if (!targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex).getTaskList()
                .get(targetTaskIndex).isCompleted()) {
            try {
                editedStudent = createEditedStudent(targetStudent, targetMilestoneIndex, targetTaskIndex);
            } catch (DuplicateTaskException e) {
                throw new AssertionError("The task cannot be duplicated");
            } catch (TaskNotFoundException e) {
                throw new AssertionError("The target task cannot be missing");
            } catch (DuplicateMilestoneException e) {
                throw new AssertionError("The milestone cannot be duplicated");
            } catch (MilestoneNotFoundException e) {
                throw new AssertionError("The milestone cannot be missing");
            }
        } else {
            editedStudent = targetStudent;
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the task in the specified milestone marked as completed.
     */
    private Student createEditedStudent(Student studentToEdit, Index milestoneIndex, Index taskIndex)
            throws DuplicateTaskException, TaskNotFoundException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(studentToEdit, milestoneIndex, taskIndex);

        return new StudentBuilder(studentToEdit).withTaskCompleted(milestoneIndex, taskIndex).build();
    }

    /**
     * Checks if the student index, milestone index and task index are valid
     * @throws IllegalValueException if any of the indexes are invalid
     */
    private void checkIfIndexesAreValid() throws IllegalValueException {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, targetTaskIndex);

        /* Check if student index is valid */
        List<Student> lastShownList = model.getFilteredStudentList();
        if (targetStudentIndex.getZeroBased() < 0 || targetStudentIndex.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        /* Check if milestone index is valid */
        Student student = lastShownList.get(targetStudentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = student.getDashboard().getMilestoneList();
        if (targetMilestoneIndex.getZeroBased() < 0 || targetMilestoneIndex.getZeroBased() >= milestoneList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        /*  Check if task index is valid */
        UniqueTaskList taskList = milestoneList.get(targetMilestoneIndex).getTaskList();
        if (targetTaskIndex.getZeroBased() < 0 || targetTaskIndex.getZeroBased() >= taskList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckTaskCommand // instanceof handles null
                && ((CheckTaskCommand) other).targetStudentIndex == this.targetStudentIndex
                && ((CheckTaskCommand) other).targetMilestoneIndex == this.targetMilestoneIndex
                && ((CheckTaskCommand) other).targetTaskIndex == this.targetTaskIndex);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteMilestoneCommand.java
``` java
/**
 * Deletes a milestone from a student's dashboard
 */
public class DeleteMilestoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteMS";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a milestone from a student's dashboard.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1"
            + PREFIX_MILESTONE_INDEX + "2";

    public static final String MESSAGE_DELETE_MILESTONE_SUCCESS = "Deleted milestone: %1$s";

    private final Index studentIndex;
    private final Index milestoneIndex;

    private Student targetStudent;
    private Student editedStudent;
    private Milestone targetMilestone;

    public DeleteMilestoneCommand(Index studentIndex, Index milestoneIndex) {
        requireAllNonNull(studentIndex, milestoneIndex);
        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the milestone list is the same as before */
            throw new AssertionError("Milestone list cannot be the same");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("Student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MILESTONE_SUCCESS, targetMilestone));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            setTargetObjects();
            editedStudent = createEditedStudent(targetStudent, targetMilestone);
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the specified {@code milestone} removed from the dashboard
     */
    private Student createEditedStudent(Student targetStudent, Milestone milestoneToDelete)
            throws MilestoneNotFoundException {
        requireAllNonNull(targetStudent, milestoneToDelete);

        return new StudentBuilder(targetStudent).withoutMilestone(milestoneToDelete).build();
    }

    /**
     * Sets the {@code targetStudent} and {@code targetMilestone} objects
     * @throws IllegalValueException if any of the studentIndex or milestoneIndex are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        requireAllNonNull(studentIndex, milestoneIndex);

        List<Student> lastShownList  = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >=  lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(studentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = targetStudent.getDashboard().getMilestoneList();

        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        targetMilestone = milestoneList.get(milestoneIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMilestoneCommand // instanceof handles null
                && ((DeleteMilestoneCommand) other).studentIndex == this.studentIndex
                && ((DeleteMilestoneCommand) other).milestoneIndex == this.milestoneIndex);
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
/**
 * Add a student to favourites
 */
public class FavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a student to favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Student added to favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index targetIndex;

    private Student studentToFavourite;
    private Student editedStudent;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(studentToFavourite, editedStudent);

        try {
            model.updateStudent(studentToFavourite, editedStudent);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedStudent));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToFavourite = lastShownList.get(targetIndex.getZeroBased());
        editedStudent = createEditedStudent(studentToFavourite);
    }

    /**
     * Create and return a copy of the target {@Code Student} to favourite with its' Favourite attribute set to true.
     */
    private static Student createEditedStudent(Student target) {
        requireNonNull(target);

        return new StudentBuilder(target).withFavourite(true).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles null
                && ((FavouriteCommand) other).targetIndex == this.targetIndex);

    }
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    /**
     * Execute list command when -f flag is on, i.e. list favourite students only
     */
    public CommandResult execute_list_favourites() {
        model.updateFilteredStudentList(PREDICATE_SHOW_FAVOURITE_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS_LIST_FAVOURITES);
    }
}
```
###### \java\seedu\address\logic\commands\ShowDashboardCommand.java
``` java
/**
 * Shows the dashboard of a student who is identified using it's last displayed index from the address book to the user
 */
public class ShowDashboardCommand extends Command {

    public static final String COMMAND_WORD = "showDB";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the student's dashboard.\n"
            + "Parameters: " + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_STUDENT_DASHBOARD_SUCCESS = "Selected Dashboard of Student: %1$s";

    private final Index targetIndex;

    public ShowDashboardCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() < 0 || targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new ShowStudentDashboardEvent(lastShownList.get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_SELECT_STUDENT_DASHBOARD_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowDashboardCommand // instanceof handles null
                && ((ShowDashboardCommand) other).targetIndex == this.targetIndex);
    }
}
```
###### \java\seedu\address\logic\commands\UnfavouriteCommand.java
``` java
/**
 * Remove a Student from favourites
 */
public class UnfavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove a student from favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Student removed from favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index targetIndex;

    private Student studentToUnfavourite;
    private Student editedStudent;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(studentToUnfavourite, editedStudent);

        try {
            model.updateStudent(studentToUnfavourite, editedStudent);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedStudent));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToUnfavourite = lastShownList.get(targetIndex.getZeroBased());
        editedStudent = createEditedStudent(studentToUnfavourite);
    }

    /**
     * Create and return a copy of the target {@Code Student} to favourite with its' Favourite attribute set to false.
     */
    private static Student createEditedStudent(Student target) {
        requireNonNull(target);

        return new StudentBuilder(target).withFavourite(false).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles null
                && ((UnfavouriteCommand) other).targetIndex == this.targetIndex);

    }
}
```
###### \java\seedu\address\logic\parser\AddMilestoneCommandParser.java
``` java
/**
 * Parses input arguments and create a new AddMilestoneCommand object
 */
public class AddMilestoneCommandParser implements Parser<AddMilestoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMilestoneCommand
     * and returns an AddMilestoneCommand object for execution.
     * @throws NullPointerException if args is null
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMilestoneCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATE, PREFIX_DESCRIPTION);

        if (!argMultiMap.arePrefixesPresent(PREFIX_INDEX, PREFIX_DATE, PREFIX_DESCRIPTION)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Date date = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get());
            String objective = argMultiMap.getValue(PREFIX_DESCRIPTION).get();

            Milestone milestone = new Milestone(date, objective);

            return new AddMilestoneCommand(index, milestone);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and create a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws NullPointerException if args is null
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_NAME, PREFIX_DESCRIPTION);

        if (!argMultiMap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_MILESTONE_INDEX).get());
            String name = argMultiMap.getValue(PREFIX_NAME).get();
            String description = argMultiMap.getValue(PREFIX_DESCRIPTION).get();

            Task task = new Task(name, description);

            return new AddTaskCommand(studentIndex, milestoneIndex, task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\CheckTaskCommandParser.java
``` java
/**
 * Parses input arguments and create a new CheckTaskCommand object
 */
public class CheckTaskCommandParser implements Parser<CheckTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckTaskCommand
     * and returns a CheckTaskCommand object for execution.
     * @throws NullPointerException if args is null
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_TASK_INDEX);

        if (!argMultiMap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX, PREFIX_TASK_INDEX)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckTaskCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_MILESTONE_INDEX).get());
            Index taskIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_TASK_INDEX).get());

            return new CheckTaskCommand(studentIndex, milestoneIndex, taskIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteMilestoneCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteMilestoneCommand object
 */
public class DeleteMilestoneCommandParser implements Parser<DeleteMilestoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMilestoneCommand
     * and returns an DeleteMilestoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMilestoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_MILESTONE_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX, PREFIX_MILESTONE_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMilestoneCommand.MESSAGE_USAGE));
        }

        try {
            Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            Index milestoneIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MILESTONE_INDEX).get());

            return new DeleteMilestoneCommand(studentIndex, milestoneIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FavouriteCommand object
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns an FavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ShowDashboardCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShowDashboardCommand object
 */
public class ShowDashboardCommandParser implements Parser<ShowDashboardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDashboardCommand
     * and returns a ShowDashboardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowDashboardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowDashboardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDashboardCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\UnfavouriteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnfavouriteCommand object
 */
public class UnfavouriteCommandParser implements Parser<UnfavouriteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnfavouriteCommand
     * and returns an unFavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnfavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\student\dashboard\Dashboard.java
``` java
/**
 * Represents a Student's Dashboard
 */
public class Dashboard {

    private final UniqueMilestoneList milestoneList;

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard() {
        milestoneList = new UniqueMilestoneList();
    }

    /**
     * Constructs a {@code Dashboard}
     */
    public Dashboard(UniqueMilestoneList milestoneList) {
        requireNonNull(milestoneList);

        this.milestoneList = milestoneList;
    }

    public UniqueMilestoneList getMilestoneList() {
        return milestoneList;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Dashboard // instanceof handles null
                && this.milestoneList.equals(((Dashboard) obj).getMilestoneList()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;

        builder.append("Milestones:\n");
        for (Milestone milestone : milestoneList) {
            builder.append(index++)
                    .append(". ")
                    .append(milestone)
                    .append("\n");
        }

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return milestoneList.hashCode();
    }
}
```
###### \java\seedu\address\model\student\dashboard\Date.java
``` java
/**
 * Represents a date in a Student's Dashboard
 * Guarantees: immutable.
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be of the format dd/mm/yyyy hh:mm"
            + "where dd must be between 01 and 31"
            + ", mm between 01 and 12"
            + ", yyyy between 0000 and 9999"
            + ", hh between 00 to 23"
            + " and mm between 00 to 59."
            + " There must be a single space between the date and the time.";

    // Regex for the date format
    private static final String DAY_PART_REGEX = "([0-9]{2})";
    private static final String MONTH_PART_REGEX = "([0-9]{2})";
    private static final String YEAR_PART_REGEX = "([0-9]{4})";
    private static final String HOUR_PART_REGEX = "([0-9]{2})";
    private static final String MINUTE_PART_REGEX = "([0-9]{2})";
    public static final String DATE_VALIDATION_REGEX = DAY_PART_REGEX + "/" + MONTH_PART_REGEX + "/" + YEAR_PART_REGEX
            + "\\s" + HOUR_PART_REGEX + ":" + MINUTE_PART_REGEX;

    private static final Pattern dateFormatPattern = Pattern.compile(DATE_VALIDATION_REGEX);

    // Indexes of capturing group in the Date Matcher's Pattern
    private static final int GROUP_DAY = 1;
    private static final int GROUP_MONTH = 2;
    private static final int GROUP_YEAR = 3;
    private static final int GROUP_HOUR = 4;
    private static final int GROUP_MINUTE = 5;

    private final String value;

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);

        String trimmedDate = date.trim();
        Matcher matcher = dateFormatPattern.matcher(trimmedDate);
        matcher.matches();
        int day = Integer.parseInt(matcher.group(GROUP_DAY));
        int month = Integer.parseInt(matcher.group(GROUP_MONTH));
        int year = Integer.parseInt(matcher.group(GROUP_YEAR));
        int hour = Integer.parseInt(matcher.group(GROUP_HOUR));
        int min = Integer.parseInt(matcher.group(GROUP_MINUTE));

        value = trimmedDate;
    }

    /**
     * Returns if a given string is a valid date
     * @throws NullPointerException if input is null
     */
    public static boolean isValidDate(String input) {
        requireNonNull(input);

        Matcher matcher = dateFormatPattern.matcher(input.trim());
        if (!matcher.matches()) {
            return false;
        }

        int day = Integer.parseInt(matcher.group(GROUP_DAY));
        int month = Integer.parseInt(matcher.group(GROUP_MONTH));
        int year = Integer.parseInt(matcher.group(GROUP_YEAR));
        int hour = Integer.parseInt(matcher.group(GROUP_HOUR));
        int min = Integer.parseInt(matcher.group(GROUP_MINUTE));

        return (day >= 1 && day <= 31) && (month >= 1 && month <= 12) && (year >= 1 && year <= 9999)
                && (hour >= 0 && hour <= 23) && (min >= 0 && min <= 59);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Date
                && this.value.equals(((Date) obj).getValue()));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\student\dashboard\exceptions\DuplicateMilestoneException.java
``` java
/**
 * Signals that the operation will result in duplicate Milestone objects.
 */
public class DuplicateMilestoneException extends DuplicateDataException {
    public DuplicateMilestoneException() {
        super("Operation will result in duplicate milestones");
    }
}
```
###### \java\seedu\address\model\student\dashboard\exceptions\DuplicateTaskException.java
``` java
/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation will result in duplicate tasks");
    }
}
```
###### \java\seedu\address\model\student\dashboard\exceptions\MilestoneNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified milestone.
 */
public class MilestoneNotFoundException extends Exception {}
```
###### \java\seedu\address\model\student\dashboard\exceptions\TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {}
```
###### \java\seedu\address\model\student\dashboard\Milestone.java
``` java
/**
 * Represents a milestone in a Student's dashboard
 */
public class Milestone {

    private final Date dueDate;
    private final UniqueTaskList taskList;
    private final Progress progress;
    private final String description;

    public Milestone(Date dueDate, String description) {
        requireAllNonNull(dueDate, description);

        this.dueDate = dueDate;
        this.description = description;
        progress = new Progress();
        taskList = new UniqueTaskList();
    }

    public Milestone(Date dueDate, UniqueTaskList taskList, Progress progress, String description) {
        requireAllNonNull(dueDate, taskList, progress, description);

        this.dueDate = dueDate;
        this.taskList = taskList;
        this.progress = progress;
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public UniqueTaskList getTaskList() {
        return taskList;
    }

    public Progress getProgress() {
        return progress;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof Milestone
                && this.dueDate.equals(((Milestone) obj).getDueDate())
                && this.taskList.equals(((Milestone) obj).getTaskList())
                && this.progress.equals(((Milestone) obj).getProgress())
                && this.description.equals(((Milestone) obj).getDescription()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;

        builder.append("Description: ")
                .append(description)
                .append(" ||")
                .append(" Due Date: ")
                .append(dueDate)
                .append(" ||")
                .append(" Progress: ")
                .append(progress)
                .append("\n")
                .append("Tasks: ");
        for (Task task : taskList) {
            builder.append(index++)
                    .append(" - ")
                    .append(task)
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, taskList, progress, description);
    }
}
```
###### \java\seedu\address\model\student\dashboard\Progress.java
``` java
/**
 * Represents a Milestone's progress
 * Guarantees: details are present and not null, immutable.
 */
public class Progress {

    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "totalTasks must always be more than or equals to numCompletedTask";

    private static final String PROGRESS_FORMAT_REGEX = "([0-9]+)" + "/" + "([0-9]+)";

    private static final Pattern progressFormatPattern = Pattern.compile(PROGRESS_FORMAT_REGEX);

    // Indexes of capturing group in the Progress Matcher's Pattern
    private static final int GROUP_NUM_COMPLETED = 1;
    private static final int GROUP_TOTAL_TASKS = 2;

    private final int totalTasks;
    private final int numCompletedTasks;
    private final int progressInPercent;

    private final String value;

    public Progress() {
        totalTasks = 0;
        numCompletedTasks = 0;
        progressInPercent = 0;
        value = "0/0";
    }

    public Progress(int totalTasks, int numCompletedTasks) {
        checkArgument(isValidProgress(totalTasks, numCompletedTasks), MESSAGE_PROGRESS_CONSTRAINTS);

        this.totalTasks = totalTasks;
        this.numCompletedTasks = numCompletedTasks;
        this.progressInPercent = (totalTasks == 0) ? 0 : (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = numCompletedTasks + "/" + totalTasks;
    }

    public Progress(String value) {
        requireNonNull(value);
        checkArgument(isValidProgress(value), MESSAGE_PROGRESS_CONSTRAINTS);

        String trimmedValue = value.trim();
        Matcher matcher = progressFormatPattern.matcher(trimmedValue);
        matcher.matches();

        this.numCompletedTasks = Integer.parseInt(matcher.group(GROUP_NUM_COMPLETED));
        this.totalTasks = Integer.parseInt(matcher.group(GROUP_TOTAL_TASKS));
        this.progressInPercent = (totalTasks == 0) ? 0 : (int) (((double) numCompletedTasks / totalTasks) * 100);
        this.value = trimmedValue;
    }

    /**
     * Returns if a given Progress attributes are valid
     */
    public static boolean isValidProgress(int totalTasks, int numCompletedTasks) {
        return totalTasks >= numCompletedTasks;
    }

    /**
     * Returns if a given Progress attributes are valid
     */
    public static boolean isValidProgress(String value) {
        requireNonNull(value);

        Matcher matcher = progressFormatPattern.matcher(value.trim());
        if (!matcher.matches()) {
            return false;
        }

        int numCompletedTasks = Integer.parseInt(matcher.group(GROUP_NUM_COMPLETED));
        int totalTasks = Integer.parseInt(matcher.group(GROUP_TOTAL_TASKS));

        return isValidProgress(totalTasks, numCompletedTasks);
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNumCompletedTasks() {
        return numCompletedTasks;
    }

    public int getProgressInPercent() {
        return progressInPercent;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof Progress
                && this.totalTasks == ((Progress) obj).getTotalTasks()
                && this.numCompletedTasks == ((Progress) obj).getNumCompletedTasks());
    }

    @Override
    public String toString() {
        return progressInPercent + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTasks, numCompletedTasks, progressInPercent);
    }
}
```
###### \java\seedu\address\model\student\dashboard\Task.java
``` java
/**
 * Represents a task in a milestone
 * Guarantees: details are present and not null, immutable.
 */
public class Task {

    private final String name;
    private final String description;
    private final boolean isCompleted;

    public Task(String name, String description) {
        requireAllNonNull(name, description);

        this.name = name;
        this.description = description;
        isCompleted = false;
    }

    public Task(String name, String description, boolean isCompleted) {
        requireAllNonNull(name, description);

        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Task // instanceof checks null
                && this.name.equals(((Task) obj).getName())
                && this.description.equals(((Task) obj).getDescription())
                && this.isCompleted == ((Task) obj).isCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append(" ||")
                .append(" Desc: ")
                .append(description)
                .append(" ||")
                .append(" Completed: ")
                .append(isCompleted);
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\student\dashboard\UniqueMilestoneList.java
``` java
/**
 * A list of milestones that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Milestone#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMilestoneList implements Iterable<Milestone> {

    private final ObservableList<Milestone> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent milestone as the given argument.
     */
    public boolean contains(Milestone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a milestone to the list.
     *
     * @throws DuplicateMilestoneException if the milestone to add is a duplicate of an existing milestone in the list.
     */
    public void add(Milestone toAdd) throws DuplicateMilestoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMilestoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the milestone at the specific {@code index} in the list
     *
     * @throws IndexOutOfBoundsException if the index provided is out of range
     */
    public Milestone get(Index index) throws IndexOutOfBoundsException {
        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns the size of the internal list
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the milestone {@code target} in the list with {@code editedMilestone}.
     *
     * @throws DuplicateMilestoneException if the replacement is equivalent to another existing milestone in the list.
     * @throws MilestoneNotFoundException if {@code target} could not be found in the list.
     */
    public void setMilestone(Milestone target, Milestone editedMilestone)
            throws DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(target, editedMilestone);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MilestoneNotFoundException();
        }

        if (!target.equals(editedMilestone) && contains(editedMilestone)) {
            throw new DuplicateMilestoneException();
        }

        internalList.set(index, editedMilestone);
    }

    /**
     * Removes the equivalent milestone from the list.
     *
     * @throws MilestoneNotFoundException if no such milestone could be found in the list.
     */
    public boolean remove(Milestone toRemove) throws MilestoneNotFoundException {
        requireNonNull(toRemove);
        final boolean milestoneFoundAndDeleted = internalList.remove(toRemove);
        if (!milestoneFoundAndDeleted) {
            throw new MilestoneNotFoundException();
        }
        return milestoneFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Milestone> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Milestone> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || (obj instanceof UniqueMilestoneList // instanceof handles null
                    && this.internalList.equals(((UniqueMilestoneList) obj).asObservableList()));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\student\dashboard\UniqueTaskList.java
``` java
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the Task to add is a duplicate of an existing Task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the task at the specific {@code index} in the list
     *
     * @throws IndexOutOfBoundsException if the index provided is out of range
     */
    public Task get(Index index) throws IndexOutOfBoundsException {
        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns the size of the internal list
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof UniqueTaskList // instanceof handles null
                && this.internalList.equals(((UniqueTaskList) obj).asObservableList()));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedDashboard.java
``` java
/**
 * JAXB-friendly adapted version of the Dashboard.
 */
public class XmlAdaptedDashboard {

    @XmlElement(required = true)
    private List<XmlAdaptedMilestone> milestoneList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedDashboard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDashboard() {}

    /**
     * Constructs an {@code XmlAdaptedDashboard} with the given dashboard details.
     */
    public XmlAdaptedDashboard(List<XmlAdaptedMilestone> milestoneList) {
        if (milestoneList != null) {
            this.milestoneList = new ArrayList<>(milestoneList);
        }
    }

    /**
     * Converts a given Dashboard into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDashboard
     */
    public XmlAdaptedDashboard(Dashboard source) {
        milestoneList = new ArrayList<>();
        for (Milestone milestone : source.getMilestoneList()) {
            milestoneList.add(new XmlAdaptedMilestone(milestone));
        }
    }

    /**
     * Converts this jaxb-friendly adapted dashboard object into the model's Dashboard object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted dashboard
     */
    public Dashboard toModelType() throws IllegalValueException {
        final UniqueMilestoneList modelMilestoneList = new UniqueMilestoneList();
        for (XmlAdaptedMilestone milestone : milestoneList) {
            modelMilestoneList.add(milestone.toModelType());
        }

        return new Dashboard(modelMilestoneList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDashboard)) {
            return false;
        }

        XmlAdaptedDashboard otherDashboard = (XmlAdaptedDashboard) other;
        return milestoneList.equals(otherDashboard.milestoneList);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedMilestone.java
``` java
/**
 * JAXB-friendly adapted version of the Milestone.
 */
public class XmlAdaptedMilestone {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Milestone's %s field is missing!";


    @XmlElement (required = true)
    private String dueDate;
    @XmlElement (required = true)
    private String progress;
    @XmlElement (required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedTask> tasksList;

    /**
     * Constructs an XmlAdaptedMilestone.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMilestone() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedMilestone(List<XmlAdaptedTask> tasksList, String dueDate, String progress, String description) {
        if (tasksList != null) {
            this.tasksList = new ArrayList<>(tasksList);
        }
        this.dueDate = dueDate;
        this.progress = progress;
        this.description = description;
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMilestone
     */
    public XmlAdaptedMilestone(Milestone source) {
        tasksList = new ArrayList<>();
        for (Task task : source.getTaskList()) {
            tasksList.add(new XmlAdaptedTask(task));
        }
        dueDate = source.getDueDate().getValue();
        progress = source.getProgress().getValue();
        description = source.getDescription();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted milestone
     */
    public Milestone toModelType() throws IllegalValueException {
        final UniqueTaskList modelTaskList = new UniqueTaskList();
        if (tasksList != null) {
            for (XmlAdaptedTask task : tasksList) {
                modelTaskList.add(task.toModelType());
            }
        }

        if (this.dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelDate = new Date(this.dueDate);

        if (this.progress == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Progress.class.getSimpleName()));
        }
        final Progress modelProgress = new Progress(this.progress);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        final String modelDescription = this.description;

        return new Milestone(modelDate, modelTaskList, modelProgress, modelDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMilestone)) {
            return false;
        }

        XmlAdaptedMilestone otherMilestone = (XmlAdaptedMilestone) other;
        return Objects.equals(dueDate, otherMilestone.dueDate)
                && Objects.equals(progress, otherMilestone.progress)
                && Objects.equals(description, otherMilestone.description)
                && tasksList.equals(otherMilestone.tasksList);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly adapted version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private static final String COMPLETED = "completed";
    private static final String NOT_COMPLETED = "not completed";

    @XmlElement (required = true)
    private String name;
    @XmlElement (required = true)
    private String description;
    @XmlElement (required = true)
    private String isCompleted;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String name, String description, String isCompleted) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName();
        description = source.getDescription();
        isCompleted = source.isCompleted() ? COMPLETED : NOT_COMPLETED;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        if (this.isCompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isCompleted"));
        }

        return this.isCompleted.equals(COMPLETED) ? new Task(name, description, true)
                : new Task(name, description, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(name, otherTask.name)
                && Objects.equals(description, otherTask.description)
                && Objects.equals(isCompleted, otherTask.isCompleted);
    }
}
```
###### \java\seedu\address\ui\DashboardPanel.java
``` java
/**
 * Panel containing the student's dashboard.
 */
public class DashboardPanel extends UiPart<Region> {

    private static final String FXML = "DashboardPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label studentName;

    @FXML
    private ListView<MilestoneCard> milestoneListView;

    public DashboardPanel() {
        super(FXML);
        loadDefaultDashboard();

        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default dashboard at start up
     */
    private void loadDefaultDashboard() {
        studentName.setText("John Doe");
        ObservableList<MilestoneCard> milestoneCardList = FXCollections.observableArrayList();
        milestoneCardList.add(new MilestoneCard(new Milestone(
                new Date("31/12/2018 23:59"), "placeholder"), 1));
        milestoneListView.setItems(milestoneCardList);
        milestoneListView.setCellFactory(listView -> new MilestoneListViewCell());
    }

    /**
     * Loads the list of milestones into the dashboard
     */
    private void loadMilestoneList(ObservableList<Milestone> milestoneList) {
        ObservableList<MilestoneCard> mappedList = EasyBind.map(
                milestoneList, (milestone) -> new MilestoneCard(milestone, milestoneList.indexOf(milestone) + 1));
        milestoneListView.setItems(mappedList);
    }

    private void loadStudentName(Name name) {
        studentName.setText(name.fullName);
    }

    @Subscribe
    public void handleShowMilestonesEvent(ShowMilestonesEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMilestoneList(event.getMilestoneList().asObservableList());
    }

    @Subscribe
    public void handleShowStudentNameInDashboardEvent(ShowStudentNameInDashboardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadStudentName(event.getName());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MilestoneCard}.
     */
    class MilestoneListViewCell extends ListCell<MilestoneCard> {

        @Override
        protected void updateItem(MilestoneCard milestone, boolean empty) {
            super.updateItem(milestone, empty);

            if (empty || milestone == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(milestone.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
/**
 * Panel that contains the browser panel and the dashboard panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private DashboardPanel dashboardPanel;

    @FXML
    private StackPane browserPanelPlaceholder;

    @FXML
    private StackPane dashboardPanelPlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPanelPlaceholder.getChildren().add(browserPanel.getRoot());

        dashboardPanel = new DashboardPanel();
        dashboardPanelPlaceholder.getChildren().add(dashboardPanel.getRoot());

        browserPanelPlaceholder.toFront();

        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    /**
     * Show the browser panel
     */
    private void showBrowserPanel() {
        dashboardPanelPlaceholder.setVisible(false);
        browserPanelPlaceholder.setVisible(true);
        browserPanelPlaceholder.toFront();
    }

    /**
     * Show the dashboard panel
     */
    private void showDashboardPanel() {
        browserPanelPlaceholder.setVisible(false);
        dashboardPanelPlaceholder.setVisible(true);
        dashboardPanelPlaceholder.toFront();
    }

    @Subscribe
    public void handleStudentPanelSelectionChangedEvent(StudentPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showBrowserPanel();
        raise(new BrowserDisplayEvent(event.getNewSelection()));
    }

    @Subscribe
    public void handleShowStudentDashboardEvent(ShowStudentDashboardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showDashboardPanel();
        raise(new ShowStudentNameInDashboardEvent(event.getTargetStudent().getName()));
        raise(new ShowMilestonesEvent(event.getTargetStudent().getDashboard().getMilestoneList()));
    }
}
```
###### \java\seedu\address\ui\MilestoneCard.java
``` java
/**
 * An UI component that displays information of a {@code Milestone}.
 */
public class MilestoneCard extends UiPart<Region> {

    private static final String FXML = "MilestoneCard.fxml";

    public final Milestone milestone;

    @FXML
    private VBox cardPane;

    @FXML
    private ListView<TaskCard> taskListView;

    @FXML
    private Label index;

    @FXML
    private Label description;

    @FXML
    private Label dueDate;

    @FXML
    private Label progressPercent;

    @FXML
    private ProgressBar progress;

    public MilestoneCard(Milestone milestone, int displayedIndex) {
        super(FXML);
        this.milestone = milestone;

        index.setText(Integer.toString(displayedIndex));
        description.setText(milestone.getDescription());
        dueDate.setText(milestone.getDueDate().toString());
        progress.setProgress(milestone.getProgress().getProgressInPercent() / 100.0);
        progressPercent.setText(milestone.getProgress().getProgressInPercent() + "%");
        loadTaskList(milestone.getTaskList().asObservableList());
    }

    /**
     * Loads the list of tasks in to the milestone card
     */
    private void loadTaskList(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
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
###### \java\seedu\address\ui\TaskCard.java
``` java
/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    public final Task task;

    @FXML
    private VBox cardPane;

    @FXML
    private Label index;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label isCompleted;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;

        index.setText("Task " + displayedIndex);
        name.setText(task.getName());
        description.setText(task.getDescription());
        isCompleted.setText(String.valueOf(task.isCompleted()));
    }
}
```
###### \resources\view\DashboardPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<Pane prefHeight="800.0" prefWidth="900.0" style="-fx-background-color: gray;" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <AnchorPane layoutX="8.0" layoutY="5.0" prefWidth="800.0">
         <children>
            <Text layoutX="14.0" layoutY="22.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Dashboard">
               <font>
                  <Font name="Segoe UI Black" size="20.0" />
               </font>
            </Text>
            <Text layoutX="14.0" layoutY="93.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Milestones">
               <font>
                  <Font name="Rockwell" size="20.0" />
               </font>
            </Text>
            <Label fx:id="studentName" layoutX="14.0" layoutY="27.0" style="-fx-font-size: 18; -fx-font-family: Bookman Old Style; -fx-text-fill: black;" text="studentName">
               <font>
                  <Font name="Bookman Old Style Bold" size="20.0" />
               </font>
            </Label>
            <VBox layoutX="14.0" layoutY="97.0" prefHeight="200.0" prefWidth="769.0">
               <children>
                  <ListView fx:id="milestoneListView" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="406.0" prefWidth="900.0" style="-fx-border-color: black; -fx-background-color: white; -fx-background-insets: 0;">
                     <VBox.margin>
                        <Insets top="10.0" />
                     </VBox.margin>
                  </ListView>
               </children>
            </VBox>
         </children>
      </AnchorPane>
   </children>
</Pane>
```
###### \resources\view\InfoPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>

<StackPane fx:id="infoPanel" prefHeight="700.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
      <StackPane fx:id="browserPanelPlaceholder" prefHeight="150.0" prefWidth="200.0" />
      <StackPane fx:id="dashboardPanelPlaceholder" prefHeight="150.0" prefWidth="200.0" style="-fx-background-color: white;" />
</StackPane>
```
###### \resources\view\MilestoneCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.ProgressBar?>
<?import javafx.scene.effect.Blend?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<VBox fx:id="cardPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="260.0" prefWidth="900.0" style="-fx-border-color: black; -fx-background-color: #e0dedc;" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox prefHeight="21.0" prefWidth="600.0">
         <children>
            <Text fill="#00000091" strokeType="OUTSIDE" strokeWidth="0.0" text="MILESTONE">
               <font>
                  <Font name="System Bold" size="14.0" />
               </font>
               <HBox.margin>
                  <Insets left="5.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="index" style="-fx-text-fill: #4a4d4f; -fx-font-weight: bold;" text="idx" textFill="#585858">
               <font>
                  <Font name="System Bold" size="13.0" />
               </font>
               <HBox.margin>
                  <Insets left="5.0" top="4.0" />
               </HBox.margin>
            </Label>
         </children>
      </HBox>
      <HBox prefHeight="20.0" prefWidth="600.0">
         <children>
            <Text fill="#000000bf" strokeType="OUTSIDE" strokeWidth="0.0" text="Description:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="description" style="-fx-font-family: Segoe UI; -fx-text-fill: #030303; -fx-font-size: 14;" text="desc">
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
            </Label>
         </children>
         <effect>
            <Blend />
         </effect>
      </HBox>
      <HBox prefHeight="20.0" prefWidth="600.0">
         <children>
            <Text fill="#000000bf" strokeType="OUTSIDE" strokeWidth="0.0" text="Due Date:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="dueDate" style="-fx-font-family: Segoe UI; -fx-font-size: 14; -fx-text-fill: #030303;" text="date">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Label>
         </children>
         <effect>
            <Blend />
         </effect>
      </HBox>
      <HBox prefHeight="150.0" prefWidth="600.0">
         <children>
            <Text fill="#000000bf" strokeType="OUTSIDE" strokeWidth="0.0" text="Tasks:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <ListView fx:id="taskListView" prefHeight="150.0" prefWidth="700.0">
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </ListView>
         </children>
         <effect>
            <Blend />
         </effect>
      </HBox>
      <HBox prefHeight="20.0" prefWidth="600.0">
         <children>
            <Text fill="#000000bf" strokeType="OUTSIDE" strokeWidth="0.0" text="Progress:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <ProgressBar fx:id="progress" prefWidth="200.0" progress="0.0" />
                  <Label fx:id="progressPercent" style="-fx-text-fill: black;" text="percent">
                     <font>
                        <Font size="10.0" />
                     </font>
                  </Label>
               </children>
               <HBox.margin>
                  <Insets left="5.0" top="8.0" />
               </HBox.margin>
            </StackPane>
         </children>
         <effect>
            <Blend />
         </effect>
      </HBox>
      <StackPane maxHeight="-Infinity" maxWidth="-Infinity" prefHeight="20.0" prefWidth="35.0">
         <VBox.margin>
            <Insets top="5.0" />
         </VBox.margin>
      </StackPane>
   </children>
</VBox>
```
###### \resources\view\TaskCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<VBox fx:id="cardPane" prefWidth="650.0" style="-fx-background-color: #827f7d; -fx-border-color: black;" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label fx:id="index" style="-fx-font-size: 15; -fx-text-fill: black; -fx-underline: true; -fx-font-weight: bold;" text="index">
         <VBox.margin>
            <Insets left="10.0" top="5.0" />
         </VBox.margin>
      </Label>
      <HBox prefHeight="20.0" prefWidth="600.0">
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Name:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="name" style="-fx-font-family: Segoe UI; -fx-font-size: 14;" text="name">
               <HBox.margin>
                  <Insets left="10.0" top="7.0" />
               </HBox.margin>
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
            </Label>
         </children>
      </HBox>
      <HBox prefHeight="20.0" prefWidth="600.0">
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Description:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="description" style="-fx-font-family: Segoe UI; -fx-font-size: 14;" text="desc">
               <HBox.margin>
                  <Insets left="10.0" top="7.0" />
               </HBox.margin>
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
            </Label>
         </children>
      </HBox>
      <HBox prefHeight="20.0" prefWidth="650.0">
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Completed:">
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
               <HBox.margin>
                  <Insets left="10.0" top="5.0" />
               </HBox.margin>
            </Text>
            <Label fx:id="isCompleted" style="-fx-font-family: Segoe UI; -fx-font-size: 14;" text="isCompleted">
               <HBox.margin>
                  <Insets left="10.0" top="7.0" />
               </HBox.margin>
               <font>
                  <Font name="System Bold" size="15.0" />
               </font>
            </Label>
         </children>
      </HBox>
   </children>
</VBox>
```
