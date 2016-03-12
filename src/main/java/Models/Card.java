package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Card {
    public String visableImageURL;
    public String hiddenImageURL;
    public boolean faceVisable;
    public final int value;
    public final Suit suit;

    @JsonCreator
    public Card(@JsonProperty("visableImageURL") String visableImageURL,
                @JsonProperty("hiddenImageURL") String hiddenImageURL,
                @JsonProperty("faceVisable") boolean faceVisable) {
        this.value = 0;
        this.suit = null;
        this.visableImageURL = visableImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisable = faceVisable;
    }

    @JsonCreator
    public Card(@JsonProperty("value") int value, @JsonProperty("suit") Suit suit, @JsonProperty("visableImageURL") String visableImageURL,
                @JsonProperty("hiddenImageURL") String hiddenImageURL, @JsonProperty("faceVisable") boolean faceVisable) {
        this.value = value;
        this.suit = suit;
        this.visableImageURL = visableImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisable = faceVisable;
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

    public boolean getFace(){
        return this.faceVisable;
    }

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    //public Card(){}

}
