package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRAMMING_LANGUAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.testutil.EditMiscDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_KEY_AMY = "bdb76b";
    public static final String VALID_KEY_BOB = "f3f315";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_PROGRAMMING_LANGUAGE_AMY = "Java";
    public static final String VALID_PROGRAMMING_LANGUAGE_BOB = "Java";
    public static final String VALID_ALLERGIES_AMY = "milk";
    public static final String VALID_ALLERGIES_BOB = "nuts";
    public static final String VALID_NOKNAME_BOB = "Sam";
    public static final String VALID_NOKNAME_AMY = "Sam";
    public static final String VALID_NOKPHONE_AMY = "12345678";
    public static final String VALID_NOKPHONE_BOB = "87654321";
    public static final String VALID_REMARKS_AMY = "well behaved";
    public static final String VALID_REMARKS_BOB = "naughty";
    public static final String VALID_PROFILEPICTUREPATH_AMY = ProfilePicturePath.DEFAULT_PROFILE_PICTURE;
    public static final String VALID_PROFILEPICTUREPATH_BOB = "src/main/resource/view/test.png";
    public static final String PROFILEPICTUREPATH_DESC_AMY = PREFIX_PICTURE_PATH + VALID_PROFILEPICTUREPATH_AMY;
    public static final String PROFILEPICTUREPATH_DESC_BOB = PREFIX_PICTURE_PATH + VALID_PROFILEPICTUREPATH_BOB;
    public static final String INVALID_PROFILEPICTUREPATH_DESC = " " + PREFIX_PICTURE_PATH + "invalid";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_UNUSED = "unused"; // do not use this tag when creating a student

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String PROGRAMMING_LANGUAGE_DESC_AMY = " " + PREFIX_PROGRAMMING_LANGUAGE
            + VALID_PROGRAMMING_LANGUAGE_AMY;
    public static final String PROGRAMMING_LANGUAGE_DESC_BOB = " " + PREFIX_PROGRAMMING_LANGUAGE
            + VALID_PROGRAMMING_LANGUAGE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PROGRAMMING_LANGUAGE_DESC = " "
            + PREFIX_PROGRAMMING_LANGUAGE + "\t"; // '\t' not allowed in Programming Language

    public static final String ALLERGIES_DESC_AMY = " " + PREFIX_ALLERGIES + VALID_ALLERGIES_AMY;
    public static final String ALLERGIES_DESC_BOB = " " + PREFIX_ALLERGIES + VALID_ALLERGIES_BOB;
    public static final String NOKNAME_DESC_BOB = " " + PREFIX_NEXTOFKINNAME + VALID_NOKNAME_BOB;
    public static final String NOKNAME_DESC_AMY = " " + PREFIX_NEXTOFKINNAME + VALID_NOKNAME_AMY;
    public static final String NOKPHONE_DESC_BOB = " " + PREFIX_NEXTOFKINPHONE + VALID_NOKPHONE_BOB;
    public static final String NOKPHONE_DESC_AMY = " " + PREFIX_NEXTOFKINPHONE + VALID_NOKPHONE_AMY;
    public static final String REMARKS_DESC_BOB = " " + PREFIX_REMARKS + VALID_REMARKS_BOB;
    public static final String REMARKS_DESC_AMY = " " + PREFIX_REMARKS + VALID_REMARKS_AMY;



    public static final String INVALID_ALLERGIES_DESC = " " + PREFIX_ALLERGIES + " ";
    public static final String INVALID_NOKNAME_DESC = " " + PREFIX_NEXTOFKINNAME + " ";
    public static final String INVALID_NOKPHONE_DESC = " " + PREFIX_NEXTOFKINPHONE + "hello";
    public static final String INVALID_REMARKS_DESC = " " + PREFIX_REMARKS + " ";



    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    public static final EditMiscCommand.EditMiscDescriptor DESC_MISC_AMY;
    public static final EditMiscCommand.EditMiscDescriptor DESC_MISC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withKey(VALID_KEY_AMY).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withProgrammingLanguage(VALID_PROGRAMMING_LANGUAGE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withKey(VALID_KEY_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withProgrammingLanguage(VALID_PROGRAMMING_LANGUAGE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .build();

        DESC_MISC_AMY = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_AMY)
                .withNextOfKinName(VALID_NOKNAME_AMY).withNextOfKinPhone(VALID_NOKPHONE_AMY)
                .withRemarks(VALID_REMARKS_AMY).build();
        DESC_MISC_BOB = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinName(VALID_NOKNAME_BOB).withNextOfKinPhone(VALID_NOKPHONE_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();

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
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered student list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Deletes the first student in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstStudent(Model model) {
        Student firstStudent = model.getFilteredStudentList().get(0);
        try {
            model.deleteStudent(firstStudent);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("Student in filtered list must exist in model.", pnfe);
        } catch (LessonNotFoundException lnfe) {
            throw new AssertionError("Lesson in Schedule to be deleted must exist in model.", lnfe);
        } catch (DuplicateLessonException dle) {
            throw new AssertionError("Lesson in schedule must be unique.", dle);
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new AssertionError("Lesson in filtered list must not be clashing.", iltse);
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
