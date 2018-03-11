package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.model.book.Category;

public class UniqueListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_validItem_success() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        uniqueList.add(new Category("Cat 2"));
        uniqueList.add(new Category("Cat 3"));
        assertEquals(3, uniqueList.toSet().size());
    }

    @Test
    public void add_duplicateItem_throwsDuplicateDataException() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        uniqueList.add(new Category("Cat 2"));
        thrown.expect(DuplicateDataException.class);
        uniqueList.add(new Category("Cat 1"));
    }

    @Test
    public void add_null_throwsNullPointerException() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        thrown.expect(NullPointerException.class);
        uniqueList.add(null);
    }

    @Test
    public void toSet_modifyList_doesNotMutateList() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        Set<Category> set = uniqueList.toSet();
        set.add(new Category("Cat 2"));
        assertEquals(1, uniqueList.toSet().size());
        set.remove(new Category("Cat 1"));
        assertEquals(1, uniqueList.toSet().size());
    }

    @Test
    public void setItems_modifyList_success() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        UniqueList<Category> replacement = new UniqueList<>();
        replacement.add(new Category("Cat 2"));
        replacement.add(new Category("Cat 3"));
        uniqueList.setItems(replacement.toSet());
        assertEquals(2, uniqueList.toSet().size());
        assertEquals(true, uniqueList.contains(new Category("Cat 2")));
        assertEquals(false, uniqueList.contains(new Category("Cat 1")));
    }

    @Test
    public void setItems_null_throwsNullPointerException() {
        UniqueList<Category> uniqueList = new UniqueList<>();
        thrown.expect(NullPointerException.class);
        uniqueList.setItems(null);
    }

    @Test
    public void mergeFrom_modifyList_success() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        UniqueList<Category> toMerge = new UniqueList<>();
        toMerge.add(new Category("Cat 2"));
        toMerge.add(new Category("Cat 3"));
        uniqueList.mergeFrom(toMerge);
        assertEquals(3, uniqueList.toSet().size());
        assertEquals(true, uniqueList.contains(new Category("Cat 2")));
        assertEquals(true, uniqueList.contains(new Category("Cat 1")));
    }

    @Test
    public void mergeFrom_null_throwsNullPointerException() {
        UniqueList<Category> uniqueList = new UniqueList<>();
        thrown.expect(NullPointerException.class);
        uniqueList.mergeFrom(null);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueList<Category> uniqueList = new UniqueList<>();
        thrown.expect(UnsupportedOperationException.class);
        uniqueList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameContent_returnsTrue() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        UniqueList<Category> uniqueList2 = new UniqueList<>();
        uniqueList2.add(new Category("Cat 1"));
        assertEquals(true, uniqueList.equals(uniqueList2));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() throws Exception {
        UniqueList<Category> uniqueList = new UniqueList<>();
        uniqueList.add(new Category("Cat 1"));
        UniqueList<Category> uniqueList2 = new UniqueList<>();
        uniqueList2.add(new Category("Cat 1"));
        assertEquals(uniqueList.hashCode(), uniqueList2.hashCode());
    }

}
