package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MOVIENAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MOVIENAME_DESC_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.MOVIENAME_DESC_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_COMEDY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SUPERHERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOVIENAME_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOVIENAME_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INCREDIBLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_MARVEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMEDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUPERHERO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddMovieCommand;
import seedu.address.model.movie.Duration;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.MovieName;
import seedu.address.model.movie.Rating;
import seedu.address.model.movie.StartDate;
import seedu.address.testutil.MovieBuilder;
//@@author slothhy
public class AddMovieCommandParserTest {
    private AddMovieCommandParser parser = new AddMovieCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Movie expectedMovie = new MovieBuilder().withMovieName(VALID_MOVIENAME_MARVEL)
                .withDuration(VALID_DURATION_MARVEL).withRating(VALID_RATING_MARVEL)
                .withStartDate(VALID_STARTDATE_MARVEL).withTags(VALID_TAG_SUPERHERO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MOVIENAME_DESC_MARVEL
                        + DURATION_DESC_MARVEL + RATING_DESC_MARVEL + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO,
                new AddMovieCommand(expectedMovie));

        // multiple movie names - last movie name accepted
        assertParseSuccess(parser, MOVIENAME_DESC_INCREDIBLES + MOVIENAME_DESC_MARVEL
                        + DURATION_DESC_MARVEL + RATING_DESC_MARVEL + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO,
                new AddMovieCommand(expectedMovie));

        // multiple durations - last duration accepted
        assertParseSuccess(parser, MOVIENAME_DESC_MARVEL + DURATION_DESC_INCREDIBLES
                        + DURATION_DESC_MARVEL + RATING_DESC_MARVEL + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO,
                new AddMovieCommand(expectedMovie));

        // multiple ratings - last rating accepted
        assertParseSuccess(parser, MOVIENAME_DESC_MARVEL + DURATION_DESC_MARVEL
                        + RATING_DESC_INCREDIBLES + RATING_DESC_MARVEL + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO,
                new AddMovieCommand(expectedMovie));

        // multiple startDates - last startDate accepted
        assertParseSuccess(parser, MOVIENAME_DESC_MARVEL + DURATION_DESC_MARVEL
                        + RATING_DESC_MARVEL + STARTDATE_DESC_INCREDIBLES + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO,
                new AddMovieCommand(expectedMovie));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMovieCommand.MESSAGE_USAGE);

        // missing movieName prefix
        assertParseFailure(parser, VALID_MOVIENAME_INCREDIBLES + DURATION_DESC_INCREDIBLES
                + RATING_DESC_INCREDIBLES + STARTDATE_DESC_INCREDIBLES + TAG_DESC_COMEDY, expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, MOVIENAME_DESC_INCREDIBLES + VALID_DURATION_INCREDIBLES
                + RATING_DESC_INCREDIBLES + STARTDATE_DESC_INCREDIBLES + TAG_DESC_COMEDY, expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, MOVIENAME_DESC_INCREDIBLES + DURATION_DESC_INCREDIBLES
                + VALID_RATING_INCREDIBLES + STARTDATE_DESC_INCREDIBLES + TAG_DESC_COMEDY, expectedMessage);

        // missing startDate prefix
        assertParseFailure(parser, MOVIENAME_DESC_INCREDIBLES + DURATION_DESC_INCREDIBLES
                + RATING_DESC_INCREDIBLES + VALID_STARTDATE_INCREDIBLES + TAG_DESC_COMEDY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MOVIENAME_INCREDIBLES + VALID_DURATION_INCREDIBLES
                + VALID_RATING_INCREDIBLES + VALID_STARTDATE_INCREDIBLES + VALID_TAG_COMEDY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid movieName
        assertParseFailure(parser, INVALID_MOVIENAME_DESC + DURATION_DESC_MARVEL + RATING_DESC_MARVEL
                + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO, MovieName.MESSAGE_MOVIENAME_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, MOVIENAME_DESC_MARVEL + INVALID_DURATION_DESC + RATING_DESC_MARVEL
                + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO, Duration.MESSAGE_DURATION_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, MOVIENAME_DESC_MARVEL + DURATION_DESC_MARVEL + INVALID_RATING_DESC
                + STARTDATE_DESC_MARVEL + TAG_DESC_SUPERHERO, Rating.MESSAGE_RATING_CONSTRAINTS);

        // invalid startDate
        assertParseFailure(parser, MOVIENAME_DESC_MARVEL + DURATION_DESC_MARVEL + RATING_DESC_MARVEL
                + INVALID_STARTDATE_DESC + TAG_DESC_SUPERHERO, StartDate.MESSAGE_STARTDATE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MOVIENAME_DESC + DURATION_DESC_MARVEL + RATING_DESC_MARVEL
                + INVALID_STARTDATE_DESC + TAG_DESC_SUPERHERO, MovieName.MESSAGE_MOVIENAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MOVIENAME_DESC_MARVEL + DURATION_DESC_MARVEL
                        + RATING_DESC_MARVEL + STARTDATE_DESC_MARVEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMovieCommand.MESSAGE_USAGE));
    }
}
