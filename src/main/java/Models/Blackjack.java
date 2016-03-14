package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Drew Hamm on 3/5/2016.
 */
public class Blackjack {
    public int ante;
    public PlayingCardsContainer playingCards;
    public java.util.List<Option> gameOptions;
    public Boolean dealerTurnInProgress;
    public int playerBalance;
    public DealerHand dealerHand;
    public java.util.List<PlayerHand> playerHands;
    public boolean errorState;

    @JsonCreator
    public Blackjack(@JsonProperty("ante") int ante,
                     @JsonProperty("playingCards") PlayingCardsContainer playingCards,
                     @JsonProperty("gameOptions") java.util.List<Option> gameOptions,
                     @JsonProperty("dealerTurnInProgress") Boolean dealerTurnInProgress,
                     @JsonProperty("playerBalance") int playerBalance,
                     @JsonProperty("dealerHand") DealerHand dealerHand,
                     @JsonProperty("playerHands") java.util.List<PlayerHand> playerHands,
                     @JsonProperty("errorState") boolean errorState) {
        this.ante = ante;
        this.playingCards = playingCards;
        this.gameOptions = gameOptions;
        this.dealerTurnInProgress = dealerTurnInProgress;
        this.playerBalance = playerBalance;
        this.dealerHand = dealerHand;
        this.playerHands = playerHands;
        this.errorState = errorState;
    }

    public Blackjack(int playerBalance, int ante){
        this.ante = ante;
        playingCards = new PlayingCardsContainer();
        gameOptions = new ArrayList<>();
        gameOptions.add(new Option("newRound", "Deal"));
        dealerTurnInProgress = false;
        this.playerBalance = playerBalance;
        dealerHand = new DealerHand(new ArrayList<Card>(), "");
        playerHands = new ArrayList<>();
        playerHands.add(new PlayerHand(new ArrayList<Card>(), ""));
        errorState = false;
    }

    //New round
    public void newRound(){
        //Reset any possible error states
        errorState = false;

        //Check that player is short of funds before enabling the error state
        if(playerBalance < ante){
            errorState = true;
        } else{
            //Discard all hands
            playingCards.discardCards(dealerHand.discardHand());
            for(PlayerHand hand : playerHands){
                playingCards.discardCards(hand.discardHand());
            }
            //Deal the dealer a new hand
            dealerHand.newHand(playingCards.drawCards(1).get(0), playingCards.drawCards(1).get(0));

            //Deal the player a new hand
            playerHands = new ArrayList<>();
            playerHands.add(new PlayerHand(new ArrayList<Card>(), ""));
            playerHands.get(0).newHand(playingCards.drawCards(1).get(0), playingCards.drawCards(1).get(0));

            //Start dealers turn if player has 21
            if(playerHands.get(0).getHandValue() == 21){
                dealerTurnInProgress = true;
            }

            //Update player balance
            playerBalance -= ante;
        }
    }

    //Dealer Action
    public void dealerAction(){
        //Reset any possible error states
        errorState = false;

        //Flip bottom card if not already visible
        if(!dealerHand.cards.get(0).faceVisible){
            dealerHand.cards.get(0).flipCard();
        } else {
            //Decide to hit or stay
            if (dealerHand.getHandValue() < 17) {
                //hit
                dealerHand.cards.add(playingCards.drawCards(1).get(0));
            } else {
                //stay
                dealerTurnInProgress = false;
                concludeRound();
            }
        }
    }

    //Conclude Round
    public void concludeRound(){
        //Reset any possible error states
        errorState = false;

        int dealerValue = dealerHand.getHandValue();

        //Determine betting results and set resolution statuses
        for(PlayerHand hand : playerHands){
            //Draw
            if((hand.getHandValue() > 21 && dealerValue > 21) || (hand.getHandValue() == dealerValue)){
                playerBalance += hand.bet;
                hand.bet = 0;
                hand.status = "Draw";

            //Win
            }else if(dealerValue > 21 || hand.getHandValue() > dealerValue && hand.getHandValue() <= 21){
                playerBalance += (2 * hand.bet);
                hand.bet = 0;
                hand.status = "Win";

            //Lose
            }else {
                hand.bet = 0;
                hand.status = "Lose";
            }
        }
    }

    //Doubledown for player hand
    public void doubleDownPlayerHand(int handIndex){
        //Reset any possible error states
        errorState = false;

        PlayerHand activeHand = playerHands.get(handIndex);

        //Update player balance
        playerBalance -= activeHand.bet;

        //Double the bet and draw a new card
        activeHand.bet += activeHand.bet;
        activeHand.cards.add(playingCards.drawCards(1).get(0));

        //Stay the hand because that is the rules of the game.
        stayPlayerHand(handIndex);
    }

    //Split for player hand
    public void splitPlayerHand(int handIndex){
        //Reset any possible error states
        errorState = false;

        //Update balance
        playerBalance -= ante;

        //Get the original hand
        PlayerHand originalHand = playerHands.get(handIndex);

        //Create the first hand
        PlayerHand hand1 = new PlayerHand(new ArrayList<Card>(), "");
        hand1.newHand(originalHand.cards.get(0), playingCards.drawCards(1).get(0));
        hand1.bet = originalHand.bet;

        //Create the second hand
        PlayerHand hand2 = new PlayerHand(new ArrayList<Card>(), "");
        hand2.newHand(originalHand.cards.get(1), playingCards.drawCards(1).get(0));

        //Remove the old hand and add the new hands
        playerHands.remove(handIndex);
        playerHands.add(hand1);
        playerHands.add(hand2);
    }

    //Stay for player hand
    public void stayPlayerHand(int handIndex){
        //Reset any possible error states
        errorState = false;

        //Remove the options for the designated hand
        playerHands.get(handIndex).handOptions.clear();

        //Determine if the dealer should start their turn
        Boolean noOptionsFound = true;
        for(PlayerHand hand : playerHands){
            if(hand.handOptions.size() >= 1){
                noOptionsFound = false;
            }
        }

        if(noOptionsFound){
            dealerTurnInProgress = true;
        }
    }

    //Hit for player hand
    public void hitPlayerHand(int handIndex){
        //Reset any possible error states
        errorState = false;

        //Add card to hand
        playerHands.get(handIndex).cards.add(playingCards.drawCards(1).get(0));

        //Reset the options for the designated hand
        playerHands.get(handIndex).setHandOptions();

        //Determine if the dealer should start their turn
        Boolean noOptionsFound = true;
        for(PlayerHand hand : playerHands){
            if(hand.handOptions.size() >= 1){
                noOptionsFound = false;
            }
        }

        if(noOptionsFound){
            dealerTurnInProgress = true;
        }
    }
}