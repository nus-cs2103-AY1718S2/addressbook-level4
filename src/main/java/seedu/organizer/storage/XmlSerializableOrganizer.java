package seedu.organizer.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.user.UserWithQuestionAnswer;

/**
 * An Immutable Organizer that is serializable to XML format
 */
@XmlRootElement(name = "organizer")
public class XmlSerializableOrganizer {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedUser> users;

    /**
     * Creates an empty XmlSerializableOrganizer.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableOrganizer() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
        users = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableOrganizer(ReadOnlyOrganizer src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        //@@author dominickenn
        users.addAll(src.getUserList().stream().map(user -> {
            if (user instanceof UserWithQuestionAnswer) {
                return new XmlAdaptedUser((UserWithQuestionAnswer) user);
            } else {
                return new XmlAdaptedUser(user);
            }
        }).collect(Collectors.toList()));
        //@@author
    }

    /**
     * Converts this organizer into the model's {@code Organizer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask} or {@code XmlAdaptedTag} or {@code XmlAdaptedUser}.
     */
    public Organizer toModelType() throws IllegalValueException {
        Organizer organizer = new Organizer();
        for (XmlAdaptedTag t : tags) {
            organizer.addTag(t.toModelType());
        }
        for (XmlAdaptedTask p : tasks) {
            organizer.addTask(p.toModelType());
        }
        for (XmlAdaptedUser u : users) {
            if (u.isUserWithQuestionAnswer()) {
                organizer.addUser(u.toUserQuestionAnswerModelType());
            } else {
                organizer.addUser(u.toUserModelType());
            }
        }
        return organizer;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableOrganizer)) {
            return false;
        }

        XmlSerializableOrganizer otherAb = (XmlSerializableOrganizer) other;
        return tasks.equals(otherAb.tasks) && tags.equals(otherAb.tags) && users.equals(otherAb.users);
    }
}
