package Models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yichen duan on 3/10/16.
 */
public class CardTest {

    @Test
    public void testCard() throws Exception {
        Card c = new Card("assets/cards/2Spades.png", "assets/cards/cardback.png", true);
        assertEquals(c.faceVisible, true);
        assertEquals(c.hiddenImageURL, "assets/cards/cardback.png");
        assertEquals(c.visibleImageURL, "assets/cards/2Spades.png");
    }

    @Test
    public void testGetSuit() throws Exception {
        Card c = new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true);
        assertEquals(Suit.Hearts, c.getSuit());
    }

    @Test
    public void testGetValue() throws Exception {
        Card c = new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true);
        assertEquals(5, c.getValue());
    }

    @Test
    public void testToString() throws Exception {
        Card c = new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true);
        assertEquals("5Hearts", c.toString());
    }

    @Test
    public void testGetFace() throws Exception {
        Card c = new Card(5, Suit.Hearts, "assets/cards/5Hearts.png", "assets/cards/cardback.jpg", true);
        assertEquals(true, c.getFaceVisible());
    }

    @Test
    public void testFlipCard() throws Exception {
        String sideA = "assets/cards/5Hearts.png";
        String sideB = "assets/cards/cardback.jpg";
        Card c = new Card(5, Suit.Hearts, sideA, sideB, true);

        assertEquals(true, c.faceVisible);
        assertEquals(sideA, c.visibleImageURL);
        assertEquals(sideB, c.hiddenImageURL);
        c.flipCard();
        assertEquals(false, c.faceVisible);
        assertEquals(sideB, c.visibleImageURL);
        assertEquals(sideA, c.hiddenImageURL);
    }
}