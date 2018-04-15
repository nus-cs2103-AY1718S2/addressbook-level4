package seedu.flashy.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.tag.Tag;

//@@author yong-jie
/**
 * A UI component that displays information about a {@code Card}.
 */
public class CardCard extends UiPart<Region> {
    private static final String FXML = "CardListCard.fxml";

    public final Card card;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label front;

    @FXML
    private FlowPane tags;

    @FXML
    private FlowPane options;

    public CardCard(Card card, int displayedIndex, List<Tag> tagList) {
        super(FXML);
        this.card = card;
        id.setText(Integer.toString(displayedIndex));
        front.setText(card.getFront());
        tagList.forEach(tag -> tags.getChildren().add(new Label(tag.getName().toString())));
        if (card.getType().equals(McqCard.TYPE)) {
            for (int i = 0; i < card.getOptions().size(); i++) {
                String option = card.getOptions().get(i);
                options.getChildren().add(new Label((i + 1) + ") " + option));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardCard)) {
            return false;
        }

        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && card.equals(card.card);
    }
}
