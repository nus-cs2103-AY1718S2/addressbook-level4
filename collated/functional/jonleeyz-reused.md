# jonleeyz-reused
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String[] TAG_COLOUR_STYLES =
        {"teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey"};
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    // given a tagName, returns the String representation of a colour style
    private String getTagColourStyleFor(String tagName) {
        // hash code of tag name used to generate random colour
        // colour of tags changes between different runs of the application
        // might want to tweak this behaviour in the LoanShark Tycoon context
        return TAG_COLOUR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOUR_STYLES.length];
    }

    /**
     * Creates the Labels fot a given {@code Person}.
     * 1. Creates a new Label object for each tag, initialised with the respective tag.
     * 2. Adds a style colour attribute to each Label based on its tag.
     * 3. Adds each properly initialised Label to the containing FlowPane object.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColourStyleFor(tag.tagName));    // getStyleClass(): Node class method
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            displayed.setValue(event.message);
            if (event.isSuccessful()) {
                setStyleToIndicateCommandSuccess();
            } else {
                setStyleToIndicateCommandFailure();
            }
        });
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private void setStyleToIndicateCommandSuccess() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }
```
###### \resources\view\DarkTheme.css
``` css
#tags .teal {
     -fx-text-fill: white;
     -fx-background-color: #3e7b91;
 }

 #tags .red {
     -fx-text-fill: black;
     -fx-background-color: red;
 }

 #tags .yellow {
     -fx-text-fill: black;
     -fx-background-color: yellow;
 }

 #tags .blue {
     -fx-text-fill: white;
     -fx-background-color: blue;
 }

 #tags .orange {
     -fx-text-fill: black;
     -fx-background-color: orange;
 }

 #tags .brown {
     -fx-text-fill: white;
     -fx-background-color: brown;
 }

 #tags .green {
     -fx-text-fill: black;
     -fx-background-color: green;
 }

 #tags .pink {
     -fx-text-fill: black;
     -fx-background-color: pink;
 }

 #tags .black {
     -fx-text-fill: white;
     -fx-background-color: black;
 }

 #tags .grey {
     -fx-text-fill: black;
     -fx-background-color: grey;
 }
```
