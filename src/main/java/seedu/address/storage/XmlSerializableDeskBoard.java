package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;

//@@author karenfrilya97
/**
 * An immutable {@link DeskBoard} that is serializable to XML format
 */
@XmlRootElement(name = "deskBoard")
public class XmlSerializableDeskBoard {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableDeskBoard.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDeskBoard() {
        tasks = new ArrayList<>();
        events = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDeskBoard(ReadOnlyDeskBoard src) {
        this();
        for (Activity activity : src.getActivityList()) {
            if (activity instanceof Task) {
                tasks.add(new XmlAdaptedTask((Task) activity));
            } else if (activity instanceof Event) {
                events.add(new XmlAdaptedEvent((Event) activity));
            }
        }
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code DeskBoard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedActivity} or {@code XmlAdaptedTag}.
     */
    public DeskBoard toModelType() throws IllegalValueException {
        DeskBoard deskBoard = new DeskBoard();
        for (XmlAdaptedTag t : tags) {
            deskBoard.addTag(t.toModelType());
        }
        for (XmlAdaptedActivity a : tasks) {
            deskBoard.addActivity(((XmlAdaptedTask) a).toModelType());
        }
        for (XmlAdaptedActivity e : events) {
            deskBoard.addActivity(((XmlAdaptedEvent) e).toModelType());
        }
        return deskBoard;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDeskBoard)) {
            return false;
        }

        XmlSerializableDeskBoard otherDb = (XmlSerializableDeskBoard) other;
        return tasks.equals(otherDb.tasks) && events.equals(otherDb.events) && tags.equals(otherDb.tags);
    }
}
