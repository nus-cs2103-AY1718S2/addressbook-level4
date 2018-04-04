package seedu.address.model.movie;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.cinema.Theater;
import seedu.address.model.screening.Screening;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
//@@author slothhy
/**
 * Represents a Movie in the movie planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Movie {

    private final MovieName movieName;
    private final Duration duration;
    private final Rating rating;
    private final StartDate startDate;
    private final UniqueTagList tags;
    private final ArrayList<Screening> screenings;

    public Movie(MovieName movieName, Duration duration, Rating rating, StartDate startDate, Set<Tag> tags) {
        requireAllNonNull(movieName, duration, rating, startDate);
        this.movieName = movieName;
        this.duration = duration;
        this.rating = rating;
        this.startDate = startDate;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.screenings = new ArrayList<>();
    }

    public void addScreening(Screening s) {
        screenings.add(s);
    }

    /**
     * Called by DeleteMovieCommand.
     * It will delete all screenings linked to the movie.
     */
    public void deleteScreenings() {
        for (int i = 0; i < screenings.size(); i++) {
            Screening s = screenings.get(i);
            Theater t = s.getTheater();
            t.deleteScreening(s);
            screenings.remove(i);
        }
    }

    public MovieName getName() {
        return movieName;
    }

    public Duration getDuration() {
        return duration;
    }

    public Rating getRating() {
        return rating;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Movie)) {
            return false;
        }

        Movie otherMovie = (Movie) other;
        return otherMovie.getName().equals(this.getName())
                && otherMovie.getDuration().equals(this.getDuration())
                && otherMovie.getRating().equals(this.getRating())
                && otherMovie.getStartDate().equals(this.getStartDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(movieName, duration, rating, startDate, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Duration: ")
                .append(getDuration())
                .append(" Rating: ")
                .append(getRating())
                .append(" StartDate: ")
                .append(getStartDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
