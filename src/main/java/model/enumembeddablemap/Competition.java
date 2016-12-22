package model.enumembeddablemap;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Competition {
    public enum Side {
        RED, BLUE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
