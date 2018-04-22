//@@author IzHoBX
package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.NotificationCard;
import seedu.address.ui.NotificationCenter;
import seedu.address.ui.testutil.NotificationCardStub;

public class NotificationSystemTest {

    @Test
    public void run() {
        //object instantiation
        NotificationCenterStub notificationCenterStub = new NotificationCenterStub();
        ModelStub modelStub = new ModelStub();
        modelStub.setNotificationCenter(notificationCenterStub);
        AddressbookStub addressbookStub = new AddressbookStub();

        //setup initial data
        Person person1 = null;
        Person person2 = null;
        try {
            person1 = new Person(new Name("Alex"), new Phone("12344"), new Email("h@b.c"), new Address("ascada"),
                    ParserUtil.parseTags(new LinkedList<>()), "asasdasd");
            person1.setId(2);
            person2 = new Person(new Name("David"), new Phone("12344"), new Email("h@b.c"), new Address("ascada"),
                    ParserUtil.parseTags(new LinkedList<>()), "asasdasd");
            person2.setId(5);
        } catch (IllegalValueException e) {
            assertTrue(false);
        }

        try {
            addressbookStub.addPerson(person1);
            addressbookStub.addPerson(person2);
        } catch (DuplicatePersonException e) {
            assertTrue(false);
        }
        assertEquals(addressbookStub.getPersonList().size(), 2);
        modelStub.setAddressBook(addressbookStub);
        assertEquals(modelStub.getAddressBook().getPersonList().size(), 2);

        //adding notification test
        Notification notification1 = new Notification("TEST1", person1.getCalendarId(), "ascca",
                "wasccas", person1.getId() + "");
        Notification notification2 = new Notification("TEST2", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        Notification notification3 = new Notification("TEST3", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        Notification notification4 = new Notification("TEST4", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        addressbookStub.addNotification(notification1);
        addressbookStub.addNotification(notification2);
        addressbookStub.addNotification(notification3);
        addressbookStub.addNotification(notification4);

        notificationCenterStub.add(new NotificationCardStub(notification1.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "Alex", notification1.getEndDate(), notification1.getOwnerId(), true,
                notification1.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification2.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification2.getOwnerId(), true,
                notification2.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification3.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification3.getOwnerId(), true,
                notification3.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification4.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification4.getOwnerId(), true,
                notification4.getEventId()));
        assertTrue(notificationCenterStub.getTotalUndismmissedNotificationCards() == 4);

        //email command test
        EmailCommandStub ec = new EmailCommandStub(Index.fromOneBased(1));
        ec.setData(modelStub);
        try {
            ec.preprocessUndoableCommand();
        } catch (CommandException e) {
            assertTrue(false);
        }
        assertEquals(ec.getPerson().getEmail(), person1.getEmail());
    }
}

class NotificationCenterStub extends NotificationCenter {

    protected LinkedList<NotificationCardStub> notificationCardCopy;

    public NotificationCenterStub() {
        notificationCardCopy = new LinkedList<>();
        notificationCardCopy.add(null);
    }

    public void add(NotificationCardStub newNotificationCard) {
        notificationCardCopy.add(newNotificationCard);
    }

    @Override
    public int getTotalUndismmissedNotificationCards() {
        return notificationCardCopy.size() - 1;
    }

    public NotificationCardStub getNotificationCardStub(Index targetIndex) {
        return notificationCardCopy.get(targetIndex.getOneBased());
    }

    public String getOwnerIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getOwnerId();
    }
}

class ModelStub extends ModelManager {

    private NotificationCenterStub notificationCenter;
    private AddressBook addressBook;
    private LinkedList<Person> persons = new LinkedList<>();

    @Override
    public void addNotification(Notification e) {

    }

    @Override
    public void addPerson(Person person) throws DuplicatePersonException {
        persons.add(person);
    }

    @Override
    public void deletePerson(Person target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void deleteNotification(String id, boolean deleteFromAddressBookOnly)
            throws NotificationNotFoundException {

    }

    public void setNotificationCenter(NotificationCenterStub notificationCenter) {
        this.notificationCenter = notificationCenter;
    }

    @Override
    public NotificationCenterStub getNotificationCenter() {
        return notificationCenter;
    }

    @Override
    public NotificationCard deleteNotificationByIndex(Index targetIndex) throws NotificationNotFoundException {
        return null;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }
}

class AddressbookStub extends AddressBook {
    private LinkedList<Notification> notifications;
    private Semaphore semaphore;

    public AddressbookStub() {
        this.notifications = new LinkedList<>();
        semaphore = new Semaphore(1);
    }
}

class EmailCommandStub {
    private final Index targetIndex;
    private ModelStub model;
    private Person owner;

    public EmailCommandStub(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public void setData(ModelStub model) {
        this.model = model;
    }

    /**
     * Mimics the behavior of preprocessUndoableCommand() in EmailCommand
     */
    public void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards() - 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
        NotificationCardStub notificationCard = model.getNotificationCenter().getNotificationCardStub(targetIndex);
        String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
        owner = model.getAddressBook().findPersonById(Integer.parseInt(ownerId));
    }

    public Person getPerson() {
        return owner;
    }
}

