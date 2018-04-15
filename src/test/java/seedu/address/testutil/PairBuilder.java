package seedu.address.testutil;

import java.util.Set;

import seedu.address.model.pair.Pair;
import seedu.address.model.person.Level;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

//@@author alexawangzi
/**
 * A utility class to help with building Pair objects.
 */
public class PairBuilder {

    public static final Person DEFAULT_STUDENT = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Student").build();
    public static final Person DEFAULT_TUTOR = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Tutor").withRemark("Impatient and poor in explanation.").build();
    public static final Subject DEFAULT_SUBJECT = new Subject("Math");
    public static final Level DEFAULT_LEVEL = new Level("Upper Sec");
    public static final Price DEFAULT_PRICE = new Price("50");
    public static final String DEFAULT_TAG_SUBJECT = "Math";
    public static final String DEFAULT_TAG_LEVEL = "Upper Sec";
    public static final String DEFAULT_TAG_PRICE = "50";

    private Person student;
    private Person tutor;
    private String subject;
    private String level;
    private String price;
    private Set<Tag> tags;

    public PairBuilder() {
        student = DEFAULT_STUDENT;
        tutor =  DEFAULT_TUTOR;
        subject = DEFAULT_SUBJECT.toString();
        level = DEFAULT_LEVEL.toString();
        price = DEFAULT_PRICE.toString();
        tags = SampleDataUtil.getTagSet(DEFAULT_TAG_SUBJECT, DEFAULT_TAG_LEVEL, DEFAULT_TAG_PRICE);
    }


    /**
     * Sets the {@code studentName} of the {@code Pair} that we are building.
     */
    public PairBuilder withStudent(Person student) {
        this.student = student;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Pair} that we are building.
     */
    public PairBuilder withTutor(Person tutor) {
        this.tutor = tutor;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pair} that we are building.
     */
    public PairBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withLevel(String level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withPrice(String price) {
        this.price = price;
        return this;
    }


    /**
     * Sets the required attribute tags for the pair
     */
    private void setTags() {

        tags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        tags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        tags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
    }

    /**
     * Builds a pair based off the attributes in this class
     * @return Pair with set attributes
     */
    public Pair build() {
        setTags();
        return new Pair(student, tutor, student.getSubject(), student.getLevel(), student.getPrice());
    }

}
