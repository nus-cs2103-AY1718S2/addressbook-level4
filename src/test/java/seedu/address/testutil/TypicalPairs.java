package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.pair.Pair;

//@@author alexawangzi
/**
 * A utility class containing a list of {@code Pair} objects to be used in tests.
 */
public class TypicalPairs {

    public static final Pair RANDOM_PAIR_A = new PairBuilder().withStudent(BENSON).withTutor(ALICE)
            .build();

    public static final Pair RANDOM_PAIR_B = new PairBuilder().withStudent(DANIEL).withTutor(CARL)
            .build();

    public static final Pair RANDOM_PAIR_C = new PairBuilder().withStudent(FIONA).withTutor(ELLE)
            .build();



    private TypicalPairs() {} // prevents instantiation

    public static List<Pair> getTypicalPairs() {
        return new ArrayList<>(Arrays.asList(RANDOM_PAIR_A, RANDOM_PAIR_B, RANDOM_PAIR_C));
    }

}
