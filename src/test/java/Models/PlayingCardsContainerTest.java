package Models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Drew Hamm on 3/10/16.
 */
public class PlayingCardsContainerTest {

    @Test
    public void testPlayingCards() throws Exception {
        java.util.List<Card> drawPile = new ArrayList<>();
        drawPile.add(new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true));
        drawPile.add(new Card(6, Suit.Hearts, "assets/cards/6Hearts.png", "assets/cards/cardback.jpg", true));
        drawPile.add(new Card(7, Suit.Hearts, "assets/cards/7Hearts.png", "assets/cards/cardback.jpg", true));
        java.util.List<Card> discardPile = new ArrayList<>();
        PlayingCardsContainer cardsContainer = new PlayingCardsContainer(drawPile, discardPile);
        assertEquals(5, cardsContainer.drawPile.get(0).value);
        assertEquals(6, cardsContainer.drawPile.get(1).value);
        assertEquals(7, cardsContainer.drawPile.get(2).value);
    }

    @Test
    public void testConstructor() throws Exception {
        PlayingCardsContainer cardsContainer = new PlayingCardsContainer();
        assertEquals(52, cardsContainer.drawPile.size());
    }

    @Test
    public void testDrawCards() throws Exception {
        PlayingCardsContainer cardsContainer = new PlayingCardsContainer();
        java.util.List<Card> drawnCards = new ArrayList<>();

        //Randomly flip the decks cards
        java.util.List<Boolean> flipCheck = new ArrayList<>();
        for (int i = 0; i < 26; ++i)
            flipCheck.add(true);
        for (int i = 26; i < 52; ++i)
            flipCheck.add(false);
        Collections.shuffle(flipCheck);
        for(Card card : cardsContainer.drawPile){
            if(flipCheck.remove(flipCheck.size()-1)){
                card.flipCard();
            }
        }

        //Test that we can draw all cards from the deck and that they are all face up
        assertEquals(52, cardsContainer.drawPile.size());
        assertEquals(0, drawnCards.size());
        drawnCards.addAll(cardsContainer.drawCards(52));
        assertEquals(0, cardsContainer.drawPile.size());
        assertEquals(52, drawnCards.size());
        for(Card card : drawnCards){
            assertEquals(true, card.getFaceVisible());
        }
        //Test that drawing more cards than are in the draw/discard piles combined returns null
        assertEquals(null, cardsContainer.drawCards(1));

        //Test that drawing cards from an empty drawPile shuffles in the discard pile
        cardsContainer.discardPile.addAll(drawnCards.subList(0, drawnCards.size()));
        drawnCards.clear();
        drawnCards.addAll(cardsContainer.drawCards(52));
        assertEquals(0, cardsContainer.drawPile.size());
        assertEquals(52, drawnCards.size());

        //Test that drawing more cards than available from a non-empty drawPile
        //draws from the available drawPile cards before shuffling in the discard pile and drawing the remaining
        cardsContainer.drawPile.addAll(drawnCards.subList(0, 1));
        cardsContainer.discardPile.addAll(drawnCards.subList(1, 10));
        drawnCards.clear();
        assertEquals(1, cardsContainer.drawPile.size());
        assertEquals(9, cardsContainer.discardPile.size());
        assertEquals(0, drawnCards.size());
        drawnCards.addAll(cardsContainer.drawCards(3));
        assertEquals(7, cardsContainer.drawPile.size());
        assertEquals(0, cardsContainer.discardPile.size());
        assertEquals(3, drawnCards.size());
    }

    @Test
    public void testDiscardCards() throws Exception {
        PlayingCardsContainer cardsContainer = new PlayingCardsContainer();
        java.util.List<Card> Cards = new ArrayList<>();
        Cards.add(new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true));
        Cards.add(new Card(6, Suit.Hearts, "assets/cards/6Hearts.png", "assets/cards/cardback.jpg", true));
        Cards.add(new Card(7, Suit.Hearts, "assets/cards/7Hearts.png", "assets/cards/cardback.jpg", true));
        cardsContainer.discardCards(Cards);
        assertEquals(Cards, cardsContainer.discardPile);
    }
}