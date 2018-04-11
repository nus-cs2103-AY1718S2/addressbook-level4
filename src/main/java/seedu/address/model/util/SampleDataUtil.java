package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SessionLogs;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final String SESSION_LOG_DIVIDER = "\n\n=============================================";
    public static final String SESSION_LOG_DATE_PREFIX = "\nSession log date added: ";

    public static final String SAMPLE_SESSION_REPORT_A =
            SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + "11/11/2017 15:01"
            + "Presenting Problem\n"
            + "Stan is a 32 year-old, homosexual, white male. Stan has been using methamphetamine for the "
            + "past eight years. Initially, he was smoking the drug. Five years ago he began using intravenously."
            + " Stan injects methamphetamine 3-5 times daily.\n"
            + "Personal Status\n"
            + "Stan is the middle of three siblings. He has an older sister and a younger brother. Stan was born and "
            + "raised in Arkansas. He moved to the West Coast at the age of 20. He was thrown out of the family home"
            + " after revealing his homosexuality. Stan became sexually active at the age of 13. He has been engaging"
            + " in promiscuous, unprotected sexual activity since the age of 20. Stan was involved in the gay party "
            + "circuit in the San Francisco Bay area during the ten years he lived there. Upon moving to NJ, he began "
            + "frequenting the gay bathhouses in NYC. The majority of Stan’s social circle is made up of individuals"
            + " he"
            + " met in these establishments. Stan reports that they are all methamphetamine users. Stan has been "
            + "sexually involved with several of them.\n"
            + "Drug History and Current Pattern of Use\n"
            + "Stan first began experimenting with drugs at the age of 15. He drank alcohol every weekend and smoked"
            + " marijuana on an almost daily basis throughout his teens. At 20 he began drinking heavily and "
            + "experimenting with Ecstasy."
            + " At 24 Stan was introduced to methamphetamine. He was initially smoking it 3-6 times weekly. "
            + "Five years ago he began injecting. Within a few months he became a daily user. He is currently injecting"
            + " methamphetamine 3-5 times daily.\n";

    public static final String SAMPLE_SESSION_REPORT_B =
            SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + "01/01/2018 14:01"
            + "\n\nPresenting Problem\n"
            + "Alex is a 78 year-old, chinese male. Stan has been using methamphetamine for the "
            + "past eight years. Initially, he was smoking the drug. Five years ago he began using intravenously."
            + " Alex injects ketamine 3-5 times daily.\n"
            + "Personal Status\n"
            + "Alex is a high school graduate. He briefly attended a community college but dropped out after"
            + " two semesters. He appears to be of above average intelligence. He reports being an A/B student "
            + "in high school.\n"
            + "Drug History and Current Pattern of Use\n"
            + "Alex first began experimenting with drugs at the age of 15. He drank alcohol every weekend and smoked"
            + " marijuana on an almost daily basis throughout his teens. At 20 he began drinking heavily and "
            + "experimenting with Ecstasy."
            + " At 48, Alex was introduced to ketamine. He was initially smoking it 3-6 times weekly. "
            + "Five years ago he began injecting. Within a few months he became a daily user. He is currently injecting"
            + " ketamine 3-5 times daily.\n"
            + SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + "12/01/2018 11:20"
            + "Alex is more stable now. His condition seems to be improving. He reports of voices that he hears every"
            + "night, however no suicidal tendencies."
            + "\n Session was smooth but patient requires frequent observation.";

    public static final String SAMPLE_SESSION_REPORT_C =
            SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + "23/03/2018 17:45"
            + "\n\nToday session with Jun Kai was interesting. He exhibited a sense of altruism despite his usual"
            + "character. He dressed surprisingly more formal than usual. While his condition is stable,"
            + "more frequent observations needs to be done."
            + SESSION_LOG_DIVIDER + SESSION_LOG_DATE_PREFIX + "25/03/2018 11:22"
            + "\n\n blah blah blah....";



    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Poh Yao Boon"), new Phone("87438807"), new Email("alexpoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("priorityhigh", "DrugAddiction", "AlzheimersDisease"),
                new SessionLogs(SAMPLE_SESSION_REPORT_B)),
            new Person(new Name("Stan Low"), new Phone("92624417"), new Email("st.low@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("prioritymedium", "ADHD"), new SessionLogs(SAMPLE_SESSION_REPORT_A)),
            new Person(new Name("Ng Jun Kai"), new Phone("83318392"), new Email("jkng23@example.com"),
                new Address("Blk 223 Serangoon Ave 4, #02-120"),
                getTagSet("priorityhigh", "LoveSick"), new SessionLogs(SAMPLE_SESSION_REPORT_C)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("prioritylow", "OCD"), new SessionLogs("")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet( "prioritymedium", "CatLady", "Hoarder"), new SessionLogs("")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("prioritylow", "Exhibitionist", "SexAddict"), new SessionLogs("")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("priorityhigh", "PTSD"), new SessionLogs("")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("priorityhigh", "Depression"), new SessionLogs("")),

        };
    }

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

    public static AddressBook getSampleAddressBookForTest() {
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
