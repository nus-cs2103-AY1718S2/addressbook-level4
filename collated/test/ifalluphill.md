# ifalluphill
###### /java/guitests/guihandles/MainMenuHandle.java
``` java
    /**
     * Opens the {@code CalendarWindow} using the menu bar in {@code MainWindow}.
     */
    public void openCalendarWindowUsingMenu() {
        clickOnMenuItemsSequentially("View", "Open Calendar");
    }

    /**
     * Opens the {@code CalendarWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openCalendarWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F8);
    }

    /**
     * Opens the {@code ErrorLog} using the menu bar in {@code MainWindow}.
     */
    public void openErrorLogUsingMenu() {
        clickOnMenuItemsSequentially("View", "Show Error Log");
    }
```
