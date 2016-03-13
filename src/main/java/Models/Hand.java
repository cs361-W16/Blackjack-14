package Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public abstract class Hand {
    public java.util.List<Card> cards = new ArrayList<>();
    public String status;

    public java.util.List<Card> discardHand(){
        java.util.List<Card> cardsToDiscard = new ArrayList<>();
        cardsToDiscard = cards.subList(0, cards.size());
        cards = new ArrayList<>();
        return cardsToDiscard;
    }

    @JsonIgnore
    public int getHandValue(){
        int handValue = 0;
        java.util.List<Integer> values = new ArrayList<>();

        //get card values
        for(Card card : cards){
            values.add(card.getValue());
        }

        //find the hand total
        for(Integer value : values){
            handValue += value;
        }

        //sort card values
        Collections.sort(values);

        //if hand > 21 try to reduces aces
        while(values.get(values.size()-1) == 11 && handValue > 21){
            values.remove(values.size()-1);
            handValue-=10;
            Collections.sort(values);
        }

        return handValue;
    }

    //Template Method Pattern
    public void newHand(Card bottomCard, Card topCard){
        addBottomCard(bottomCard);
        addTopCard(topCard);
        resetHand();
    }

    protected abstract void resetHand();
    protected abstract void addBottomCard(Card card);
    protected void addTopCard(Card card){
        cards.add(card);
    }
}