package model;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Game extends WithID {
    @ManyToOne
    @JoinColumn(name = "homeTeamId")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "awayTeamId")
    private Team awayTeam;

    @ManyToOne
    @JoinFormula("(SELECT _s.id FROM Score _s WHERE _s.teamId = homeTeamId AND _s.gameId = id)")
    private Score homeScore;

    @ManyToOne
    @JoinFormula("(SELECT _s.id FROM Score _s WHERE _s.teamId = awayTeamId AND _s.gameId = id)")
    private Score awayScore;

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Score getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Score homeScore) {
        this.homeScore = homeScore;
    }

    public Score getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Score awayScore) {
        this.awayScore = awayScore;
    }
}
