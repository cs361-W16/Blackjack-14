package Models;

import java.util.ArrayList;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class PlayingCardsContainer {
    public java.util.List<Card> drawPile = new ArrayList<>();
    public java.util.List<Card> discardPile = new ArrayList<>();

    //AJAX calls require an empty constructor to create an object from json
    public PlayingCardsContainer(){}
}