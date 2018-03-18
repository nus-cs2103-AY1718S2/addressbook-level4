package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
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
import seedu.address.model.student.dashboard.Homework;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a Task to a Milestone
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to a Milestone in a Student's Dashboard."
            + " Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX"
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX"
            + PREFIX_NAME + "NAME OF TASK"
            + PREFIX_DESCRIPTION + "DESCRIPTION"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2"
            + PREFIX_NAME + "Learn syntax of arrays "
            + PREFIX_DESCRIPTION + "Learn declaration and initialisation of arrays";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index studentIndex;
    private final Index milestoneIndex;
    private final Task newTask;

    private Student studentToEdit;
    private Student editedStudent;

    public AddTaskCommand(Index studentIndex, Index milestoneIndex, Task newTask) {
        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(studentToEdit, editedStudent);

        try {
            model.updateStudent(studentToEdit, editedStudent);
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
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

        List<Milestone> milestoneList =
                lastShownList.get(studentIndex.getZeroBased()).getDashboard().getMilestoneList();
        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        studentToEdit = lastShownList.get(studentIndex.getZeroBased());
        editedStudent = createEditedStudent(studentToEdit, newTask);
    }

    /**
     * Creates and return a copy of {@code Student} with the new task added to its targeted milestone in the Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Task newTask) {
        assert (studentToEdit != null) && (newTask != null);

        /* Get all the attributes of the student */
        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        Address address = studentToEdit.getAddress();
        Set<Tag> tags = studentToEdit.getTags();
        ProgrammingLanguage programmingLanguage = studentToEdit.getProgrammingLanguage();
        Favourite fav = studentToEdit.getFavourite();
        List<Milestone> milestoneList = studentToEdit.getDashboard().getMilestoneList().size() > 0
                ? Milestone.copyMilestoneList(studentToEdit.getDashboard().getMilestoneList())
                : new ArrayList<>();
        List<Homework> homeworkList = studentToEdit.getDashboard().getHomeworkList().size() > 0
                ? Homework.copyHomeworkList(studentToEdit.getDashboard().getHomeworkList())
                : new ArrayList<>();

        milestoneList.get(milestoneIndex.getZeroBased()).getTaskList().add(newTask);
        Dashboard dashboard = new Dashboard(milestoneList, homeworkList);

        return new Student(name, phone, email, address, programmingLanguage, tags, fav, dashboard);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
