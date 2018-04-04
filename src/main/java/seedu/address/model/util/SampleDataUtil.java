package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.MoviePlanner;
import seedu.address.model.ReadOnlyMoviePlanner;
import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;
import seedu.address.model.cinema.Theater;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;
import seedu.address.model.movie.Duration;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.MovieName;
import seedu.address.model.movie.Rating;
import seedu.address.model.movie.StartDate;
import seedu.address.model.movie.exceptions.DuplicateMovieException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code MoviePlanner} with sample data.
 */
public class SampleDataUtil {
    public static Cinema[] getSampleCinemas() {
        return new Cinema[] {
            new Cinema(new Name("Cathay East"), new Phone("67438807"), new Email("cathayeast@cathay.com"),
                new Address("Cathay East, #02-03"),
                getTheaterList(3)),
            new Cinema(new Name("Cathay West"), new Phone("69272758"), new Email("cathaywest@cathay.com"),
                new Address("Cathay West, #07-18"),
                getTheaterList(5)),
            new Cinema(new Name("Shaws 1"), new Phone("63210283"), new Email("shaw1@shaw.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTheaterList(4)),
            new Cinema(new Name("Golden Village Jurong"), new Phone("61031282"), new Email("gvj@gv.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #05-43"),
                getTheaterList(3)),
            new Cinema(new Name("Cathay Cineleisure"), new Phone("62492021"), new Email("cathaycine@cathay.com"),
                new Address("6 Orchard, #05-35"),
                getTheaterList(3)),
            new Cinema(new Name("Cathay North"), new Phone("62624417"), new Email("cathaynorth@cathay.com"),
                new Address("45 Woodlands Street 85, #03-31"),
                getTheaterList(7))
        };
    }

    public static Movie[] getSampleMovies() {
        return new Movie[] {new Movie(new MovieName("Spiderman 1"), new Duration("120"), new Rating("G"),
                        new StartDate("01/02/2000"), getTagSet("superhero")),
            new Movie(new MovieName("Batman"), new Duration("100"), new Rating("PG"),
                        new StartDate("06/12/2017"), getTagSet("superhero", "thriller")),
            new Movie(new MovieName("Insidious"), new Duration("100"), new Rating("NC16"),
                        new StartDate("05/05/2012"), getTagSet("horror", "ghost")),
            new Movie(new MovieName("Hungry Good"), new Duration("150"), new Rating("M18"),
                        new StartDate("12/11/2015"), getTagSet("comedy")),
            new Movie(new MovieName("Ah Boys to Men 4"), new Duration("80"), new Rating("PG"),
                        new StartDate("15/03/2017"), getTagSet("comedy")),
            new Movie(new MovieName("Jaws"), new Duration("60"), new Rating("PG"),
                        new StartDate("08/02/2000"), getTagSet("animal")),
        };
    }

    public static ReadOnlyMoviePlanner getSampleMoviePlanner() {
        try {
            MoviePlanner sampleAb = new MoviePlanner();
            for (Cinema sampleCinema : getSampleCinemas()) {
                sampleAb.addCinema(sampleCinema);
            }
            for (Movie sampleMovie : getSampleMovies()) {
                sampleAb.addMovie(sampleMovie);
            }
            return sampleAb;
        } catch (DuplicateCinemaException e) {
            throw new AssertionError("sample data cannot contain duplicate cinemas", e);
        } catch (DuplicateMovieException e) {
            throw new AssertionError("sample data cannot contain duplicate movies", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    public static ArrayList<Theater> getTheaterList(int numOfTheaters) {
        ArrayList<Theater> theaters = new ArrayList<>();
        for (int i = 1; i <= numOfTheaters; i++) {
            theaters.add(new Theater(i));
        }
        return theaters;
    }

}
