# Isaaaca-unused
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code tag} from all {@code persons} in the {@code AddressBook} and from the {@code AddressBook}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is not found from the address book.");
        }

        removeTagFromAddressBook(tag);

    }

    /**
     * Removes {@code tag} from the {@code AddressBook}.
     */
    private void removeTagFromAddressBook(Tag tag) {
        Set<Tag> editedTagList = tags.toSet();
        if (editedTagList.contains(tag)) {
            editedTagList.remove(tag);
            tags.setTags(editedTagList);
        }
    }
```
