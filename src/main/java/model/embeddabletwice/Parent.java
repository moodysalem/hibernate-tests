package model.embeddabletwice;

import javax.persistence.*;
import java.util.Set;

//@Entity
public class Parent {
    @Id
    private Integer id;

    @ElementCollection
    private Set<EmbeddableOne> one;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<EmbeddableOne> getOne() {
        return one;
    }

    public void setOne(Set<EmbeddableOne> one) {
        this.one = one;
    }
}
