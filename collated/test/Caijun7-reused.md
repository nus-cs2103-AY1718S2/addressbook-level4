# Caijun7-reused
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void updatePerson_detailsChanged_personsAndTagsListUpdated() throws Exception {
        AddressBook addressBookUpdatedToAmy = new AddressBookBuilder().withPerson(BOB).build();
        addressBookUpdatedToAmy.updatePerson(BOB, AMY);
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).build();
        assertEquals(expectedAddressBook, addressBookUpdatedToAmy);
    }

    @Test
    public void removeTag_tagNotInUsed_addressBookUnchanged() throws Exception {
        addressBookWithAmyAndBob.removeTag(new Tag(VALID_TAG_UNUSED));
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(expectedAddressBook, addressBookWithAmyAndBob);
    }

    @Test
    public void removeTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        addressBookWithAmyAndBob.removeTag(new Tag(VALID_TAG_FRIEND));
        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutFriendTag)
                .withPerson(bobWithoutFriendTag).build();

        assertEquals(expectedAddressBook, addressBookWithAmyAndBob);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void removeTag_tagNotInUsed_addressBookUnchanged() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withPerson(AMY)
                .withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBookWithAmyAndBob, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBookWithAmyAndBob, userPrefs);

        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(modelManagerCopy, modelManager);
    }

    @Test
    public void removeTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withPerson(AMY)
                .withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBookWithAmyAndBob, userPrefs);

        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutFriendTag)
                .withPerson(bobWithoutFriendTag).build();
        ModelManager expectedModelManager = new ModelManager(expectedAddressBook, userPrefs);

        assertEquals(expectedModelManager, modelManager);
    }
```
