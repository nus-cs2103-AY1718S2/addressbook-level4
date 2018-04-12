package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Test;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Nric("S8743880X"), getTagSet("3A"),
                getSubjectSet("English A2"), new Remark("Class Rep"), new Cca("BasketBall", "Member"),
                    new InjuriesHistory(""), new NextOfKin("James Yeoh", "978",
                    "j@gmail.com", "")),

            new Person(new Name("Bernice Yu"), new Nric("S9927275Z"),
                getTagSet("3A"), getSubjectSet("EMath A1"), new Remark("Math Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("James Yu", "789",
                    "h@gmail.com", "")),


            new Person(new Name("Charlotte Oliveiro"), new Nric("S9321028H"),
                getTagSet("3G"), getSubjectSet("Chem B3"), new Remark("Chemistry Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(""), new NextOfKin("James Oliveiro", "456",
                    "jo@gmail.com", "")),

            new Person(new Name("David Li"), new Nric("S9103128J"),
                getTagSet("4D"), getSubjectSet("Phy B3"), new Remark("Physics Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("James Li", "345", "jl@gmail.com",
                    "")),

            new Person(new Name("Irfan Ibrahim"), new Nric("S9249202K"),
                getTagSet("3G"), getSubjectSet("Geog B4"), new Remark("Geography Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("James Ibrahim", "456",
                    "ji@gmail.com", "")),

            new Person(new Name("Roy Balakrishnan"), new Nric("S9262441U"),
                getTagSet("4G"), getSubjectSet("Chinese C5"), new Remark("Chinese Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("James Balakrishnan", "3457",
                    "jb@gmail.com", "")),

            new Person(new Name("John"), new Nric("S9123123A"),
                getTagSet("4G"), getSubjectSet("ELit C5"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("James", "5678", "james@gmail.com",
                    "")),

            new Person(new Name("Ben"), new Nric("S9456456B"),
                getTagSet("3G"), getSubjectSet("Econs A1"), new Remark("Econs Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("Jon", "98745623", "jon@gmail.com",
                    "")),

            new Person(new Name("Jill"), new Nric("S9321321C"),
                getTagSet("4G"), getSubjectSet("German C5"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("Jack", "98567321", "jack@gmail.com",
                    "")),

            new Person(new Name("Tom"), new Nric("S9789789A"),
                getTagSet("3A"), getSubjectSet("Bio A2"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NextOfKin("Tim", "96784562", "tim@gmail.com",
                    "")),

            new Person(new Name("Mary"), new Nric("S9654654B"),
                getTagSet("5D"), getSubjectSet("HChi A1"), new Remark("Higher Chinese Rep"),
                    new Cca("BasketBall", "Member"), new InjuriesHistory(" "), new NextOfKin("Martha",
                    "98562341", "martha@gmail.com", ""))
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

    //@@author TeyXinHui
    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        HashSet<Subject> subjects = new HashSet<>();
        for (String s : strings) {
            subjects.add(new Subject(s));
        }

        return subjects;
    }
    //@@author
}
