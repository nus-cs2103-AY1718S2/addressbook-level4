package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Favourite;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Progress;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.UniqueHomeworkList;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.UniqueTaskList;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.tag.Tag;

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

    private Student studentToEdit;
    private Student editedStudent;

    public AddTaskCommand(Index studentIndex, Index milestoneIndex, Task newTask) {
        requireAllNonNull(studentIndex, milestoneIndex, newTask);

        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireAllNonNull(studentToEdit, editedStudent);

        try {
            model.updateStudent(studentToEdit, editedStudent);
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

        studentToEdit = lastShownList.get(studentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = studentToEdit.getDashboard().getMilestoneList();

        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        try {
            editedStudent = createEditedStudent(studentToEdit, newTask);
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
    private Student createEditedStudent(Student studentToEdit, Task newTask)
            throws DuplicateTaskException, DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(studentToEdit, newTask);

        /* Get all the attributes of the student */
        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        Address address = studentToEdit.getAddress();
        Set<Tag> tags = studentToEdit.getTags();
        ProgrammingLanguage programmingLanguage = studentToEdit.getProgrammingLanguage();
        Favourite fav = studentToEdit.getFavourite();
        UniqueMilestoneList milestoneList = studentToEdit.getDashboard().getMilestoneList();
        UniqueHomeworkList homeworkList = studentToEdit.getDashboard().getHomeworkList();
        ProfilePicturePath profilePicturePath = studentToEdit.getProfilePicturePath();

        /* Get the components that needs to be modified */
        Milestone targetMilestone = milestoneList.get(milestoneIndex);
        UniqueTaskList targetTaskList = targetMilestone.getTaskList();

        /* Update the task list and milestone list to reflect the new task added */
        targetTaskList.add(newTask);
        Progress newProgress = new Progress(
                targetMilestone.getProgress().getTotalTasks() + 1,
                targetMilestone.getProgress().getNumCompletedTasks());
        Milestone newMilestone = new Milestone(targetMilestone.getDueDate(), targetTaskList,
                newProgress, targetMilestone.getDescription());
        milestoneList.setMilestone(targetMilestone, newMilestone);

        Dashboard dashboard = new Dashboard(milestoneList, homeworkList);

        return new Student(name, phone, email, address, programmingLanguage, tags, fav, dashboard, profilePicturePath);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
