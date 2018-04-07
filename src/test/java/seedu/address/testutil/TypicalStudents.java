package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRAMMING_LANGUAGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRAMMING_LANGUAGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withKey("c5daab").withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withProgrammingLanguage("Java").build();

    public static final Student BENSON = buildStudentWithFilledDashboard();

    public static final Student CARL = new StudentBuilder().withKey("8e90ba").withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withProgrammingLanguage("Java").build();
    public static final Student DANIEL = new StudentBuilder().withKey("dd5605").withName("Daniel Meier")
            .withPhone("87652533").withEmail("cornelia@example.com")
            .withAddress("10th street").withProgrammingLanguage("Java").build();
    public static final Student ELLE = new StudentBuilder().withKey("abbcfd").withName("Elle Meyer")
            .withPhone("9482224").withEmail("werner@example.com").withAddress("michegan ave")
            .withProgrammingLanguage("Java").build();
    public static final Student FIONA = new StudentBuilder().withKey("9d2b20").withName("Fiona Kunz")
            .withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo")
            .withProgrammingLanguage("Java").build();
    public static final Student GEORGE = new StudentBuilder().withKey("4e0965").withName("George Best")
            .withPhone("9482442").withEmail("anna@example.com").withAddress("4th street")
            .withProgrammingLanguage("Java").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withKey("bbcd69").withName("Hoon Meier")
            .withPhone("8482424").withEmail("stefan@example.com")
            .withAddress("little india").withProgrammingLanguage("Java").build();
    public static final Student IDA = new StudentBuilder().withKey("5dc953").withName("Ida Mueller")
            .withPhone("8482131").withEmail("hans@example.com")
            .withAddress("chicago ave").withProgrammingLanguage("Java").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withKey("f20af3").withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withProgrammingLanguage((VALID_PROGRAMMING_LANGUAGE_AMY)).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder().withKey("c81b30").withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withProgrammingLanguage(VALID_PROGRAMMING_LANGUAGE_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches name MEIER

    public static final String TAG_MATCHING_OWESMONEY = "owesMoney"; // A keyword that matches tag owesMoney

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            try {
                ab.addStudent(student);
            } catch (DuplicateStudentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Builds and return a student with 1 milestone and 1 task in the dashboard
     */
    private static Student buildStudentWithFilledDashboard() {
        Student student;
        try {
            student = new StudentBuilder().withKey("558a24").withName("Benson Meier")
                    .withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@example.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").withProgrammingLanguage("Java")
                    .withNewMilestone(new Milestone(new Date("31/12/2018 23:59"), "Arrays"))
                    .withNewTask(Index.fromOneBased(1), new Task("Learn Array syntax", "Refer to textbook"))
                    .build();
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("Should not have duplicated milestone");
        } catch (DuplicateTaskException e) {
            throw new AssertionError("Should not have duplicated task");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Should not have missing milestone");
        }

        return student;
    }
}
