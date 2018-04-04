# Livian1107
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
