package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.LateInterest;
import seedu.address.model.person.customer.MoneyBorrowed;
import seedu.address.model.person.customer.StandardInterest;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.runner.Runner;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    //@@author jonleeyz
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Customer(new Name("Xiao Ming"), new Phone("88888888"), new Email("xiao@ming.com"),
                    new Address("The Fullerton"),
                    getTagSet("richxiaoming", "mingdynasty", "HighSES"), new MoneyBorrowed(314159265),
                    createDate(2017, 5, 7), createDate(2018, 5, 7),
                    new StandardInterest(9.71), new LateInterest(), new Runner()),
            new Customer(new Name("Korean Defender"), new Phone("99994321"),
                    new Email("kalbitanglover@tourism.korea.com"), new Address("The Hwang's"),
                    getTagSet("defenderOfTheFree", "defenderOfKalbiTang", "yummeh", "UTownHeritage"),
                    new MoneyBorrowed(413255),
                    createDate(2010, 10, 3), createDate(2019, 1, 1),
                    new StandardInterest(5.4), new LateInterest(), new Runner()),
            new Customer(new Name("Bob the Builder"), new Phone("92334532"), new Email("bob@bobthebuilder.com"),
                    new Address("IKEA Alexandra"),
                    getTagSet("FatherOfHDB", "InBobWeTrust"), new MoneyBorrowed(0.24),
                    createDate(1965, 8, 9), createDate(2015, 8, 9),
                    new StandardInterest(0.0005), new LateInterest(), new Runner()),
            new Runner(new Name("Ah Seng"), new Phone("90011009"), new Email("quick_and_easy_money@hotmail.com"),
                    new Address("Marina Bay Sands"),
                    getTagSet("EmployeeOfTheMonth", "InvestorFirstGrade", "HighSES"), new ArrayList<>()),
            new Runner(new Name("Mas Selamat Kastari"), new Phone("999"), new Email("kastari@johorbahru.my"),
                    new Address("Internal Security Department"),
                    getTagSet("BeatTheSystem", "BeatByTheSystem"), new ArrayList<>()),
            new Customer(new Name("Aunty Kim"), new Phone("99994321"), new Email("hotkorean1905@hotmail.com"),
                    new Address("I'm Kim Korean BBQ"),
                    getTagSet("RichAunty", "KBBQBossLady", "Aunty"),
                    new MoneyBorrowed(413255),
                    createDate(2010, 10, 3), createDate(2019, 1, 1),
                    new StandardInterest(5.4), new LateInterest(), new Runner()),
            new Runner(new Name("Leon Tay"), new Phone("93498349"), new Email("laoda@leontay349.com"),
                    new Address("Bao Mei Boneless Chicken Rice"),
                    getTagSet("LaoDa", "349", "Joker"), new ArrayList<>()),
            new Runner(new Name("Ping An"), new Phone("93698369"), new Email("pingan@houseofahlong.com"),
                    new Address("Ang Mo Kio Police Divison HQ"),
                    getTagSet("UndercoverRunner", "TripleAgent", "Joker"), new ArrayList<>()),
            new Customer(new Name("Da Ming"), new Phone("83699369"), new Email("da@ming.com"),
                    new Address("Fountain of Wealth"),
                    getTagSet("RicherDaMing", "BigMing", "MingSuperior", "mingdynasty"), new MoneyBorrowed(98789060),
                    createDate(2017, 3, 1), createDate(2020, 12, 5),
                    new StandardInterest(3.14), new LateInterest(), new Runner()),
            //@@author
            //@@author melvintzw
            new Runner(new Name("The Terminator"), new Phone("84444448"), new Email("protection@money.com"),
                    new Address("Fountain of Wealth"),
                    getTagSet("Arnold", "HealthIsWealth"), new ArrayList<>()),
            new Runner(new Name("Donny J"), new Phone("0013451945"), new Email("protection@money.com"),
                    new Address("Changi Prison Complex"),
                    getTagSet("Inactive", "Disavowed", "Joker"), new ArrayList<>()),
            new Customer(new Name("Zhong Ming"), new Phone("91121345"), new Email("important@ming.com"),
                    new Address("Merlion"),
                    getTagSet("ImportantMing", "ZhongMing", "MingGreatest", "mingdynasty", "HighSES"),
                    new MoneyBorrowed(98789060),
                    createDate(2014, 6, 7), createDate(2016, 11, 9),
                    new StandardInterest(1.75), new LateInterest(), new Runner()),
            new Runner(new Name("Wu Lui"), new Phone("90011009"), new Email("nigerian_prince@bankofchina.com"),
                    new Address("The LINQ Hotel & Casino"),
                    getTagSet("OnTheStrip", "HighRoller"), new ArrayList<>()),
            new Customer(new Name("Queen Samsung"), new Phone("000"), new Email("king@kim.com"),
                    new Address("Samsung Innovation Museum"),
                    getTagSet("Korean", "Royalty", "Untouchable", "HighSES"), new MoneyBorrowed(999999999),
                    createDate(2000, 1, 1), createDate(2112, 12, 12),
                    new StandardInterest(0.01), new LateInterest(), new Runner()),
            new Customer(new Name("Ma Qing Da Wen"), new Phone("764543543123"), new Email("important@ming.com"),
                    new Address("Town Green"),
                    getTagSet("ForeignContact", "Code49"), new MoneyBorrowed(1124),
                    createDate(2003, 4, 11), createDate(2028, 5, 29),
                    new StandardInterest(5.76), new LateInterest(), new Runner()),
            new Customer(new Name("Lim Tin Ken"), new Phone("81140976"), new Email("limtincan@u.nus.edu"),
                    new Address("Cinnamon College"),
                    getTagSet("USP", "Cinnamonster"), new MoneyBorrowed(0.1),
                    createDate(2018, 4, 1), createDate(2018, 11, 11),
                    new StandardInterest(1000), new LateInterest(), new Runner()),
            new Customer(new Name("Master Wu Gui"), new Phone("94523112"), new Email("turtle@dojo.net"),
                    new Address("The Singapore Island Country Club"),
                    getTagSet("MOJO", "HighSES"), new MoneyBorrowed(645644),
                    createDate(2012, 3, 17), createDate(2015, 7, 30),
                    new StandardInterest(0.9), new LateInterest(), new Runner()),
            new Customer(new Name("Hilarious Kleiny"), new Phone("91208888"), new Email("turtle@dojo.net"),
                    new Address("Institute of Mental Health"),
                    getTagSet("SiaoLiao", "Joker"), new MoneyBorrowed(12064543),
                    createDate(2010, 10, 10), createDate(2022, 9, 22),
                    new StandardInterest(2.309), new LateInterest(), new Runner()),
        };
    }

    /**
     * helper method to generate a custom meaningful date.
     *
     * @return
     */
    private static Date createDate(int year, int month, int dayOfMonth) {
        return createDate(year, month, dayOfMonth, 0, 0, 0);
    }

    /**
     * helper method to generate a custom meaningful date.
     *
     * @return
     */
    private static Date createDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        return calendar.getTime();
    }
    //@@author

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
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
