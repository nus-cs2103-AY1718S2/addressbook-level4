package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getGroupSet("friends"), getPreferenceSet("videoGames")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getGroupSet("colleagues", "friends"), getPreferenceSet("cosmetics", "shoes")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getGroupSet("neighbours"), getPreferenceSet("skirts")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getGroupSet("family"), getPreferenceSet("shoes")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getGroupSet("classmates"), getPreferenceSet("videoGames", "computers")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getGroupSet("colleagues"), getPreferenceSet("photobooks", "notebooks"))
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order(
                new OrderInformation("Books"), new OrderStatus("ongoing"), new Price("12.50"),
                new Quantity("3"), new DeliveryDate("12-08-2018")
            ),
            new Order(
                new OrderInformation("Facewash"), new OrderStatus("done"), new Price("15.00"),
                new Quantity("2"), new DeliveryDate("05-11-2018")
            ),
            new Order(
                new OrderInformation("Chocolates"), new OrderStatus("ongoing"), new Price("5.00"),
                new Quantity("10"), new DeliveryDate("22-04-2018")
            ),
            new Order(
                new OrderInformation("Shoes"), new OrderStatus("ongoing"), new Price("129.99"),
                new Quantity("1"), new DeliveryDate("10-05-2018")
            )
        };
    }

    public static CalendarEntry[] getSampleCalendarEntries() {
        return new CalendarEntry[] {
            new CalendarEntry(
                    new EntryTitle("Meeting with Boss"), new StartDate("20-04-2018"), new EndDate("20-04-2018"),
                    new StartTime("08:00"), new EndTime("12:00")
            ),
            new CalendarEntry(
                    new EntryTitle("Meeting with Books Supplier"), new StartDate("22-04-2018"),
                    new EndDate("22-04-2018"), new StartTime("10:00"), new EndTime("12:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Meeting with Cars Supplier"), new StartDate("23-04-2018"),
                    new EndDate("23-04-2018"), new StartTime("15:00"), new EndTime("18:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Deliver Books"), new StartDate("28-04-2018"),
                    new EndDate("28-04-2018"), new StartTime("13:00"), new EndTime("14:00")
            ),
            new CalendarEntry(
                    new EntryTitle("RoadShow at VivoCity"), new StartDate("20-05-2018"),
                    new EndDate("22-05-2018"), new StartTime("12:00"), new EndTime("18:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Get comicbooks from Supplier"), new StartDate("02-05-2018"),
                    new EndDate("02-05-2018"), new StartTime("18:00"), new EndTime("20:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Design promo poster for Cars"), new StartDate("20-04-2018"),
                    new EndDate("22-05-2018"), new StartTime("00:00"), new EndTime("12:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Import cosmetics"), new StartDate("23-04-2018"),
                    new EndDate("15-05-2018"), new StartTime("00:00"), new EndTime("18:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Meet Stakeholders"), new StartDate("06-06-2018"),
                    new EndDate("06-06-2018"), new StartTime("10:00"), new EndTime("14:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Meeting with Chocolate Supplier"), new StartDate("22-07-2018"),
                    new EndDate("22-07-2018"), new StartTime("14:00"), new EndTime("16:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Find Supplier for Cars"), new StartDate("22-04-2018"),
                    new EndDate("22-05-2018"), new StartTime("00:00"), new EndTime("23:59")
            ),
            new CalendarEntry(
                    new EntryTitle("Create Website for products"), new StartDate("20-03-2018"),
                    new EndDate("22-04-2018"), new StartTime("08:00"), new EndTime("17:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Meet Supplier for History Books"), new StartDate("01-05-2018"),
                    new EndDate("01-05-2018"), new StartTime("18:00"), new EndTime("19:00")
            ),
            new CalendarEntry(
                    new EntryTitle("Meet bosses for monthly review"), new StartDate("30-04-2018"),
                    new EndDate("30-04-2018"), new StartTime("10:00"), new EndTime("17:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Prepare for monthly review"), new StartDate("22-04-2018"),
                    new EndDate("29-04-2018"), new StartTime("00:00"), new EndTime("23:59")
            ),
            new CalendarEntry(
                    new EntryTitle("Meeting new Client Roy"), new StartDate("24-04-2018"),
                    new EndDate("24-04-2018"), new StartTime("20:00"), new EndTime("22:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Prepare for July Roadshow"), new StartDate("26-05-2018"),
                    new EndDate("02-07-2018"), new StartTime("10:00"), new EndTime("23:59")
            ),
            new CalendarEntry(
                    new EntryTitle("July Roadshow at Orchard"), new StartDate("03-07-2018"),
                    new EndDate("13-07-2018"), new StartTime("10:00"), new EndTime("20:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Meet Charlotte"), new StartDate("15-05-2018"),
                    new EndDate("15-05-2018"), new StartTime("12:00"), new EndTime("12:30")
            ),
            new CalendarEntry(
                    new EntryTitle("Trip to Europe"), new StartDate("29-07-2018"),
                    new EndDate("28-08-2018"), new StartTime("10:00"), new EndTime("22:30")
            ),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }

            for (Order sampleOrder : getSampleOrders()) {
                sampleAb.addOrderToOrderList(sampleOrder);
            }
            return sampleAb;
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("sample data cannot contain duplicate persons", dpe);
        } catch (DuplicateOrderException doe) {
            throw new AssertionError("sample data cannot contain duplicate orders", doe);
        }
    }

    /**
     * Returns a group set containing the list of strings given.
     */
    public static Set<Group> getGroupSet(String... strings) {
        HashSet<Group> groupTags = new HashSet<>();
        for (String s : strings) {
            groupTags.add(new Group(s));
        }

        return groupTags;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Preference> getPreferenceSet(String... strings) {
        HashSet<Preference> prefTags = new HashSet<>();
        for (String s : strings) {
            prefTags.add(new Preference(s));
        }

        return prefTags;
    }

    //@@author SuxianAlicia
    /**
     * Returns a {@code CalendarManager} with no {@code CalendarEntry} in it.
     */
    public static ReadOnlyCalendarManager getSampleCalendarManager() {
        CalendarManager sampleCm = new CalendarManager();
        try {
            for (CalendarEntry sampleEntry: getSampleCalendarEntries()) {
                sampleCm.addCalendarEntry(sampleEntry);
            }
        } catch (DuplicateCalendarEntryException dcee) {
            throw new AssertionError("sample data should not contain duplicate calendar entries.");
        }
        return sampleCm;
    }
    //@@author
}
