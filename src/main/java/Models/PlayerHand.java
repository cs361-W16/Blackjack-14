package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class PlayerHand extends Hand{
    public java.util.List<Option> handOptions = new ArrayList<>();
    public int bet;

    @JsonCreator
    public PlayerHand(@JsonProperty("cards") java.util.List<Card> cards,
                      @JsonProperty("status") String status) {
        this.cards = cards;
        this.status = status;
    }

    protected void addBottomCard(Card card){
        cards.add(card);
    }

    public void resetHand(){
        status = "";
        bet = 2;
        setHandOptions();
    }

    public void setHandOptions(){
        handOptions.clear();
        int handValue = getHandValue();

        //check for hit, double down and stay
        if(handValue < 21){
            handOptions.add(new Option("hit", "Hit"));
            handOptions.add(new Option("doubleDown", "Double Down"));
            handOptions.add(new Option("stay", "Stay"));
        }

        //check for split
        if((cards.size()==2) && (cards.get(0).getValue()==cards.get(1).getValue())){
            //Check that cards with value 10 are the same
            if(cards.get(0).getValue() == 10){
                if(cards.get(0).visibleImageURL.charAt(14) == cards.get(1).visibleImageURL.charAt(14)){
                    handOptions.add(new Option("split", "Split"));
                }
            }else{
                handOptions.add(new Option("split", "Split"));
            }
        }
    }
}
