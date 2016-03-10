package Models;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Option {
    public String action;
    public String label;

    //AJAX POST calls were forcing the requirement of dummy a constructor when mapping from JSON
    public Option(){}

    public Option(String action, String label){
        this.action = action;
        this.label = label;
    }
}