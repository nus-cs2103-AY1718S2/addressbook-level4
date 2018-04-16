package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
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
            new Person(new Name("Aaron Ramsey"), new Phone("87438807"), new Email("aramsey@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Remark("Sign him for one more year"),
                new TeamName("Arsenal"),
                getTagSet("redCard"), new Rating("3"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Hector Moruno"), new Phone("99272758"), new Email("hectorm@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark(""), new TeamName("Arsenal"),
                getTagSet("fastRunner", "goodAttitude"), new Rating("1"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Cesc Fabregas"), new Phone("93210283"), new Email("cescfabregas@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new TeamName("Chelsea"),
                getTagSet("yellowCard"), new Rating("4"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new TeamName("Chelsea"),
                    getTagSet("yellowCard"), new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Ospina"), new Phone("99272758"), new Email("Ospina@arsenal.com"),
                    new Address("70 Jurong Central Circle"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("fastRunner", "goodAttitude"), new Rating("1"), new Position("1"), new JerseyNumber("22"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Cech"), new Phone("93210283"), new Email("cech@arsenal.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new TeamName("Chelsea"),
                    getTagSet("yellowCard"), new Rating("4"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Mertesacker"), new Phone("95432223"), new Email("mertesacker@arsenal.com"),
                    new Address("Blk 430 Pasir Ris Street 33, #12-26"), new Remark(""),
                    new TeamName("Arsenal"), getTagSet("injured"),
                    new Rating("0"), new Position("1"), new JerseyNumber("23"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Koscielny"), new Phone("92352021"), new Email("koscielny@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("badAttitude"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Holding"), new Phone("92624417"), new Email("holding@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("fastRunner"), new Rating("0"), new Position("1"), new JerseyNumber("7"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Monreal"), new Phone("99272758"), new Email("monreal@arsenal.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("fastRunner", "goodAttitude"), new Rating("1"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Mustafi"), new Phone("93215483"), new Email("mustafi@arsenal.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("yellowCard"), new Rating("4"), new Position("1"), new JerseyNumber("4"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Chambers"), new Phone("91031282"), new Email("chambers@arsenal.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark(""),
                    new TeamName("Arsenal"), getTagSet("injured"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Chambers"), new Phone("92492021"), new Email("chambers@arsenal.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("badAttitude"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark(""),
                    new TeamName("Chelsea"), getTagSet("injured"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Irfan Fandi"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new TeamName("Chelsea"),
                getTagSet("badAttitude"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Mavropanos"), new Phone("92624417"), new Email("mavropanos@arsenal.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new TeamName("Chelsea"),
                    getTagSet("yellowCard"), new Rating("0"), new Position("1"), new JerseyNumber("98"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Kolasinac"), new Phone("99272758"), new Email("kolasinac@arsenal.com"),
                    new Address("70 Jurong Central Circle"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("fastRunner", "goodAttitude"), new Rating("1"), new Position("2"), new JerseyNumber("52"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Mkhitaryan"), new Phone("93210283"), new Email("mkhitaryan@arsenal.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("yellowCard"), new Rating("4"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Wilshere"), new Phone("95432223"), new Email("wilshere@arsenal.com"),
                    new Address("Blk 430 Pasir Ris Street 33, #12-26"), new Remark(""),
                    new TeamName("Arsenal"), getTagSet("injured"),
                    new Rating("0"), new Position("1"), new JerseyNumber("23"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Ozil"), new Phone("92352021"), new Email("ozil@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("badAttitude"),
                    new Rating("0"), new Position("1"), new JerseyNumber("2"),
                    new Avatar("<UNSPECIFIED>")),
            new Person(new Name("Xhaka"), new Phone("92624417"), new Email("xhaka@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new TeamName("Arsenal"),
                    getTagSet("fastRunner"), new Rating("0"), new Position("1"), new JerseyNumber("7"),
                    new Avatar("<UNSPECIFIED>")),

        };
    }

    //@@author Codee
    public static Team[] getSampleTeams()  {
        return new Team[] {
            new Team(new TeamName("Arsenal")),
            new Team(new TeamName("Chelsea"))
        };
    }
    //@@author

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            //@@author Codee
            for (Team sampleTeam : getSampleTeams()) {
                sampleAb.createTeam(sampleTeam);
            }
            //@@author
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
                sampleAb.addPersonToTeam(samplePerson, samplePerson.getTeamName());
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
