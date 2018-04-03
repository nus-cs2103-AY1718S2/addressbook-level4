package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.skill.Skill;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new CurrentPosition("Software Engineer"),
                new Company("Google"), new ProfilePicture(),
                getSkillSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new CurrentPosition("Student"),
                new Company("NUS"), new ProfilePicture(), getSkillSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),  new CurrentPosition("Marketing Intern"),
                new Company("Facebook"), new ProfilePicture(), getSkillSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new CurrentPosition("Software Engineer"),
                new Company("Facebook"), new ProfilePicture(), getSkillSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new CurrentPosition("Student"),
                new Company("NTU"), new ProfilePicture(), getSkillSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),  new CurrentPosition("Backend Engineer"),
                new Company("Apple"), new ProfilePicture(), getSkillSet("colleagues"))
        };
    }

    public static Job[] getSampleJobs() {
        return new Job[] {
            new Job(new Position("Software Engineer"), new Team("Cloud Services"), new Location("Singapore"),
                new NumberOfPositions("2"), getSkillSet("Java", "Algorithms")),
            new Job(new Position("Marketing Intern"), new Team("Social Media Marketing"),
                new Location("Kuala Lampur, Malaysia"), new NumberOfPositions("1"), getSkillSet("Excel",
                    "Writing")),
            new Job(new Position("DevOps Engineer"), new Team("DevOps"), new Location("Singapore"),
                new NumberOfPositions("3"), getSkillSet("AWS", "SQL-Server")),
            new Job(new Position("Product Manager"), new Team("Mobile Products"), new Location("Singapore"),
                new NumberOfPositions("1"), getSkillSet("UI/UX", "Testing"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Job sampleJob : getSampleJobs()) {
                sampleAb.addJob(sampleJob);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateJobException e) {
            throw new AssertionError("sample data cannot contain duplicate jobs", e);
        }
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        HashSet<Skill> skills = new HashSet<>();
        for (String s : strings) {
            skills.add(new Skill(s));
        }

        return skills;
    }

}
