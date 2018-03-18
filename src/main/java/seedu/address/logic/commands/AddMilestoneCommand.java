package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a Milestone to a Student's Dashboard
 */
public class AddMilestoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a milestone to a Student's Dashboard."
            + " Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_DATE + "DATE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD +" "
            + PREFIX_INDEX + "1 "
            + PREFIX_DATE + "17/05/2018 23:59 "
            + PREFIX_DESCRIPTION + "Learn Arrays";

    public static final String MESSAGE_SUCCESS = "Milestone added to Student's Dashboard: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index index;
    private final Milestone newMilestone;

    private Student studentToEdit;
    private Student editedStudent;

    public AddMilestoneCommand(Index index, Milestone newMilestone) {
        requireNonNull(newMilestone);
        this.newMilestone = newMilestone;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(studentToEdit, editedStudent);

        try {
            model.updateStudent(studentToEdit, editedStudent);
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedStudent));
    }

    @Override
    public void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToEdit = lastShownList.get(index.getZeroBased());
        editedStudent = createEditedStudent(studentToEdit, newMilestone);
    }

    /**
     * Creates and return a copy of {@code Student} with the new Milestone added to its Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Milestone newMilestone) {
        assert (studentToEdit != null) && (newMilestone != null);

        /* Get all the attributes of the student */
        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        Address address = studentToEdit.getAddress();
        Set<Tag> tags = studentToEdit.getTags();
        ProgrammingLanguage programmingLanguage = studentToEdit.getProgrammingLanguage();
        Favourite fav = studentToEdit.getFavourite();
        List<Milestone> milestoneList = studentToEdit.getDashboard().getMilestoneList().size() > 0
                ? copyMilestoneList(studentToEdit.getDashboard().getMilestoneList())
                : new ArrayList<>();
        List<Homework> homeworkList = studentToEdit.getDashboard().getHomeworkList().size() > 0
                ? copyHomeworkList(studentToEdit.getDashboard().getHomeworkList())
                : new ArrayList<>();

        milestoneList.add(newMilestone);
        Dashboard dashboard = new Dashboard(milestoneList, homeworkList);

        return new Student(name, phone, email, address, programmingLanguage, tags, fav, dashboard);
    }

    /**
     * Creates and returns a deep copy of a list of Milestone.
     */
    private List<Milestone> copyMilestoneList(List<Milestone> listToCopy) {
        return listToCopy.stream().map(Milestone::copyMilestone).collect(Collectors.toList());
    }

    /**
     * Creates and returns a deep copy of a list of Homework.
     */
    private List<Homework> copyHomeworkList(List<Homework> listToCopy) {
        return listToCopy.stream().map(Homework::copyHomework).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
