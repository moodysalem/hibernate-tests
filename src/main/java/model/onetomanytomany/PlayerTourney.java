package model.onetomanytomany;

import model.WithID;
import model.hhh10741.Team;

import javax.persistence.*;
import java.util.List;

@Entity
public class PlayerTourney extends WithID {
    @ManyToOne(optional = false)
    @JoinColumn(name = "tourneyId", updatable = false)
    private Tourney tourney;

    @ManyToOne(optional = false)
    @JoinColumn(name = "playerId", updatable = false)
    private Player player;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "PlayerTourney_Team",
        inverseJoinColumns = @JoinColumn(name = "teamId", referencedColumnName = "id"),
        joinColumns = @JoinColumn(name = "playerTourneyId", referencedColumnName = "id")
    )
    private List<Team> teams;

    public Tourney getTourney() {
        return tourney;
    }

    public void setTourney(Tourney tourney) {
        this.tourney = tourney;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
