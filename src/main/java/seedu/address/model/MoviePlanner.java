package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.cinema.UniqueCinemaList;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.UniqueMovieList;
import seedu.address.model.movie.exceptions.DuplicateMovieException;
import seedu.address.model.movie.exceptions.MovieNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Wraps all data at the movie-planner level
 * Duplicates are not allowed (by .equals comparison)
 */
public class MoviePlanner implements ReadOnlyMoviePlanner {

    private final UniqueCinemaList cinemas;
    private final UniqueMovieList movies;
    private final UniqueTagList tags;
    private ArrayList<Theater> theaters;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cinemas = new UniqueCinemaList();
        theaters = new ArrayList<>();
        movies = new UniqueMovieList();
        tags = new UniqueTagList();
    }

    public MoviePlanner() {

    }

    /**
     * Creates an MoviePlanner using the Cinemas, Tags and Movies in the {@code toBeCopied}
     */
    public MoviePlanner(ReadOnlyMoviePlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setCinemas(List<Cinema> cinemas) throws DuplicateCinemaException {
        this.cinemas.setCinemas(cinemas);
    }

    public void setTheaters(ArrayList<Theater> theaters) {
        this.theaters = theaters;
    }

    public void setMovies(List<Movie> movies) throws DuplicateMovieException {
        this.movies.setMovies(movies);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code MoviePlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyMoviePlanner newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setTheaters(new ArrayList<>(newData.getTheaterList()));
        List<Cinema> syncedCinemaList = newData.getCinemaList();
        List<Movie> syncedMovieList = newData.getMovieList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        try {
            setCinemas(syncedCinemaList);
            setMovies(syncedMovieList);
        } catch (DuplicateCinemaException dce) {
            throw new AssertionError("MoviePlanners should not have duplicate Cinemas");
        } catch (DuplicateMovieException dme) {
            throw new AssertionError("MoviePlanners should not have duplicate Movies");
        }
    }

    //// Cinema-level operations

    /**
     * Adds a Cinema to the movie planner.
     *
     * @throws DuplicateCinemaException if an equivalent Cinema already exists.
     */
    public void addCinema(Cinema c) throws DuplicateCinemaException {
        cinemas.add(c);
    }

    /**
     * Replaces the given Cinema {@code target} in the list with {@code editedCinema}.
     * {@code MoviePlanner}'s tag list will be updated with the tags of {@code editedCinema}.
     *
     * @throws DuplicateCinemaException if updating the Cinema's details causes the Cinema to be equivalent to
     *      another existing Cinema in the list.
     * @throws CinemaNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateCinema(Cinema target, Cinema editedCinema)
            throws DuplicateCinemaException, CinemaNotFoundException {
        requireNonNull(editedCinema);
        cinemas.setCinema(target, editedCinema);
    }

    /**
     *  Updates the master tag list to include tags in {@code Movie} that are not in the list.
     *  @return a copy of this {@code Movie} such that every tag in this Movie points to a Tag object in the master
     *  list.
     */
    private Movie syncWithMasterTagList(Movie movie) {
        final UniqueTagList movieTags = new UniqueTagList(movie.getTags());
        tags.mergeFrom(movieTags);

        // Create map with values = tag object references in the master list
        // used for checking Movie tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of Movie tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        movieTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Movie(
                movie.getName(), movie.getDuration(), movie.getRating(),
                movie.getStartDate(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code MoviePlanner}.
     * @throws CinemaNotFoundException if the {@code key} is not in this {@code MoviePlanner}.
     */
    public boolean removeCinema(Cinema key) throws CinemaNotFoundException {
        if (cinemas.remove(key)) {
            return true;
        } else {
            throw new CinemaNotFoundException();
        }
    }

    /**
    * Adds a Theater to the movie planner
    */
    public void addTheater(Theater t) {
        theaters.add(t);
    }

    //// Tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes {@code tag} from this {@code MoviePlanner}
     * @throws TagNotFoundException if the {@code tag} is not found in this {@code MoviePlanner}.
     */
    public void removeTag(Tag tag) throws TagNotFoundException {
        if (tags.contains(tag)) {
            for (Movie movie : movies) {
                removeTagFromMovie(tag, movie);
            }
        } else {
            throw new TagNotFoundException();
        }
    }

    /**
     * Removes {@code tag} from {@code cinema} if tag is found.
     *
     */
    public void removeTagFromMovie(Tag tag, Movie movie) {
        Set<Tag> newTags = new HashSet<>(movie.getTags());

        if (newTags.remove(tag)) {
            Movie newMovie = new Movie(movie.getName(), movie.getDuration(), movie.getRating(),
                                          movie.getStartDate(), newTags);
            try {
                updateMovie(movie, newMovie);
            } catch (MovieNotFoundException mnfe) {
                throw new AssertionError("Movie should not be missing");
            } catch (DuplicateMovieException dme) {
                throw new AssertionError("Removing tag should not result in duplicate movies");
            }
        }
    }

    /**
     * Removes {@code tag} if tag is not used
     *
     */
    public void removeUnusedTags() {
        Set<Tag> tagsOfMovies = movies.asObservableList()
                                        .stream()
                                        .flatMap(movie -> movie.getTags().stream())
                                        .collect(Collectors.toSet());
        tags.setTags(tagsOfMovies);
    }


    //// Movie-level operations
    /**
     * Adds a Movie to the movie planner.
     * Also checks the new Movie's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the Movie to point to those in {@link #tags}.
     *
     * @throws DuplicateMovieException if an equivalent Movie already exists.
     */
    public void addMovie(Movie m) throws DuplicateMovieException {
        Movie movie = syncWithMasterTagList(m);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any Cinema
        // in the Cinema list.
        movies.add(movie);
    }

    /**
     * Removes {@code key} from this {@code MoviePlanner}.
     * @throws MovieNotFoundException if the {@code key} is not in this {@code MoviePlanner}.
     */
    public boolean removeMovie(Movie key) throws MovieNotFoundException {
        if (movies.remove(key)) {
            return true;
        } else {
            throw new MovieNotFoundException();
        }
    }

    /**
     * Replaces the given Movie {@code target} in the list with {@code editedMovie}.
     * {@code MoviePlanner}'s tag list will be updated with the tags of {@code editedMovie}.
     *
     * @throws DuplicateMovieException if updating the Movie's details causes the Movie to be equivalent to
     *      another existing Movie in the list.
     * @throws MovieNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Movie)
     */
    public void updateMovie(Movie target, Movie editedMovie)
            throws DuplicateMovieException, MovieNotFoundException {
        requireNonNull(editedMovie);

        Movie syncedEditedMovie = syncWithMasterTagList(editedMovie);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any Cinema
        // in the Cinema list.
        movies.setMovie(target, syncedEditedMovie);
        removeUnusedTags();
    }

    //// util methods
    @Override
    public String toString() {
        return cinemas.asObservableList().size() + " Cinemas, " + movies.asObservableList().size() + " movies, "
                + tags.asObservableList().size() +  " tags, ";
        // TODO: refine later
    }

    @Override
    public ObservableList<Cinema> getCinemaList() {
        return cinemas.asObservableList();
    }

    @Override
    public ObservableList<Movie> getMovieList() {
        return movies.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Theater> getTheaterList() {
        return FXCollections.observableList(theaters);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoviePlanner // instanceof handles nulls
                && this.cinemas.equals(((MoviePlanner) other).cinemas)
                && this.movies.equals(((MoviePlanner) other).movies)
                && this.tags.equalsOrderInsensitive(((MoviePlanner) other).tags)
                && this.theaters.equals(((MoviePlanner) other).theaters));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(cinemas, movies, tags, theaters);
    }
}
