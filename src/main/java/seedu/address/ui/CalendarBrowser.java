package seedu.address.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A pop-up browser window of the user's own calendar
 */
class CalendarBrowser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public CalendarBrowser() {
        webEngine.load("https://calendar.google.com");
        getChildren().add(browser);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 600;
    }
}
