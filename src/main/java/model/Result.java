package model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Result extends WithID {
    @ManyToOne(optional = false)
    @JoinColumn(name = "gameId")
    private Game game;

    @OneToMany
    @JoinColumn(name = "resultId")
    private List<Score> scores;

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
