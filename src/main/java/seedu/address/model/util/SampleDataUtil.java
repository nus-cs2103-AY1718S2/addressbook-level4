package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Link;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.report.Proportion;
import seedu.address.model.report.Report;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");
    public static final Remark SAMPLE_REMARK = new Remark("Like fishing");

    public static final Link INIT_LINK = new Link("https://github.com/CS2103JAN2018-W11-B3/main");
    public static final Skill SAMPLE_SKILL = new Skill("Java");
    public static final Skill DATA_SKILL = new Skill("Python");


    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                       new Address("Blk 30 Geylang Street 29, #06-40"), EMPTY_REMARK, INIT_LINK, SAMPLE_SKILL,
                       getTagSet("FreshGrad", "Computing")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                       new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_REMARK, INIT_LINK, SAMPLE_SKILL,
                       getTagSet("FreshGrad", "Business")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                       new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_REMARK, INIT_LINK, SAMPLE_SKILL,
                       getTagSet("SEIntern", "Interviewing")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                       new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    EMPTY_REMARK, INIT_LINK, SAMPLE_SKILL, getTagSet("SEIntern", "Offered")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                       new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_REMARK, INIT_LINK, SAMPLE_SKILL,
                       getTagSet("SEIntern", "Screening")),
            new Person(new Name("Clinton Francis Barton"), new Phone("92624417"), new Email("royb@example.com"),
                       new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                       getTagSet("SEIntern", "Screening")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("a@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Screening")),
            new Person(new Name("Roy Doe"), new Phone("92624417"), new Email("b@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Rejected")),
            new Person(new Name("Sam Balakrishnan"), new Phone("92624417"), new Email("c@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Offered")),
            new Person(new Name("Alex Smith"), new Phone("92624417"), new Email("d@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Interviewing")),
            new Person(new Name("Jacques Duquesne"), new Phone("92624417"), new Email("e@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, DATA_SKILL,
                    getTagSet("SEIntern", "Interviewing", "Intern")),
            new Person(new Name("Pietro Maximoff"), new Phone("92624417"), new Email("f@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Screening")),
            new Person(new Name("Wanda Maximoff"), new Phone("92624417"), new Email("j@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_REMARK, INIT_LINK, SAMPLE_SKILL,
                    getTagSet("SEIntern", "Screening", "Experienced"))
        };
    }

    public static Job[] getSampleJobs() {
        return new Job[] {
            new Job(new JobTitle("Backend Engineer"), new Location("Geylang"), new Skill("Java,SQL"),
                    getTagSet("SEIntern")),
            new Job(new JobTitle("Frontend Engineer"), new Location("Serangoon"), new Skill("JavaScript,CSS,HTML"),
                    getTagSet("FreshGrad")),
            new Job(new JobTitle("Data Scientist"), new Location("Ang Mo Kio"), new Skill("Python, R"),
                    getTagSet("Experienced")),
            new Job(new JobTitle("Systems Engineer"), new Location("Tampines"), new Skill("ALL"),
                    getTagSet("ALL")),
            new Job(new JobTitle("Frontend Developer"), new Location("Aljunied"), new Skill("ALL"),
                    getTagSet("Experienced")),
            new Job(new JobTitle("Data Engineer"), new Location("ALL"), new Skill("Python, R"),
                    getTagSet("Intern"))
        };
    }
    // @@author anh2111
    public static Report[] getSampleReports() throws InterruptedException {
        ArrayList<Report> sampleHistory = new ArrayList<>();

        Tag samplePopulation = new Tag("SEIntern");

        List<Proportion> sampleListA = new ArrayList<>();
        sampleListA.add(new Proportion("Screening", 10, 10));
        sampleListA.add(new Proportion("Interviewing", 1, 1));
        Report reportA = new Report(samplePopulation, sampleListA, 11);

        TimeUnit.SECONDS.sleep(2); // to make the data more reasonable

        List<Proportion> sampleListB = new ArrayList<>();
        sampleListB.add(new Proportion("Screening", 9, 9));
        sampleListB.add(new Proportion("Interviewing", 2, 2));
        Report reportB = new Report(samplePopulation, sampleListB, 11);

        TimeUnit.SECONDS.sleep(2); // to make the data more reasonable

        List<Proportion> sampleListC = new ArrayList<>();
        sampleListC.add(new Proportion("Screening", 8, 8));
        sampleListC.add(new Proportion("Interviewing", 3, 3));
        Report reportC = new Report(samplePopulation, sampleListC, 11);

        return new Report[] {
            reportA,
            reportB,
            reportC
        };
    }
    // @@author
    public static Interview[] getSampleInterviews() {
        return new Interview[] {
            new Interview(new InterviewTitle("Backend Interview"), new Name("David"), new Date("01.01.2015"),
                    new InterviewLocation("One North")),
            new Interview(new InterviewTitle("Frontend Interview"), new Name("Joe"), new Date("01.02.2016"),
                    new InterviewLocation("SOC")),
            new Interview(new InterviewTitle("Data Scientist Interview"), new Name("Kelvin"),
                    new Date("01.03.2017"), new InterviewLocation("NUS")),
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
            for (Report sampleReport : getSampleReports()) {
                sampleAb.addReport(sampleReport);
            }
            for (Interview sampleInterview: getSampleInterviews()) {
                sampleAb.addInterview(sampleInterview);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateJobException e) {
            throw new AssertionError("sample data cannot contain duplicate jobs", e);
        } catch (DuplicateInterviewException e) {
            throw new AssertionError("sample data cannot contain duplicate interviews", e);
        } catch (InterruptedException e) {
            throw new Error("error in generating sample reports", e);
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
