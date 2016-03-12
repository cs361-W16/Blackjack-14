package Models;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by yichen duan on 3/10/16.
 */
public class HandTest {

    @Test
    public void testNewHand() throws Exception {
        Card ace1 = new Card(11, Suit.Diamonds, "front", "back", true);
        Card ace2 = new Card(11, Suit.Clubs, "front", "back", true);
        Card king1 = new Card(10, Suit.Spades, "front", "back", true);
        Card king2 = new Card(10, Suit.Hearts, "front", "back", true);
        Card seven = new Card(7, Suit.Hearts, "front", "back", true);

        //test dealer hand
        DealerHand dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.newHand(ace1, king1);
        assertEquals("", dHand.status);
        assertEquals(2, dHand.cards.size());
        assertEquals(false, dHand.cards.get(0).getFaceVisible());
        assertEquals(true, dHand.cards.get(1).getFaceVisible());
        ace1.flipCard();

        //test player hand with 21
        PlayerHand pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.newHand(ace1, king1);
        assertEquals("", pHand.status);
        assertEquals(2, pHand.cards.size());
        assertEquals(true, pHand.cards.get(0).getFaceVisible());
        assertEquals(true, pHand.cards.get(1).getFaceVisible());
        assertEquals(0, pHand.handOptions.size());

        //test player hand under 21
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.newHand(king1, seven);
        assertEquals("", pHand.status);
        assertEquals(2, pHand.cards.size());
        assertEquals(true, pHand.cards.get(0).getFaceVisible());
        assertEquals(true, pHand.cards.get(1).getFaceVisible());
        assertEquals(3, pHand.handOptions.size());

        //test player hand with split
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.newHand(king1, king2);
        assertEquals("", pHand.status);
        assertEquals(2, pHand.cards.size());
        assertEquals(true, pHand.cards.get(0).getFaceVisible());
        assertEquals(true, pHand.cards.get(1).getFaceVisible());
        assertEquals(4, pHand.handOptions.size());
    }


    @Test
    public void testGetHandValue() throws Exception {
        Card ace1 = new Card(11, Suit.Diamonds, "front", "back", true);
        Card king1 = new Card(10, Suit.Spades, "front", "back", true);
        Card king2 = new Card(10, Suit.Hearts, "front", "back", true);
        Card seven = new Card(7, Suit.Hearts, "front", "back", true);

        //test player hand with 21
        PlayerHand pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(ace1);
        pHand.cards.add(king1);
        assertEquals(21, pHand.getHandValue());

        //test player hand under 21
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(king1);
        pHand.cards.add(king2);
        assertEquals(20, pHand.getHandValue());

        //test player hand over 21
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(king1);
        pHand.cards.add(king2);
        pHand.cards.add(seven);
        assertEquals(27, pHand.getHandValue());

        //test player hand over 21 with reducible aces still over
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(ace1);
        pHand.cards.add(king1);
        pHand.cards.add(king2);
        pHand.cards.add(seven);
        assertEquals(28, pHand.getHandValue());

        //test player hand over 21 with reducible aces now under
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(ace1);
        pHand.cards.add(king1);
        pHand.cards.add(seven);
        assertEquals(18, pHand.getHandValue());

        //test player hand under 21 with reducible aces
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(ace1);
        pHand.cards.add(seven);
        assertEquals(18, pHand.getHandValue());


        //test dealer hand with 21
        DealerHand dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(ace1);
        dHand.cards.add(king1);
        assertEquals(21, dHand.getHandValue());

        //test player hand under 21
        dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(king1);
        dHand.cards.add(king2);
        assertEquals(20, dHand.getHandValue());

        //test player hand over 21
        dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(king1);
        dHand.cards.add(king2);
        dHand.cards.add(seven);
        assertEquals(27, dHand.getHandValue());

        //test player hand over 21 with reducible aces still over
        dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(ace1);
        dHand.cards.add(king1);
        dHand.cards.add(king2);
        dHand.cards.add(seven);
        assertEquals(28, dHand.getHandValue());

        //test player hand over 21 with reducible aces now under
        dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(ace1);
        dHand.cards.add(king1);
        dHand.cards.add(seven);
        assertEquals(18, dHand.getHandValue());

        //test player hand under 21 with reducible aces
        dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(ace1);
        dHand.cards.add(seven);
        assertEquals(18, dHand.getHandValue());
    }

    @Test
    public void testDiscardHand() throws Exception {
        Card ace1 = new Card(11, Suit.Diamonds, "front", "back", true);
        Card ace2 = new Card(11, Suit.Clubs, "front", "back", true);
        Card ace3 = new Card(11, Suit.Spades, "front", "back", true);
        java.util.List<Card> cardsDiscarded = new ArrayList<>();

        //test discardHand for PlayerHand with one card
        PlayerHand pHand = new PlayerHand(new ArrayList<Card>(), "");
        pHand.cards.add(ace1);
        cardsDiscarded.addAll(pHand.discardHand());
        assertEquals(1, cardsDiscarded.size());
        assertEquals(0, pHand.cards.size());

        //test discardHand for PlayerHand with more than one cards
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        cardsDiscarded = new ArrayList<>();
        pHand.cards.add(ace1);
        pHand.cards.add(ace2);
        pHand.cards.add(ace3);
        cardsDiscarded.addAll(pHand.discardHand());
        assertEquals(3, cardsDiscarded.size());
        assertEquals(0, pHand.cards.size());

        //test discardHand for PlayerHand with zero cards
        pHand = new PlayerHand(new ArrayList<Card>(), "");
        cardsDiscarded = new ArrayList<>();
        cardsDiscarded.addAll(pHand.discardHand());
        assertEquals(0, cardsDiscarded.size());
        assertEquals(0, pHand.cards.size());



        //test discardHand for DealerHand with one card
        DealerHand dHand = new DealerHand(new ArrayList<Card>(), "");
        dHand.cards.add(ace1);
        cardsDiscarded.addAll(dHand.discardHand());
        assertEquals(1, cardsDiscarded.size());
        assertEquals(0, dHand.cards.size());

        //test discardHand for PlayerHand with more than one cards
        dHand = new DealerHand(new ArrayList<Card>(), "");
        cardsDiscarded = new ArrayList<>();
        dHand.cards.add(ace1);
        dHand.cards.add(ace2);
        dHand.cards.add(ace3);
        cardsDiscarded.addAll(dHand.discardHand());
        assertEquals(3, cardsDiscarded.size());
        assertEquals(0, pHand.cards.size());

        //test discardHand for PlayerHand with zero cards
        dHand = new DealerHand(new ArrayList<Card>(), "");
        cardsDiscarded = new ArrayList<>();
        cardsDiscarded.addAll(dHand.discardHand());
        assertEquals(0, cardsDiscarded.size());
        assertEquals(0, pHand.cards.size());

    }
}