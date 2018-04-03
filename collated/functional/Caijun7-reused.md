# Caijun7-reused
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes all {@code tag}s that are not used by any {@code person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
    * Removes {@code tag} from {@code person} in this {@code AddressBook}.
    * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
    */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(person.getTags());
        if (!newTags.remove(tag)) {
            return;
        }
        Person newPerson =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getBirthday(), newTags);
        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    /**
    * Removes {@code tag} from all persons in this {@code AddressBook}.
    */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible exception: person is obtained from the address book.");
        }
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Remove {@code tag} from all {@code person}s in the {@code AddressBook}.
     * @param tag
     */
    void deleteTag(Tag tag);
```
