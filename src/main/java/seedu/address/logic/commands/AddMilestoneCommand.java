package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Favourite;
import seedu.address.model.student.miscellaneousInfo.ProfilePicturePath;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.UniqueHomeworkList;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

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

    private final Index index;
    private final Milestone newMilestone;

    private Student studentToEdit;
    private Student editedStudent;

    public AddMilestoneCommand(Index index, Milestone newMilestone) {
        requireAllNonNull(index, newMilestone);

        this.newMilestone = newMilestone;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireAllNonNull(studentToEdit, editedStudent);

        try {
            model.updateStudent(studentToEdit, editedStudent);
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
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToEdit = lastShownList.get(index.getZeroBased());

        try {
            editedStudent = createEditedStudent(studentToEdit, newMilestone);
        } catch (DuplicateMilestoneException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MILESTONE);
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the new Milestone added to its Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Milestone newMilestone)
            throws DuplicateMilestoneException {
        requireAllNonNull(studentToEdit, newMilestone);

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

        milestoneList.add(newMilestone);

        Dashboard dashboard = new Dashboard(milestoneList, homeworkList);

        return new Student(name, phone, email, address, programmingLanguage, tags, fav, dashboard, profilePicturePath);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
