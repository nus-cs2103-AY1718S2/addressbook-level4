package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedMovie.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalMovies.ABTM4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.movie.Duration;
import seedu.address.model.movie.MovieName;
import seedu.address.model.movie.Rating;
import seedu.address.model.movie.StartDate;
import seedu.address.testutil.Assert;
//@@author slothhy
public class XmlAdaptedMovieTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DURATION = "+651234";
    private static final String INVALID_RATING = " ";
    private static final String INVALID_STARTDATE = "03031231";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = ABTM4.getName().toString();
    private static final String VALID_DURATION = ABTM4.getDuration().toString();
    private static final String VALID_RATING = ABTM4.getRating().toString();
    private static final String VALID_STARTDATE = ABTM4.getStartDate().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ABTM4.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMovieDetails_returnsMovie() throws Exception {
        XmlAdaptedMovie movie = new XmlAdaptedMovie(ABTM4);
        assertEquals(ABTM4, movie.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedMovie movie =
                new XmlAdaptedMovie(INVALID_NAME, VALID_DURATION, VALID_RATING, VALID_STARTDATE, VALID_TAGS);
        String expectedMessage = MovieName.MESSAGE_MOVIENAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedMovie movie = new XmlAdaptedMovie(null, VALID_DURATION, VALID_RATING, VALID_STARTDATE,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MovieName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        XmlAdaptedMovie movie =
                new XmlAdaptedMovie(VALID_NAME, INVALID_DURATION, VALID_RATING, VALID_STARTDATE, VALID_TAGS);
        String expectedMessage = Duration.MESSAGE_DURATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedMovie movie = new XmlAdaptedMovie(VALID_NAME, null, VALID_RATING,
                VALID_STARTDATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        XmlAdaptedMovie movie =
                new XmlAdaptedMovie(VALID_NAME, VALID_DURATION, INVALID_RATING, VALID_STARTDATE, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        XmlAdaptedMovie movie = new XmlAdaptedMovie(VALID_NAME, VALID_DURATION, null,
                VALID_STARTDATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        XmlAdaptedMovie movie =
                new XmlAdaptedMovie(VALID_NAME, VALID_DURATION, VALID_RATING, INVALID_STARTDATE, VALID_TAGS);
        String expectedMessage = StartDate.MESSAGE_STARTDATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        XmlAdaptedMovie movie = new XmlAdaptedMovie(VALID_NAME, VALID_DURATION, VALID_RATING, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, movie::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedMovie movie =
                new XmlAdaptedMovie(VALID_NAME, VALID_DURATION, VALID_RATING, VALID_STARTDATE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, movie::toModelType);
    }

}
