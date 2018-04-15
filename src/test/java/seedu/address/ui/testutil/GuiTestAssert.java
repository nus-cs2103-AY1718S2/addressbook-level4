package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CreditorCardHandle;
import guitests.guihandles.DebtorCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TransactionCardHandle;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    //@@author phmignot
    /**
     * Asserts that {@code actualTransactionCard} displays the same values as {@code expectedTransactionCard}.
     */
    public static void assertCardEquals(TransactionCardHandle expectedCard, TransactionCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPayer(), actualCard.getPayer());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getPayees(), actualCard.getPayees());
    }

    //@@author phmignot
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }
    //@@author phmignot
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCreditorCardDisplays(Creditor expectedCreditor, CreditorCardHandle actualCard) {
        assertEquals(expectedCreditor.getCreditor().getName().fullName, actualCard.getName());
        assertEquals(expectedCreditor.getCreditor().getPhone().value, actualCard.getPhone());
        assertEquals(expectedCreditor.getCreditor().getEmail().value, actualCard.getEmail());
        assertEquals(expectedCreditor.getCreditor().getTags().stream().map(tag -> tag.tagName)
                        .collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedCreditor.getDebt().value, actualCard.getDebt());
    }
    //@@author phmignot
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertDebtorCardDisplays(Debtor expectedDebtor, DebtorCardHandle actualCard) {
        assertEquals(expectedDebtor.getDebtor().getName().fullName, actualCard.getName());
        assertEquals(expectedDebtor.getDebtor().getPhone().value, actualCard.getPhone());
        assertEquals(expectedDebtor.getDebtor().getEmail().value, actualCard.getEmail());
        assertEquals(expectedDebtor.getDebtor().getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedDebtor.getDebt().value, actualCard.getDebt());
    }
    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    //@@author phmignot
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTransaction}.
     */
    public static void assertCardDisplaysTransaction(Transaction expectedTransaction,
                                                     TransactionCardHandle actualCard) {
        assertEquals(expectedTransaction.getPayer().getName().fullName, actualCard.getPayer());
        assertEquals(expectedTransaction.getAmount().toString(), actualCard.getAmount());
        assertEquals(expectedTransaction.getDescription().value, actualCard.getDescription());

        String expectedPayeesString = "";
        for (int i = 0; i < expectedTransaction.getPayees().asObservableList().size(); i++) {
            Person expectedPayee = expectedTransaction.getPayees().asObservableList().get(i);
            expectedPayeesString += expectedPayee.getName().fullName;
            if (i != expectedTransaction.getPayees().asObservableList().size() - 1) {
                expectedPayeesString += ", ";
            }
        }

        assertEquals(expectedPayeesString, actualCard.getPayees());
    }
}
