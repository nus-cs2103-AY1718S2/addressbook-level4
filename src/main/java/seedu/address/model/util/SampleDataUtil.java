package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Email;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameOfKin;
import seedu.address.model.person.NextOfKin;
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
                    new InjuriesHistory(""), new NameOfKin("James Yeoh")),

            new Person(new Name("Bernice Yu"), new Nric("S9927275Z"),
                getTagSet("3A"), getSubjectSet("EMath A1"), new Remark("Math Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("James Yu")),


            new Person(new Name("Charlotte Oliveiro"), new Nric("S9321028H"),
                getTagSet("3G"), getSubjectSet("Chem B3"), new Remark("Chemistry Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(""), new NameOfKin("James Oliveiro")),

            new Person(new Name("David Li"), new Nric("S9103128J"),
                getTagSet("4D"), getSubjectSet("Phy B3"), new Remark("Physics Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("James Li")),

            new Person(new Name("Irfan Ibrahim"), new Nric("S9249202K"),
                getTagSet("3G"), getSubjectSet("Geog B4"), new Remark("Geography Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("James Ibrahim")),

            new Person(new Name("Roy Balakrishnan"), new Nric("S9262441U"),
                getTagSet("4G"), getSubjectSet("Chinese C5"), new Remark("Chinese Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("James Balakrishnan")),

            new Person(new Name("John"), new Nric("S9123123A"),
                getTagSet("4G"), getSubjectSet("ELit C5"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("James")),

            new Person(new Name("Ben"), new Nric("S9456456B"),
                getTagSet("3G"), getSubjectSet("Econs A1"), new Remark("Econs Rep"),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("Jon")),

            new Person(new Name("Jill"), new Nric("S9321321C"),
                getTagSet("4G"), getSubjectSet("German C5"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("Jack")),

            new Person(new Name("Tom"), new Nric("S9789789A"),
                getTagSet("3A"), getSubjectSet("Bio A2"), new Remark(""),
                    new Cca("BasketBall", "Member"),
                    new InjuriesHistory(" "), new NameOfKin("Tim")),

            new Person(new Name("Mary"), new Nric("S9654654B"),
                getTagSet("5D"), getSubjectSet("HChi A1"), new Remark("Higher Chinese Rep"),
                    new Cca("BasketBall", "Member"), new InjuriesHistory(" "), new NameOfKin("Martha"))
        };
    }

    public static NextOfKin[] getSampleNextOfKin() {
        return new NextOfKin[] {
            new NextOfKin(new Name("James Yeoh"), new Nric("S9876345H"), new Phone("96784561"),
                    new Email("james@gmail.com"), new Remark("Father"))
        };
    }

    public static Appointment[] getSampleAppointments() {
        return new Appointment[] {
            new Appointment("Consultation", "04042018", "1200", "1300"),
            new Appointment("Remedial", "05052018", "1400", "1600")
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }

            for (Appointment sampleAppointment : getSampleAppointments()) {
                sampleAb.addAppointment(sampleAppointment);
            }
              
            for (NextOfKin sampleNextOfKin : getSampleNextOfKin()) {
                sampleAb.addNextOfKin(sampleNextOfKin);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("sample data cannot contain duplicate appointments", e);
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
