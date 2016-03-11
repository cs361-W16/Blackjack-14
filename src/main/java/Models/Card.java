package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Card {
    public String visibleImageURL;
    public String hiddenImageURL;
    public boolean faceVisible;
    public final int value;
    public final Suit suit;

    @JsonCreator
    public Card(@JsonProperty("visibleImageURL") String visibleImageURL,
                @JsonProperty("hiddenImageURL") String hiddenImageURL,
                @JsonProperty("faceVisible") boolean faceVisible) {
        this.value = 0;
        this.suit = null;
        this.visibleImageURL = visibleImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisible = faceVisible;
    }

    @JsonCreator
    public Card(@JsonProperty("value") int value, @JsonProperty("suit") Suit suit, @JsonProperty("visibleImageURL") String visibleImageURL,
                @JsonProperty("hiddenImageURL") String hiddenImageURL, @JsonProperty("faceVisible") boolean faceVisible) {
        this.value = value;
        this.suit = suit;
        this.visibleImageURL = visibleImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisible = faceVisible;
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

    public boolean getFaceVisible(){
        return this.faceVisible;
    }

    public void flipCard(){
        String tempURL = visibleImageURL;
        visibleImageURL = hiddenImageURL;
        hiddenImageURL = tempURL;
        if(faceVisible){
            faceVisible = false;
        }else{
            faceVisible = true;
        }
    }
}
