package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Imdb;
import seedu.address.model.ReadOnlyImdb;

/**
 * An Immutable Imdb that is serializable to XML format
 */
@XmlRootElement(name = "imdb")
public class XmlSerializableImdb {

    @XmlElement
    private List<XmlAdaptedPatient> patients;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    @XmlElement
    private List<XmlAdaptedQueue> queue;
    /**
     * Creates an empty XmlSerializableImdb.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableImdb() {
        patients = new ArrayList<>();
        tags = new ArrayList<>();
        queue = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableImdb(ReadOnlyImdb src) {
        this();
        patients.addAll(src.getPersonList().stream().map(XmlAdaptedPatient::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        queue.addAll(src.getUniquePatientQueueNo().stream().map(XmlAdaptedQueue::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Imdb} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPatient} or {@code XmlAdaptedTag}.
     */
    public Imdb toModelType() throws IllegalValueException {
        Imdb imdb = new Imdb();
        for (XmlAdaptedTag t : tags) {
            imdb.addTag(t.toModelType());
        }
        for (XmlAdaptedPatient p : patients) {
            imdb.addPerson(p.toModelType());
        }

        for (XmlAdaptedQueue queueNo : queue) {
            imdb.addPatientToQueue(queueNo.toModelType());
        }
        return imdb;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableImdb)) {
            return false;
        }

        XmlSerializableImdb otherAb = (XmlSerializableImdb) other;
        return patients.equals(otherAb.patients) && tags.equals(otherAb.tags)
                && queue.equals(otherAb.queue);
    }
}
