package model.onetomanytomany;

import model.WithID;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Tourney extends WithID {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
