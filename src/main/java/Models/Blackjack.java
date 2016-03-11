package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Drew Hamm on 3/5/2016.
 */
public class Blackjack {
    public java.util.List<Card> deck = new ArrayList<>();

    public PlayingCardsContainer playingCards;
    public java.util.List<Option> gameOptions = new ArrayList<>();
    public Boolean dealerTurnInProgress;
    public int playerBalance;
    public Hand dealerHand;
    public java.util.List<Hand> playerHands = new ArrayList<>();
    public boolean errorState;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Blackjack(){}

    public void buildDeck() {
        for(int i = 2; i < 15; i++){
            deck.add(new Card("assets/cards/" + i + "Clubs.png","assets/cards/cardback.jpg",true,i,Suit.Clubs));
            deck.add(new Card("assets/cards/" + i + "Hearts.png","assets/cards/cardback.jpg",true,i,Suit.Hearts));
            deck.add(new Card("assets/cards/" + i + "Diamonds.png","assets/cards/cardback.jpg",true,i,Suit.Diamonds));
            deck.add(new Card("assets/cards/" + i + "Spades.png","assets/cards/cardback.jpg",true,i,Suit.Spades));
        }
    }

    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    public void deal2() {
        for(int i = 0; i < 2; i++){
            deck.get(deck.size()-1;
            deck.remove(deck.size()-1);
        }
    }
}