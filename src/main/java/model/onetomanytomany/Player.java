package model.onetomanytomany;

import model.WithID;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player extends WithID {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PlayerTourney> tourneys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerTourney> getTourneys() {
        return tourneys;
    }

    public void setTourneys(List<PlayerTourney> tourneys) {
        this.tourneys = tourneys;
        if (this.tourneys != null) {
            for (PlayerTourney pt : this.tourneys) {
                pt.setPlayer(this);
            }
        }
    }
}
