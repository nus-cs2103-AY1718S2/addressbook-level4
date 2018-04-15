package seedu.address.model.util;

import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import seedu.address.MainApp;
import seedu.address.model.person.Person;

//@@author hypertun
/**
 * Change PersonCard to html format
 */
public class HtmlFormatter {

    public static String getHtmlFormat(Person person) {

        String name = person.getName().toString();
        String phone = person.getPhone().toString();
        String email = person.getEmail().toString();
        String address = person.getAddress().toString();
        String height = person.getHeight().toString();
        String weight = person.getWeight().toString();
        String bmi = person.getBodyMassIndex().toString();
        String age = person.getAge().toString();
        String activityLevel = person.getActivityLevel().toString();

        String gender;

        if (person.getGender().toString().equals("m")) {
            gender = "Male";
        }
        else {
            gender = "Female";
        }

        URL personcardcss = MainApp.class.getResource(FXML_FILE_FOLDER + "PersonCard.css");

        String bodyhtmlcode = "<div class=\"container\">\n"
                + "<h1>Personal Trainer Pro</h1>\n"
                + "<h2>v1.5</h2>\n"
                + "<div class=\"service-details\">"
                + "<img src=\"https://cdn.pixabay.com/photo/2014/03/24/13/40/dumbbells-293955_960_720.png\""
                + " alt=\"realm\" />\n"
                + "<div class=\"service-hover-text\">\n"
                + "<h3>"
                + gender
                + "</h3>\n"
                + "<h4>BMI :"
                + bmi
                + "</h4>\n"
                + "<p>Phone Number :</p>\n"
                + phone
                + "<p>Address :</p>\n"
                + address
                + "<p>Height :</p>\n"
                + height
                + "<p>Weight :</p>\n"
                + weight
                + "<p>Age :</p>\n"
                + age
                + "<p>Activity Level :</p>\n"
                + activityLevel
                + "</div>\n"
                + "<div class=\"service-white service-text\">\n"
                + "<p>"
                + name
                + "</p>\n"
                + "<a href=\"#\">@"
                + email
                + "</a></div>\n"
                + "</div>\n"
                + "</div>";

        String htmlcode = "<html>"
                + "<head>"
                + "<link rel = 'stylesheet' type='text/css' href='" + personcardcss.toExternalForm() + "' />"
                + "</head>"
                + "<body>"
                + bodyhtmlcode
                + "</body>"
                + "</html>";

        return htmlcode;
    }
}
