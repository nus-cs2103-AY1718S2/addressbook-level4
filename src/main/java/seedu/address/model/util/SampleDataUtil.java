package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cell.Cell;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("1-1 [Old address: Blk 30 Geylang Street 29, #06-40]"), new Role("p"),
                getTagSet("ChickenAllergy"), true),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Role("g"),
                getTagSet("Vegetarian")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("1-1 [Old address: Blk 11 Ang Mo Kio Street 74, #11-04]"), new Role("p"),
                getTagSet("ViolentTendencies"), true),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Role("g"),
                getTagSet()),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Role("p"),
                getTagSet("HalalDiet")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Role("g"),
                getTagSet("CounsellorTrained")),
            new Person(new Name("John Tan"), new Phone("85112935"), new Email("johnt@example.com"),
                new Address("Blk 504 Elias Road, #22-11"), new Role("g"),
                getTagSet("Sharpshooter")),
            new Person(new Name("Abdulluh Muhammad"), new Phone("91882734"), new Email("dulluhm@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Role("g"),
                getTagSet("HalalDiet")),
            new Person(new Name("Max Balakrishnan"), new Phone("98672261"), new Email("maxb@example.com"),
                new Address("Blk 402 Kent Ridge Street 9, #11-31"), new Role("g"),
                getTagSet("MMATrained")),
            new Person(new Name("Xiang Tao"), new Phone("93255626"), new Email("xiangtao@example.com"),
                new Address("38B Craig Road, 089676, Singapore"), new Role("g"),
                getTagSet("PeanutAllergy")),
            new Person(new Name("An Luo"), new Phone("89923718"), new Email("anluo@example.com"),
                new Address("Blk 568 Ganges Avenue, 01-100 160568, Singapore"), new Role("g"),
                getTagSet("NightShift")),
            new Person(new Name("Shui Peng"), new Phone("94772831"), new Email("shuipeng@example.com"),
                new Address("63 Arab Street  199760, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("Xinyi Pan"), new Phone("89917238"), new Email("xinyip@example.com"),
                new Address("1 Pickering Street #07-03 Great Eastern Centre, 048659, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("Shi Mao"), new Phone("90197583"), new Email("shimao@example.com"),
                new Address("10 Hoe Chiang Road #01-01 Keppel Towers, 089315, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Role("p"),
                getTagSet("SubmissionJujitsuTrained")),
            new Person(new Name("Yimu Huang"), new Phone("89371844"), new Email("yimuhuang@example.com"),
                new Address("227 Ubi Avenue 4, 408815, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("Dan Zhan"), new Phone("97773917"), new Email("danzhan@example.com"),
                new Address("166 Upper East Coast Road 455270, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("Rachiket Arya"), new Phone("99283411"), new Email("rachicketarya@example.com"),
                new Address("143 Cecil Street #16-04 Gb Building, 069542, Singapore"), new Role("p"),
                getTagSet()),
            new Person(new Name("Hafiz Muhammad"), new Phone("89923645"), new Email("hafizm@example.com"),
                new Address("Golden Sultan Plaza 100 Jalan Sultan #01-32 199001, Singapore"), new Role("p"),
                getTagSet("HalalDiet")),
            new Person(new Name("Vinodhanan SO Balakrishnan"), new Phone("88199374"), new Email("vinodb@example.com"),
                new Address("Blk 402 Kent Ridge Street 9, #11-31"), new Role("p"),
                getTagSet()),
            new Person(new Name("Philos Tsai"), new Phone("81227783"), new Email("philostsai@example.com"),
                new Address("11 Chancery Lane #01-04, 309502, Singapore"), new Role("p"),
                getTagSet("French")),
            new Person(new Name("Sarah Goh"), new Phone("90288173"), new Email("gohst@example.com"),
                new Address("3-5 [Old address: Tung Ann Association Building 141 Cecil Street #03-02]"),
                new Role("p"), getTagSet(), true)
        };
    }

    //@@author sarahgoh97 TODO
    public static Cell[] getSampleCells() {
        Person alex = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("1-1 [Old address: Blk 30 Geylang Street 29, #06-40]"), new Role("p"),
                getTagSet("ChickenAllergy"), true);
        Person charlotte = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("1-1 [Old address: Blk 11 Ang Mo Kio Street 74, #11-04]"), new Role("p"),
                getTagSet("ViolentTendencies"), true);
        Person sarah = new Person(new Name("Sarah Goh"), new Phone("90288173"), new Email("gohst@example.com"),
                new Address("3-5 [Old address: Tung Ann Association Building 141 Cecil Street #03-02]"),
                new Role("p"), getTagSet(), true);

        return new Cell[] {
            new Cell(getPrisoners(alex, charlotte), "1-1", false),
            new Cell(1, 2), new Cell(1, 3), new Cell(1, 4), new Cell(1,  5),
            new Cell(2, 1), new Cell(2, 2), new Cell(2, 3), new Cell(2, 4), new Cell(2,  5),
            new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4),
            new Cell(getPrisoners(sarah), "3-5", true)
        };
    }

    public static ArrayList<Person> getPrisoners(Person... prisoners) {
        ArrayList<Person> list = new ArrayList<Person>();
        for (Person p: prisoners) {
            list.add(p);
        }
        return list;
    }

    //@@author zacci
    public static User[] getSampleUsers() {
        return new User[] {
            new User("prisonguard", "password1", 1),
            new User("prisonleader", "password2", 2),
            new User("prisonwarden", "password3", 3),
            new User("prisonguard2", "password1", 1),
            new User("prisonguard3", "password1", 1),
            new User("prisonleader2", "password2", 2),
            new User("prisonleader3", "password2", 2),
            new User("prisonwarden2", "password3", 3)
        };
    }
    //@@author

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Cell sampleCell : getSampleCells()) {
                sampleAb.addCell(sampleCell);
            }
            for (User sampleUser : getSampleUsers()) {
                sampleAb.addUser(sampleUser);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (UserAlreadyExistsException uaee) {
            throw new AssertionError("sample data cannot contain duplicate users", uaee);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
