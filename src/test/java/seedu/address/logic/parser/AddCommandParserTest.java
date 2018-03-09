package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_ONE)
                .withAuthors(VALID_AUTHOR_ONE)
                .withCategories(VALID_CATEGORY_ONE)
                .withDescription(VALID_DESCRIPTION_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_ONE + AUTHOR_DESC_ONE
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, new AddCommand(expectedBook));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_TWO + TITLE_DESC_ONE + AUTHOR_DESC_ONE
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, new AddCommand(expectedBook));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, TITLE_DESC_ONE + AUTHOR_DESC_ONE + CATEGORY_DESC_ONE
                + DESCRIPTION_DESC_TWO + DESCRIPTION_DESC_ONE, new AddCommand(expectedBook));

        // multiple authors
        Book expectedBookMultipleAuthors = new BookBuilder().withTitle(VALID_TITLE_ONE)
                .withAuthors(VALID_AUTHOR_ONE, VALID_AUTHOR_TWO)
                .withCategories(VALID_CATEGORY_ONE)
                .withDescription(VALID_DESCRIPTION_ONE).build();
        assertParseSuccess(parser, TITLE_DESC_ONE + AUTHOR_DESC_ONE + AUTHOR_DESC_TWO
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, new AddCommand(expectedBookMultipleAuthors));

        // multiple categories
        Book expectedBookMultipleCategories = new BookBuilder().withTitle(VALID_TITLE_ONE)
                .withAuthors(VALID_AUTHOR_ONE, VALID_AUTHOR_TWO)
                .withCategories(VALID_CATEGORY_ONE, VALID_CATEGORY_TWO)
                .withDescription(VALID_DESCRIPTION_ONE).build();
        assertParseSuccess(parser, TITLE_DESC_ONE + AUTHOR_DESC_ONE + CATEGORY_DESC_TWO
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, new AddCommand(expectedBookMultipleCategories));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_ONE + AUTHOR_DESC_ONE
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TITLE_DESC_ONE + AUTHOR_DESC_ONE
                + CATEGORY_DESC_ONE + VALID_DESCRIPTION_ONE, expectedMessage);

        // missing author prefix
        assertParseFailure(parser, TITLE_DESC_ONE + VALID_AUTHOR_ONE
                + CATEGORY_DESC_ONE + DESCRIPTION_DESC_ONE, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, TITLE_DESC_ONE + AUTHOR_DESC_ONE
                + VALID_CATEGORY_ONE + DESCRIPTION_DESC_ONE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_ONE + VALID_AUTHOR_ONE
                + VALID_CATEGORY_ONE + VALID_DESCRIPTION_ONE, expectedMessage);
    }
}
