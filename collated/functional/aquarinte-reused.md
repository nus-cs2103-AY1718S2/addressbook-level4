# aquarinte-reused
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns a string with trailing whitespaces on the left removed.
     *
     * Reused from: http://tutorial4java.blogspot.sg/2013/05/trim-ltrim-and-rtrim-in-java.html
     */
    public static String leftTrim(String s) {
        return STRING_LEFT_TRIM.matcher(s).replaceAll("");
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        /** Caret position bug fix from https://bugs.openjdk.java.net/browse/JDK-8088614 */
        autocompleteListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        triggerAutocomplete(newValue);
                    }
                });
            }
        };
        commandTextField.textProperty().addListener(autocompleteListener);
        isAutocompleting = true;
```
