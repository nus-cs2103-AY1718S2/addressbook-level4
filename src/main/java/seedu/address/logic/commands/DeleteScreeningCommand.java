package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CINEMA_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENING_DATE_TIME;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ReloadBrowserPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;
import seedu.address.model.screening.Screening;

//@@author qwlai
/**
 * Delete a movie screening from a cinema theater.
 */
public class DeleteScreeningCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletescreening";
    public static final String COMMAND_ALIAS = "ds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a movie screening from a cinema theater. "
            + "Parameters: "
            + PREFIX_CINEMA_INDEX + "CINEMA_INDEX "
            + PREFIX_NUMOFTHEATERS + "THEATER_NUMBER "
            + PREFIX_SCREENING_DATE_TIME + "SCREEN_DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CINEMA_INDEX + "2 "
            + PREFIX_NUMOFTHEATERS + "3 "
            + PREFIX_SCREENING_DATE_TIME + "13/03/2018 13:35";

    public static final String MESSAGE_SUCCESS = "Screening deleted: %1$s";

    private final Index cinemaIndex;
    private final int theaterNumber;
    private final LocalDateTime toDeleteScreeningDateTime;

    private Cinema cinema;
    private Cinema updatedCinema;
    private Screening toDelete;

    /**
     * Creates an DeleteScreeningCommand to add the specified {@code Screening}
     */
    public DeleteScreeningCommand(Index cinemaIndex, int theaterNumber, LocalDateTime toDeleteScreeningDateTime) {
        requireNonNull(cinemaIndex);
        requireNonNull(theaterNumber);
        requireNonNull(toDeleteScreeningDateTime);
        this.cinemaIndex = cinemaIndex;
        this.theaterNumber = theaterNumber;
        this.toDeleteScreeningDateTime = toDeleteScreeningDateTime;
    }

    /**
     * Adds a screening to a cinema and updates the cinema
     * @return CommandResult on successful add screening
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireNonNull(cinema);
        requireNonNull(updatedCinema);

        try {
            model.updateCinema(cinema, updatedCinema);
            EventsCenter.getInstance().post(new ReloadBrowserPanelEvent(updatedCinema, toDeleteScreeningDateTime));
        } catch (CinemaNotFoundException cnfe) {
            throw new AssertionError("The target cinema cannot be missing");
        } catch (DuplicateCinemaException dce) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_CINEMA);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    /**
     * Checks that a screening entry is valid and adds it to the updated cinema
     * @throws CommandException if screening is invalid
     */
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        cinema = getValidCinema();
        Theater theater = getValidTheater(cinema);
        toDelete = getValidScreening(theater);
        if (toDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELETE_SCREENING_DATE_TIME);
        }
        updatedCinema = generateUpdatedCinema(theater);
    }

    /**
     * Returns a valid screening if it's found
     */
    private Screening getValidScreening(Theater theater) throws CommandException {
        Screening screening = null;
        for (Screening s : theater.getScreeningList()) {
            if (s.getScreeningDateTime().equals(toDeleteScreeningDateTime)) {
                screening = s;
            }
        }
        return screening;
    }

    /**
     * Creates and returns a {@code Cinema} with the screening removed
     */
    private Cinema generateUpdatedCinema(Theater theater) {
        ArrayList<Theater> updatedTheaterList = generateUpdatedTheaterList(theater);
        return new Cinema(cinema.getName(), cinema.getPhone(), cinema.getEmail(),
                cinema.getAddress(), updatedTheaterList);
    }

    /**
     * Generates and returns an updated list of theaters, with the screening removed
     */
    public ArrayList<Theater> generateUpdatedTheaterList(Theater theater) {
        ArrayList<Theater> updatedTheaterList = new ArrayList<>();

        for (Theater t : cinema.getTheaters()) {
            if (t.equals(theater)) {
                Theater theaterToBeUpdated = new Theater(t.getTheaterNumber());
                ArrayList<Screening> updatedScreeningList = new ArrayList<>();

                addScreeningsToExistingTheater(t, theaterToBeUpdated, updatedScreeningList);
                updatedTheaterList.add(theaterToBeUpdated);
            } else {
                updatedTheaterList.add(t);
            }
        }
        return updatedTheaterList;
    }

    /**
     * Populates the list of screenings in a theater in the given list
     */
    private void addScreeningsToExistingTheater(Theater theater, Theater updatedTheater,
                                                ArrayList<Screening> screeningList) {
        for (Screening s : theater.getScreeningList()) {
            if (!s.equals(toDelete)) {
                screeningList.add(s);
            }
        }
        updatedTheater.setScreeningList(screeningList);
        updatedTheater.sortScreeningList();
    }

    /**
     * Gets a valid cinema based on the cinema index
     * @return a valid cinema based on the cinema index
     */
    private Cinema getValidCinema() throws CommandException {
        List<Cinema> lastShownCinemaList = model.getFilteredCinemaList();

        if (cinemaIndex.getZeroBased() >= lastShownCinemaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
        }

        Cinema cinema = lastShownCinemaList.get(cinemaIndex.getZeroBased());
        return cinema;
    }

    /**
     * Gets a valid theater based on the cinema provided
     * @return a valid theater based on the cinema provided and theater number
     */
    private Theater getValidTheater(Cinema cinema) throws CommandException {
        int theaterIndex = theaterNumber - 1;

        if (theaterIndex >= cinema.getTheaters().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEATER_NUMBER);
        }

        Theater theater = cinema.getTheaters().get(theaterIndex);
        return theater;
    }

    @Override
    public boolean equals(Object other) {
        DeleteScreeningCommand ds = (DeleteScreeningCommand) other;

        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteScreeningCommand)) {
            return false;
        }

        return  this.cinemaIndex.equals(ds.cinemaIndex)
                && this.theaterNumber == ds.theaterNumber
                && Objects.equals(this.cinema, ds.cinema)
                && Objects.equals(this.updatedCinema, ds.updatedCinema)
                && Objects.equals(this.toDelete, ds.toDelete)
                && Objects.equals(this.toDeleteScreeningDateTime, ds.toDeleteScreeningDateTime);
    }
}
