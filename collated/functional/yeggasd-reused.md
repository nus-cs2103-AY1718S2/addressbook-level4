# yeggasd-reused
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initializes the tag labels for {@code person}.
     */
    private void initializeTags(Person person) {
        for (Tag tag : person.getTags()) {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        }
    }

    /**
     * @param tagName
     * @return colorStyle for {@code tagName}'s label.
     */
    public static String getColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }
```
###### \resources\view\DarkTheme.css
``` css
#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: #009688;
}
#tags .cyan {
    -fx-text-fill: black;
    -fx-background-color: #00BCD4;
}
#tags .purple{
    -fx-text-fill: white;
    -fx-background-color: #9C27B0;
}
#tags .indigo {
    -fx-text-fill: white;
    -fx-background-color: #3F51B5;
}
#tags .lightgreen {
    -fx-text-fill: black;
    -fx-background-color: #8BC34A;
}
#tags .bluegrey {
    -fx-text-fill: white;
    -fx-background-color: #607D8B;
}
#tags .amber {
    -fx-text-fill: white;
    -fx-background-color: #FFC107;
}
#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: #FFEB3B;
}
```
