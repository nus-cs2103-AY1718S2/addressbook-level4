package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPairs.RANDOM_PAIR_A;
import static seedu.address.testutil.TypicalPairs.RANDOM_PAIR_B;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.pair.UniquePairList;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.pair.exceptions.PairNotFoundException;

public class UniquePairListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairList uniquePairList = new UniquePairList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairList.asObservableList().remove(0);
    }

    //@@author alexawangzi
    @Test
    public void checkContainPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
        assertFalse(uniquePairList.contains(RANDOM_PAIR_B));
    }

    //@@author alexawangzi
    @Test
    public void addPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
    }

    //@@author alexawangzi
    @Test
    public void setPair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B);
        assertEquals(listA, listB);

    }

    //@@author alexawangzi
    @Test
    public void setPairs() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPairs(listB);
        assertEquals(listA, listB);
    }

    //@@author alexawangzi
    @Test
    public void removePair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        UniquePairList listB = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        listA.add(RANDOM_PAIR_B);
        listB.add(RANDOM_PAIR_B);
        listA.remove(RANDOM_PAIR_A);
        assertEquals(listA, listB);
    }

    //@@author aussiroth
    @Test
    public void deletePair_noMatchingPair_throwsPairNotFoundException() {
        UniquePairList uniquePairList = new UniquePairList();
        assertThrows(PairNotFoundException.class, () -> uniquePairList.remove(RANDOM_PAIR_A));
    }

    @Test
    public void checkHashCodeMethod() throws Exception {
        UniquePairList uniquePairListA = new UniquePairList();
        UniquePairList uniquePairListB = new UniquePairList();
        uniquePairListA.add(RANDOM_PAIR_A);
        uniquePairListB.add(RANDOM_PAIR_A);
        assertTrue(uniquePairListA.hashCode() == uniquePairListB.hashCode());
    }

    @Test
    public void editPair_targetNotFound_throwsPairNotFoundException() throws Exception {
        UniquePairList uniquePairList = new UniquePairList();
        assertThrows(PairNotFoundException.class, () -> uniquePairList.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B));
    }

    @Test
    public void editPair_pairInList_throwsDuplicatePairException() throws Exception {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_B);
        assertThrows(PairNotFoundException.class, () -> uniquePairList.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B));
        uniquePairList.add(RANDOM_PAIR_A);
        assertThrows(DuplicatePairException.class, () -> uniquePairList.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B));
    }
}
