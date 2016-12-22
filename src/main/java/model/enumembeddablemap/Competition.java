package model.enumembeddablemap;

import model.WithID;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Competition extends WithID {
    public enum Side {
        RED, BLUE
    }

    @Fetch(FetchMode.SUBSELECT)
    @MapKeyColumn(name = "side")
    @MapKeyEnumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "competition_participants",
        joinColumns = @JoinColumn(name = "competition_id", nullable = false),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"competition_id", "side"})}
    )
    private Map<Side, Participant> participants;

    public Map<Side, Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<Side, Participant> participants) {
        this.participants = participants;
    }
}
