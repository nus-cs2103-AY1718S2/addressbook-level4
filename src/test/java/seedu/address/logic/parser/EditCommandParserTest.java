package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CCA_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.CCA_DESC_DANCE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_OF_FRIENDSHIP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_OF_FRIENDSHIP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_OF_FRIENDSHIP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_DANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_OF_FRIENDSHIP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_OF_FRIENDSHIP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String CCA_EMPTY = " " + PREFIX_CCA;
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_BIRTHDAY_DESC, Birthday
                .MESSAGE_BIRTHDAY_CONSTRAINTS); // invalid birthday
        assertParseFailure(parser, "1" + INVALID_LEVEL_OF_FRIENDSHIP_DESC,
                LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS); //invalid level of friendship
        assertParseFailure(parser, "1" + INVALID_UNIT_NUMBER_DESC, UnitNumber
                .MESSAGE_UNIT_NUMBER_CONSTRAINTS); // invalid unit number
        assertParseFailure(parser, "1" + INVALID_CCA_DESC, Cca.MESSAGE_CCA_CONSTRAINTS); // invalid cca
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid birthday
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + BIRTHDAY_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_BIRTHDAY_DESC + VALID_PHONE_AMY
                        + VALID_LEVEL_OF_FRIENDSHIP_AMY + INVALID_UNIT_NUMBER_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + BIRTHDAY_DESC_AMY + UNIT_NUMBER_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_AMY
                + NAME_DESC_AMY + CCA_DESC_DANCE + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // level of friendship
        userInput = targetIndex.getOneBased() + LEVEL_OF_FRIENDSHIP_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // unit number
        userInput = targetIndex.getOneBased() + UNIT_NUMBER_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withUnitNumber(VALID_UNIT_NUMBER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ccas
        userInput = targetIndex.getOneBased() + CCA_DESC_DANCE;
        descriptor = new EditPersonDescriptorBuilder().withCcas(VALID_CCA_DANCE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB
                + CCA_DESC_BADMINTON + BIRTHDAY_DESC_BOB + UNIT_NUMBER_DESC_BOB + LEVEL_OF_FRIENDSHIP_DESC_BOB
                + CCA_DESC_DANCE + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB)
                .withUnitNumber(VALID_UNIT_NUMBER_BOB).withCcas(VALID_CCA_BADMINTON, VALID_CCA_DANCE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_BOB + INVALID_PHONE_DESC + UNIT_NUMBER_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB).withUnitNumber(VALID_UNIT_NUMBER_BOB)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
