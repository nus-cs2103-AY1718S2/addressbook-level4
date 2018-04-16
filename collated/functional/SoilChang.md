# SoilChang
###### /java/seedu/address/ui/PersonDetail.java
``` java
        income.setText(person.getIncome().toString());
        age.setText(person.getAge().toString());
        email.setText(person.getEmail().value);
        actualSpending.setText(person.getActualSpending().toString());
        expectedSpending.setText(person.getExpectedSpending().toString());
        isNewClient.setText("New Client");
        if (person.getPolicy().isPresent()) {
            policy.setText(person.getPolicy().get().toString());
        } else {
            policy.setText("");
        }

        if (person.getActualSpending().value != 0.0) {
            // the client has actual income
            actualSpending.setVisible(true);
            isNewClient.setVisible(false);
            expectedSpending.setVisible(false);
        } else {
            actualSpending.setVisible(false);
            isNewClient.setVisible(true);
            expectedSpending.setVisible(true);
        }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    @FXML
    private Label income;
```
###### /java/seedu/address/ui/PersonCard.java
``` java
        income.setText("Income: " + person.getIncome().toString());
        age.setText("Age: " + person.getAge().toString() + " years old");
        email.setText(person.getEmail().value);
        actualSpending.setText("Actual Spending: " + person.getActualSpending().toString());
        expectedSpending.setText("Predicted Spending: " + person.getExpectedSpending().toString());
        isNewClient.setText("New Client");
        isNewClient.setTextFill(Color.GREEN);
        if (person.getPolicy().isPresent()) {
            policy.setText("Policy: " + person.getPolicy().get().toString());
        } else {
            policy.setText("Has not applied to any policy");
        }

        if (person.getActualSpending().value != 0.0) {
            // the client has actual income
            actualSpending.setVisible(true);
            isNewClient.setVisible(false);
        } else {
            actualSpending.setVisible(false);
            isNewClient.setVisible(true);
        }
        if (person.getExpectedSpending().value == 0.0) {
            expectedSpending.setVisible(false);
        } else {
            expectedSpending.setVisible(true);
        }


```
###### /java/seedu/address/logic/GradientDescent.java
``` java
public class GradientDescent {
    public static final String MESSAGE_PREDICTION_SUCCESS = "Prediction success";
    public static final String MESSAGE_PREDICTION_FAIL = "Prediction failed";
    public static final String MESSAGE_PREDICTION_DIVERGENT = "Prediction Solution is not convergent";
    private static GradientDescent instance;
    /**
     * The weights we are calculating
     * For more fields, please add in the default value below
     * [ x1= income, x2 = age ]
     */
    private static ArrayList<Double> weights = new ArrayList<>();

    /**
     * Some constants to normalize income and age so that the value don't differ so much
     */
    private static ArrayList<Double> normalizationConstant =
            new ArrayList<>(Arrays.asList(1000.0));


    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private PredictionModel model;
    /**
     * The learning rate
     */
    private final Double learningRate = 0.000001;

    /**
     * The amount of epoch of looping through training data
     */
    private final Integer epoch = 300000;


    /**
     * Singleton
     *
     * @param model
     */
    private GradientDescent(PredictionModel model) {
        this.model = model;
    }

```
###### /java/seedu/address/logic/GradientDescent.java
``` java
    public static GradientDescent getInstance(PredictionModel model) {
        if (instance == null) {
            instance = new GradientDescent(model);
            instance.resetWeights();
        }
        return instance;
    }


```
###### /java/seedu/address/logic/GradientDescent.java
``` java

    /**
     * Drives the whole algorithm to solve the problem
     */
    public CommandResult solve() throws CommandException {
        /**
         * row -> each entry of data
         * col -> [ income ]
         */
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(new ArrayList<>());

        /**
         * The actual outcome, this "ActualSpending"
         */
        ArrayList<Double> targets = new ArrayList<>();


        //extract values
        this.model.preparePredictionData(matrix, targets, normalizationConstant);


        if (this.hasNaN(this.weights)) {
            resetWeights();
        }

        //solve
        descent(matrix, targets);

        if (this.hasNaN(this.weights)) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_DIVERGENT));
        }

        Double confidence = this.validate(matrix, targets);
        this.logger.info("Confidence Level: " + confidence);


        //update results
        try {
            this.model.updatePredictionResult(this.getWeights());
            return new CommandResult(String.format(MESSAGE_PREDICTION_SUCCESS)
                    + ", with Confidence Rate "
                    + String.format("%.2f", confidence)
                    + "%"
            );
        } catch (CommandException e) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_FAIL));
        }
    }

```
###### /java/seedu/address/logic/GradientDescent.java
``` java

    /**
     * Validate the training results
     */
    private Double validate(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        Double average = 0.0;
        for (int i = 0; i < matrix.size(); i++) {

            //loop through each row

            Double outcome = this.predict(matrix.get(i));
            Double error = Math.abs((outcome - targets.get(i)) / targets.get(i));
            average += (1 - error);
        }

        Double confidence = average / targets.size() * 100;
        if (confidence < 0) {
            confidence = 0.0;
        } else if (confidence > 100) {
            confidence = 100.0;
        }
        return confidence;
    }

```
###### /java/seedu/address/logic/GradientDescent.java
``` java

    /**
     * Perform stochastic gradient descent on the input data
     */
    private void descent(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        for (int itt = 0; itt < epoch; itt++) {

            //check data validity
            if (this.hasNaN(this.weights)) {
                this.logger.warning("The solution is not convergent");
                break;
            }

            // fixed amount of training iteration
            for (int r = 0; r < matrix.size(); r++) { //going through each training data

                ArrayList<Double> row = matrix.get(r);
                Double outcome = predict(row);
                Double error = targets.get(r) - outcome;
                for (int i = 0; i < row.size(); i++) {
                    Double deltaW = this.learningRate * error * row.get(i);
                    this.weights.set(i, this.weights.get(i) + deltaW);
                }
            }
        }
    }

    /**
     * Check if the ArrayList contains NaN
     *
     * @param weights
     * @return
     */
    private boolean hasNaN(ArrayList<Double> weights) {
        for (Double i : weights) {
            if (i.isNaN() || Double.isInfinite(i)) {
                return true;
            }
        }
        return false;
    }

```
###### /java/seedu/address/logic/GradientDescent.java
``` java

    /**
     * calculate values based on the current weights
     */
    private Double predict(ArrayList<Double> row) {
        Double sum = 0.0;
        //sum income
        for (int i = 0; i < row.size(); i++) {
            sum += this.weights.get(i) * row.get(i);
        }

        return sum;
    }


    /**
     * Weight getter
     *
     * @return
     */
    public static ArrayList<Double> getWeights() {
        //restore the un-normalized value
        ArrayList<Double> trueWeights = new ArrayList<>();
        for (int j = 0; j < GradientDescent.weights.size(); j++) {
            trueWeights.add(GradientDescent.weights.get(j) / normalizationConstant.get(j));
        }
        return trueWeights;
    }

    /**
     * reset the weights to initial values of zeros
     */
    public static void resetWeights() {
        GradientDescent.weights = new ArrayList<>(Arrays.asList(0.0));
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_INCOME, PREFIX_TAG, PREFIX_ACTUALSPENDING, PREFIX_AGE);
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
            Income income = ParserUtil.parseIncome(argMultimap.getValue(PREFIX_INCOME)).get();
            Expenditure actualSpending = ParserUtil.parseActualSpending(argMultimap
                    .getValue(PREFIX_ACTUALSPENDING)).orElse(null);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java

    /**
     * Parses a {@code String value} into an {@code value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code value} is invalid.
     */
    public static Income parseIncome(String income) throws IllegalValueException {
        requireNonNull(income);
        Double trimmedIncome = 0.0;
        try {
            trimmedIncome = Double.parseDouble(income.trim());
        } catch (Exception e) {
            throw new IllegalValueException(Income.MESSAGE_INCOME_CONSTRAINTS);
        }

        if (!Income.isValid(trimmedIncome)) {
            throw new IllegalValueException(Income.MESSAGE_INCOME_CONSTRAINTS);
        }
        return new Income(Math.abs(trimmedIncome));
    }

    /**
     * Parses a {@code Optional<String> value} into an {@code Optional<value>} if {@code value} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Income> parseIncome(Optional<String> income) throws IllegalValueException {
        requireNonNull(income);
        return income.isPresent() ? Optional.of(parseIncome(income.get())) : Optional.empty();
    }


    /**
     * Parses a {@code String value} into an {@code value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code value} is invalid.
     */
    public static Expenditure parseActualSpending(String actualSpending) throws IllegalValueException {
        requireNonNull(actualSpending);
        Double trimmedActualSpending = 0.0;
        try {
            trimmedActualSpending = Double.parseDouble(actualSpending.trim());
        } catch (Exception e) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }

        if (!Expenditure.isValid(trimmedActualSpending)) {
            throw new IllegalValueException(Expenditure.MESSAGE_EXPENDITURE_CONSTRAINTS);
        }
        return new Expenditure(Math.abs(trimmedActualSpending));
    }


    /**
     * Parses a {@code Optional<String> value} into an {@code Optional<value>} if {@code value} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Expenditure> parseActualSpending(Optional<String> actualSpending)
            throws IllegalValueException {
        requireNonNull(actualSpending);
        return actualSpending.isPresent()
                ? Optional.of(parseActualSpending(actualSpending.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
        //1. tokienize
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_INCOME, PREFIX_ACTUALSPENDING, PREFIX_AGE);
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
            ParserUtil.parseIncome(argMultimap.getValue(PREFIX_INCOME)).ifPresent(editPersonDescriptor::setIncome);
            ParserUtil.parseActualSpending(argMultimap.getValue(PREFIX_ACTUALSPENDING))
                    .ifPresent(editPersonDescriptor::setActualSpending);
            ParserUtil.parseIncome(argMultimap.getValue(PREFIX_INCOME)).ifPresent(editPersonDescriptor::setIncome);
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
            + PREFIX_INCOME + "INCOME "
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
            + PREFIX_INCOME + "29000 "
```
###### /java/seedu/address/logic/commands/AddPolicyCommand.java
``` java
                personToEdit.getIncome(),
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
            + "[" + PREFIX_INCOME + "INCOME] "
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        Income updatedIncome = editPersonDescriptor.getIncome().orElse(personToEdit.getIncome());
        Expenditure updatedActualSpending = editPersonDescriptor.getActualSpending()
                .orElse((personToEdit.getActualSpending()));
        Expenditure updatedExpectedSpending = editPersonDescriptor.getExpectedSpending()
                .orElse((personToEdit.getExpectedSpending()));
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedIncome, updatedActualSpending, updatedExpectedSpending, updatedAge, personToEdit.getPolicy());
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        private Income income;
        private Expenditure actualSpending;
        private Expenditure expectedSpending;
        private Age age;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
            setIncome(toCopy.income);
            setActualSpending(toCopy.actualSpending);
            setExpectedSpending(toCopy.expectedSpending);
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.tags,
                    this.income, this.actualSpending, this.expectedSpending, this.age);
        }
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setIncome(Income income) {
            this.income = income;
        }

        public Optional<Income> getIncome() {
            return Optional.ofNullable(income);
        }

        public void setActualSpending(Expenditure actualSpending) {
            this.actualSpending = actualSpending;
        }

        public Optional<Expenditure> getActualSpending() {
            return Optional.ofNullable(actualSpending);
        }

        public void setExpectedSpending(Expenditure expectedSpending) {
            this.expectedSpending = expectedSpending;
        }

        public Optional<Expenditure> getExpectedSpending() {
            return Optional.ofNullable(expectedSpending);
        }
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
                new Income(2000.00),
                new Expenditure(2000.0),
                new Expenditure(2000.0),
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
                new Income(2200.00),
                new Expenditure(2000.0),
                new Expenditure(2000.0),
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new Income(34550.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Income(202323.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new Income(5000.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Income(7000.00), new Expenditure(2000.0),
                new Expenditure(2000.0), new Age(20), Optional.empty()),

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                correctTagReferences, person.getIncome(), person.getActualSpending(),
                person.getExpectedSpending(), person.getAge(), person.getPolicy());
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void preparePredictionData(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets,
                                      ArrayList<Double> normalizationConstant) {
        ObservableList<Person> personList = this.getAddressBook().getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            double as = personList.get(i).getActualSpending().value;

            //the person has no actual spending recorded
            if (as == 0.0) {
                continue;
            }

            ArrayList<Double> row = new ArrayList<>();
            //record down the actual value
            row.add(personList.get(i).getIncome().value / normalizationConstant.get(0));
            targets.add(as);


            //push to matrix
            matrix.add(row);
        }
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updatePredictionResult(ArrayList<Double> trueWeights) throws CommandException {
        ObservableList<Person> personList = this.addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getActualSpending().value != 0.0) {
                //the person already has known value of spending
                continue;
            }

            //else update the person with expected spending
            Person p = personList.get(i);
            logger.info("Prediction results: income coefficient-> " + "\n"
                    + "Income coefficient: " + trueWeights.get(0) + "\n"
            );


            Person updatedPerson = p.updateSelectedField(trueWeights);
            //update the model here


            try {
                this.updatePerson(personList.get(i), updatedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            this.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }
}
```
###### /java/seedu/address/model/PredictionModel.java
``` java
public interface PredictionModel extends Model {
    void preparePredictionData(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets,
                               ArrayList<Double> normalizationConstant);

    void updatePredictionResult(ArrayList<Double> weights)
            throws CommandException;
}
```
