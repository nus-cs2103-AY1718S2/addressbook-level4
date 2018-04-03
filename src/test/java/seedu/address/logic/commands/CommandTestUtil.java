package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_PICTURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.PositionContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_CURRENT_POSITION_AMY = "Software Engineer";
    public static final String VALID_CURRENT_POSITION_BOB = "Marketing Intern";
    public static final String VALID_COMPANY_AMY = "Amy Technologies";
    public static final String VALID_COMPANY_BOB = "Bob Consultants";
    public static final String VALID_PROFILE_PICTURE_AMY = "./src/test/data/images/amy.jpeg";
    public static final String VALID_PROFILE_PICTURE_BOB = "./src/test/data/images/bob.jpeg";
    public static final String VALID_SKILL_HUSBAND = "husband";
    public static final String VALID_SKILL_FRIEND = "friend";
    public static final String VALID_USERNAME_USER = "John";
    public static final String VALID_PASSWORD_USER = "1234";
    public static final String VALID_POSITION_DEVELOPER_INTERN = "Developer Intern";
    public static final String VALID_POSITION_INTERN = "Intern";
    public static final String VALID_TEAM_DEVELOPER_INTERN = "Web Development";
    public static final String VALID_TEAM_INTERN = "Human Resources";
    public static final String VALID_LOCATION_DEVELOPER_INTERN = "Jakarta, Indonesia";
    public static final String VALID_LOCATION_INTERN = "Kuala Lampur, Malaysia";
    public static final String VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN = "2";
    public static final String VALID_NUMBER_OF_POSITIONS_INTERN = "5";
    public static final String VALID_SKILL_JAVASCRIPT = "JavaScript";
    public static final String VALID_SKILL_ALGORITHMS = "Algorithms";
    public static final String VALID_SKILL_EXCEL = "Excel";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String CURRENT_POSITION_DESC_AMY = " " + PREFIX_CURRENT_POSITION + VALID_CURRENT_POSITION_AMY;
    public static final String CURRENT_POSITION_DESC_BOB = " " + PREFIX_CURRENT_POSITION + VALID_CURRENT_POSITION_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String PROFILE_PICTURE_DESC_AMY = " " + PREFIX_PROFILE_PICTURE + VALID_PROFILE_PICTURE_AMY;
    public static final String PROFILE_PICTURE_DESC_BOB = " " + PREFIX_PROFILE_PICTURE + VALID_PROFILE_PICTURE_BOB;
    public static final String SKILL_DESC_FRIEND = " " + PREFIX_SKILL + VALID_SKILL_FRIEND;
    public static final String SKILL_DESC_HUSBAND = " " + PREFIX_SKILL + VALID_SKILL_HUSBAND;
    public static final String SKILL_DESC_JAVASCRIPT = " " + PREFIX_SKILL + VALID_SKILL_JAVASCRIPT;
    public static final String SKILL_DESC_ALGORITHMS = " " + PREFIX_SKILL + VALID_SKILL_ALGORITHMS;
    public static final String SKILL_DESC_EXCEL = " " + PREFIX_SKILL + VALID_SKILL_EXCEL;
    public static final String USERNAME_DESC_USER = " " + PREFIX_USERNAME + VALID_USERNAME_USER;
    public static final String PASSWORD_DESC_USER = " " + PREFIX_PASSWORD + VALID_PASSWORD_USER;
    public static final String POSITION_DESC_DEVELOPER_INTERN = " " + PREFIX_POSITION
            + VALID_POSITION_DEVELOPER_INTERN;
    public static final String POSITION_DESC_INTERN = " " + PREFIX_POSITION + VALID_POSITION_INTERN;
    public static final String TEAM_DESC_DEVELOPER_INTERN = " " + PREFIX_TEAM + VALID_TEAM_DEVELOPER_INTERN;
    public static final String TEAM_DESC_INTERN = " " + PREFIX_TEAM + VALID_TEAM_INTERN;
    public static final String LOCATION_DESC_DEVELOPER_INTERN = " " + PREFIX_LOCATION
            + VALID_LOCATION_DEVELOPER_INTERN;
    public static final String LOCATION_DESC_INTERN = " " + PREFIX_LOCATION
            + VALID_LOCATION_INTERN;
    public static final String NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN = " " + PREFIX_NUMBER_OF_POSITIONS
            + VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN;
    public static final String NUMBER_OF_POSITIONS_DESC_INTERN = " " + PREFIX_NUMBER_OF_POSITIONS
            + VALID_NUMBER_OF_POSITIONS_INTERN;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_CURRENT_POSITION_DESC =
            " " + PREFIX_CURRENT_POSITION; // empty string not allowed for current position
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "G@ogle"; // '@' not allowed in companies
    // .jpx not allowed in profile picture
    public static final String INVALID_PROFILE_PICTURE_DESC = " " + PREFIX_PROFILE_PICTURE + "Emy.jpx";
    public static final String INVALID_SKILL_DESC = " " + PREFIX_SKILL + "hubby dubby"; // '*' not allowed in skills
    // '$' not allowed in positions
    public static final String INVALID_POSITION_DESC = " " + PREFIX_POSITION + "Enginee$";
    public static final String INVALID_TEAM_DESC = " " + PREFIX_TEAM; // empty string not allowed for teams
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION; // empty string not allowed for locations
    // alphabets not allowed in numberOfPositions
    public static final String INVALID_NUMBER_OF_POSITIONS_DESC = " " + PREFIX_NUMBER_OF_POSITIONS + " a12";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_TITLE_INTERVIEW1 = "Interview1";
    public static final String VALID_TITLE_INTERVIEW2 = "Interview2";
    public static final String VALID_START_DATE_TIME_INTERVIEW1 = "2018-05-01 10:00";
    public static final String VALID_START_DATE_TIME_INTERVIEW2 = "2018-05-03 10:00";
    public static final String VALID_END_DATE_TIME_INTERVIEW1 = "2018-05-01 12:00";
    public static final String VALID_END_DATE_TIME_INTERVIEW2 = "2018-05-03 12:00";

    public static final String TITLE_DESC_INTERVIEW1 = " " + PREFIX_TITLE + VALID_TITLE_INTERVIEW1;
    public static final String TITLE_DESC_INTERVIEW2 = " " + PREFIX_TITLE + VALID_TITLE_INTERVIEW2;
    public static final String START_DATE_TIME_DESC_INTERVIEW1 = " " + PREFIX_START_DATE_TIME
            + VALID_START_DATE_TIME_INTERVIEW1;
    public static final String START_DATE_TIME_DESC_INTERVIEW2 = " " + PREFIX_START_DATE_TIME
            + VALID_START_DATE_TIME_INTERVIEW2;
    public static final String END_DATE_TIME_DESC_INTERVIEW1 = " " + PREFIX_END_DATE_TIME
            + VALID_END_DATE_TIME_INTERVIEW1;
    public static final String END_DATE_TIME_DESC_INTERVIEW2 = " " + PREFIX_END_DATE_TIME
            + VALID_END_DATE_TIME_INTERVIEW2;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // can not be blank
    public static final String INVALID_START_DATE_TIME = " " + PREFIX_START_DATE_TIME + "26/03/2018 20:00";
    // wrong format
    public static final String INVALID_END_DATE_TIME = " " + PREFIX_END_DATE_TIME + "26/03/2018"; // missing time

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withCurrentPosition(VALID_CURRENT_POSITION_AMY).withCompany(VALID_COMPANY_AMY)
                .withProfilePicture(VALID_PROFILE_PICTURE_AMY).withSkills(VALID_SKILL_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCurrentPosition(VALID_CURRENT_POSITION_BOB).withCompany(VALID_COMPANY_BOB)
                .withProfilePicture(VALID_PROFILE_PICTURE_BOB).withSkills(VALID_SKILL_HUSBAND, VALID_SKILL_FRIEND)
                .build();
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
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the job at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showJobAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredJobList().size());

        Job job = model.getFilteredJobList().get(targetIndex.getZeroBased());
        final String[] splitPosition = job.getPosition().value.split("\\s+");
        model.updateFilteredJobList(new PositionContainsKeywordsPredicate(Arrays.asList(splitPosition[0])));

        assertEquals(1, model.getFilteredJobList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
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
