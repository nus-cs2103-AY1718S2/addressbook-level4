package seedu.address.model.book;

/**
 * Represents a book's priority or importance.
 */
public enum Priority {
    NONE("\uD83D\uDEA9 None", "priority-none"),
    LOW("\uD83D\uDEA9 Low", "priority-low"),
    MEDIUM("\uD83D\uDEA9 Medium", "priority-medium"),
    HIGH("\uD83D\uDEA9 High", "priority-high");

    public static final Priority DEFAULT_PRIORITY = NONE;

    private final String displayText;
    private final String styleClass;

    Priority(String displayText, String styleClass) {
        this.displayText = displayText;
        this.styleClass = styleClass;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
