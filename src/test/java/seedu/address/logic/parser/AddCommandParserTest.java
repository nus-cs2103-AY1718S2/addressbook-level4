package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ARTEMIS;
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
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_ARTEMIS)
                .withAuthors(VALID_AUTHOR_ARTEMIS)
                .withCategories(VALID_CATEGORY_ARTEMIS)
                .withDescription(VALID_DESCRIPTION_ARTEMIS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, new AddCommand(expectedBook));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_BABYLON + TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, new AddCommand(expectedBook));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS + CATEGORY_DESC_ARTEMIS
                + DESCRIPTION_DESC_BABYLON + DESCRIPTION_DESC_ARTEMIS, new AddCommand(expectedBook));

        // multiple authors
        Book expectedBookMultipleAuthors = new BookBuilder().withTitle(VALID_TITLE_ARTEMIS)
                .withAuthors(VALID_AUTHOR_ARTEMIS, VALID_AUTHOR_BABYLON)
                .withCategories(VALID_CATEGORY_ARTEMIS)
                .withDescription(VALID_DESCRIPTION_ARTEMIS).build();
        assertParseSuccess(parser, TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS + AUTHOR_DESC_BABYLON
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, new AddCommand(expectedBookMultipleAuthors));

        // multiple categories
        Book expectedBookMultipleCategories = new BookBuilder().withTitle(VALID_TITLE_ARTEMIS)
                .withAuthors(VALID_AUTHOR_ARTEMIS, VALID_AUTHOR_BABYLON)
                .withCategories(VALID_CATEGORY_ARTEMIS, VALID_CATEGORY_BABYLON)
                .withDescription(VALID_DESCRIPTION_ARTEMIS).build();
        assertParseSuccess(parser, TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS + CATEGORY_DESC_BABYLON
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, new AddCommand(expectedBookMultipleCategories));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_ARTEMIS + AUTHOR_DESC_ARTEMIS
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS
                + CATEGORY_DESC_ARTEMIS + VALID_DESCRIPTION_ARTEMIS, expectedMessage);

        // missing author prefix
        assertParseFailure(parser, TITLE_DESC_ARTEMIS + VALID_AUTHOR_ARTEMIS
                + CATEGORY_DESC_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, TITLE_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS
                + VALID_CATEGORY_ARTEMIS + DESCRIPTION_DESC_ARTEMIS, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_ARTEMIS + VALID_AUTHOR_ARTEMIS
                + VALID_CATEGORY_ARTEMIS + VALID_DESCRIPTION_ARTEMIS, expectedMessage);
    }
}
