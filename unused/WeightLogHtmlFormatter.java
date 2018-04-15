package seedu.address.model.util;

import seedu.address.model.person.Person;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
/**
 * HTML formatter for Person class. HTML referenced from Google Charts API
 * (https://developers.google.com/chart/interactive/docs/gallery/linechart)
 */
public class WeightLogHtmlFormatter {

    public static String getWeightLogHtmlFormat(Person person) {

        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>"
                + "<script type='text/javascript'>"
                + "google.charts.load('current', {'packages':['corechart']});"
                + "google.charts.setOnLoadCallback(drawChart);"
                + "function drawChart() {"
                + "var data = google.visualization.arrayToDataTable(["
                + "['Instance', 'Weight'],"
                + getWeightData(person)
                + "]);"
                + "var options = {"
                + "title: 'Weight Log for " + person.getName().toString() + "',"
                + "curveType: 'function',"
                + "legend: { position: 'bottom' }"
                + "};"
                + "var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));"
                + "chart.draw(data, options);"
                + "}"
                + "</script>"
                + "</head>"
                + "<body>"
                + "<div id='curve_chart' style='width: 900px; height: 500px'></div>"
                + getWeightDataDetails(person)
                + "</body>"
                + "</html>";
    }

    /*
     * Returns the HTML String containing the weight data to be input as text below the chart
     */
    private static String getWeightDataDetails(Person person) {
        final String twoTabSpaces = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String result = "<p><u> Past Weight Changes for " + person.getName().toString() + "</u></p>";
        for (int entry = 0; entry < person.getWeightLog().getList().size(); entry++) {
            result = String.format("%s %s %s %s %s", result, Integer.toString(entry) + ". ",
                    "[" + person.getWeightLog().getList().get(entry).getKey().toString() + "]", twoTabSpaces,
                    person.getWeightLog().getList().get(entry).getValue().toString() + "kg <br>");
        }
        return result;
    }

    /*
     * Returns the HTML String containing the weight data to be plotted in the chart
     */
    private static String getWeightData(Person person) {
        String result = "";
        for (int entry = 0; entry < person.getWeightLog().getList().size(); entry++) {
            result = String.format("%s %s %s", result,
                    "[" + Integer.toString(entry) + ",",
                    person.getWeightLog().getList().get(entry).getValue().toString() + "]");
            if (entry != person.getWeightLog().getList().size() - 1) {
                result += ",";
            }
        }
        return result;
    }
}
