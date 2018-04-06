package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.exceptions.DuplicateTeamException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Remark("Sign him for one more year"),
                new TeamName("Arsenal"),
                getTagSet("redCard"), new Rating("3"), new Position("1"), new JerseyNumber("2")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark(""), new TeamName("Arsenal"),
                getTagSet("colleagues", "friends"), new Rating("1"), new Position("1"), new JerseyNumber("2")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new TeamName("Chelsea"),
                getTagSet("yellowCard"), new Rating("4"), new Position("1"), new JerseyNumber("2")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark(""),
                    new TeamName("goodAttitude"), getTagSet("injured"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new TeamName("Chelsea"),
                getTagSet("badAttitude"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new TeamName("Chelsea"),
                getTagSet("yellowCard"), new Rating("0"), new Position("1"), new JerseyNumber("2"))
        };
    }

    public static Team[] getSampleTeams()  {
        return new Team[] {
            new Team(new TeamName("Arsenal")),
            new Team(new TeamName("Chelsea"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Team sampleTeam : getSampleTeams()) {
                sampleAb.createTeam(sampleTeam);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateTeamException e) {
            throw new AssertionError(" sample data cannot contain duplicate teams", e);
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
