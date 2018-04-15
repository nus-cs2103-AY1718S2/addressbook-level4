package seedu.address.logic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Level;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

//@@author aussiroth

/**
 * This class contains static methods for use when performing attribute tag related tasks.
 */
public class AttributeTagSetter {

    /**
     * This function takes in a person and a set of tags.
     * If present, each of the attribute tags for that person will be removed from the set of tags.
     * The resulting set of tags is then returned.
     * @param person A person to remove the attribute tags from.
     * @param updatedTags The current set of tags of that person.
     * @return A copy of attributeTags representing the Tags for that person, with all attribute tags removed.
     */
    public static Set<Tag> removePresentAttributeTags(Person person, Set<Tag> updatedTags) {
        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //ignore if attribute is empty (not entered yet by user)
        if (!person.getPrice().toString().equals("")) {
            attributeTags.remove(new Tag(person.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!person.getLevel().toString().equals("")) {
            attributeTags.remove(new Tag(person.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!person.getSubject().toString().equals("")) {
            attributeTags.remove(new Tag(person.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!person.getStatus().toString().equals("")) {
            attributeTags.remove(new Tag(person.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!person.getRole().toString().equals("")) {
            attributeTags.remove(new Tag(person.getRole().toString(), Tag.AllTagTypes.ROLE));
        }
        return new HashSet<>(attributeTags);
    }

    /**
     *
     * @param attributeTags The set of tags to update
     * @param price The new price entered
     * @param subject The new subject entered
     * @param level The new level entered
     * @param status The new status entered
     * @param role The new role entered
     * @return A copy of attributeTags representing the Tags for that person, with all the 5 attribute tags entered.
     */
    public static Set<Tag> addNewAttributeTags(Set<Tag> attributeTags, Price price, Subject subject,
        Level level, Status status, Role role) {
        requireAllNonNull(price, subject, level, status, role);
        //ignore empty strings i.e. user did not enter that field
        if (!price.toString().equals("")) {
            attributeTags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
        }
        if (!subject.toString().equals("")) {
            attributeTags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!level.toString().equals("")) {
            attributeTags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!status.toString().equals("")) {
            attributeTags.add(new Tag(status.toString(), Tag.AllTagTypes.STATUS));
        }
        if (!role.toString().equals("")) {
            attributeTags.add(new Tag(role.toString(), Tag.AllTagTypes.ROLE));
        }
        return new HashSet<>(attributeTags);
    }
}
