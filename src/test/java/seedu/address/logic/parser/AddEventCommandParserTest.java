package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Datetime;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_CHRISTMAS)
                .withDescription(VALID_DESCRIPTION_CHRISTMAS)
                .withLocation(VALID_LOCATION_CHRISTMAS).withDatetime(VALID_DATETIME_CHRISTMAS).build();

        // situation: multiple titles - latest title accepted
        assertParseSuccess(parser, TITLE_DESC_CNY + TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS
                + LOCATION_DESC_CHRISTMAS + DATETIME_DESC_CHRISTMAS, new AddEventCommand(expectedEvent));

        // situation: multiple description - latest description accepted
        assertParseSuccess(parser, TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CNY + DESCRIPTION_DESC_CHRISTMAS
                + LOCATION_DESC_CHRISTMAS + DATETIME_DESC_CHRISTMAS, new AddEventCommand(expectedEvent));

        // situation: multiple locations - latest location accepted
        assertParseSuccess(parser, TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS + LOCATION_DESC_CNY
                + LOCATION_DESC_CHRISTMAS + DATETIME_DESC_CHRISTMAS, new AddEventCommand(expectedEvent));

        // situation: multiple datetime - latest datetime accepted
        assertParseSuccess(parser, TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS + LOCATION_DESC_CHRISTMAS
                + DATETIME_DESC_CNY + DATETIME_DESC_CHRISTMAS, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_CNY)
                .withDescription(VALID_DESCRIPTION_CNY)
                .withLocation(VALID_LOCATION_CNY).withDatetime(VALID_DATETIME_CNY).build();

        assertParseSuccess(parser, TITLE_DESC_CNY + DESCRIPTION_DESC_CNY
                + LOCATION_DESC_CNY + DATETIME_DESC_CNY, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS
                + LOCATION_DESC_CHRISTMAS + DATETIME_DESC_CHRISTMAS, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TITLE_DESC_CHRISTMAS + VALID_DESCRIPTION_CHRISTMAS
                + LOCATION_DESC_CHRISTMAS + DATETIME_DESC_CHRISTMAS, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS
                + VALID_LOCATION_CHRISTMAS + DATETIME_DESC_CHRISTMAS, expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, TITLE_DESC_CHRISTMAS + DESCRIPTION_DESC_CHRISTMAS
                + LOCATION_DESC_CHRISTMAS + VALID_DATETIME_CHRISTMAS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_CHRISTMAS
                + DESCRIPTION_DESC_CHRISTMAS + LOCATION_DESC_CHRISTMAS
                + INVALID_DATETIME_DESC, Datetime.MESSAGE_DATETIME_CONSTRAINTS);

    }
}
