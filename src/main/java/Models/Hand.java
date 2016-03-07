package Models;

import java.util.ArrayList;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Hand {
    public java.util.List<Card> cards = new ArrayList<>();
    public String status;
    public java.util.List<Option> handOptions = new ArrayList<>();
    public int bet;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Hand(){}
}
