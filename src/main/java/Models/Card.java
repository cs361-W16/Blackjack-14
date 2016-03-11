package Models;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Card {
    public String visableImageURL;
    public String hiddenImageURL;
    public boolean faceVisable;
    public final int value;
    public final Suit suit;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Card(){}

    public Card(String visableImageURL, String hiddenImageURL, boolean faceVisable, int value, Suit suit){
        this.visableImageURL = visableImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisable = faceVisable;
        this.value = value;
        this.suit = suit;
    }

    public void setFaceVisable(boolean visibility){
        this.faceVisable = visibility;
    }

    public String getVIURL(){
        return visibleImageURL;
    }

    public String getHIURL(){
        return hiddenImageURL;
    }

    public boolean getVisibility(){
        return faceVisable;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return this.value + this.suit.toString();
    }
}