package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;

/**
 *  Provides helper methods for various command class
 */
public class CommandHelper {

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * is used in ProcessOrderCommand, ProcessNextCommand, CompleteOneOrderCommand
     */
    public static Person createNewTaggedPerson(Person personToEdit, String tag) {
        assert personToEdit != null;
        assert tag != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Order updatedOrder = personToEdit.getOrder();
        Address updatedAddress = personToEdit.getAddress();
        Halal updatedHalal = personToEdit.getHalal();
        Vegetarian updatedVegetarian = personToEdit.getVegetarian();
        UniqueTagList updatedTags = new UniqueTagList(personToEdit.getTags());

        try {
            updatedTags.add(new Tag(tag));
        } catch (UniqueTagList.DuplicateTagException dte) {
            //does not add tag "processing" if already exists
        }
        return new Person(updatedName, updatedPhone, updatedOrder, updatedAddress,
                updatedHalal, updatedVegetarian, updatedTags);
    }

    /**
     * finds first person that does not have a tag "Processed"
     * @param lastShownList list of persons last shown to the user
     * @return index of the first person does not have the tag
     * is used in ProcessOrderCommand, ProcessNextCommand
     */
    public static int findIndexOfPersonToBeProcessed(List<Person> lastShownList) {
        int targetIndex = -1;
        for (Person person:lastShownList) {
            if (!person.getTagList().contains(new Tag("Processed"))) {
                targetIndex = lastShownList.indexOf(person);
                break;
            }
        }
        return targetIndex;
    }

    /**
     * finds the person that matches the order in the processing queue
     * @param taskToDelete Order to be completed
     * @param personList list of person
     * @return index of the matching person in personlist
     * is used in CompleteOneOrderCommand, CompleteMoreOrderCommand
     */
    public static int findIndexOfMatchingPerson(Task taskToDelete, List<Person> personList) {
        int editIndex = -1;
        for (Person person : personList) {
            if (person.getOrder().equals(taskToDelete.getOrder())
                    && person.getAddress().equals(taskToDelete.getAddress())) {
                editIndex = personList.indexOf(person);
                break;
            }
        }
        return editIndex;
    }

    /**
     * check whether a person has already been processed
     * @param person person to be checked
     * @return boolean
     */
    public static boolean checkIsProcessed(Person person) {
        if (person.getTagList().contains(new Tag("Processed"))
                || person.getTagList().contains(new Tag("Cooked"))
                || person.getTagList().contains(new Tag("Delivering"))
                || person.getTagList().contains(new Tag("Delivered"))) {
            return true;
        } else {
            return false;
        }
    }
}
