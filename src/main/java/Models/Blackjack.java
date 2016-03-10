package Models;

import java.util.ArrayList;

/**
 * Created by Drew Hamm on 3/5/2016.
 */
public class Blackjack {
    public PlayingCardsContainer playingCards;
    public java.util.List<Option> gameOptions = new ArrayList<>();
    public Boolean dealerTurnInProgress;
    public int playerBalance;
    public Hand dealerHand;
    public java.util.List<Hand> playerHands = new ArrayList<>();
    public boolean errorState;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Blackjack(){}
}