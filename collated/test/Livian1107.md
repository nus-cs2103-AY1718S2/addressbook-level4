# Livian1107
###### \java\guitests\guihandles\ProfilePanelHandle.java
``` java
/**
 * Provides a handle to profile panel.
 */
public class ProfilePanelHandle extends NodeHandle<Node> {

    private static final String NAME_FIELD_ID = "#name";
    private static final String MAJOR_FIELD_ID = "#major";
    private static final String YEAR_FIELD_ID = "#year";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label nameLabel;
    private final Label majorLabel;
    private final Label yearLabel;
    private final Label phoneLabel;
    private final Label usernameLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    private String lastRememberedName;

    public ProfilePanelHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.majorLabel = getChildNode(MAJOR_FIELD_ID);
        this.yearLabel = getChildNode(YEAR_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.usernameLabel = getChildNode(USERNAME_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return majorLabel.getText();
    }

    public String getYear() {
        return yearLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<Label> getTagLabels() {
        return tagLabels;
    }
}
```
###### \java\seedu\progresschecker\logic\commands\ThemeCommandTest.java
``` java
/**
 * Contains assertion tests for {@code ThemeCommand}.
 */
public class ThemeCommandTest {
    @Test
    public void equals() {
        ThemeCommand dayTheme = new ThemeCommand(DAY_THEME);
        ThemeCommand nightTheme = new ThemeCommand(NIGHT_THEME);

        // same object -> returns true
        assertTrue(dayTheme.equals(dayTheme));

        // same values -> returns true
        ThemeCommand dayThemeCopy = new ThemeCommand(DAY_THEME);
        assertTrue(dayTheme.equals(dayThemeCopy));

        // different types -> returns false
        assertFalse(dayTheme.equals(1));

        // null -> returns false
        assertFalse(dayTheme.equals(null));

        // different type -> returns false
        assertFalse(dayTheme.equals(nightTheme));
    }
}
```
###### \java\seedu\progresschecker\logic\commands\UploadCommandTest.java
``` java
public class UploadCommandTest {
    @Test
    public void isValidLocalPath() {

        // valid photo path
        assertTrue(UploadCommand.isValidLocalPath("C:\\Users\\Livian\\desktop\\1.png"));

        // empty path
        assertFalse(UploadCommand.isValidLocalPath("")); // empty string
        assertFalse(UploadCommand.isValidLocalPath(" ")); // spaces only

        // invalid extension
        assertFalse(UploadCommand.isValidLocalPath("C:\\photo.gif"));
        assertFalse(UploadCommand.isValidLocalPath("D:\\photo.bmp"));

        // invalid path format
        assertFalse(UploadCommand.isValidLocalPath("C:\\\\1.jpg")); // too many backslashes
        assertFalse(UploadCommand.isValidLocalPath("C:\\")); // no file name
    }
}
```
###### \java\seedu\progresschecker\model\photo\PhotoPathTest.java
``` java
public class PhotoPathTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PhotoPath(null));
    }

    @Test
    public void isValidPhotoPath() {
        // null photo path
        Assert.assertThrows(NullPointerException.class, () -> PhotoPath.isValidPhotoPath(null));

        // blank photo path
        assertFalse(PhotoPath.isValidPhotoPath(" ")); // spaces only

        // invalid starting
        assertFalse(PhotoPath.isValidPhotoPath("src/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/image.jpg")); // missing parent path
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/image.jpg")); // missing parent path

        // invalid file extension
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.psd"));
        assertFalse(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.gif"));

        // valid photo path
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.jpeg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contact/image.png"));
        assertTrue(PhotoPath.isValidPhotoPath("")); // empty path
    }
}
```
###### \java\seedu\progresschecker\testutil\TypicalThemes.java
``` java
/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalThemes {
    public static final String DAY_THEME = "day";
    public static final String NIGHT_THEME = "night";
}
```
###### \java\seedu\progresschecker\ui\ProfilePanelTest.java
``` java
public class ProfilePanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ProfilePanel profilePanel = new ProfilePanel();
        profilePanel.loadPerson(personWithNoTags);
        uiPartRule.setUiPart(profilePanel);
        assertProfileDisplay(profilePanel, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        profilePanel = new ProfilePanel();
        profilePanel.loadPerson(personWithTags);
        uiPartRule.setUiPart(profilePanel);
        assertProfileDisplay(profilePanel, personWithTags);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        ProfilePanel profilePanel = new ProfilePanel();
        profilePanel.loadPerson(person);

        // same object -> returns true
        assertTrue(profilePanel.equals(profilePanel));

        // null -> returns false
        assertFalse(profilePanel.equals(null));

        // different types -> returns false
        assertFalse(profilePanel.equals(0));
    }

    /**
     * Asserts that {@code personProfile} displays the details of {@code expectedPerson} correctly.
     */
    private void assertProfileDisplay(ProfilePanel personProfile, Person expectedPerson) {
        guiRobot.pauseForHuman();

        ProfilePanelHandle profilePanelHandle = new ProfilePanelHandle(personProfile.getRoot());

        // verify person details are displayed correctly
        assertProfileDisplaysPerson(expectedPerson, profilePanelHandle);
    }
}
```
###### \java\seedu\progresschecker\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualProfile} displays the details of {@code expectedPerson}.
     */
    public static void assertProfileDisplaysPerson(Person expectedPerson, ProfilePanelHandle actualProfile) {
        assertEquals(expectedPerson.getName().fullName, actualProfile.getName());
    }
```
