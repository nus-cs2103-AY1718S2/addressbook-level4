package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CINEMAS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MOVIES;

/**
 * Lists all cinemas in the movie planner to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_SUCCESS = "Listed all cinemas and movies.";


    @Override
    public CommandResult execute() {
        model.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);
        model.updateFilteredMovieList(PREDICATE_SHOW_ALL_MOVIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
