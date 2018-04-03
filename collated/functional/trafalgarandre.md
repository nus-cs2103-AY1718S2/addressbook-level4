# trafalgarandre
###### /java/seedu/address/ui/DetailsPanel.java
``` java
    /**
     * Adds the CalendarView to the DetailsPanel
     */
    public void addCalendarPanel() {
        CalendarPanel calendarPanel = new CalendarPanel();
        calendar.setContent(calendarPanel.getRoot());
    }

```
###### /resources/view/PersonListCard.fxml
``` fxml
   <ImageView fx:id="imageView" fitHeight="100.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true">
     <image>
       <Image url="/images/default.png"/>
     </image>
   </ImageView>
</HBox>
```
