//@@author amad-person
package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalOrders.BOOKS;
import static seedu.address.testutil.TypicalOrders.CHOCOLATES;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.DuplicateOrderException;

public class UniqueOrderListTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws DuplicateOrderException {
        UniqueOrderList firstOrderList = new UniqueOrderList();
        firstOrderList.add(BOOKS);
        UniqueOrderList secondOrderList = new UniqueOrderList();
        secondOrderList.add(CHOCOLATES);

        // same object -> true
        assertTrue(firstOrderList.equals(firstOrderList));

        // different type -> false
        assertFalse(firstOrderList.equals(1));

        // different objects, same type -> false
        assertFalse(firstOrderList.equals(secondOrderList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws DuplicateOrderException {
        UniqueOrderList firstOrderList = new UniqueOrderList();
        firstOrderList.add(BOOKS);
        firstOrderList.add(CHOCOLATES);
        UniqueOrderList secondOrderList = new UniqueOrderList();
        secondOrderList.add(CHOCOLATES);
        secondOrderList.add(BOOKS);

        assertTrue(firstOrderList.equalsOrderInsensitive(secondOrderList));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws DuplicateOrderException {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(DuplicateOrderException.class);
        uniqueOrderList.add(BOOKS);
        uniqueOrderList.add(BOOKS);
    }
}
