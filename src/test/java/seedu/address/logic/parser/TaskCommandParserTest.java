//package seedu.address.logic.parser;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.DATE_TIME_DESC_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.DATE_TIME_DESC_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
//import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_MA2108_HOMEWORK;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_CS2010_QUIZ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2010;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA2108;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//
////import org.junit.Test;
//
////import seedu.address.logic.commands.TaskCommand;
//import seedu.address.model.activity.Activity;
//import seedu.address.model.activity.DateTime;
//import seedu.address.model.activity.Name;
//import seedu.address.model.activity.Remark;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.TaskBuilder;
//
//public class TaskCommandParserTest {
//    private TaskCommandParser parser = new TaskCommandParser();
//
//    //TODO: TEST
//    /**
//     * Test
//     */
//    public void parse_allFieldsPresent_success() {
//
//        Activity expectedActivity = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
//                .withRemark(VALID_REMARK_CS2010_QUIZ).withTags(VALID_TAG_CS2010).build();
//
//        // whitespace only preamble
//        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_FRIEND, new AddCommand(expectedActivity));
//
//        // multiple names - last name accepted
//        assertParseSuccess(parser, NAME_DESC_MA2108_HOMEWORK + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_FRIEND, new AddCommand(expectedActivity));
//
//        // multiple phones - last phone accepted
//        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_MA2108_HOMEWORK + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_FRIEND, new AddCommand(expectedActivity));
//
//        // multiple emails - last email accepted
//        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_MA2108_HOMEWORK + EMAIL_DESC_CS2010_QUIZ
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_FRIEND, new AddCommand(expectedActivity));
//
//        // multiple addresses - last address accepted
//        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_MA2108_HOMEWORK
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_FRIEND, new AddCommand(expectedActivity));
//
//        // multiple tags - all accepted
//        Activity expectedActivityMultipleTags = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ)
//                .withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
//                .withRemark(VALID_REMARK_CS2010_QUIZ)
//                .withTags(VALID_TAG_CS2010, VALID_TAG_MA2108).build();
//        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedActivityMultipleTags));
//    }
//
//    //TODO: TEST
//    /**
//     * Test
//     */
//    public void parse_optionalFieldsMissing_success() {
//        // zero tags
//        Activity expectedActivity = new TaskBuilder().withName(VALID_NAME_MA2108_HOMEWORK).withDateTime(VALID_DATE_TIME_MA2108_HOMEWORK)
//                .withRemark(VALID_REMARKS_MA2108_HOMEWORK).withTags().build();
//        assertParseSuccess(parser, NAME_DESC_MA2108_HOMEWORK + DATE_TIME_DESC_MA2108_HOMEWORK + EMAIL_DESC_MA2108_HOMEWORK + ADDRESS_DESC_MA2108_HOMEWORK,
//                new AddCommand(expectedActivity));
//    }
//
//    //TODO: TEST
//    /**
//     * Test
//     */
//    public void parse_compulsoryFieldMissing_failure() {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
//
//        // missing name prefix
//        assertParseFailure(parser, VALID_NAME_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ,
//                expectedMessage);
//
//        // missing phone prefix
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + VALID_DATE_TIME_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ,
//                expectedMessage);
//
//        // missing email prefix
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + VALID_EMAIL_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ,
//                expectedMessage);
//
//        // missing address prefix
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + VALID_REMARK_CS2010_QUIZ,
//                expectedMessage);
//
//        // all prefixes missing
//        assertParseFailure(parser, VALID_NAME_CS2010_QUIZ + VALID_DATE_TIME_CS2010_QUIZ + VALID_EMAIL_CS2010_QUIZ + VALID_REMARK_CS2010_QUIZ,
//                expectedMessage);
//    }
//
//    //TODO: TEST
//    /**
//     * Test
//     */
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, INVALID_NAME_DESC + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + INVALID_PHONE_DESC + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateTime.MESSAGE_DATETIME_CONSTRAINTS);
//
//        // invalid address
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + INVALID_ADDRESS_DESC
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Remark.MESSAGE_REMARK_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + ADDRESS_DESC_CS2010_QUIZ
//                + INVALID_TAG_DESC + VALID_TAG_CS2010, Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        // two invalid values, only first invalid value reported
//        assertParseFailure(parser, INVALID_NAME_DESC + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ + INVALID_ADDRESS_DESC,
//                Name.MESSAGE_NAME_CONSTRAINTS);
//
//        // non-empty preamble
//        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + EMAIL_DESC_CS2010_QUIZ
//                + ADDRESS_DESC_CS2010_QUIZ + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//    }
//}
