package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.movie.Duration;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.MovieName;
import seedu.address.model.movie.Rating;
import seedu.address.model.movie.StartDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
//@@author slothhy
/**
 * A utility class to help with building Movie objects.
 */
public class MovieBuilder {

    public static final String DEFAULT_MOVIENAME = "The Incredibles";
    public static final String DEFAULT_DURATION = "90";
    public static final String DEFAULT_RATING = "PG";
    public static final String DEFAULT_STARTDATE = "13/03/2018";
    public static final String DEFAULT_TAGS = "superhero";

    private MovieName movieName;
    private Duration duration;
    private Rating rating;
    private StartDate startDate;
    private Set<Tag> tags;

    public MovieBuilder() {
        movieName = new MovieName(DEFAULT_MOVIENAME);
        duration = new Duration(DEFAULT_DURATION);
        rating = new Rating(DEFAULT_RATING);
        startDate = new StartDate(DEFAULT_STARTDATE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the MovieBuilder with the data of {@code movieToCopy}.
     */
    public MovieBuilder(Movie movieToCopy) {
        movieName = movieToCopy.getName();
        duration = movieToCopy.getDuration();
        rating = movieToCopy.getRating();
        startDate = movieToCopy.getStartDate();
        tags = new HashSet<>(movieToCopy.getTags());
    }

    /**
     * Sets the {@code MovieName} of the {@code Movie} that we are building.
     */
    public MovieBuilder withMovieName(String movieName) {
        this.movieName = new MovieName(movieName);
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code Movie} that we are building.
     */
    public MovieBuilder withDuration(String duration) {
        this.duration = new Duration(duration);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Movie} that we are building.
     */
    public MovieBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Movie} that we are building.
     */
    public MovieBuilder withStartDate(String startDate) {
        this.startDate = new StartDate(startDate);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Cinema} that we are building.
     */
    public MovieBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Movie build() {
        return new Movie(movieName, duration, rating, startDate, tags);
    }

}
