package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATHEMATICS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSubjectCommand;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.EditPersonDescriptorBuilder;

//@@author TeyXinHui
public class AddSubjectCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubjectCommand.MESSAGE_USAGE);

    private AddSubjectCommandParser parser = new AddSubjectCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SUBJECT_BIOLOGY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddSubjectCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + SUBJECT_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + SUBJECT_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SUBJECT_NAME_DESC,
                Subject.MESSAGE_SUBJECT_NAME_CONSTRAINTS); // invalid subject name
        assertParseFailure(parser, "1" + INVALID_SUBJECT_GRADE_DESC,
                Subject.MESSAGE_SUBJECT_GRADE_CONSTRAINTS); // invalid subject grade

        // valid subject followed by invalid subject. The test case for invalid subject followed by valid subject
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1 " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS + " "
                        + "wqfqwf A1", Subject.MESSAGE_SUBJECT_NAME_CONSTRAINTS);
        assertParseFailure(parser, "1 " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS + " "
                        + "English aswfwef", Subject.MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SUBJECT + VALID_SUBJECT_HISTORY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withSubjects(VALID_SUBJECT_HISTORY).build();
        AddSubjectCommand expectedCommand = new AddSubjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_addDuplicateSubject_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withSubjects(VALID_SUBJECT_MATHEMATICS)
                .build();
        AddSubjectCommand expectedCommand = new AddSubjectCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateSubjectsInInput_throwsException() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SUBJECT + VALID_SUBJECT_MATHEMATICS + " "
                + VALID_SUBJECT_MATHEMATICS;

        String expectedMessage = "There should not be duplicate subject(s) assigned to student.";

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noSubjectInput_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SUBJECT;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withSubjects().build();
        AddSubjectCommand expectedCommand = new AddSubjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
//@@author
