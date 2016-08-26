package model.embeddabletwice;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EmbeddableTwo {
    @Basic
    private String twoString;

    @ManyToOne
    @JoinColumn(name = "two_parent_id")
    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }


    public String getTwoString() {
        return twoString;
    }

    public void setTwoString(String twoString) {
        this.twoString = twoString;
    }
}
