package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MoviePlanner;
import seedu.address.model.ReadOnlyMoviePlanner;

/**
 * An Immutable MoviePlanner that is serializable to XML format
 */
@XmlRootElement(name = "movieplanner")
public class XmlSerializableMoviePlanner {

    @XmlElement
    private List<XmlAdaptedCinema> cinemas;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedMovie> movies;

    /**
     * Creates an empty XmlSerializableMoviePlanner.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableMoviePlanner() {
        cinemas = new ArrayList<>();
        tags = new ArrayList<>();
        movies = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableMoviePlanner(ReadOnlyMoviePlanner src) {
        this();
        cinemas.addAll(src.getCinemaList().stream().map(XmlAdaptedCinema::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        movies.addAll(src.getMovieList().stream().map(XmlAdaptedMovie::new).collect(Collectors.toList()));
    }

    /**
     * Converts this movieplanner into the model's {@code MoviePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCinema} or {@code XmlAdaptedTag}.
     */
    public MoviePlanner toModelType() throws IllegalValueException {
        MoviePlanner moviePlanner = new MoviePlanner();
        for (XmlAdaptedTag t : tags) {
            moviePlanner.addTag(t.toModelType());
        }
        for (XmlAdaptedCinema c : cinemas) {
            moviePlanner.addCinema(c.toModelType());
        }

        for (XmlAdaptedMovie m : movies) {
            moviePlanner.addMovie(m.toModelType());
        }

        return moviePlanner;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableMoviePlanner)) {
            return false;
        }

        XmlSerializableMoviePlanner otherAb = (XmlSerializableMoviePlanner) other;
        return cinemas.equals(otherAb.cinemas)
                && tags.equals(otherAb.tags)
                && movies.equals(otherAb.movies);
    }
}
