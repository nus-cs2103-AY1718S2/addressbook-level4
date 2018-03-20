package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Phone;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.tag.Tag;

/**
 * Adds a lesson to the schedule for a student in the address book.
 */
public class AddLessonCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addLesson";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the schedule, "
            + "assuming student is in address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "10:30 ";

    public static final String MESSAGE_SUCCESS = "New lesson added";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This lesson already exists in the address book";

    private final Name name;
    private final Time startTime;
    private final Time endTime;
    private final Student student;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Name name, Time startTime, Time endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;

        this.student = new StudentBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("85355255")
                .withTags("friends").withProgrammingLanguage("Java").build();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        model.addLesson(student, startTime, endTime);
        return new CommandResult(String.format(MESSAGE_SUCCESS, name));


    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
