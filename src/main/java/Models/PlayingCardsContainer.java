package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class PlayingCardsContainer {
    public java.util.List<Card> drawPile = new ArrayList<>();
    public java.util.List<Card> discardPile = new ArrayList<>();

    @JsonCreator
    public PlayingCardsContainer(@JsonProperty("drawPile") java.util.List<Card> drawPile,
                                 @JsonProperty("discardPile") java.util.List<Card> discardPile) {
        this.drawPile = drawPile;
        this.discardPile = discardPile;
    }

    //Initialize
    public PlayingCardsContainer(){
        for(int i = 2; i < 15; i++){
            switch (i){
                case 10:
                case 11:
                case 12:
                case 13:
                    drawPile.add(new Card(10,Suit.Clubs, "assets/cards/" + i + Suit.Clubs + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(10,Suit.Hearts, "assets/cards/" + i + Suit.Hearts + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(10,Suit.Diamonds, "assets/cards/" + i + Suit.Diamonds + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(10,Suit.Spades, "assets/cards/" + i + Suit.Spades + ".png", "assets/cards/cardback.jpg", true));
                    break;
                case 14:
                    drawPile.add(new Card(11,Suit.Clubs, "assets/cards/" + i + Suit.Clubs + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(11,Suit.Hearts, "assets/cards/" + i + Suit.Hearts + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(11,Suit.Diamonds, "assets/cards/" + i + Suit.Diamonds + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(11,Suit.Spades, "assets/cards/" + i + Suit.Spades + ".png", "assets/cards/cardback.jpg", true));
                    break;
                default:
                    drawPile.add(new Card(i,Suit.Clubs, "assets/cards/" + i + Suit.Clubs + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(i,Suit.Hearts, "assets/cards/" + i + Suit.Hearts + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(i,Suit.Diamonds, "assets/cards/" + i + Suit.Diamonds + ".png", "assets/cards/cardback.jpg", true));
                    drawPile.add(new Card(i,Suit.Spades, "assets/cards/" + i + Suit.Spades + ".png", "assets/cards/cardback.jpg", true));
                    break;
            }
        }
        shuffle();
    }

    //Draw cards
    //all cards should be returned face up
    public java.util.List<Card> drawCards(int amount){
        if(amount > drawPile.size() + discardPile.size()) return null;

        java.util.List<Card> drawnCards = new ArrayList<>();

        for(int i = 0; i < amount; i++){
            if(drawPile.size() == 0){
                drawPile.addAll(discardPile.subList(0, discardPile.size()));
                discardPile = new ArrayList<>();;
                shuffle();
            }
            drawnCards.add(drawPile.remove(drawPile.size() - 1));
        }

        for(Card card : drawnCards){
            if(card.getFaceVisible() == false){
                card.flipCard();
            }
        }
        return drawnCards;
    }

    //Discard cards
    public void discardCards(java.util.List<Card> cards){
        discardPile.addAll(cards);
    }

    //Shuffle
    private void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(drawPile, new Random(seed));
    }
}