package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;
import static seedu.address.model.person.PersonSortUtil.compareNameLexicographically;
import static seedu.address.model.person.PersonSortUtil.getComparator;

import java.util.Comparator;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.tutee.Tutee;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TuteeBuilder;

//@@author yungyung04
public class PersonSortUtilTest {
    private static final String LOWER_ORDER = "a";
    private static final String MIDDLE_ORDER = "b";
    private static final String HIGHER_ORDER = "c";
    private static final String EDUCATION_LEVEL_PRIMARY = "primary";
    private static final String EDUCATION_LEVEL_SECONDARY = "secondary";
    private static final String EDUCATION_LEVEL_JUNIOR_COLLEGE = "junior college";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Person lowerOrder = new TuteeBuilder().withName(MIDDLE_ORDER).withEducationLevel(EDUCATION_LEVEL_PRIMARY)
            .withGrade(MIDDLE_ORDER).withSchool(MIDDLE_ORDER).withSubject(MIDDLE_ORDER).build();
    private Person higherOrder = new TuteeBuilder().withName(HIGHER_ORDER).withEducationLevel(EDUCATION_LEVEL_SECONDARY)
            .withGrade(HIGHER_ORDER).withSchool(HIGHER_ORDER).withSubject(HIGHER_ORDER).build();
    private Person versatileOrder;

    @Test
    public void getComparator_validNameCategory_compareSuccessfully() {
        //all first person's categories have lower lexicographical order
        Comparator<Person> comparator = getComparator(CATEGORY_NAME);
        int expected = lowerOrder.getName().fullName.compareTo(higherOrder.getName().fullName);
        assertCompareSuccessfully(comparator, expected, lowerOrder, higherOrder);

        //first person's name has lower lexicographical order and the other categories have equal order
        versatileOrder = new TuteeBuilder((Tutee) lowerOrder).withName(HIGHER_ORDER).build();
        expected = lowerOrder.getName().fullName.compareTo(versatileOrder.getName().fullName);
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's name has lower lexicographical order but the other categories have higher order
        versatileOrder = new TuteeBuilder((Tutee) higherOrder).withEducationLevel(EDUCATION_LEVEL_JUNIOR_COLLEGE)
                .withGrade(LOWER_ORDER).withSchool(LOWER_ORDER).withSubject(LOWER_ORDER).build();
        expected = lowerOrder.getName().fullName.compareTo(versatileOrder.getName().fullName);
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's name has equal lexicographical order
        expected = lowerOrder.getName().fullName.compareTo(lowerOrder.getName().fullName);
        assertCompareSuccessfully(comparator, expected, lowerOrder, lowerOrder);

        //first person's name has higher lexicographical order
        expected = higherOrder.getName().fullName.compareTo(lowerOrder.getName().fullName);
        assertCompareSuccessfully(comparator, expected, higherOrder, lowerOrder);
    }

    @Test
    public void getComparator_validEducationLevelCategory_compareSuccessfully() {
        //all first person's categories have lower lexicographical order
        Comparator<Person> comparator = getComparator(CATEGORY_EDUCATION_LEVEL);
        int expected = ((Tutee) lowerOrder).getEducationLevel().toString()
                .compareTo(((Tutee) higherOrder).getEducationLevel().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, higherOrder);

        //first person's education level has lower lexicographical order and the other categories have equal order
        versatileOrder = new TuteeBuilder((Tutee) lowerOrder).withEducationLevel(EDUCATION_LEVEL_SECONDARY).build();
        expected = ((Tutee) lowerOrder).getEducationLevel().toString()
                .compareTo(((Tutee) versatileOrder).getEducationLevel().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's education level has lower lexicographical order but the other categories have higher order
        versatileOrder = new TuteeBuilder((Tutee) higherOrder).withName(LOWER_ORDER)
                .withGrade(LOWER_ORDER).withSchool(LOWER_ORDER).withSubject(LOWER_ORDER).build();
        expected = ((Tutee) lowerOrder).getEducationLevel().toString()
                .compareTo(((Tutee) versatileOrder).getEducationLevel().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's education level has equal lexicographical order
        expected = ((Tutee) lowerOrder).getEducationLevel().toString()
                .compareTo(((Tutee) lowerOrder).getEducationLevel().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, lowerOrder);

        //first person's education level has higher lexicographical order
        expected = ((Tutee) higherOrder).getEducationLevel().toString()
                .compareTo(((Tutee) lowerOrder).getEducationLevel().toString());
        assertCompareSuccessfully(comparator, expected, higherOrder, lowerOrder);
    }

    @Test
    public void getComparator_validGradeCategory_compareSuccessfully() {
        //all first person's categories have lower lexicographical order
        Comparator<Person> comparator = getComparator(CATEGORY_GRADE);
        int expected = ((Tutee) lowerOrder).getGrade().toString()
                .compareTo(((Tutee) higherOrder).getGrade().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, higherOrder);

        //first person's grade has lower lexicographical order and the other categories have equal order
        versatileOrder = new TuteeBuilder((Tutee) lowerOrder).withGrade(HIGHER_ORDER).build();
        expected = ((Tutee) lowerOrder).getGrade().toString()
                .compareTo(((Tutee) versatileOrder).getGrade().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's grade has lower lexicographical order but the other categories have higher order
        versatileOrder = new TuteeBuilder((Tutee) higherOrder).withEducationLevel(EDUCATION_LEVEL_JUNIOR_COLLEGE)
                .withName(LOWER_ORDER).withSchool(LOWER_ORDER).withSubject(LOWER_ORDER).build();
        expected = ((Tutee) lowerOrder).getGrade().toString()
                .compareTo(((Tutee) versatileOrder).getGrade().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's grade has equal lexicographical order
        expected = ((Tutee) lowerOrder).getGrade().toString()
                .compareTo(((Tutee) lowerOrder).getGrade().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, lowerOrder);

        //first person's grade has higher lexicographical order
        expected = ((Tutee) higherOrder).getGrade().toString()
                .compareTo(((Tutee) lowerOrder).getGrade().toString());
        assertCompareSuccessfully(comparator, expected, higherOrder, lowerOrder);
    }

    @Test
    public void getComparator_validSchoolCategory_compareSuccessfully() {
        //all first person's categories have lower lexicographical order
        Comparator<Person> comparator = getComparator(CATEGORY_SCHOOL);
        int expected = ((Tutee) lowerOrder).getSchool().toString()
                .compareTo(((Tutee) higherOrder).getSchool().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, higherOrder);

        //first person's school has lower lexicographical order and the other categories have equal order
        versatileOrder = new TuteeBuilder((Tutee) lowerOrder).withSchool(HIGHER_ORDER).build();
        expected = ((Tutee) lowerOrder).getSchool().toString()
                .compareTo(((Tutee) versatileOrder).getSchool().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's school has lower lexicographical order but the other categories have higher order
        versatileOrder = new TuteeBuilder((Tutee) higherOrder).withEducationLevel(EDUCATION_LEVEL_JUNIOR_COLLEGE)
                .withName(LOWER_ORDER).withGrade(LOWER_ORDER).withSubject(LOWER_ORDER).build();
        expected = ((Tutee) lowerOrder).getSchool().toString()
                .compareTo(((Tutee) versatileOrder).getSchool().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's school has equal lexicographical order
        expected = ((Tutee) lowerOrder).getSchool().toString()
                .compareTo(((Tutee) lowerOrder).getSchool().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, lowerOrder);

        //first person's school has higher lexicographical order
        expected = ((Tutee) higherOrder).getSchool().toString()
                .compareTo(((Tutee) lowerOrder).getSchool().toString());
        assertCompareSuccessfully(comparator, expected, higherOrder, lowerOrder);
    }

    @Test
    public void getComparator_validSubjectCategory_compareSuccessfully() {
        //all first person's categories have lower lexicographical order
        Comparator<Person> comparator = getComparator(CATEGORY_SUBJECT);
        int expected = ((Tutee) lowerOrder).getSubject().toString()
                .compareTo(((Tutee) higherOrder).getSubject().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, higherOrder);

        //first person's subject has lower lexicographical order and the other categories have equal order
        versatileOrder = new TuteeBuilder((Tutee) lowerOrder).withSubject(HIGHER_ORDER).build();
        expected = ((Tutee) lowerOrder).getSubject().toString()
                .compareTo(((Tutee) versatileOrder).getSubject().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's subject has lower lexicographical order but the other categories have higher order
        versatileOrder = new TuteeBuilder((Tutee) higherOrder).withEducationLevel(EDUCATION_LEVEL_JUNIOR_COLLEGE)
                .withName(LOWER_ORDER).withGrade(LOWER_ORDER).withSchool(LOWER_ORDER).build();
        expected = ((Tutee) lowerOrder).getSubject().toString()
                .compareTo(((Tutee) versatileOrder).getSubject().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, versatileOrder);

        //first person's subject has equal lexicographical order
        expected = ((Tutee) lowerOrder).getSubject().toString()
                .compareTo(((Tutee) lowerOrder).getSubject().toString());
        assertCompareSuccessfully(comparator, expected, lowerOrder, lowerOrder);

        //first person's subject has higher lexicographical order
        expected = ((Tutee) higherOrder).getSubject().toString()
                .compareTo(((Tutee) lowerOrder).getSubject().toString());
        assertCompareSuccessfully(comparator, expected, higherOrder, lowerOrder);
    }

    @Test
    public void getComparator_invalidCategory_assertionErrorHappen() {
        thrown.expect(AssertionError.class);
        Comparator<Person> comparator = getComparator("email");
    }

    @Test
    public void compareNameLexicographically_validInput_compareSuccessfully() {
        lowerOrder = new PersonBuilder().withName("Albert").build();
        higherOrder = new PersonBuilder().withName("Alice").build();

        //first person has lower lexicographical order
        int expected = PersonSortUtil.compareNameLexicographically(lowerOrder, higherOrder);
        int actual = lowerOrder.getName().fullName.compareToIgnoreCase(higherOrder.getName().fullName);
        assertEquals(expected, actual);

        //first person has higher lexicographical order
        expected = PersonSortUtil.compareNameLexicographically(higherOrder, lowerOrder);
        actual = higherOrder.getName().fullName.compareToIgnoreCase(lowerOrder.getName().fullName);
        assertEquals(expected, actual);

        //both have exactly same name
        Person lowerOrderCopy = new PersonBuilder(lowerOrder).build();
        expected = PersonSortUtil.compareNameLexicographically(lowerOrder, lowerOrderCopy);
        actual = lowerOrder.getName().fullName.compareToIgnoreCase(lowerOrderCopy.getName().fullName);
        assertEquals(expected, actual);

        //both have same name with different cases -> treated as 2 same namess
        higherOrder = new PersonBuilder(lowerOrder).withName("ALBERT").build();
        int expectedFromSameName = expected;
        int expectedFromDiffName = compareNameLexicographically(lowerOrder, higherOrder);
        assertEquals(expectedFromSameName, expectedFromDiffName);
    }

    /**
     * Checks whether comparator is able to perform the desired comparison.
     */
    private void assertCompareSuccessfully(Comparator<Person> comparator, int expected, Person first, Person second) {
        int actual = comparator.compare(first, second);
        assertEquals(expected, actual);
    }
}
