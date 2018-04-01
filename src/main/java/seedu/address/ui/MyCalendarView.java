package seedu.address.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View the pop-uped browser window of user's calendar.
 */
public class MyCalendarView extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("My Calendar");
        CalendarBrowser calendarBrowser = new CalendarBrowser();
        scene = new Scene(calendarBrowser,calendarBrowser.computePrefWidth(750),
                calendarBrowser.computePrefHeight(600));
        stage.setScene(scene);
        stage.show();
    }
}
