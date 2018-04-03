# AzuraAiR-reused
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException,
                                                                            WrongPasswordException {
        logger.fine("Attempting to write to backup data file: ");
        addressBookStorage.backupAddressBook(addressBook);
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException {
        saveAddressBook(addressBook, filePath + ".backup");
    }
```
