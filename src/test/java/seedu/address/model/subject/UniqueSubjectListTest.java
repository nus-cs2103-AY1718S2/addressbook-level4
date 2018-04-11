package seedu.address.model.subject;

import static junit.framework.TestCase.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//@@author TeyXinHui
public class UniqueSubjectListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueSubjectList.asObservableList().remove(0);
    }

    @Test
    public void testEquals_similarObject_returnTrue() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        UniqueSubjectList uniqueSubjectList1 = new UniqueSubjectList();
        assertEquals(uniqueSubjectList, uniqueSubjectList1);
    }

    @Test
    public void hashCode_checkForHashCode_sameHashCode() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        UniqueSubjectList uniqueSubjectList1 = new UniqueSubjectList();
        assertEquals(uniqueSubjectList.hashCode(), uniqueSubjectList1.hashCode());
    }

}
//@@author
