package seedu.address.model.tag;

import java.util.*;

import javafx.collections.ObservableList;
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;


//@@author {clarissayong}

/**
 * Maps the list of users to their tags.
 */

public class TagMap {

    private HashMap<Tag, UniquePersonList> tagMap;

    public TagMap() {
        tagMap = new HashMap<Tag, UniquePersonList>();
    }


    public void add(Tag tag, Person person) throws DuplicatePersonException {
        if (!tagMap.containsKey(tag)) {
            tagMap.put(tag, new UniquePersonList());
        }
        tagMap.get(tag).add(person);
    }

    public ObservableList<Person> filter(Tag tag) {
        return tagMap.get(tag).asObservableList();
    }

}
