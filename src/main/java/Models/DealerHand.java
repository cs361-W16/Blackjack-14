package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class DealerHand extends Hand{

    @JsonCreator
    public DealerHand(@JsonProperty("cards") java.util.List<Card> cards,
                      @JsonProperty("status") String status) {
        this.cards = cards;
        this.status = status;
    }

    protected void addBottomCard(Card card){
        card.flipCard();
        cards.add(card);
    }

    protected void resetHand(){
        status = "";
    }
}
