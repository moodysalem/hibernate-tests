package model.embeddabletwice;

import javax.persistence.*;

@Embeddable
public class EmbeddableOne {
    @Basic
    private String oneString;

    @Embedded
    private EmbeddableTwo two;

    public String getOneString() {
        return oneString;
    }

    public void setOneString(String oneString) {
        this.oneString = oneString;
    }

    public EmbeddableTwo getTwo() {
        return two;
    }

    public void setTwo(EmbeddableTwo two) {
        this.two = two;
    }
}
