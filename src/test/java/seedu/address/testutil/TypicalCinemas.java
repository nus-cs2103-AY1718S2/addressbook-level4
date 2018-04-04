package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMOFTHEATERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_TAMPINES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MoviePlanner;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;

/**
 * A utility class containing a list of {@code Cinema} objects to be used in tests.
 */
public class TypicalCinemas {

    public static final Cinema ALJUNIED = new CinemaBuilder().withName("Aljunied Cathay")
            .withAddress("123, Aljunied").withEmail("aljunied@cathay.com")
            .withPhone("65355255").withTheater(3).build();
    public static final Cinema BEDOK  = new CinemaBuilder().withName("Bedok Shaws")
            .withAddress("311, Bedok")
            .withEmail("bedok@shaw.com").withPhone("68765432")
            .withTheater(3).build();
    public static final Cinema CLEMENTI = new CinemaBuilder().withName("Clementi Cathay").withPhone("65352563")
            .withEmail("clementi@cathay.com").withAddress("111, Clementi").withTheater(3).build();
    public static final Cinema DOVER = new CinemaBuilder().withName("Dover Shaws").withPhone("67652533")
            .withEmail("dover@shaw.com").withAddress("10th Street").withTheater(3).build();
    public static final Cinema EUNOS = new CinemaBuilder().withName("Eunos Cathay").withPhone("6482224")
            .withEmail("eunos@cathay.com").withAddress("Eunos Street").withTheater(3).build();
    public static final Cinema FARRER = new CinemaBuilder().withName("Farrer Cathay").withPhone("6482427")
            .withEmail("farrer@cathay.com").withAddress("Farrer Park").withTheater(3).build();
    public static final Cinema GUL = new CinemaBuilder().withName("Gul Cathay").withPhone("6482442")
            .withEmail("gul@cathay.com").withAddress("Gul Circle").withTheater(3).build();

    // Manually added
    public static final Cinema HOUGANG = new CinemaBuilder().withName("Hougang Shaws").withPhone("6482424")
            .withEmail("hougang@shaws.com").withAddress("Hougang Avenue").withTheater(3).build();
    public static final Cinema INDO = new CinemaBuilder().withName("Indo Cathay").withPhone("6482131")
            .withEmail("indo@cathay.com").withAddress("Indo Street").withTheater(3).build();

    // Manually added - Cinema's details found in {@code CommandTestUtil}
    public static final Cinema SENGKANG = new CinemaBuilder().withName(VALID_NAME_SENGKANG)
            .withPhone(VALID_PHONE_SENGKANG).withEmail(VALID_EMAIL_SENGKANG)
            .withAddress(VALID_ADDRESS_SENGKANG).withTheater(VALID_NUMOFTHEATERS).build();
    public static final Cinema TAMPINES = new CinemaBuilder().withName(VALID_NAME_TAMPINES)
            .withPhone(VALID_PHONE_TAMPINES).withEmail(VALID_EMAIL_TAMPINES)
            .withAddress(VALID_ADDRESS_TAMPINES).withTheater(VALID_NUMOFTHEATERS).build();

    public static final String KEYWORD_MATCHING_SHAWS = "Shaws"; // A keyword that matches SHAWS

    private TypicalCinemas() {} // prevents instantiation

    /**
     * Returns an {@code MoviePlanner} with all the typical cinemas.
     */
    public static MoviePlanner getTypicalMoviePlanner() {
        MoviePlanner ab = new MoviePlanner();
        for (Cinema cinema : getTypicalCinemas()) {
            try {
                ab.addCinema(cinema);
            } catch (DuplicateCinemaException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Cinema> getTypicalCinemas() {
        return new ArrayList<>(Arrays.asList(ALJUNIED, BEDOK, CLEMENTI, DOVER, EUNOS, FARRER, GUL));
    }
}
