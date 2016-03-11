package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Drew Hamm on 3/6/2016.
 */
public class Option {
    public String action;
    public String label;

    @JsonCreator
    public Option(@JsonProperty("action") String action,
                @JsonProperty("label") String label) {
        this.action = action;
        this.label = label;
    }
}