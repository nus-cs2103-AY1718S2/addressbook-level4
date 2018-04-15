# samulloh
###### \src\test\java\seedu\address\storage\XmlAdaptedStudentTest.java
``` java
    @Test
    public void toModelType_nullPicturePath_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_FAVOURITE, INVALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProgrammingLanguage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}
```
