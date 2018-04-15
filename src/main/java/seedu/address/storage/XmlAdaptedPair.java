package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Pair.
 */
public class XmlAdaptedPair {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pair's %s field is missing!";

    @XmlElement(required = true)
    private String studentName;
    @XmlElement(required = true)
    private String tutorName;
    @XmlElement(required = true)
    private String subject;
    @XmlElement(required = true)
    private String level;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String pairHash;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPair.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPair() {}

    /**
     * Constructs an {@code XmlAdaptedPair} with the given pair details.
     */
    public XmlAdaptedPair(String studentName, String tutorName, String subject, String level,
                            String price,  List<XmlAdaptedTag> tagged, String pairHash) {
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.price = price;

        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.pairHash = pairHash;
    }

    /**
     * Converts a given Pair into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPair
     */
    public XmlAdaptedPair(Pair source) {
        studentName = source.getStudentName();
        tutorName = source.getTutorName();
        subject = source.getSubject();
        level = source.getLevel();
        price = source.getPrice();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        pairHash = source.getPairHash().toString();
    }

    /**
     * Converts this jaxb-friendly adapted pair object into the model's Pair object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pair
     */
    public Pair toModelType() throws IllegalValueException {

        final List<Tag> pairTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            pairTags.add(tag.toModelType());
        }

        if (this.studentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.studentName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final String studentName = this.studentName;

        if (this.tutorName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.tutorName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final String tutorName = this.tutorName;

        if (this.subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(this.subject)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        final String subject = this.subject;

        if (this.level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        if (!Level.isValidLevel(this.level)) {
            throw new IllegalValueException(Level.MESSAGE_LEVEL_CONSTRAINTS);
        }
        final String level = this.level;


        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(this.price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final String price = this.price;

        if (this.pairHash == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PairHash.class.getSimpleName()));
        }
        final PairHash pairHash =  new PairHash(Integer.parseInt(this.pairHash));

        return new Pair(studentName, tutorName, subject, level, price, pairHash);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPair)) {
            return false;
        }

        XmlAdaptedPair otherPair = (XmlAdaptedPair) other;
        return Objects.equals(studentName, otherPair.studentName)
                && Objects.equals(tutorName, otherPair.tutorName)
                && Objects.equals(subject, otherPair.subject)
                && Objects.equals(level, otherPair.level)
                && Objects.equals(price, otherPair.price)
                && tagged.equals(otherPair.tagged);
    }
}
