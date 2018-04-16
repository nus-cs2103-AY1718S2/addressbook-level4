package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_MAC_LINUX;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_WINDOWS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_NO_FILE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JERSEY_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JERSEY_NUMBER_DESC_17;
import static seedu.address.logic.commands.CommandTestUtil.JERSEY_NUMBER_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_MIDFILED;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_STRIKER;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_0;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_MAC_LINUX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_WINDOWS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_17;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_STRIKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_0;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import com.sun.javafx.PlatformUtil;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Avatar.setUpPlaceholderForTest();
        Person toAdd = AMY;
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND  + " " + RATING_DESC_0 + "   " + POSITION_DESC_STRIKER + "   "
                + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND  + " " + RATING_DESC_0 + "   " + POSITION_DESC_STRIKER + "   "
                + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + TAG_DESC_FRIEND  + " " + RATING_DESC_0 + "   " + POSITION_DESC_STRIKER + "   "
                + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17 + RATING_DESC_1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPhone(UNSPECIFIED_FIELD).withTags().withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + RATING_DESC_0
                + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(UNSPECIFIED_FIELD).withTags().withRating(VALID_RATING_0)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RATING_DESC_0
                + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        /* Case: missing rating -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags().withRating(UNSPECIFIED_FIELD)
                .withPosition(VALID_POSITION_STRIKER).withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandSuccess(command, toAdd);

        if (PlatformUtil.isWindows()) {
            toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                    .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).withAvatar(VALID_AVATAR_WINDOWS)
                    .withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0).withJerseyNumber(VALID_JERSEY_NUMBER_17)
                            .build();
            command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + EMAIL_DESC_BOB + AVATAR_WINDOWS
                    + PHONE_DESC_AMY + ADDRESS_DESC_AMY + RATING_DESC_0 + JERSEY_NUMBER_DESC_17 + POSITION_DESC_STRIKER
                    + TAG_DESC_FRIEND;
            assertCommandSuccess(command, toAdd);
        } else if (PlatformUtil.isMac() || PlatformUtil.isLinux()) {
            toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                    .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).withAvatar(VALID_AVATAR_MAC_LINUX)
                    .withTags(VALID_TAG_FRIEND).withRating(VALID_RATING_0).withJerseyNumber(VALID_JERSEY_NUMBER_17)
                    .build();
            command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + EMAIL_DESC_BOB + AVATAR_MAC_LINUX
                    + PHONE_DESC_AMY + ADDRESS_DESC_AMY + RATING_DESC_0 + JERSEY_NUMBER_DESC_17 + POSITION_DESC_STRIKER
                    + TAG_DESC_FRIEND;
            assertCommandSuccess(command, toAdd);
        }

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + RATING_DESC_0
                + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + RATING_DESC_0
                + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        //@@author lithiumlkid
        /* Case: invalid rating -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + INVALID_RATING_DESC + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Rating.MESSAGE_RATING_CONSTRAINTS);

        /* Case: invalid position -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + RATING_DESC_0 + INVALID_POSITION_DESC + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Position.MESSAGE_POSITION_CONSTRAINTS);

        /* Case: invalid jersey number -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + RATING_DESC_0 + POSITION_DESC_STRIKER + INVALID_JERSEY_NUMBER_DESC;
        assertCommandFailure(command, JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS);

        /* Case: invalid jersey avatar -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2 + INVALID_AVATAR_NO_FILE;
        assertCommandFailure(command, AddCommand.MESSAGE_FILE_NOT_FOUND);

        /* Case: invalid jersey avatar -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2 + INVALID_AVATAR_TYPE;
        assertCommandFailure(command, Avatar.MESSAGE_AVATAR_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        // TODO: place holder for success message, change to proper assert method
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
