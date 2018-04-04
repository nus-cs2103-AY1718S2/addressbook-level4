package seedu.address.testutil;

import seedu.address.model.MoviePlanner;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.exceptions.DuplicateMovieException;

/**
 * A utility class to help with building MoviePlanner objects.
 * Example usage: <br>
 *     {@code MoviePlanner ab = new MoviePlannerBuilder().withCinema("John", "Doe").withTag("Friend").build();}
 */
public class MoviePlannerBuilder {

    private MoviePlanner moviePlanner;

    public MoviePlannerBuilder() {
        moviePlanner = new MoviePlanner();
    }

    public MoviePlannerBuilder(MoviePlanner moviePlanner) {
        this.moviePlanner = moviePlanner;
    }

    /**
     * Adds a new {@code Cinema} to the {@code MoviePlanner} that we are building.
     */
    public MoviePlannerBuilder withCinema(Cinema cinema) {
        try {
            moviePlanner.addCinema(cinema);
        } catch (DuplicateCinemaException dce) {
            throw new IllegalArgumentException("cinema is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a new {@code Movie} to the {@code MoviePlanner} that we are building.
     */
    public MoviePlannerBuilder withMovie(Movie movie) {
        try {
            moviePlanner.addMovie(movie);
        } catch (DuplicateMovieException dce) {
            throw new IllegalArgumentException("cinema is expected to be unique.");
        }
        return this;
    }

    public MoviePlanner build() {
        return moviePlanner;
    }
}
