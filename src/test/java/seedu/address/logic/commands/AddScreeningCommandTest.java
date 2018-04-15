package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showCinemaAtIndex;
import static seedu.address.testutil.TypicalCinemas.getTypicalMoviePlanner;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CINEMA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MOVIE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CINEMA;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MOVIE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.email.EmailManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.movie.StartDate;
import seedu.address.testutil.TypicalMovies;

//@@author qwlai
public class AddScreeningCommandTest {

    private static final int VALID_THEATER_NUMBER = 1;
    private static final String VALID_DATE_TIME = "11/11/2015 11:10";

    private static final int INVALID_THEATER_NUMBER = 5;

    private static final String DATE_TIME_FORMAT = "dd/MM/uuuu HH:mm";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private DateTimeFormatter dtf;
    private AddScreeningCommand addScreeningCommand;

    @Before
    public void setUp() throws Exception {

        model = new ModelManager(getTypicalMoviePlanner(), new UserPrefs(), new EmailManager());
        model.addMovie(TypicalMovies.BLACK_PANTHER);
        model.addMovie(TypicalMovies.THOR_RAGNAROK);
        dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
    }

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddScreeningCommand(null, null, 0, null);
    }

    @Test
    public void execute_invalidIndexCinemaFilteredList_throwsCommandException() throws Exception {
        showCinemaAtIndex(model, INDEX_FIRST_CINEMA);
        Index outOfBoundIndex = INDEX_THIRD_CINEMA;

        // ensures that outOfBoundIndex is still in bounds of movie planner list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMoviePlanner().getCinemaList().size());
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, outOfBoundIndex,
                VALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexMovieFilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = INDEX_THIRD_MOVIE;
        addScreeningCommand = prepareCommand(outOfBoundIndex, INDEX_FIRST_CINEMA,
                VALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_MOVIE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTheaterNumber_throwsCommandException() throws Exception {
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, INDEX_FIRST_CINEMA,
                INVALID_THEATER_NUMBER, getDateTime(VALID_DATE_TIME));
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_THEATER_NUMBER);
    }

    @Test
    public void execute_screeningDateBeforeMovieReleaseDate_throwsCommandException() throws Exception {
        StartDate firstMovieDate = model.getFilteredMovieList().get(INDEX_FIRST_MOVIE.getZeroBased()).getStartDate();
        LocalDateTime invalidScreenDate = getDateTime(firstMovieDate.toString() + " 10:00").minusDays(1);
        addScreeningCommand = prepareCommand(INDEX_FIRST_MOVIE, INDEX_FIRST_CINEMA,
                VALID_THEATER_NUMBER, invalidScreenDate);
        assertCommandFailure(addScreeningCommand, model, Messages.MESSAGE_INVALID_SCREENING);
    }

    /**
     * Parses datetime and returns a valid LocalDateTime object
     */
    private LocalDateTime getDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dtf);
    }

    /**
     * Prepares an addScreeningCommand
     */
    private AddScreeningCommand prepareCommand(Index movieIndex, Index cinemaIndex,
                                               int theaterNumber, LocalDateTime screeningDateTime) {
        AddScreeningCommand command =
                new AddScreeningCommand(movieIndex, cinemaIndex, theaterNumber, screeningDateTime);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
