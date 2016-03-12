package Models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Drew Hamm on 3/10/16.
 */
public class BlackjackTest {
    @Test
    public void testConstructor() throws Exception{
        int initialBalance = 100;
        int initialAnte = 2;
        Blackjack blackjack = new Blackjack(initialBalance, initialAnte);
        assertEquals(initialAnte, blackjack.ante);
        assertEquals(initialBalance, blackjack.playerBalance);
        assertEquals(52, blackjack.playingCards.drawPile.size());
        Assert.assertNotNull(blackjack.gameOptions);
        Assert.assertNotNull(blackjack.dealerHand);
        assertEquals(1, blackjack.playerHands.size());
        assertEquals(false, blackjack.dealerTurnInProgress);
        assertEquals(false, blackjack.errorState);
    }

    @Test
    public void testConcludeRound() throws Exception{
        int initialBalance = 100;
        int initialAnte = 2;
        Blackjack blackjack = new Blackjack(initialBalance, initialAnte);

        //Test draw when both dealer and player busts
        blackjack.dealerHand.newHand(new Card(10, Suit.Clubs, "", "", true), new Card(9, Suit.Clubs, "", "", true));
        blackjack.dealerHand.cards.add(new Card(8, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).newHand(new Card(7, Suit.Clubs, "", "", true), new Card(7, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).cards.add(new Card(8, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).bet = 2;
        blackjack.concludeRound();
        assertEquals(102, blackjack.playerBalance);
        assertEquals(0, blackjack.playerHands.get(0).bet);
        assertEquals("Draw", blackjack.playerHands.get(0).status);

        //Test draw when both dealer and player hand has same value
        blackjack = new Blackjack(initialBalance, initialAnte);
        blackjack.dealerHand.cards.add(new Card(10, Suit.Clubs, "", "", true));
        blackjack.dealerHand.cards.add(new Card(10, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).cards.add(new Card(10, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).cards.add(new Card(10, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).bet = 2;
        blackjack.concludeRound();
        assertEquals(102, blackjack.playerBalance);
        assertEquals(0, blackjack.playerHands.get(0).bet);
        assertEquals("Draw", blackjack.playerHands.get(0).status);

        //Test lose
        blackjack = new Blackjack(initialBalance, initialAnte);
        blackjack.dealerHand.newHand(new Card(10, Suit.Clubs, "", "", true), new Card(10, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).newHand(new Card(10, Suit.Clubs, "", "", true), new Card(9, Suit.Clubs, "", "", true));
        blackjack.concludeRound();
        assertEquals(100, blackjack.playerBalance);
        assertEquals(0, blackjack.playerHands.get(0).bet);
        assertEquals("Lose", blackjack.playerHands.get(0).status);

        //Test win
        blackjack = new Blackjack(initialBalance, initialAnte);
        blackjack.dealerHand.newHand(new Card(10, Suit.Clubs, "", "", true), new Card(9, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).newHand(new Card(10, Suit.Clubs, "", "", true), new Card(10, Suit.Clubs, "", "", true));
        blackjack.concludeRound();
        assertEquals(104, blackjack.playerBalance);
        assertEquals(0, blackjack.playerHands.get(0).bet);
        assertEquals("Win", blackjack.playerHands.get(0).status);
    }

    @Test
    public void testDoubleDownPlayerHand() throws Exception{
        int initialBalance = 100;
        int initialAnte = 2;
        Blackjack blackjack = new Blackjack(initialBalance, initialAnte);

        //Test double down that results in hand under 21
        blackjack.playerHands.get(0).newHand(new Card(2, Suit.Clubs, "", "", true), new Card(2, Suit.Clubs, "", "", true));
        blackjack.doubleDownPlayerHand(0);
        assertEquals(98, blackjack.playerBalance);
        assertEquals(4, blackjack.playerHands.get(0).bet);
        assertEquals(3, blackjack.playerHands.get(0).cards.size());
        assertTrue(blackjack.playerHands.get(0).handOptions.size() > 0);
        assertEquals(false, blackjack.dealerTurnInProgress);

        //Test double down that results in hand over 21
        blackjack = new Blackjack(initialBalance, initialAnte);
        blackjack.playerHands.get(0).newHand(new Card(10, Suit.Clubs, "", "", true), new Card(9, Suit.Clubs, "", "", true));
        blackjack.playerHands.get(0).cards.add(new Card(2, Suit.Clubs, "", "", true));
        blackjack.doubleDownPlayerHand(0);
        assertEquals(98, blackjack.playerBalance);
        assertEquals(4, blackjack.playerHands.get(0).bet);
        assertEquals(4, blackjack.playerHands.get(0).cards.size());
        assertEquals(0, blackjack.playerHands.get(0).handOptions.size());
        assertEquals(true, blackjack.dealerTurnInProgress);
    }

    @Test
    public void testSplitPlayerHand() throws Exception{
        int initialBalance = 100;
        int initialAnte = 2;
        Blackjack blackjack = new Blackjack(initialBalance, initialAnte);

        //Test split player hand
        blackjack.playerHands.get(0).newHand(new Card(2, Suit.Clubs, "", "", true), new Card(2, Suit.Clubs, "", "", true));
        blackjack.splitPlayerHand(0);
        assertEquals(98, blackjack.playerBalance);
        assertEquals(2, blackjack.playerHands.get(0).bet);
        assertEquals(2, blackjack.playerHands.get(1).bet);
        assertEquals(2, blackjack.playerHands.get(0).cards.size());
        assertEquals(2, blackjack.playerHands.get(1).cards.size());
        assertEquals(2, blackjack.playerHands.get(0).cards.get(0).getValue());
        assertEquals(2, blackjack.playerHands.get(1).cards.get(0).getValue());
        assertEquals(false, blackjack.dealerTurnInProgress);
    }

    @Test
    public void testStayPlayerHand() throws Exception{
        int initialBalance = 100;
        int initialAnte = 2;
        Blackjack blackjack = new Blackjack(initialBalance, initialAnte);

        //Test stay player hand when player only has one hand
        blackjack.playerHands.get(0).newHand(new Card(2, Suit.Clubs, "", "", true), new Card(2, Suit.Clubs, "", "", true));
        blackjack.stayPlayerHand(0);
        assertEquals(100, blackjack.playerBalance);
        assertEquals(2, blackjack.playerHands.get(0).bet);
        assertEquals(2, blackjack.playerHands.get(0).cards.size());
        assertEquals(0, blackjack.playerHands.get(0).handOptions.size());
        assertEquals(true, blackjack.dealerTurnInProgress);

        //Test stay player hand when player has two hands with one ongoing
        blackjack = new Blackjack(initialBalance, initialAnte);
        blackjack.playerHands.get(0).newHand(new Card(2, Suit.Clubs, "", "", true), new Card(2, Suit.Clubs, "", "", true));
        blackjack.playerHands.add(new PlayerHand(new ArrayList<Card>(), ""));
        blackjack.playerHands.get(1).newHand(new Card(3, Suit.Clubs, "", "", true), new Card(3, Suit.Clubs, "", "", true));
        blackjack.stayPlayerHand(0);
        assertEquals(100, blackjack.playerBalance);
        assertEquals(2, blackjack.playerHands.get(0).bet);
        assertEquals(2, blackjack.playerHands.get(0).cards.size());
        assertEquals(0, blackjack.playerHands.get(0).handOptions.size());
        assertEquals(false, blackjack.dealerTurnInProgress);
    }
}