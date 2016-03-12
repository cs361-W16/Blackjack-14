package Models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yichen duan on 3/10/16.
 */
public class CardTest {

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
        assertEquals(true, c.getFace());
    }
}