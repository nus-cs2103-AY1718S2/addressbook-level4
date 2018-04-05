# iNekox3
###### \java\seedu\progresschecker\commons\events\ui\PageLoadChangedEvent.java
``` java
/**
 * Represents a page change in the Browser Panel
 */
public class PageLoadChangedEvent extends BaseEvent {

    public final int targetIndex;

    public PageLoadChangedEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getPageIndex() {
        return targetIndex;
    }
}
```
###### \java\seedu\progresschecker\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

```
###### \java\seedu\progresschecker\model\exercise\Exercise.java
``` java
/**
 * Represents an Exercise in the ProgressChecker.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Exercise {

    private final QuestionIndex questionIndex;
    private final QuestionType questionType;
    private final Question question;
    private final StudentAnswer studentAnswer;
    private final ModelAnswer modelAnswer;

    /**
     * Every field must be present and not null.
     */
    public Exercise(QuestionIndex questionIndex, QuestionType questionType, Question question,
                    StudentAnswer studentAnswer, ModelAnswer modelAnswer) {
        requireAllNonNull(questionIndex, questionType, question);
        this.questionIndex = questionIndex;
        this.questionType = questionType;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.modelAnswer = modelAnswer;
    }

    public QuestionIndex getQuestionIndex() {
        return questionIndex;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Question getQuestion() {
        return question;
    }

    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }

    public ModelAnswer getModelAnswer() {
        return modelAnswer;
    }

    @Override
    public String toString() {
        return "Q" + questionIndex + " " + question + "\n\n"
                + "Your Answer: " + studentAnswer + "\n\n"
                + "Suggested Answer: " + modelAnswer;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\ModelAnswer.java
``` java
/**
 * Represents an Exercise's model answer in the ProgressChecker.
 */
public class ModelAnswer {

    public final String value;

    /**
     * Constructs a {@code ModelAnswer}.
     *
     * @param answer An answer of any word and character.
     */
    public ModelAnswer(String answer) {
        requireNonNull(answer);
        this.value = answer;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\Question.java
``` java
/**
 * Represents an Exercise's question in the ProgressChecker.
 */
public class Question {

    public final String value;

    /**
     * Constructs a {@code Question}.
     *
     * @param question A question of any word and character.
     */
    public Question(String question) {
        requireNonNull(question);
        this.value = question;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\QuestionIndex.java
``` java
/**
 * Represents an Exercise's question index in the ProgressChecker.
 */
public class QuestionIndex {

    public static final String MESSAGE_INDEX_CONSTRAINTS =
            "Indices can only contain numbers, and should be in the format of"
            + "SECTION NUMBER.PART NUMBER.QUESTION NUMBER";
    public static final String INDEX_VALIDATION_REGEX = "([2-9]|1[0-3])\\.([0-9]|[0-9]{2})\\.([0-9]|[0-9]{2})";
    public final String value;

    /**
     * Constructs a {@code QuestionIndex}.
     *
     * @param index A valid index number.
     */
    public QuestionIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_INDEX_CONSTRAINTS);
        this.value = index;
    }

    /**
     * Returns true if a given string is a valid index number.
     */
    public static boolean isValidIndex(String test) {
        return test.matches(INDEX_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\QuestionType.java
``` java
/**
 * Represents an Exercise's question type in the ProgressChecker.
 */
public class QuestionType {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "Type can only be 'text' or 'choice'";
    public static final String TYPE_VALIDATION_REGEX = "text|choice";
    public final String value;

    /**
     * Constructs a {@code QuestionType}.
     *
     * @param type A valid type.
     */
    public QuestionType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_TYPE_CONSTRAINTS);
        this.value = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\StudentAnswer.java
``` java
/**
 * Represents an Exercise's student answer in the ProgressChecker.
 */
public class StudentAnswer {

    public final String value;

    /**
     * Constructs a {@code StudentAnswer}.
     *
     * @param answer An answer of any word and character.
     */
    public StudentAnswer(String answer) {
        requireNonNull(answer);
        this.value = answer;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\UniqueExerciseList.java
``` java
/**
 * A list of exercises that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Exercise#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueExerciseList implements Iterable<Exercise> {

    private final ObservableList<Exercise> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent exercise as the given argument.
     */
    public boolean contains(Exercise toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an exercise to the list.
     *
     * @throws DuplicateExerciseException if the exercise to add is a duplicate of an existing exercise in the list.
     */
    public void add(Exercise toAdd) throws DuplicateExerciseException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExerciseException();
        }
        internalList.add(toAdd);
    }

    public void setExercises(UniqueExerciseList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        requireAllNonNull(exercises);
        final UniqueExerciseList replacement = new UniqueExerciseList();
        for (final Exercise exercise : exercises) {
            replacement.add(exercise);
        }
        setExercises(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Exercise> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Exercise> iterator() {
        return internalList.iterator();
    }
}
```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code progressChecker}
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return FXCollections.unmodifiableObservableList(filteredExercises);
    }

}
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        this.exercises.setExercises(exercises);
    }

```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    //// exercise-level operations

    /**
     * Adds an exercise to the ProgressChecker.
     *
     * @throws DuplicateExerciseException if an equivalent exercise already exists.
     */
    public void addExercise(Exercise e) throws DuplicateExerciseException {
        Exercise exercise = new Exercise(
                e.getQuestionIndex(), e.getQuestionType(), e.getQuestion(),
                e.getStudentAnswer(), e.getModelAnswer());
        exercises.add(exercise);
    }

```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asObservableList();
    }

```
###### \java\seedu\progresschecker\model\util\SampleDataUtil.java
``` java
    public static Exercise[] getSampleExercises() {
        return new Exercise[] {
            new Exercise(new QuestionIndex("3.1.1"), new QuestionType("choice"),
                new Question("Choose the correct statements\n"
                    + "\n"
                    + "a. Refactoring can improve understandability\n"
                    + "b. Refactoring can uncover bugs\n"
                    + "c. Refactoring can result in better performance\n"
                    + "d. Refactoring can change the number of methods/classes"),
                new StudentAnswer(""),
                new ModelAnswer("a b c d. (a, b, c) Although the primary aim of refactoring is to improve"
                    + "internal code structure, there are other secondary benefits. (d) Some refactorings"
                    + "result in adding/removing methods/classes.")),
            new Exercise(new QuestionIndex("3.1.2"), new QuestionType("text"),
                new Question("Do you agree with the following statement? Justify your answer.\n"
                    + "\n"
                    + "Statement: Whenever we refactor code to fix bugs,"
                    + "we need not do regression testing if the bug fix was minor."),
                new StudentAnswer(""),
                new ModelAnswer("DISAGREE. Even a minor change can have major repercussions on the system."
                    + "We MUST do regression testing after each change, no matter how minor it is."
                    + "Fixing bugs is technically not refactoring.")),
            new Exercise(new QuestionIndex("3.1.3"), new QuestionType("text"),
                new Question("Explain what is refactoring and "
                    + "why it is not the same as rewriting, bug fixing, or adding features."),
                new StudentAnswer(""),
                new ModelAnswer(""))
        };
    }

}
```
###### \java\seedu\progresschecker\storage\XmlAdaptedExercise.java
``` java
/**
 * JAXB-friendly version of the Exercise.
 */
public class XmlAdaptedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    @XmlElement(required = true)
    private String questionIndex;
    @XmlElement(required = true)
    private String questionType;
    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private String studentAnswer;
    @XmlElement(required = true)
    private String modelAnswer;

    /**
     * Constructs an XmlAdaptedExercise.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExercise() {}

    /**
     * Constructs an {@code XmlAdaptedExercise} with the given exercise details.
     */
    public XmlAdaptedExercise(
            String questionIndex, String questionType, String question,
            String studentAnswer, String modelAnswer) {
        this.questionIndex = questionIndex;
        this.questionType = questionType;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.modelAnswer = modelAnswer;
    }

    /**
     * Converts a given Exercise into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedExercise
     */
    public XmlAdaptedExercise(Exercise source) {
        questionIndex = source.getQuestionIndex().value;
        questionType = source.getQuestionType().value;
        question = source.getQuestion().value;
        studentAnswer = source.getStudentAnswer().value;
        modelAnswer = source.getModelAnswer().value;
    }

    /**
     * Converts this jaxb-friendly adapted exercise object into the model's Exercise object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise
     */
    public Exercise toModelType() throws IllegalValueException {
        if (this.questionIndex == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionIndex.class.getSimpleName()));
        }
        if (!QuestionIndex.isValidIndex(this.questionIndex)) {
            throw new IllegalValueException(QuestionIndex.MESSAGE_INDEX_CONSTRAINTS);
        }
        final QuestionIndex questionIndex = new QuestionIndex(this.questionIndex);

        if (this.questionType == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionType.class.getSimpleName()));
        }
        if (!QuestionType.isValidType(this.questionType)) {
            throw new IllegalValueException(QuestionType.MESSAGE_TYPE_CONSTRAINTS);
        }
        final QuestionType questionType = new QuestionType(this.questionType);

        if (this.question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        final Question question = new Question(this.question);

        if (this.studentAnswer == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentAnswer.class.getSimpleName()));
        }
        final StudentAnswer studentAnswer = new StudentAnswer(this.studentAnswer);

        if (this.modelAnswer == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModelAnswer.class.getSimpleName()));
        }
        final ModelAnswer modelAnswer = new ModelAnswer(this.modelAnswer);

        return new Exercise(questionIndex, questionType, question, studentAnswer, modelAnswer);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedExercise)) {
            return false;
        }

        XmlAdaptedExercise otherExercise = (XmlAdaptedExercise) other;
        return Objects.equals(questionIndex, otherExercise.questionIndex)
                && Objects.equals(questionType, otherExercise.questionType)
                && Objects.equals(question, otherExercise.question);
    }
}
```
###### \java\seedu\progresschecker\ui\ExerciseCard.java
``` java
/**
 * An UI component that displays information of an {@code Exercise}.
 */
public class ExerciseCard extends UiPart<Region> {

    private static final String FXML = "ExerciseListCard.fxml";

    public final Exercise exercise;

    @FXML
    private Label questionIndex;
    @FXML
    private Label questionType;
    @FXML
    private Label question;
    @FXML
    private Label studentAnswer;
    @FXML
    private Label modelAnswer;

    public ExerciseCard(Exercise exercise) {
        super(FXML);
        this.exercise = exercise;
        questionIndex.setText(exercise.getQuestionIndex().value);
        question.setText(exercise.getQuestion().value);
        studentAnswer.setText(exercise.getStudentAnswer().value);
        modelAnswer.setText(exercise.getModelAnswer().value);
    }
}
```
###### \java\seedu\progresschecker\ui\ExerciseListPanel.java
``` java
/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "ExerciseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<ExerciseCard> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        setConnections(exerciseList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Exercise> exerciseList) {
        ObservableList<ExerciseCard> mappedList = EasyBind.map(
                exerciseList, (exercise) -> new ExerciseCard(exercise));
        exerciseListView.setItems(mappedList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ExerciseCard}.
     */
    class ExerciseListViewCell extends ListCell<ExerciseCard> {

        @Override
        protected void updateItem(ExerciseCard exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(exercise.getRoot());
            }
        }
    }

}
```
###### \resources\view\ExerciseListCard.fxml
``` fxml
<VBox id="cardPane" fx:id="cardPane" prefHeight="140.0" prefWidth="380.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
    </padding>
    <Label fx:id="questionIndex" text="\$questionIndex" />
    <Label fx:id="question" text="\$question"/>
    <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Your Answer: "/>
    <Label fx:id="studentAnswer" styleClass="cell_small_label" text="\$studentAnswer"/>
    <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Suggested Answer: "/>
    <Label fx:id="modelAnswer" text="\$modelAnswer"/>
</VBox>
```
###### \resources\view\ExerciseListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="exerciseListView" VBox.vgrow="ALWAYS" />
</VBox>
```
