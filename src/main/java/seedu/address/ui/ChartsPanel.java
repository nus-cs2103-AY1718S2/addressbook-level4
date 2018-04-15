package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowChartsEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Subject;

//@@author dannyngmx94
/**
 * The Chart Panel of the App.
 */
public class ChartsPanel extends UiPart<Region> {

    private static final String FXML = "ChartsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<Person> personList;

    @FXML
    private PieChart roleDistribution;
    @FXML
    private BarChart<?, ?> tutorSubject;
    @FXML
    private BarChart<?, ?> studentSubject;


    public ChartsPanel(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        loadChartsDetails();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads chart details into the chart panel.
     */
    public void loadChartsDetails() {
        ObservableList<Person> tutorList = personList.filtered(person -> person.getRole().equals(new Role("Tutor")));
        ObservableList<Person> studentList =
                personList.filtered(person -> person.getRole().equals(new Role("Student")));
        int numTutor = tutorList.size();
        int numStudent = studentList.size();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tutor: " + numTutor, numTutor),
                new PieChart.Data("Student: " + numStudent, numStudent));
        roleDistribution.setData(pieChartData);

        ObservableList<Person> tutorEngList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("English")));
        ObservableList<Person> tutorChiList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Chinese")));
        ObservableList<Person> tutorMathList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Math")));
        ObservableList<Person> tutorPhyList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Physics")));
        ObservableList<Person> tutorChemList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Chemistry")));
        int numEngTutor = tutorEngList.size();
        int numChiTutor = tutorChiList.size();
        int numMathTutor = tutorMathList.size();
        int numPhyTutor = tutorPhyList.size();
        int numChemTutor = tutorChemList.size();

        XYChart.Series set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("English", numEngTutor));
        set1.getData().add(new XYChart.Data("Chinese", numChiTutor));
        set1.getData().add(new XYChart.Data("Math", numMathTutor));
        set1.getData().add(new XYChart.Data("Physics", numPhyTutor));
        set1.getData().add(new XYChart.Data("Chemistry", numChemTutor));
        tutorSubject.getData().clear();
        tutorSubject.layout();
        tutorSubject.getData().addAll(set1);

        ObservableList<Person> studentEngList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("English")));
        ObservableList<Person> studentChiList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Chinese")));
        ObservableList<Person> studentMathList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Math")));
        ObservableList<Person> studentPhyList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Physics")));
        ObservableList<Person> studentChemList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Chemistry")));
        int numEngStudent = studentEngList.size();
        int numChiStudent = studentChiList.size();
        int numMathStudent = studentMathList.size();
        int numPhyStudent = studentPhyList.size();
        int numChemStudent = studentChemList.size();

        XYChart.Series set2 = new XYChart.Series<>();
        set2.getData().add(new XYChart.Data("English", numEngStudent));
        set2.getData().add(new XYChart.Data("Chinese", numChiStudent));
        set2.getData().add(new XYChart.Data("Math", numMathStudent));
        set2.getData().add(new XYChart.Data("Physics", numPhyStudent));
        set2.getData().add(new XYChart.Data("Chemistry", numChemStudent));
        studentSubject.getData().clear();
        studentSubject.layout();
        studentSubject.getData().addAll(set2);

    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        System.out.println(this);
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadChartsDetails();
    }
}
