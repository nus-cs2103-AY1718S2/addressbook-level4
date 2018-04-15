# Aritcho28-reused
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Returns the color for a specific tag.
     */
    private String getTagColor(String tagName) {
        return TAG_COLOR[Math.abs(tagName.hashCode()) % TAG_COLOR.length];
    }

    /**
     * Creates the tag colors for a person.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
