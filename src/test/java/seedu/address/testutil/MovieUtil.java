package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddMovieCommand;
import seedu.address.model.movie.Movie;
//@@author slothhy
/**
 * A utility class for Movie.
 */
public class MovieUtil {

    /**
     * Uses the add command word
     * Returns an add command string for adding the {@code movie}.
     */
    public static String getAddCommand(Movie movie) {
        return AddMovieCommand.COMMAND_WORD + " " + getMovieDetails(movie);
    }

    /**
     * Uses the add command alias
     * Returns an add command string for adding the {@code movie}.
     */
    public static String getAddUsingAliasCommand(Movie movie) {
        return AddMovieCommand.COMMAND_ALIAS + " " + getMovieDetails(movie);
    }

    /**
     * Returns the part of command string for the given {@code movie}'s details.
     */
    public static String getMovieDetails(Movie movie) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + movie.getName().movieName + " ");
        sb.append(PREFIX_DURATION + movie.getDuration().duration + " ");
        sb.append(PREFIX_RATING + movie.getRating().rating + " ");
        sb.append(PREFIX_STARTDATE + movie.getStartDate().startDate + " ");
        movie.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }
}
