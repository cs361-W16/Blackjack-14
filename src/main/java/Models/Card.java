package Models;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Card {
    public String visableImageURL;
    public String hiddenImageURL;
    public boolean faceVisable;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Card(){}

    public Card(String visableImageURL, String hiddenImageURL, boolean faceVisable){
        this.visableImageURL = visableImageURL;
        this.hiddenImageURL = hiddenImageURL;
        this.faceVisable = faceVisable;
    }
}