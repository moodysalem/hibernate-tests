package model;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Game extends WithID {
    @ManyToOne
    @JoinColumn(name = "homeTeamId")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "awayTeamId")
    private Team awayTeam;

    @OneToMany(mappedBy = "game")
    private List<Result> results;

    @ManyToOne
    @JoinFormula("(SELECT _s.id FROM Score _s JOIN Result _r ON _s.resultId = _r.id WHERE _s.teamId = homeTeamId AND _r.gameId = id)")
    private Score homeScore;

    @ManyToOne
    @JoinFormula("(SELECT _s.id FROM Score _s JOIN Result _r ON _s.resultId = _r.id WHERE _s.teamId = awayTeamId AND _r.gameId = id)")
    private Score awayScore;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

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
