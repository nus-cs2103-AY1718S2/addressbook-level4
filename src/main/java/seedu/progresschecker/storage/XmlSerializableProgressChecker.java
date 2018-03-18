package seedu.progresschecker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

/**
 * An Immutable ProgressChecker that is serializable to XML format
 */
@XmlRootElement(name = "progresschecker")
public class XmlSerializableProgressChecker {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableProgressChecker.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableProgressChecker() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableProgressChecker(ReadOnlyProgressChecker src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this progresschecker into the model's {@code ProgressChecker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public ProgressChecker toModelType() throws IllegalValueException {
        ProgressChecker progressChecker = new ProgressChecker();
        for (XmlAdaptedTag t : tags) {
            progressChecker.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            progressChecker.addPerson(p.toModelType());
        }
        return progressChecker;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableProgressChecker)) {
            return false;
        }

        XmlSerializableProgressChecker otherAb = (XmlSerializableProgressChecker) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags);
    }
}
